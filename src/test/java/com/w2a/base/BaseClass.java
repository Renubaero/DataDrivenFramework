package com.w2a.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static FileInputStream fis;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static WebDriver driver;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\excel.xlsx");
	public static WebDriverWait wait;
	public static ExtentReports repo = ExtentManager.getInstance();
	public static ExtentTest test;
	static WebElement dropdown;
	public static String browser;

	@BeforeSuite
	public void setUp() throws IOException {

		if (driver == null) {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\config.properties");
			config.load(fis);
			log.debug("Config File Loaded");
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			OR.load(fis);
			log.debug("OR File Loaded");
		}
		if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {

			browser = System.getenv("browser");
		} else {
			browser = config.getProperty("browser");			
		}
		config.setProperty("browser", browser);
		
		if (config.getProperty("browser").equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (config.getProperty("browser").equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (config.getProperty("browser").equals("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}
		driver.get(config.getProperty("url"));
		log.debug("Get Url Link");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public void click(String locator) {
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();

		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();

		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on " + locator);

	}

	public void type(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);

		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);

		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);

		}
		test.log(LogStatus.INFO, "Typing :" + locator + "value as " + value);

	}

	public static void verifyEquals(String expected, String actual) throws IOException {

		try {
			Assert.assertEquals(actual, expected);
		} catch (Throwable e) {
			TestUtil.CaptureScreenshot();
			test.log(LogStatus.FAIL, e.getMessage() + " Assert Equals - FAIL");
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotPath + TestUtil.screenshotName));
			repo.endTest(test);
			repo.flush();
			Reporter.log("<br>Verification Failure : " + e.getMessage() + "</br>");
			Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.screenshotPath + TestUtil.screenshotName
					+ "\">Click here to see screenshot</a>");
			Reporter.log("<br>");
			Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.screenshotPath + TestUtil.screenshotName
					+ "\"><img src=\"" + TestUtil.screenshotPath + TestUtil.screenshotName
					+ "\" height=200 width=200></img></a>");
		}

	}

	public static void select(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
	}

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
		}
		log.debug("Browser Quit !!!");

	}

}

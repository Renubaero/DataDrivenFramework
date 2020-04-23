package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.w2a.base.BaseClass;
import com.w2a.utilities.TestUtil;

public class AddCustomerTest extends BaseClass {

	@Test(dataProviderClass=TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(Hashtable<String,String> data) {

//		driver.findElement(By.cssSelector(OR.getProperty("addCustBtn_CSS"))).click();
//		driver.findElement(By.cssSelector(OR.getProperty("firstName_CSS"))).sendKeys(firstName);
//		driver.findElement(By.cssSelector(OR.getProperty("lastName_CSS"))).sendKeys(lastName);
//		driver.findElement(By.cssSelector(OR.getProperty("postalCode"))).sendKeys(postalCode);
//		driver.findElement(By.cssSelector(OR.getProperty("submitCustomerBtn"))).click();
		if(!data.get("runmode").equals("Y")) {
			throw new SkipException("Skip the test data as runmode is 'No' ");
		}
		click("addCustBtn_CSS");
		type("firstName_CSS", data.get("firstname"));
		type("lastName_CSS", data.get("lastname"));
		type("postalCode_CSS", data.get("postalcode"));
		click("submitCustomerBtn_CSS");
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		alert.accept();
	}

	

}

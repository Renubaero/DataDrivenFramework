package com.w2a.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.BaseClass;
import com.w2a.utilities.MonitoringMail;
import com.w2a.utilities.TestConfig;
import com.w2a.utilities.TestUtil;

public class CustomListeners extends BaseClass implements ITestListener, ISuiteListener {
	String messageBody;
	
	public void onTestFailure(ITestResult result) {

		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.CaptureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL, result.getThrowable() + " - FAIL");
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotPath + TestUtil.screenshotName));
		Reporter.log("<br>");
		Reporter.log("Click here to see ");
		// Reporter.log(""+System.getProperty("user.dir")+"\\target\\surefire-reports\\screenshot\\"+TestUtil.screenshotName+"");
		Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.screenshotPath + TestUtil.screenshotName+ "\">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log(
				"<a target=\"_blank\" href=\"" + TestUtil.screenshotPath + TestUtil.screenshotName + "\"><img src=\""
						+ TestUtil.screenshotPath + TestUtil.screenshotName + "\" height=200 width=200></img></a>");
		repo.endTest(test);
		repo.flush();
	}

	public void onTestSuccess(ITestResult result) {
		test.log(LogStatus.PASS, result.getName().toUpperCase() + " - PASS");
		repo.endTest(test);
		repo.flush();
	}

	public void onTestStart(ITestResult result) {
		test = repo.startTest(result.getName().toUpperCase());
	}
	public void onFinish(ISuite suite) {
		
		MonitoringMail mail = new MonitoringMail();
		try {
			messageBody = "http://"+InetAddress.getLocalHost().getHostAddress()+":8080/job/DataDrivenFramework/ExtentReports/extentReport.html";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}

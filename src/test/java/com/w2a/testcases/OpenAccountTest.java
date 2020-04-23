package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.w2a.base.BaseClass;
import com.w2a.utilities.TestUtil;

public class OpenAccountTest extends BaseClass{
	
	@Test(dataProviderClass=TestUtil.class, dataProvider="dp")
	public void openAccountTest(Hashtable<String,String> data ) throws InterruptedException {
		
		click("openAccountBtn_CSS");		
		select("selectCustId_CSS", data.get("customer"));
		Thread.sleep(2000);
		select("selectCurrency_XPATH", data.get("currency"));
		click("processBtn_XPATH");
		Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		
	}

}

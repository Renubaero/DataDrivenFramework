package com.w2a.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.base.BaseClass;

public class BankManagerLoginTest extends BaseClass{
	
	@Test
	
	public void bankManagerLoginTest() throws InterruptedException, IOException  {
		log.debug("Bank Manger Login");
		//verifyEquals("renub", "reshmi");
		//driver.findElement(By.cssSelector(OR.getProperty("bnkLoginBtn_CSS"))).click();
		click("bnkLoginBtn_CSS");
		Thread.sleep(3000);
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Alert Message Mismatch");
		log.debug("Test Successful");
		//Assert.fail("Intentional Fail");
		
	}

}

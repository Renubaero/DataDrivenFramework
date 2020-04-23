package com.w2a.utilities;

import java.io.File;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	
	
	public static ExtentReports extent;
	public static String extentReportsName;
	public static ExtentReports getInstance() {
		//Date d= new Date();
		//extentReportsName = d.toString().replace(":", "_").replace(" ", "_")+".html";
		if(extent==null) {
			extent =new ExtentReports(System.getProperty("user.dir")+"\\target\\surefire-reports\\ExtentReports\\extentReport.html",true,DisplayOrder.OLDEST_FIRST);
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\extentConfig\\ReportsConfig.xml"));
		}
		return extent;
		
	}
	

}

package com.w2a.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.w2a.base.BaseClass;

public class TestUtil extends BaseClass{
	
	public static String screenshotPath;
	public static String screenshotName;
	
	public static void CaptureScreenshot() throws IOException {
		Date d= new Date();
		//System.out.println(d.toString().replace(":", "_").replace(" ", "_"));
		screenshotName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";		
		screenshotPath = System.getProperty("user.dir")+"\\target\\surefire-reports\\screenshot\\";
		File srcfile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcfile, new File(screenshotPath+screenshotName));
	}
	@DataProvider(name="dp")
	public Object[][] getData(Method m) {

		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		System.out.println(rows);
		System.out.println(cols);
		Object[][] data = new Object[rows - 1][1];
		Hashtable<String,String> table= null;
		
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			table = new Hashtable<String,String>();
			for (int colNum = 0; colNum < cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				
				data[rowNum - 2][0] = table;

			}

		}
		return data;
	}

}

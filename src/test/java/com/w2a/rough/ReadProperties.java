package com.w2a.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {

	public static FileInputStream fis;
	public static void main(String[] args) throws IOException { 
		
		Properties conf = new Properties();
		Properties OR = new Properties();
		fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\config.properties");
		conf.load(fis);
		fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");		
		OR.load(fis);
		System.out.println(conf.getProperty("browser"));
		System.out.println(OR.getProperty("bnkLoginBtn_CSS"));
	}
}

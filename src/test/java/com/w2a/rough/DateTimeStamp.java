package com.w2a.rough;

import java.util.Date;

public class DateTimeStamp {
	
	public static void main(String[] args) {
		Date d= new Date();
		System.out.println(d.toString().replace(":", "_").replace(" ", "_"));
	}

}

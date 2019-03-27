package com.excilys.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class TimestampConverter {
	public static String formatToString(Timestamp timestamp, String format) {
		if(timestamp == null) return null;
		
		String timeToString = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		
		Date date = new Date(timestamp.getTime());
		timeToString = formatter.format(formatter.parse(date.toString()));
		
		return timeToString;
	}
	
	public static Timestamp valueOf(String timestamp) {
		if(timestamp.equals("")) return null;
		return Timestamp.valueOf(timestamp);
	}
}

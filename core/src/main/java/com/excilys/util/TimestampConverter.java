package com.excilys.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import com.excilys.exception.TimestampException;

public class TimestampConverter {
	public static String formatToString(Timestamp timestamp, String format) {
		if(timestamp == null) return "";
	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);		
		Date date = new Date(timestamp.getTime());
		
		return formatter.format(formatter.parse(date.toString()));
	}
	
	public static Timestamp valueOf(String timestamp) throws TimestampException {
		if(timestamp.equals("")) return null;
		
		if(!timestamp.matches("^\\d{4}-\\d{2}-\\d{2}$"))
			throw new TimestampException("Impossible to parse string : " + timestamp);
		
		timestamp += " 00:00:00";
		
		return Timestamp.valueOf(timestamp);
	}
}

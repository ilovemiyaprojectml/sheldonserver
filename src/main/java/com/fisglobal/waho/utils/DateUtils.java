package com.fisglobal.waho.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	public DateUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static final String DATE_FORMAT_DISPLAY = "MM-dd-yyyy";
	public static final String DATE_FORMAT_DATABASE = "yyyy-MM-dd";
	
	
	public static LocalDateTime convertToLocalDateTime(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //2018-10-25
        LocalDateTime formatDateTime = LocalDateTime.parse(dateStr, formatter);
        return formatDateTime;
	}

}

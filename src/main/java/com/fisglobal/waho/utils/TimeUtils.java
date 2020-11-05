package com.fisglobal.waho.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TimeUtils {
	static Logger log = LoggerFactory.getLogger(TimeUtils.class);
	
	
	public static LocalTime hourlyRandomTime(int range) {
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalTime today = LocalTime.now(ZoneId.of("GMT+08:00"));
		String todayStr = today.getHour() + ":" + today.getMinute();
		log.debug("hourlyRandomTime() - today (GMT+08:00) : "+todayStr);
		log.debug("hourlyRandomTime() - today (GMT+08:00) obj : "+today);
		
		LocalTime today2 = localDateTime.toLocalTime();
		todayStr = today2.getHour() + ":" + today2.getMinute();
		log.debug("hourlyRandomTime() - today2 (LocalDateTime) : "+todayStr);
		log.debug("hourlyRandomTime() - today2 (LocalDateTime) obj: "+today2);
		
		LocalTime currentTime = LocalTime.of(today.getHour(), today.getMinute());
		int addedMinutes = (int)(Math.random() * range) + 1; 
		LocalTime newRandHr = currentTime.plusMinutes(addedMinutes);
		
		return newRandHr;
		
	}

}

package com.fisglobal.waho;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WahoServerApplication {
	static Logger log = LoggerFactory.getLogger(WahoServerApplication.class);
	
	public static void main(String[] args) {
		log.debug("WahoServerApplication() - Start up");
		SpringApplication.run(WahoServerApplication.class, args);
		
	}
	
	
	
	
}

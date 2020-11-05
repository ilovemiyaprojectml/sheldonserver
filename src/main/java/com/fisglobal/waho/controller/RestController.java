package com.fisglobal.waho.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fisglobal.waho.WahoServerApplication;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.services.UserService;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	static Logger log = LoggerFactory.getLogger(RestController.class);
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/saveuser")
	public String saveUser(@RequestParam String userEid, @RequestParam String firstName
			, @RequestParam String lastName, @RequestParam int userParentId
			, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime effectiveStartDate
			, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime effectiveEndDate
			, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime  dateCreated
			, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime  dateLastUpdated
			, @RequestParam int createdBy, @RequestParam int lastUpdatedBy
			, @RequestParam String isActive
			, @RequestParam String email) {
		log.debug("saveUser() - Start");
		
		User user = new User(userEid, firstName, lastName, email, userParentId, effectiveStartDate
				, effectiveEndDate, dateCreated, dateLastUpdated, createdBy, lastUpdatedBy
				, isActive);
		
		userService.saveUser(user);
		
		log.debug("saveUser() - End");
		return "User saved!";
	}

}

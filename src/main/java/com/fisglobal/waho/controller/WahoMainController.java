package com.fisglobal.waho.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisglobal.waho.model.User;
import com.fisglobal.waho.services.UserService;
 
@RestController
public class WahoMainController {
	static Logger log = LoggerFactory.getLogger(WahoMainController.class);
	
	@Autowired
	private UserService userService;
 
	@RequestMapping("/api/hi")
	public String hi() {
		return "Hello world! >>> Message from <a href='https://grokonez.com' target='_blank'>grokonez.com</a>";
	}
	
	@RequestMapping("/api/users")
	public String getUsers() {
		log.debug("getUsers()");
		return "{\r\n" + 
				"id: \"1\",\r\n" + 
				"employee_name: \"Live DSP Patel\",\r\n" + 
				"employee_salary: \"44582565\",\r\n" + 
				"employee_age: \"23\",\r\n" + 
				"profile_image: \"\"\r\n" + 
				"}";
	}
	
//	@RequestMapping("/api/new-shift")
//	public String showNewShift() {
//		return "{\r\n" + 
//		"id: \"1\",\r\n" + 
//		"employee_name: \"Live DSP Patel\",\r\n" + 
//		"employee_salary: \"44582565\",\r\n" + 
//		"employee_age: \"23\",\r\n" + 
//		"profile_image: \"\"\r\n" + 
//		"}";
//	}
	
//	@CrossOrigin(origins = "http://localhost:8080") 
//	@RequestMapping("/api/employees")
//	public String getEmployees() {
//		return "[\r\n" + 
//				"	{\r\n" + 
//				"		\"id\": \"1\",\r\n" + 
//				"		\"first_name\": \"Arthur\",\r\n" + 
//				"		\"last_name\": \"Olano\",\r\n" + 
//				"		\"phone\": \"09175786869\",\r\n" + 
//				"		\"email\": \"artotyo@gmail.com\",\r\n" + 
//				"		\"address\": \"18H ONe gateway PLace\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		\"id\": \"2\",\r\n" + 
//				"		\"first_name\": \"111Arthur\",\r\n" + 
//				"		\"last_name\": \"111Olano\",\r\n" + 
//				"		\"phone\": \"11109175786869\",\r\n" + 
//				"		\"email\": \"11artotyo@gmail.com\",\r\n" + 
//				"		\"address\": \"11118H ONe gateway PLace\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		\"id\": \"2\",\r\n" + 
//				"		\"first_name\": \"111Arthur\",\r\n" + 
//				"		\"last_name\": \"111Olano\",\r\n" + 
//				"		\"phone\": \"11109175786869\",\r\n" + 
//				"		\"email\": \"11artotyo@gmail.com\",\r\n" + 
//				"		\"address\": \"11118H ONe gateway PLace\"\r\n" + 
//				"	}\r\n" + 
//				"]";
//	}
	
	@GetMapping(value="/api/user/{id}")
	public User getUser(@PathVariable int id){
		log.debug("getUser()");
		return userService.findById(id).orElse(null);
	}
	
//	@GetMapping(value="/api/user")
//	public User getUser(@PathVariable int id){
//		return userService.findById(5).orElse(null);
//	}
}

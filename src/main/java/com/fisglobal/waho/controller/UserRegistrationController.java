package com.fisglobal.waho.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fisglobal.waho.WahoServerApplication;
import com.fisglobal.waho.beans.EmployeeBean;
import com.fisglobal.waho.beans.MessageBean;
import com.fisglobal.waho.beans.UserABean;
import com.fisglobal.waho.beans.UserBean;
import com.fisglobal.waho.beans.UserCredentialBeans;
import com.fisglobal.waho.beans.UserRegistrationBean;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.model.UserRole;
import com.fisglobal.waho.model.UserTempAccess;
import com.fisglobal.waho.services.UserRoleService;
import com.fisglobal.waho.services.UserService;
import com.fisglobal.waho.services.UserTempAccessService;
import com.fisglobal.waho.utils.AES256;
import com.fisglobal.waho.utils.DateUtils;
import com.fisglobal.waho.utils.Email;

@CrossOrigin(origins = {"${cross.origins}", "*"})
//@RestController
@RequestMapping("/api")
@Controller
public class UserRegistrationController {
	static Logger log = LoggerFactory.getLogger(UserRegistrationController.class);
	
	@Autowired
	private UserDashboardController userDashboardController;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserTempAccessService userTempAccessService;

	@PostMapping("/save-user")
	public List<EmployeeBean> registerUser(@ModelAttribute UserRegistrationBean userRegistrationBean, BindingResult bindingResult, HttpServletRequest request ) {
		log.debug("registerUser() - Start");
		User user = new User();
		user.setUserEid(userRegistrationBean.getUserEid());
		user.setFirstName(userRegistrationBean.getFirstName());
		user.setLastName(userRegistrationBean.getLastName());
		user.setUserParentId(userRegistrationBean.getUserParentId());
		user.setEffectiveStartDate(DateUtils.convertToLocalDateTime(userRegistrationBean.getEffectiveStartDate()+" 00:00"));
		user.setEffectiveEndDate(DateUtils.convertToLocalDateTime(userRegistrationBean.getEffectiveEndDate()+" 00:00"));
		System.out.println("user.toString()==="+user.toString());
		
		List<UserRole> userRoles = new ArrayList<UserRole>();
		int i = 0;
		for (String role: userRegistrationBean.getRoles()) {
			System.out.println("===role["+i+"]:"+role);
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRoleCd(role);
			//userRoleService.saveUserRole(userRole);
			System.out.println("userRole.toString()==="+userRole.toString());
			userRoles.add(userRole);
		}
		
		
		user.setUserRoles(userRoles);
		userService.saveUser(user);
		
		/*
		ShiftSchedule myShiftSched = new ShiftSchedule();
		myShiftSched.setSchedStartDate(LocalDateTime.now());
		myShiftSched.setSchedEndDate(LocalDateTime.now());
		List<ShiftSchedule> myShiftSchedules = new ArrayList<ShiftSchedule>();
		myShiftSchedules.add(myShiftSched);
		user.setShiftSchedules(myShiftSchedules);*/
		log.debug("registerUser() - End");
		return null;
		//return userDashboardController.getEmployees(request);
	}
	
	@RequestMapping("/register")
	public String registration(HttpServletRequest request) {
		log.debug("registration() - Start");
		request.setAttribute("mode", "MODE_REGISTER");
		
		log.debug("registration() - End");
		return "welcomepage";
	}
	
	@PostMapping(path = "/adduser", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addUser(@RequestBody UserABean userABean, @RequestHeader HttpHeaders headers, HttpServletRequest request){
		log.debug("AddUser() - Start");

		int last_updated_by = Integer.valueOf(userABean.getLast_updated_by());

		List<UserRole>  userRoles = userRoleService.getRoleByUserId(last_updated_by);
		if(userRoles != null) {

			boolean isAdmin = false;
			for (UserRole userRole : userRoles) {
				if ("ADMIN".equals(userRole.getRoleCd())) {
					isAdmin = true;
					break;
				}
			}

			if (isAdmin == false) {
				log.debug("AddUser() - Return");
				return new ResponseEntity<>(new UserBean(0,  null, null
						, null, 0, null, null
						, null, null, "You must be an Administrator to Add User!", null),HttpStatus.OK
						);
			}

			User currentUser = userService.getUsersByUserEid(userABean.getEid());
			if (currentUser != null) {
				log.debug("addUser() - Return");
				return new ResponseEntity<Object>(new MessageBean("There is already existing user with the same E ID!"), HttpStatus.OK);
				
				
			} else {
				User user = new User();
				user.setUserEid(userABean.getEid());
				user.setFirstName(userABean.getFirstname());
				user.setLastName(userABean.getLastname());
				user.setEmail(userABean.getEmail());
				user.setEffectiveStartDate(userABean.getEffective_start_date());
				user.setEffectiveEndDate(userABean.getEffective_end_date());

				user.setDateCreated(LocalDateTime.now());
				user.setDateLastUpdated(LocalDateTime.now());
				user.setCreatedBy(last_updated_by);
				user.setLastUpdatedBy(last_updated_by);
				user.setIsActive(userABean.getIs_active());

				userRoles = new ArrayList<UserRole>();
				for (int i = 0; i < userABean.getRole().length; i++) {
					UserRole userRole1 = new UserRole();
					userRole1.setRoleCd(userABean.getRole()[i].trim());
					userRole1.setDateCreated(LocalDateTime.now());
					userRole1.setDateLastUpdated(LocalDateTime.now());
					userRole1.setCreatedBy(last_updated_by);
					userRole1.setLastUpdatedBy(last_updated_by);
					userRole1.setIsActive(userABean.getIs_active());
					userRole1.setUser(user);
					
					userRoles.add(userRole1);
				}
				user.setUserRoles(userRoles);
				
				user.setUserParentId(Integer.valueOf(userABean.getManager()));
				
				UserTempAccess userTempAccess = new UserTempAccess();
				userTempAccess.setUserEid(userABean.getEid());
				userTempAccess.setCreatedBy(last_updated_by);
				userTempAccess.setLastUpdatedBy(last_updated_by);
				userTempAccess.setIsActive(userABean.getIs_active());
				userTempAccess.setDateCreated(LocalDateTime.now());
				userTempAccess.setDateLastUpdated(LocalDateTime.now());
				

				int returnval = -1;
				returnval = userService.addUserWithReturnValue(user, userTempAccess);

				if (returnval != -1) {
					log.debug("addUser() - resetPassword() - Start");
					List<CharacterRule> rules = Arrays.asList(
							// at least one upper-case character
							new CharacterRule(EnglishCharacterData.UpperCase, 1),
							// at least one lower-case character
							new CharacterRule(EnglishCharacterData.LowerCase, 1),
							// at least one digit character
							new CharacterRule(EnglishCharacterData.Digit, 1));
					PasswordGenerator generator = new PasswordGenerator();
					// Generated password is 12 characters long, which complies with policy
					String rawpassword = generator.generatePassword(12, rules);
					String tempPassword = AES256.encrypt(rawpassword);
					String email = userABean.getEmail().trim();

					int rowsUpdated = userTempAccessService.resetPassword(tempPassword, email.toUpperCase());

					if (rowsUpdated > 0) {
						
						MessageBean msg = new MessageBean();
						msg.setMsg("Add User Successful!");
						msg.setData(Integer.toString(returnval));

//						String subject = "WAHO: Password Reset";
//
//						String body = "You are receiving this because you (or someone else) have requested the reset of the password for your account.\n" +
//								"Your temporary password is: "+ rawpassword +"\n\n" +
//								"Please click on the following link, or paste this into your browser to change your temporary password:\n\n" +
//								"http://10.111.41.193:8080/changepassword/\n\n" +
//								"If you did not request this, please ignore this email and your password will remain unchanged.\n";
//
//
//						if (Email.email(subject, body, email)) {
//							log.debug("addUser() - Return");
//							return new ResponseEntity<Object>(msg, HttpStatus.OK);
//						} else {
//							log.debug("addUser() - Return");
//							msg.setMsg("Add User successful!, but failed to email the password!");
//							return new ResponseEntity<Object>(msg, HttpStatus.OK);
//						}
						
						
						
						
						
						//**** THIS IS TEMPORARY ***************************
//						OutputStream os = null;
//				        Properties prop = new Properties();
//				        prop.setProperty("password", rawpassword);
//				        prop.setProperty("recipient", email);
//				        try {
//				        	String email1 = email + LocalDateTime.now();
//				        	email1 = email1.replace(".", "");
//				        	email1 = email1.replace("@", "");
//				        	email1 = email1.replace(":", "");
//				            os = new FileOutputStream("Z:\\sheldon\\emails\\"+email1+".properties");
//				            prop.store(os, "");
//				        } catch (IOException e) {
//				        	return new ResponseEntity<Object>(new MessageBean("Add User Failed!"), HttpStatus.OK);
//				        } finally {
//				        	try {
//								os.close();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//				        }
				        return new ResponseEntity<Object>(msg, HttpStatus.OK);
				      //**** THIS IS TEMPORARY ***************************


					}
					log.debug("addUser() - Return");
					return new ResponseEntity<Object>(new MessageBean("Add User Failed!"), HttpStatus.OK);
				} else {
					log.debug("AddUser() - Return");
					return new ResponseEntity<Object>(new MessageBean("Add User Failed!"), HttpStatus.OK);
				}
			}	

		} else {
			log.debug("AddUser() - Return");
			return new ResponseEntity<>(new UserBean(0,  null, null
					, null, 0, null, null
					, null, null, "You must be an Administrator to Add User!", null),HttpStatus.OK
					);
		}
	}

	@PostMapping(path = "/edituser", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateUser(@RequestBody UserABean userABean, @RequestHeader HttpHeaders headers, HttpServletRequest request){
		log.debug("updateUser() - Start");

		int last_updated_by = Integer.valueOf(userABean.getLast_updated_by());

		List<UserRole>  userRoles = userRoleService.getRoleByUserId(last_updated_by);
		if(userRoles != null) {

			boolean isAdmin = false;
			for (UserRole userRole : userRoles) {
				if ("ADMIN".equals(userRole.getRoleCd())) {
					isAdmin = true;
					break;
				}
			}

			if (isAdmin == false) {
				log.debug("updateUser() - Return");
				return new ResponseEntity<>(new UserBean(0,  null, null
						, null, 0, null, null
						, null, null, "You must be an Administrator to Edit User!", null),HttpStatus.OK
						);
			}

			Optional<User> optional = userService.findById(userABean.getUser_id());
			if(optional.isPresent()) {
				User currentUser = optional.get();

				User user = new User();
				user.setUserId(userABean.getUser_id());
				user.setUserEid(userABean.getEid());
				user.setFirstName(userABean.getFirstname());
				user.setLastName(userABean.getLastname());
				user.setEmail(userABean.getEmail());
				user.setUserParentId(currentUser.getUserParentId());
				user.setEffectiveStartDate(userABean.getEffective_start_date());
				user.setEffectiveEndDate(userABean.getEffective_end_date());

				user.setDateCreated(currentUser.getDateCreated());
				user.setDateLastUpdated(LocalDateTime.now());
				user.setCreatedBy(currentUser.getCreatedBy());
				user.setLastUpdatedBy(last_updated_by);
				user.setIsActive(userABean.getIs_active());

				user.setShiftSchedules(currentUser.getShiftSchedules());

				userRoles = new ArrayList<UserRole>();
				for (int i = 0; i < userABean.getRole().length; i++) {
					UserRole userRole1 = new UserRole();
					userRole1.setRoleCd(userABean.getRole()[i].trim());
					userRole1.setDateCreated(currentUser.getDateCreated());
					userRole1.setDateLastUpdated(LocalDateTime.now());
					userRole1.setCreatedBy(currentUser.getCreatedBy());
					userRole1.setLastUpdatedBy(last_updated_by);
					userRole1.setIsActive(userABean.getIs_active());
					userRole1.setUser(user);

					userRoles.add(userRole1);
				}
				user.setUserRoles(userRoles);
				
				user.setUserParentId(Integer.valueOf(userABean.getManager()));

				int returnval = 0;
				returnval = userService.editUserWithReturnValue(user, userRoles);

				if (returnval == 1) {
					log.debug("updateUser() - Return");
					return new ResponseEntity<Object>(new MessageBean("Edit User Successful!"), HttpStatus.OK);
				} else {
					log.debug("updateUser() - Return");
					return new ResponseEntity<Object>(new MessageBean("Edit User Failed!"), HttpStatus.OK);
				}
			} else {
				log.debug("updateUser() - Return");
				return new ResponseEntity<Object>(new MessageBean("Edit User Failed! User Not Found!"), HttpStatus.OK);
			}	


		} else {
			log.debug("updateUser() - Return");
			return new ResponseEntity<>(new UserBean(0,  null, null
					, null, 0, null, null
					, null, null, "You must be an Administrator to Edit User!", null),HttpStatus.OK
					);
		}
	}



	@PostMapping(path = "/deleteuser", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> deleteUser(@RequestBody UserABean userABean, @RequestHeader HttpHeaders headers, HttpServletRequest request){
		log.debug("deleteUser() - Start");

		int last_updated_by = Integer.valueOf(userABean.getLast_updated_by());

		List<UserRole>  userRoles = userRoleService.getRoleByUserId(last_updated_by);
		if(userRoles != null) {

			boolean isAdmin = false;
			for (UserRole userRole : userRoles) {
				if ("ADMIN".equals(userRole.getRoleCd())) {
					isAdmin = true;
					break;
				}
			}

			if (isAdmin == false) {
				log.debug("deleteUser() - Return");
				return new ResponseEntity<>(new UserBean(0,  null, null
						, null, 0, null, null
						, null, null, "You must be an Administrator to Delete User!", null),HttpStatus.OK
						);
			}

			Optional<User> optional = userService.findById(userABean.getUser_id());
			if(optional.isPresent()) {
				User currentUser = optional.get();
				int returnval = 0;
				UserTempAccess currentUserAccess = userTempAccessService.getUsersByUserId(userABean.getUser_id());
				returnval = userService.deleteUserWithReturnValue(currentUser, currentUserAccess);

				if (returnval == 1) {
					log.debug("deleteUser() - Return");
					return new ResponseEntity<Object>(new MessageBean("Delete User Successful!"), HttpStatus.OK);
				} else {
					log.debug("deleteUser() - Return");
					return new ResponseEntity<Object>(new MessageBean("Delete User Failed!"), HttpStatus.OK);
				}
			} else {
				log.debug("deleteUser() - Return");
				return new ResponseEntity<Object>(new MessageBean("Delete User Failed! User Not Found!"), HttpStatus.OK);
			}	

		} else {
			log.debug("deleteUser() - Return");
			return new ResponseEntity<>(new UserBean(0,  null, null
					, null, 0, null, null
					, null, null, "You must be an Administrator to Delete User!", null),HttpStatus.OK
					);
		}
	}



	@GetMapping(path = "/getallusers", produces = "application/json")
	public ResponseEntity<?> getallusers(){
		log.debug("getallusers() - Start");

		List<User> users = userService.getAllUsers();

		List<Map<String, Object>> maps = new ArrayList<>();

		for (User user : users) {
			Map<String, Object> map = new HashMap<>();
			map.put("user_id", user.getUserId());
			map.put("eid", user.getUserEid());
			map.put("firstname", user.getFirstName());
			map.put("lastname", user.getLastName());
			map.put("email", user.getEmail());

			List<String> roles = new ArrayList<>();
			for (UserRole userRole : user.getUserRoles()) {
				roles.add(userRole.getRoleCd());
			}
			//map.put("role", String.join(",", roles));
			map.put("role", roles);
			
			Optional<User> optional = userService.findById(user.getUserParentId());
			optional.ifPresent(usera -> {
				map.put("manager", usera.getUserId());
				map.put("disp", usera.getLastName() + ", " + usera.getFirstName()  + " (" + usera.getUserEid() + ")");
			});

			map.put("effective_start_date", user.getEffectiveStartDate());
			map.put("effective_end_date", user.getEffectiveEndDate());
			map.put("date_created", user.getDateCreated());
			map.put("date_last_updated", user.getDateLastUpdated());

			optional = userService.findById(user.getCreatedBy());
			optional.ifPresent(usera -> {
				map.put("created_by", usera.getFirstName() + " " + usera.getLastName()  + " (" + usera.getUserEid() + ")");
			});

			optional = userService.findById(user.getLastUpdatedBy());
			optional.ifPresent(usera -> {
				map.put("last_updated_by", usera.getFirstName() + " " + usera.getLastName()  + " (" + usera.getUserEid() + ")");
			});


			map.put("is_active", user.getIsActive());

			maps.add(map);
		}

		log.debug("getallusers() - Return");
		return new ResponseEntity<>(maps,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getallmanagers", produces = "application/json")
	public ResponseEntity<?> getallmanagers(@RequestParam("withAll") String withAll){
		log.debug("getallmanagers() - Start");

		List<User> users = userService.getAllManagers();

		List<Map<String, Object>> maps = new ArrayList<>();
		
		if ("1".equals(withAll)) {
			Map<String, Object> map0 = new HashMap<>();
			map0.put("user_id", "All");
			map0.put("disp", "All");
			maps.add(map0);
		}

		for (User user : users) {
			Map<String, Object> map = new HashMap<>();
			map.put("user_id", user.getUserId());
			map.put("eid", user.getUserEid());
			map.put("firstname", user.getFirstName());
			map.put("lastname", user.getLastName());
			map.put("email", user.getEmail());

			List<String> roles = new ArrayList<>();
			for (UserRole userRole : user.getUserRoles()) {
				roles.add(userRole.getRoleCd());
			}
			//map.put("role", String.join(",", roles));
			map.put("role", roles);
			map.put("disp", user.getLastName() + ", " + user.getFirstName()  + " (" + user.getUserEid() + ")");

			map.put("effective_start_date", user.getEffectiveStartDate());
			map.put("effective_end_date", user.getEffectiveEndDate());
			map.put("date_created", user.getDateCreated());
			map.put("date_last_updated", user.getDateLastUpdated());

			Optional<User> optional = userService.findById(user.getCreatedBy());
			optional.ifPresent(usera -> {
				map.put("created_by", usera.getFirstName() + " " + usera.getLastName()  + " (" + usera.getUserEid() + ")");
			});

			optional = userService.findById(user.getLastUpdatedBy());
			optional.ifPresent(usera -> {
				map.put("last_updated_by", usera.getFirstName() + " " + usera.getLastName()  + " (" + usera.getUserEid() + ")");
			});


			map.put("is_active", user.getIsActive());

			maps.add(map);
		}

		log.debug("getallmanagers() - Return");
		return new ResponseEntity<>(maps,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getallusersdp", produces = "application/json")
	public ResponseEntity<?> getallusers(@RequestParam("withAll") String withAll){
		log.debug("getallusers(String withAll) - Start");

		List<User> users = userService.getAllUsersDP();

		List<Map<String, Object>> maps = new ArrayList<>();
		
		if ("1".equals(withAll)) {
			Map<String, Object> map0 = new HashMap<>();
			map0.put("user_id", "All");
			map0.put("disp", "All");
			maps.add(map0);
		}

		for (User user : users) {
			Map<String, Object> map = new HashMap<>();
			map.put("user_id", user.getUserId());
			map.put("eid", user.getUserEid());
			map.put("firstname", user.getFirstName());
			map.put("lastname", user.getLastName());
			map.put("email", user.getEmail());

			List<String> roles = new ArrayList<>();
			for (UserRole userRole : user.getUserRoles()) {
				roles.add(userRole.getRoleCd());
			}
			//map.put("role", String.join(",", roles));
			map.put("role", roles);
			map.put("disp", user.getLastName() + ", " + user.getFirstName()  + " (" + user.getUserEid() + ")");

			map.put("effective_start_date", user.getEffectiveStartDate());
			map.put("effective_end_date", user.getEffectiveEndDate());
			map.put("date_created", user.getDateCreated());
			map.put("date_last_updated", user.getDateLastUpdated());

			Optional<User> optional = userService.findById(user.getCreatedBy());
			optional.ifPresent(usera -> {
				map.put("created_by", usera.getFirstName() + " " + usera.getLastName()  + " (" + usera.getUserEid() + ")");
			});

			optional = userService.findById(user.getLastUpdatedBy());
			optional.ifPresent(usera -> {
				map.put("last_updated_by", usera.getFirstName() + " " + usera.getLastName()  + " (" + usera.getUserEid() + ")");
			});


			map.put("is_active", user.getIsActive());

			maps.add(map);
		}

		log.debug("getallusers(String withAll) - Return");
		return new ResponseEntity<>(maps,HttpStatus.OK);
	}

}

package com.fisglobal.waho.controller;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisglobal.waho.beans.ErrorBeans;
import com.fisglobal.waho.beans.MessageBean;
import com.fisglobal.waho.beans.UserBean;
import com.fisglobal.waho.beans.UserCredentialBeans;
import com.fisglobal.waho.beans.UserOptionBean;
import com.fisglobal.waho.model.SystemCode;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.model.UserOption;
import com.fisglobal.waho.model.UserTempAccess;
import com.fisglobal.waho.services.SystemCodeService;
import com.fisglobal.waho.services.UserOptionService;
import com.fisglobal.waho.services.UserService;
import com.fisglobal.waho.services.UserTempAccessService;
import com.fisglobal.waho.utils.AES256;
import com.fisglobal.waho.utils.Email;

import javassist.expr.NewArray;


@CrossOrigin(origins = {"${cross.origins}", "*"})
@RestController
@RequestMapping("/api")
public class LoginController { 
	
	Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserTempAccessService userTempAccessService;
	
	@Autowired
	private UserOptionService userOptionService;
	

	// as of now, this would be our login code (we apply jwt later on)
	@PostMapping(path = "/users/authenticate", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserCredentialBeans userCredentialBeans, @RequestHeader HttpHeaders headers, HttpServletRequest request){
		log.debug("createAuthenticationToken() - Start");
		
		UserTempAccess currentUserAccess = userTempAccessService.getUsersByUserEid(userCredentialBeans.getUsername());
		
		//check if current user is not null
		if(currentUserAccess != null) {
			//if not null, then check for the credentials
			String password = AES256.encrypt(userCredentialBeans.getPassword().trim());
			if(currentUserAccess.getPassword().equals(password)) {
				User currentUser = userService.getUsersByUserEid(currentUserAccess.getUserEid());
				userTempAccessService.resetPassword(null, currentUser.getEmail());
				
				//get user options and system options
				List<UserOptionBean> userOptions = userOptionService.configureUserOption(currentUser.getUserId());
				
				//send user credntials
				
				log.debug("createAuthenticationToken() - Return");
				return new ResponseEntity<>(new UserBean(currentUser.getUserId(),  currentUser.getUserEid(), currentUser.getFirstName()
						, currentUser.getLastName(), currentUser.getUserParentId(), "passwordHere", "otpHere"
						, currentUser.getUserRoles().toString(), "thisIsAranDomToken", "", userOptions),HttpStatus.OK
						);
			} else if(currentUserAccess.getResetpassword() != null && currentUserAccess.getResetpassword().equals(password)) {
				log.debug("createAuthenticationToken() - Return");
				return new ResponseEntity<>(new UserBean(0,  null, null
						, null, 0, null, null
						, null, null, "Please change your temporary password first! Click the \"Change Password\" link!", null),HttpStatus.OK
						);
			} else {
				log.debug("createAuthenticationToken() - Return");
				return new ResponseEntity<>(new UserBean(0,  null, null
						, null, 0, null, null
						, null, null, "Username or Password is Invalid!", null),HttpStatus.OK
						);
			}
		}
		//otherwise
		
		log.debug("createAuthenticationToken() - End");
		return new ResponseEntity<>(new UserBean(0,  null, null
				, null, 0, null, null
				, null, null, "Username or Password is Invalid!", null),HttpStatus.OK
				);


	}


	// as of now, this would be our login code (we apply jwt later on)
	@PostMapping(path = "/users/resetpassword", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> resetPassword(@RequestBody UserCredentialBeans userCredentialBeans, @RequestHeader HttpHeaders headers, HttpServletRequest request){
		log.debug("resetPassword() - Start");
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
		String email = userCredentialBeans.getEmail().trim();

		int rowsUpdated = userTempAccessService.resetPassword(tempPassword, email.toUpperCase());

		if (rowsUpdated > 0) {

//			String subject = "WAHO: Password Reset";
//
//			String body = "You are receiving this because you (or someone else) have requested the reset of the password for your account.\n" +
//					"Your temporary password is: "+ rawpassword +"\n\n" +
//					"Please click on the following link, or paste this into your browser to change your temporary password:\n\n" +
//					"http://10.111.41.193:8080/changepassword/\n\n" +
//					"If you did not request this, please ignore this email and your password will remain unchanged.\n";
//
//
//			if (Email.email(subject, body, email)) {
//				return new ResponseEntity<Object>(new MessageBean("Reset Password successful!"), HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Object>(new MessageBean("Reset Password successful, but failed to email the password!"), HttpStatus.OK);
//			}
			
			
			
			
			
			//**** THIS IS TEMPORARY ***************************
			OutputStream os = null;
	        Properties prop = new Properties();
	        prop.setProperty("password", rawpassword);
	        prop.setProperty("recipient", email);
	        try {
	        	String email1 = email + LocalDateTime.now();
	        	email1 = email1.replace(".", "");
	        	email1 = email1.replace("@", "");
	        	email1 = email1.replace(":", "");
	            os = new FileOutputStream("Z:\\sheldon\\emails\\"+email1+".properties");
	            prop.store(os, "");
	        } catch (IOException e) {
	        	return new ResponseEntity<Object>(new MessageBean("Reset Password Failed!"), HttpStatus.OK);
	        } finally {
	        	try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	        return new ResponseEntity<Object>(new MessageBean("Reset Password successful!"), HttpStatus.OK);
	      //**** THIS IS TEMPORARY ***************************
	        

		}
		log.debug("resetPassword() - End");
		return new ResponseEntity<Object>(new MessageBean("Reset Password Failed!"), HttpStatus.OK);


	}

	// as of now, this would be our login code (we apply jwt later on)
	@PostMapping(path = "/users/changepassword", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> changePassword(@RequestBody UserCredentialBeans userCredentialBeans, @RequestHeader HttpHeaders headers, HttpServletRequest request){
		log.debug("changePassword() - Start");

		String password = AES256.encrypt(userCredentialBeans.getPassword().trim());
		String newPassword = AES256.encrypt(userCredentialBeans.getNewpassword().trim());
		String userEid = userCredentialBeans.getUsername();

		UserTempAccess currentUserAccess = userTempAccessService.getUsersByUserEid(userCredentialBeans.getUsername());

		//check if current user is not null
		if(currentUserAccess != null) {
			//if not null, then check for the credentials
			if (currentUserAccess.getPassword() == null && currentUserAccess.getResetpassword() == null) { //must reset password first
				return new ResponseEntity<Object>(new MessageBean("Change Password failed!"), HttpStatus.OK);
			} else if (currentUserAccess.getPassword() == null && currentUserAccess.getResetpassword() != null) { //new user that have already requested reset password
				if(currentUserAccess.getResetpassword().equals(password)) {
					int rowsUpdated = userTempAccessService.changePassword(newPassword, userEid, password, "reset");

					if (rowsUpdated > 0) {
						return new ResponseEntity<Object>(new MessageBean("Change Password successful!"), HttpStatus.OK);
					}
				}
			} else if(currentUserAccess.getPassword() != null || currentUserAccess.getResetpassword() != null) {//
				if(currentUserAccess.getPassword().equals(password)) {
					int rowsUpdated = userTempAccessService.changePassword(newPassword, userEid, password, "change");

					if (rowsUpdated > 0) {
						return new ResponseEntity<Object>(new MessageBean("Change Password successful!"), HttpStatus.OK);
					}
				} else if(currentUserAccess.getResetpassword().equals(password)) {
					int rowsUpdated = userTempAccessService.changePassword(newPassword, userEid, password, "reset");

					if (rowsUpdated > 0) {
						return new ResponseEntity<Object>(new MessageBean("Change Password successful!"), HttpStatus.OK);
					}
				}
			}
		}
		log.debug("changePassword() - End");
		return new ResponseEntity<Object>(new MessageBean("Change Password failed! Username or Password is Invalid!"), HttpStatus.OK);

	}
	
	
	
	
	
}

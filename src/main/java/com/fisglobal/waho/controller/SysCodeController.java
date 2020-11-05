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
import com.fisglobal.waho.beans.SysCodeBean;
import com.fisglobal.waho.beans.UserABean;
import com.fisglobal.waho.beans.UserBean;
import com.fisglobal.waho.beans.UserCredentialBeans;
import com.fisglobal.waho.beans.UserRegistrationBean;
import com.fisglobal.waho.model.SystemCode;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.model.UserRole;
import com.fisglobal.waho.model.UserTempAccess;
import com.fisglobal.waho.services.SystemCodeService;
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
public class SysCodeController {
	static Logger log = LoggerFactory.getLogger(SysCodeController.class);

	@Autowired
	private UserDashboardController userDashboardController;

	@Autowired
	private UserService userService;

	@Autowired
	private SystemCodeService systemCodeService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserTempAccessService userTempAccessService;

	@GetMapping(path = "/getallsyscodes", produces = "application/json")
	public ResponseEntity<?> getallsyscodes(){
		log.debug("getallsyscodes() - Start");

		List<SystemCode> systemCodes = systemCodeService.getAllSystemCodes();

		List<Map<String, Object>> maps = new ArrayList<>();

		for (SystemCode systemCode : systemCodes) {
			Map<String, Object> map = new HashMap<>();
			map.put("code_id", systemCode.getSysCodeId());
			map.put("code_name", systemCode.getSysCode());
			map.put("desc", systemCode.getSysCodeName());
			map.put("category", systemCode.getSysCodeCategory());
			map.put("value_1", systemCode.getValue1());
			map.put("value_2", systemCode.getValue2());
			map.put("value_3", systemCode.getValue3());
			map.put("value_4", systemCode.getValue4());


			map.put("date_created", systemCode.getDateCreated());
			map.put("date_last_updated", systemCode.getDateLastUpdated());

			Optional<User> optional = userService.findById(systemCode.getCreatedBy());
			optional.ifPresent(usera -> {
				map.put("created_by", usera.getFirstName() + " " + usera.getLastName()  + " (" + usera.getUserEid() + ")");
			});

			optional = userService.findById(systemCode.getLastUpdatedBy());
			optional.ifPresent(usera -> {
				map.put("last_updated_by", usera.getFirstName() + " " + usera.getLastName()  + " (" + usera.getUserEid() + ")");
			});


			map.put("is_active", systemCode.getIsActive());

			maps.add(map);
		}

		log.debug("getallsyscodes() - Return");
		return new ResponseEntity<>(maps,HttpStatus.OK);
	}


	@PostMapping(path = "/editsyscode", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> editsyscode(@RequestBody SysCodeBean sysCodeBean, @RequestHeader HttpHeaders headers, HttpServletRequest request){
		log.debug("editsyscode() - Start");

		int last_updated_by = Integer.valueOf(sysCodeBean.getLast_updated_by());

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
				log.debug("editsyscode() - Return");
				return new ResponseEntity<Object>(new MessageBean("You must be an Administrator to Edit System Code!"), HttpStatus.OK);
			}

			Optional<SystemCode> optional = systemCodeService.findById(sysCodeBean.getCode_id());
			if(optional.isPresent()) {
				SystemCode currentSystemCode = optional.get();

				SystemCode systemCode = new SystemCode();
				systemCode.setSysCodeId(sysCodeBean.getCode_id());
				systemCode.setSysCodeName(sysCodeBean.getDesc());
				systemCode.setSysCode(sysCodeBean.getCode_name());
				systemCode.setSysCodeCategory(sysCodeBean.getCategory());
				systemCode.setValue1(sysCodeBean.getValue_1());
				systemCode.setValue2(sysCodeBean.getValue_2());
				systemCode.setValue3(sysCodeBean.getValue_3());
				systemCode.setValue4(sysCodeBean.getValue_4());

				systemCode.setDateCreated(currentSystemCode.getDateCreated());
				systemCode.setDateLastUpdated(LocalDateTime.now());
				systemCode.setCreatedBy(currentSystemCode.getCreatedBy());
				systemCode.setLastUpdatedBy(last_updated_by);
				systemCode.setIsActive(sysCodeBean.getIs_active());


				int returnval = 0;
				returnval = systemCodeService.editSysCodeWithReturnValue(systemCode);

				if (returnval == 1) {
					log.debug("editsyscode() - Return");
					return new ResponseEntity<Object>(new MessageBean("Edit System Code Successful!"), HttpStatus.OK);
				} else {
					log.debug("editsyscode() - Return");
					return new ResponseEntity<Object>(new MessageBean("Edit System Code Failed!"), HttpStatus.OK);
				}
			} else {
				log.debug("editsyscode() - Return");
				return new ResponseEntity<Object>(new MessageBean("Edit System Code Failed! System Code Not Found!"), HttpStatus.OK);
			}	


		} else {
			log.debug("editsyscode() - Return");
			return new ResponseEntity<Object>(new MessageBean("You must be an Administrator to Edit System Code!"), HttpStatus.OK);
		}
	}

	@PostMapping(path = "/addsyscode", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> addsyscode(@RequestBody SysCodeBean sysCodeBean, @RequestHeader HttpHeaders headers, HttpServletRequest request){
		log.debug("addsyscode() - Start");

		int last_updated_by = Integer.valueOf(sysCodeBean.getLast_updated_by());

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
				log.debug("addsyscode() - Return");
				return new ResponseEntity<Object>(new MessageBean("You must be an Administrator to Add System Code!"), HttpStatus.OK);
			}

			SystemCode currentSystemCode = systemCodeService.getSysCodesByName(sysCodeBean.getCode_name());
			if (currentSystemCode != null) {
				log.debug("addsyscode() - Return");
				return new ResponseEntity<Object>(new MessageBean("There is already existing system code with the same name!"), HttpStatus.OK);


			} else {

				SystemCode systemCode = new SystemCode();
				//systemCode.setSysCodeId(sysCodeBean.getCode_id());
				systemCode.setSysCodeName(sysCodeBean.getDesc());
				systemCode.setSysCode(sysCodeBean.getCode_name());
				systemCode.setSysCodeCategory(sysCodeBean.getCategory());
				systemCode.setValue1(sysCodeBean.getValue_1());
				systemCode.setValue2(sysCodeBean.getValue_2());
				systemCode.setValue3(sysCodeBean.getValue_3());
				systemCode.setValue4(sysCodeBean.getValue_4());

				systemCode.setDateCreated(LocalDateTime.now());
				systemCode.setDateLastUpdated(LocalDateTime.now());
				systemCode.setCreatedBy(last_updated_by);
				systemCode.setLastUpdatedBy(last_updated_by);
				systemCode.setIsActive(sysCodeBean.getIs_active());


				int returnval = -1;
				returnval = systemCodeService.addSysCodeWithReturnValue(systemCode);

				if (returnval != -1) {
					log.debug("addsyscode() - Return");
					MessageBean msg = new MessageBean();
					msg.setMsg("Add System Code Successful!");
					msg.setData(Integer.toString(returnval));
					return new ResponseEntity<Object>(msg, HttpStatus.OK);
				} else {
					log.debug("addsyscode() - Return");
					return new ResponseEntity<Object>(new MessageBean("Add System Code Failed!"), HttpStatus.OK);
				}
			}


		} else {
			log.debug("addsyscode() - Return");
			return new ResponseEntity<Object>(new MessageBean("You must be an Administrator to Add System Code!"), HttpStatus.OK);
		}
	}
	
	@PostMapping(path = "/deletesyscode", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> deletesyscode(@RequestBody SysCodeBean sysCodeBean, @RequestHeader HttpHeaders headers, HttpServletRequest request){
		log.debug("deletesyscode() - Start");

		int last_updated_by = Integer.valueOf(sysCodeBean.getLast_updated_by());

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
				log.debug("deletesyscode() - Return");
				return new ResponseEntity<Object>(new MessageBean("You must be an Administrator to Delete System Code!"), HttpStatus.OK);
			}

			Optional<SystemCode> optional = systemCodeService.findById(sysCodeBean.getCode_id());
			if(optional.isPresent()) {
				SystemCode currentSystemCode = optional.get();
				int returnval = 0;
				returnval = systemCodeService.deleteSysCodeWithReturnValue(currentSystemCode);

				if (returnval == 1) {
					log.debug("deletesyscode() - Return");
					return new ResponseEntity<Object>(new MessageBean("Delete System Code Successful!"), HttpStatus.OK);
				} else {
					log.debug("deletesyscode() - Return");
					return new ResponseEntity<Object>(new MessageBean("Delete System Code Failed!"), HttpStatus.OK);
				}
			} else {
				log.debug("deletesyscode() - Return");
				return new ResponseEntity<Object>(new MessageBean("Delete System Code Failed! System Code Not Found!"), HttpStatus.OK);
			}	

		} else {
			log.debug("deletesyscode() - Return");
			return new ResponseEntity<Object>(new MessageBean("You must be an Administrator to Delete System Code!"), HttpStatus.OK);
		}
	}



}

package com.fisglobal.waho.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fisglobal.waho.WahoServerApplication;
import com.fisglobal.waho.beans.EmployeeBean;
import com.fisglobal.waho.beans.UserBean;
import com.fisglobal.waho.model.ShiftSchedule;
import com.fisglobal.waho.model.ShiftScheduleTime;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.model.UserOption;
import com.fisglobal.waho.model.UserTempAccess;
import com.fisglobal.waho.services.ShiftScheduleService;
import com.fisglobal.waho.services.UserService;
import com.fisglobal.waho.services.ShiftScheduleTimeService;
import com.fisglobal.waho.services.UserOptionService;

@CrossOrigin(origins = {"${cross.origins}", "*"})  
@RestController
@RequestMapping("/api")
public class UserDashboardController {
	static Logger log = LoggerFactory.getLogger(UserDashboardController.class);
	
	@Autowired
	private ShiftScheduleService shiftScheduleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShiftScheduleTimeService shiftScheduleTimeService;
	
	@Autowired
	private UserOptionService userOptionService;
	
	
	
	@RequestMapping("/current-shift")
	public String showCurrentShift(@RequestParam int userId, HttpServletRequest request) {
		log.debug("showCurrentShift() - Start");
		User myUser = userService.findById(userId).orElse(null);
		System.out.println("# of sched:"+myUser.getShiftSchedules().size());
		request.setAttribute("shiftSchedules", shiftScheduleService.findByUser(myUser));
		request.setAttribute("mode", "MODE_SHOW_SHIFT_SCHEDULES");
		
		log.debug("showCurrentShift() - End");
		return "welcomepage";
	}
	
	@RequestMapping("/new-shift")
	public String showNewShiftForm(HttpServletRequest request) {
		log.debug("showNewShiftForm() - Start");
		request.setAttribute("mode", "MODE_NEW_SHIFT");
		log.debug("showNewShiftForm() - End");
		return "welcomepage";
	}
	
	@RequestMapping("/show-my-shift")
	public String showMyShift(@RequestParam int shiftSchedId, HttpServletRequest request) {
		log.debug("showMyShift() - Start");
		ShiftSchedule shiftSchedule = shiftScheduleService.findById(shiftSchedId).get();
		request.setAttribute("shiftScheduleTimes", shiftScheduleTimeService.findByShiftSchedule(shiftSchedule));
		request.setAttribute("mode", "MODE_SHOW_MY_SHIFT");
		log.debug("showMyShift() - End");
		return "welcomepage";
	}
	
	@GetMapping("/create-shift-schedule")
	public String registerUser(HttpServletRequest request) {
		log.debug("registerUser() - Start");
		User myUser = userService.findById(5).orElse(null);
		ShiftSchedule myShiftSchedule = new ShiftSchedule();
		myShiftSchedule.setSchedStartDate(LocalDateTime.now());
		myShiftSchedule.setSchedEndDate(LocalDateTime.now());
		myShiftSchedule.setDateCreated(LocalDateTime.now());
		myShiftSchedule.setDateLastUpdated(LocalDateTime.now());
		myShiftSchedule.setCreatedBy(1);
		myShiftSchedule.setLastUpdatedBy(2);
		myShiftSchedule.setIsActive("Y");
		myShiftSchedule.setUser(myUser);
		shiftScheduleService.saveShiftSchedule(myShiftSchedule);
		request.setAttribute("mode", "MODE_HOME");
//		request.getSession().setAttribute("currentUserId", value);
		log.debug("registerUser() - End");
		return "welcomepage";
	}
	
	@GetMapping("/shift-logins/user/{id}")
	public ResponseEntity<?> getShiftLogins(@PathVariable("id") int id) {
		log.debug("getShiftLogins() - Start");
		ShiftSchedule empShiftSched = shiftScheduleService.getCurrentShiftScheduleByUser(id);
		if (empShiftSched != null) {
			List<ShiftScheduleTime> shiftSchedTimes = shiftScheduleTimeService.findByShiftSchedule(empShiftSched);
			if (shiftSchedTimes == null || shiftSchedTimes.isEmpty()) {
				log.debug("getShiftLogins(1) - No available shift schedule");
				return new ResponseEntity<>("No available shift schedule for this user", HttpStatus.BAD_REQUEST);
			} else {
				log.debug("getShiftLogins() - Shift schedule found");
				return new ResponseEntity<>(shiftSchedTimes, HttpStatus.OK);
			}
		} else {
			log.debug("getShiftLogins(2) - No available shift schedule");
			return new ResponseEntity<>("No available shift schedule for this user", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@GetMapping("/employees/manager/{id}")
	public List<EmployeeBean> getEmployeesOfManager(@PathVariable("id") int id) {
		log.debug("getEmployeesOfManager() - Start");
		System.out.println("manager id =="+id);
		List<User> employees = userService.getUsersByUserParentId(id);
		System.out.println("employees size: "+employees.size());
		List<EmployeeBean> empArray = new ArrayList<EmployeeBean>();
		for (User employee : employees) {
			log.debug(String.format(("getEmployeesOfManager() - Process [%s]"), employee.getUserEid()));
			System.out.println("1...emp name="+employee.getFirstName()+" "+employee.getLastName());
			//get the current/latest shift schedule of the employee
			ShiftSchedule empShiftSched = shiftScheduleService.getCurrentShiftScheduleByUser(employee.getUserId());
			
			
			System.out.println("2...");
			int numMissedLogin = 0;
			if (empShiftSched != null) {
				System.out.println("3...");
				List<ShiftScheduleTime> shiftSchedTimes = shiftScheduleTimeService.findMissedTimeByShiftSchedId(empShiftSched.getShftSchedId());
				System.out.println("4...");
				numMissedLogin = shiftSchedTimes.size();
			} else {
				
				System.out.println("4.1");
				empShiftSched = shiftScheduleService.getPreviousShiftScheduleByUser(employee.getUserId());
				
				if( empShiftSched == null) {
					
					System.out.println("4.2");	
				} else {
					List<ShiftScheduleTime> shiftSchedTimes = shiftScheduleTimeService.findMissedTimeByShiftSchedId(empShiftSched.getShftSchedId());
					numMissedLogin = shiftSchedTimes.size();
					System.out.println("4.3");
				}
			}
			System.out.println("5...");
			empArray.add(new EmployeeBean(employee, empShiftSched, numMissedLogin));
			System.out.println("6...");
			
			log.debug("getEmployeesOfManager() - End");
		}	
		//get all the shift schedules 
		//get the number of missed logins for the todays shift schedule of each employee
		
		return empArray;
	}

	@GetMapping(path="/manager/drshifttoday/{id}")
	public ResponseEntity<?> getDirectReportsShiftToday(@PathVariable("id") int id) {
		log.debug("getDirectReports() - Start");
		System.out.println("manager id =="+id);
		List<User> employees = userService.getUsersByUserParentId(id);
		System.out.println("employees size: "+employees.size());
		List<EmployeeBean> empArray = new ArrayList<EmployeeBean>();
		HashMap<String, Object> list = new HashMap<String, Object>();
		
		for (User employee : employees) {
			log.debug(String.format(("getEmployeesOfManager() - Process [%s]"), employee.getUserEid()));
			System.out.println("1...emp name="+employee.getFirstName()+" "+employee.getLastName());
			//get the current/latest shift schedule of the employee
			ShiftSchedule empShiftSched = shiftScheduleService.getCurrentShiftScheduleByUser(employee.getUserId());
			
			
			System.out.println("2...");
			int numMissedLogin = 0;
			if (empShiftSched != null) {
				System.out.println("3...");
				List<ShiftScheduleTime> shiftSchedTimes = shiftScheduleTimeService.findMissedTimeByShiftSchedId(empShiftSched.getShftSchedId());
				System.out.println("4...");
				numMissedLogin = shiftSchedTimes.size();
				empArray.add(new EmployeeBean(employee, empShiftSched, numMissedLogin));
			}
			else {

				System.out.println("4.1");
				empShiftSched = shiftScheduleService.getPreviousShiftScheduleByUser(employee.getUserId());
				
				if( empShiftSched == null) {
					empShiftSched = new ShiftSchedule();
					numMissedLogin = 0;
					
					System.out.println("4.2");	
				} else {
					List<ShiftScheduleTime> shiftSchedTimes = shiftScheduleTimeService.findMissedTimeByShiftSchedId(empShiftSched.getShftSchedId());
					numMissedLogin = shiftSchedTimes.size();
					System.out.println("4.3");
				}
				empArray.add(new EmployeeBean(employee, empShiftSched, numMissedLogin));
			}
			
			list.put("directreports", empArray);
			log.debug("getDirectReportsShiftToday() - End");
		}	
		//get all the shift schedules 
		//get the number of missed logins for the todays shift schedule of each employee

		return new ResponseEntity<>(list, HttpStatus.OK);

	}
	
	
	
	@GetMapping(path = "/user-options/{optionCode}/{optionValue}/{userId}")
	public ResponseEntity<String> setUserOption(@PathVariable String optionCode, @PathVariable String optionValue, @PathVariable int userId) throws InterruptedException, IOException{
		log.debug("setUserOption() - Start");
		
		UserOption userOption = new UserOption(userId, optionCode, optionValue);
		userOptionService.saveOption(userOption);
			
		ResponseEntity<String> entity = new ResponseEntity<>("{\"data\": \""+optionCode+" updated successfully\"}", HttpStatus.OK);
		log.debug("setUserOption() - End");
		return entity;
	}
	
}

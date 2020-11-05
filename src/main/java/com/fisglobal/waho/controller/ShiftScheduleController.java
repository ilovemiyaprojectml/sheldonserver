package com.fisglobal.waho.controller;


import java.io.ObjectInputStream.GetField;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fisglobal.waho.WahoServerApplication;
import com.fisglobal.waho.beans.AcknowledgeBean;
import com.fisglobal.waho.beans.DateBeans;
import com.fisglobal.waho.beans.EndShiftBean;
import com.fisglobal.waho.beans.ShiftNotificationFinalBeans;
import com.fisglobal.waho.beans.ShiftScheduleBean;
import com.fisglobal.waho.model.ShiftSchedule;
import com.fisglobal.waho.model.ShiftScheduleTime;
import com.fisglobal.waho.model.SystemCode;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.services.ShiftScheduleEmitterService;
import com.fisglobal.waho.services.ShiftScheduleService;
import com.fisglobal.waho.services.ShiftScheduleTimeService;
import com.fisglobal.waho.services.SystemCodeService;
import com.fisglobal.waho.services.UserService;
import com.fisglobal.waho.utils.DateUtils;

@CrossOrigin(origins = {"${cross.origins}", "*"}) 
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ShiftScheduleController {
	static Logger log = LoggerFactory.getLogger(ShiftScheduleController.class);
	
	@Autowired
	private ShiftScheduleService shiftScheduleService;
		
	@Autowired
	private ShiftScheduleTimeService shiftScheduleTimeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShiftScheduleEmitterService shiftScheduleEmitterService;
	
	@Autowired
	private SystemCodeService systemCodeService;
	
	//newly created function to check if there is an existing shift on that user
	@GetMapping("/getshiftsched/user/{id}")
	public ResponseEntity<?> getShiftSched(@PathVariable int id){
		log.debug("getShiftSched() - Start");
		
		ShiftSchedule currentSchedule = shiftScheduleService.getCurrentShiftScheduleByUser(id);
		
		log.debug("getShiftSched() - End");
		return (currentSchedule != null) ? (new ResponseEntity<>(currentSchedule,HttpStatus.OK)) : new ResponseEntity<>("{\"data\":\"No current shift for this user\"}", HttpStatus.OK);
	}
	

	
	
	@PostMapping("/shiftSchedules/user/{id}")
	public ResponseEntity<?> createShiftSchedule(@RequestBody ShiftScheduleBean shiftScheduleBean, @PathVariable Long id) {
		log.debug("createShiftSchedule() - Start");
		
		//create new shift schedule for user
		Optional<User> currentUser = userService.findById(id.intValue());
		LocalDateTime newStartShiftDate = DateUtils.convertToLocalDateTime(shiftScheduleBean.getShiftStartDate() + " " + shiftScheduleBean.getShiftStartTime());
		LocalDateTime newEndShiftDate = DateUtils.convertToLocalDateTime(shiftScheduleBean.getShiftEndDate() + " " + shiftScheduleBean.getShiftEndTime());
		ShiftSchedule shiftSchedule = new ShiftSchedule(newStartShiftDate, newEndShiftDate, null, LocalDateTime.now(), Integer.parseInt(shiftScheduleBean.getAllowedBreaks()),LocalDateTime.now(), id.intValue(), id.intValue(), "Y");
		shiftSchedule.setUser(currentUser.get());
		
		ShiftSchedule newShiftSchedule = shiftScheduleService.saveShiftSchedule(shiftSchedule);
		
		
		ShiftScheduleTime shiftScheduleTime = new ShiftScheduleTime();
		shiftScheduleTime.setSchedTime(LocalDateTime.now());
		shiftScheduleTime.setSchedTimeStatus("STARTED");
		shiftScheduleTime.setDateCreated(LocalDateTime.now());
		shiftScheduleTime.setDateLastUpdated(LocalDateTime.now());
		shiftScheduleTime.setCreatedBy(id.intValue());
		shiftScheduleTime.setLastUpdatedBy(id.intValue());
		shiftScheduleTime.setIsActive("Y");
		shiftScheduleTime.setShiftSchedule(newShiftSchedule);
		
		shiftScheduleTimeService.saveShiftScheduleTime(shiftScheduleTime);
		
		//this is commented due to it adding a time preemptively
		/*
		//get the number of hours difference of startDateTime and endDateTime
		LocalDateTime startTemp = LocalDateTime.from(newStartShiftDate);
		long hours = startTemp.until(newEndShiftDate, ChronoUnit.HOURS);
		
		System.out.println("0: startTemp===="+startTemp.toString());
		for (int i=0; i<hours; i++) {
			startTemp = startTemp.plusHours(1);
			System.out.println((i+1)+": startTemp===="+startTemp.toString());
			
			ShiftScheduleTime shiftScheduleTime = new ShiftScheduleTime();
			shiftScheduleTime.setSchedTime(startTemp);
			shiftScheduleTime.setOrderPriority((i+1));
			shiftScheduleTime.setSchedTimeStatus("PENDING");
			shiftScheduleTime.setDateCreated(LocalDateTime.now());
			shiftScheduleTime.setDateLastUpdated(LocalDateTime.now());
			shiftScheduleTime.setCreatedBy(id.intValue());
			shiftScheduleTime.setLastUpdatedBy(id.intValue());
			shiftScheduleTime.setIsActive("Y");
			shiftScheduleTime.setShiftSchedule(newShiftSchedule);
			
			shiftScheduleTimeService.saveShiftScheduleTime(shiftScheduleTime);
		}
		System.out.println("Last: startTemp===="+newEndShiftDate.toString());
		*/
		
		
		
		System.out.println("NEW CREATED: "+newShiftSchedule.toString());
		
		//create several shift schedule time base on the new created shift schedule
		
		//will return success or failed message for creation
		
		log.debug("createShiftSchedule() - End");
		return new ResponseEntity<>(newShiftSchedule,HttpStatus.OK);
	}
	
	@PostMapping(path = "/end-shift", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> endShift(@RequestBody EndShiftBean endShiftBean){
		log.debug("endShift() - Start"); 
		
		log.debug("Ending Shift"); 
		ShiftSchedule userShift = shiftScheduleService.getCurrentShiftScheduleByUser(endShiftBean.getUserId());
		
		if (userShift == null) {
		    log.debug("Shift Already Ended");
		    return new ResponseEntity<>("{\"data\":\"Shift Already Ended\"}", HttpStatus.OK);
		}
		
		userShift.setActualEndDate(LocalDateTime.now());
		shiftScheduleService.saveShiftSchedule(userShift);
		
		log.debug("Creating ENDED entry");
		ShiftScheduleTime shiftScheduleTime = new ShiftScheduleTime();
		shiftScheduleTime.setSchedTime(LocalDateTime.now());
		shiftScheduleTime.setSchedTimeStatus(userShift.hasShiftEnded()? "ENDED" : "ENDED(UNDERTIME)");
		shiftScheduleTime.setDateCreated(LocalDateTime.now());
		shiftScheduleTime.setDateLastUpdated(LocalDateTime.now());
		shiftScheduleTime.setCreatedBy(endShiftBean.getUserId());
		shiftScheduleTime.setLastUpdatedBy(endShiftBean.getUserId());
		shiftScheduleTime.setIsActive("Y");
		shiftScheduleTime.setShiftSchedule(userShift);
		
		shiftScheduleTimeService.saveShiftScheduleTime(shiftScheduleTime);
		
		log.debug("Cleaning up pending Shift Schedule Times");
		shiftScheduleTimeService.updateMissedPendingSchedTime(userShift.getShftSchedId());
		
		if (!userShift.hasShiftEnded()) {
		    log.debug("Undertime. Sending notification to Manager");
		    shiftScheduleEmitterService.notifyManager_Undertime(endShiftBean.getUserId());
		}
		
		log.debug("endShift() - End");
		return new ResponseEntity<>("{\"data\":\"Shift Ended\"}", HttpStatus.OK);
	}
	
	@GetMapping("/shift-login-alerts/user/{id}")
	public ResponseEntity<?> updateShiftLoginAlert(@PathVariable Long id) {
		log.debug("updateShiftLoginAlert() - Start");
		//get current shift schedule
		ShiftSchedule currShiftSched = shiftScheduleService.getCurrentShiftScheduleByUser(id.intValue());
		if (currShiftSched != null) {
			log.debug("Current shift loaded.");
			ShiftScheduleTime currShiftScheduleTime = shiftScheduleTimeService.findCurrentShiftScheduleTimeByShiftSchedId(currShiftSched.getShftSchedId());
			if (currShiftScheduleTime != null) {
				log.debug("Current shift time loaded.");
				currShiftScheduleTime.setSchedTimeStatus("CONFIRMED");
				shiftScheduleTimeService.saveShiftScheduleTime(currShiftScheduleTime);
				return new ResponseEntity<>(currShiftScheduleTime,HttpStatus.OK);
			} else {
				log.debug("No current shift time login associated with this request.");
				return new ResponseEntity<>("No current shift time login associated with this request.",HttpStatus.OK);
			}
			
		} else {
			log.debug("No current shift schedule associated with this request.");
			log.debug("updateShiftLoginAlert() - End");
			return new ResponseEntity<>("No current shift schedule associated with this request.",HttpStatus.OK);
		}
	}
	
	/*
	@Autowired
	private UserService userService;

	@GetMapping(path = "/getActive")
	public List<User> viewActive(){
		//List<User> user = userService.getUsersWithShift();
		
		return userService.getUsersWithShift();
		
		
	}
	*/
	
	
	/*
	@GetMapping(path = "/savenotif")
	public ResponseEntity<?> saveNotification(){
		
		NotificationQueue queue = new NotificationQueue(0,2,LocalTime.now());
		
		service.saveNotif(queue);
		
		return new ResponseEntity<>("Saved", HttpStatus.OK);
	}
	*/
	
	/*
	@GetMapping(path = "/time")
	public ResponseEntity<?> getTime(){
		
		List<NotificationQueue> times = service.getNotifTime(LocalTime.of(18, 18));
		return new ResponseEntity<>(times,HttpStatus.OK);
		
		
	}
	*/
	
	@GetMapping(path = "/shift-notif/pending/{id}")
	public ResponseEntity<?> getPending(@PathVariable int id){
		log.debug("getPending() - Start");
		List<ShiftScheduleTime> allPending = shiftScheduleTimeService.getPendingSchedTime(id);
		
		log.debug("getPending() - End");
		return new ResponseEntity<>(allPending,HttpStatus.OK);
	}
	
	@GetMapping(path = "/shift-notif/pendingManager/{id}")
	public ResponseEntity<?> getPendingManager(@PathVariable int id){
		log.debug("getPendingManager() - Start");
		List<User> employees = userService.getUsersByUserParentId(id);
		List<ShiftScheduleTime> allPending = shiftScheduleTimeService.getPendingManagerSchedTime(id, employees);
		
		for (ShiftScheduleTime pending : allPending) {
			User currentUser = null;
    	    Optional<User> optional = userService.findById(pending.getCreatedBy());
			if(optional.isPresent()) {
				currentUser = optional.get();
				pending.setTemp(currentUser.getFirstName() + " " + currentUser.getLastName());
			}
		}
		
		log.debug("getPendingManager() - End");
		return new ResponseEntity<>(allPending,HttpStatus.OK);
	}
	
	// query by user Id and date
	@GetMapping(path = "/shift-notif/{date}/{id}")
	public ResponseEntity<?> getShiftNotif(@PathVariable String date, @PathVariable int id){
		log.debug("getShiftNotif() - Start");
		ShiftSchedule currentShiftSched = shiftScheduleService.getCurrentShiftScheduleByUser(id);
		Object firsAndLastShiftSchedIds = shiftScheduleService.getFirsAndLastShiftSchedIds(id);
		
		HashMap<String, Object> schedule = new HashMap<String, Object>();
		try {
			if (currentShiftSched!=null) {
				List<ShiftNotificationFinalBeans> schedTime = shiftScheduleTimeService.findSchedTimeByShiftIdAndUserId(currentShiftSched.getShftSchedId(), id);
				log.debug("getShiftNotif() - currentShiftSched loaded");
				schedule.put("shiftSchedId", currentShiftSched.getShftSchedId());
				schedule.put("schedTimes", schedTime);
				schedule.put("remainingBreakTime", currentShiftSched.getRemainingBreakTime());
			} else {
				schedule.put("shiftSchedId", 0);
				schedule.put("schedTimes", null);
				schedule.put("remainingBreakTime", "");
			}
			schedule.put("firsAndLastShiftSchedIds", firsAndLastShiftSchedIds);
			return new ResponseEntity<>(schedule, HttpStatus.OK);
		} catch (Exception e) {
			log.debug("getShiftNotif() - No result found");
			return new ResponseEntity<>("No result found", HttpStatus.OK);
		} finally {
			log.debug("getShiftNotif() - End");
		}
		
	}
	
	   // historical query by user Id and date
    @GetMapping(path = "/shift-prev/{date}/{id}")
    public ResponseEntity<?> getShiftPrevious(@PathVariable String date, @PathVariable int id){
        log.debug("getShiftPrevious() - Start");
        ShiftSchedule previousShiftSched = shiftScheduleService.getPreviousShiftScheduleByUser(id);
        Object firsAndLastShiftSchedIds = shiftScheduleService.getFirsAndLastShiftSchedIds(id);
        HashMap<String, Object> schedule = new HashMap<String, Object>();
        try {
            log.debug("getShiftPrevious() - loading previous shift id: " + previousShiftSched.getShftSchedId());
            List<ShiftNotificationFinalBeans> schedTime = shiftScheduleTimeService.findSchedTimeByShiftIdAndUserId(previousShiftSched.getShftSchedId(), id);
            log.debug("getShiftPrevious() - previousShiftSched loaded");
            schedule.put("shiftSchedId", previousShiftSched.getShftSchedId());
			schedule.put("schedTimes", schedTime); 
			schedule.put("firsAndLastShiftSchedIds", firsAndLastShiftSchedIds);
            return new ResponseEntity<>(schedule, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getShiftPrevious() - No result found", e);
            return new ResponseEntity<>("No result found", HttpStatus.OK);
        } finally {
            log.debug("getShiftPrevious() - End");
        }
        
    }
	
	@GetMapping(path = "/alldate/{id}")
	public ResponseEntity<?> getAllDate(@PathVariable int id){
		log.debug("getAllDate() - Start");
		
		List<String> allDate = shiftScheduleTimeService.getAllDistinctDateById(id);
		List<DateBeans> allDate2 = new ArrayList<DateBeans>();
		
		for(String rows: allDate) {
			allDate2.add(new DateBeans(rows));
		}
		
		log.debug("getAllDate() - End");
		return new ResponseEntity<>(allDate2, HttpStatus.OK);
		
	}
		
	
	
	/*
	@GetMapping(path = "/allusers")
	public ResponseEntity<?> getUsers(){
		
		
		List<User> allUsers = userService.getAllUsers();
		return new ResponseEntity<>(allUsers,HttpStatus.OK);
		
	}
	*/
	@GetMapping(path ="/getcurrent")
	public ResponseEntity<?> getCurrentUsers(){
		log.debug("getCurrentUsers() - Start");
		
		List<ShiftSchedule> sched = shiftScheduleService.getCurrentShiftScheduleAllUser();
		
		log.debug("getCurrentUsers() - End");
		return new ResponseEntity<>(sched, HttpStatus.OK);
		
	}
	
	@GetMapping(path ="/getcurrent/{id}")
	public ResponseEntity<?> getCurrentUsers(@PathVariable int id){
		log.debug(String.format("getCurrentUsers(id) - Start"));
		
		ShiftSchedule sched = shiftScheduleService.getCurrentShiftScheduleByUser(id);
		sched.setIsShiftEnded();

		log.debug(String.format("getCurrentUsers(id) - End"));
		return new ResponseEntity<>(sched, HttpStatus.OK);
		
	}
	
	
	@GetMapping(path ="/getschedtime/{schedId}")
	public ResponseEntity<?> getSched(@PathVariable int schedId) {
		log.debug(String.format("getSched(schedId) - Start"));
		
		ShiftScheduleTime scheduleTime = shiftScheduleTimeService.getSchedBySchedTimeId(schedId);
		
		log.debug(String.format("getSched(schedId) - Start"));
		return new ResponseEntity<>(scheduleTime,HttpStatus.OK);
		
	}
	
	@PostMapping(path = "/shiftSchedTime/reason", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateShiftSchedTimeReason(@RequestBody AcknowledgeBean ack){
		log.debug("updateShiftSchedTimeReason() - Start");
		ShiftScheduleTime scheduleTime = shiftScheduleTimeService.getSchedBySchedTimeId(ack.getSchedTimeId());
		
		if(scheduleTime != null) {
			scheduleTime.setRemarks(ack.getRemarks());
			shiftScheduleTimeService.saveShiftScheduleTime(scheduleTime);
			log.debug("updateShiftSchedTimeReason() - Reason Saved");
			return new ResponseEntity<>("{\"data\": \"Reason Saved\"}", HttpStatus.OK);
		}
		else {
			log.debug("updateShiftSchedTimeReason() - Nothing to save");
			return new ResponseEntity<>("{\"data\": \"Nothing to save\"}", HttpStatus.BAD_REQUEST);
		}
		
	}
	

   @PostMapping(path = "/validateShift/user/{id}")
    public ResponseEntity<?> validateShift(@RequestBody ShiftScheduleBean shiftScheduleBean, @PathVariable Long id) {
        log.debug("validateShift() - Start");
        String response = "SUCCESS";
       
        LocalDateTime localTimeNow = LocalDateTime.now().minusMinutes(1);
        LocalDateTime startShiftDate = DateUtils.convertToLocalDateTime(shiftScheduleBean.getShiftStartDate() + " " + shiftScheduleBean.getShiftStartTime());
        LocalDateTime endShiftDate = DateUtils.convertToLocalDateTime(shiftScheduleBean.getShiftEndDate() + " " + shiftScheduleBean.getShiftEndTime());
        log.debug(String.format("localTimeNow[%s]", localTimeNow));
        log.debug(String.format("startShiftDate[%s]", startShiftDate));
        log.debug(String.format("endShiftDate[%s]", endShiftDate));
        
        if (startShiftDate.isBefore(localTimeNow)) {
            response = "ERROR: Start Shift should not be earlier than current time";
        } else if (startShiftDate.isAfter(endShiftDate) || startShiftDate.isEqual(endShiftDate)) {
            response = "ERROR: End Shift should be later than Start Shift";
        } else if (endShiftDate.isBefore(localTimeNow)) {
            response = "ERROR: End Shift should not be ealier than current time";
        }
        
        log.debug(String.format("validateShift() - [%s]", response));
        log.debug("validateShift() - End");
        return new ResponseEntity<>(String.format("{\"data\":\"%s\"}", response), HttpStatus.OK);
    }
	   
   @GetMapping(path = "/shift/{selectedShiftSchedId}/{direction}/{id}")
	public ResponseEntity<?> getShiftSchedule(@PathVariable int selectedShiftSchedId, @PathVariable int direction, @PathVariable int id){
	    log.debug("getShiftSchedule() - Start");
	    ShiftSchedule selectedShiftSched = shiftScheduleService.getShiftSchedule(selectedShiftSchedId, direction, id);
	    Object firsAndLastShiftSchedIds = shiftScheduleService.getFirsAndLastShiftSchedIds(id);
	    HashMap<String, Object> schedule = new HashMap<String, Object>();
	    try {
	        log.debug("getShiftSchedule() - loading selected shift id: " + selectedShiftSched.getShftSchedId());
	        List<ShiftNotificationFinalBeans> schedTime = shiftScheduleTimeService.findSchedTimeByShiftIdAndUserId(selectedShiftSched.getShftSchedId(), id);
	        log.debug("getShiftSchedule() - selected shift loaded");
	        schedule.put("shiftSchedId", selectedShiftSched.getShftSchedId());
			schedule.put("schedTimes", schedTime);
			schedule.put("firsAndLastShiftSchedIds", firsAndLastShiftSchedIds);
	        return new ResponseEntity<>(schedule, HttpStatus.OK);
	    } catch (Exception e) {
	        log.error("getShiftSchedule() - No result found", e);
	        return new ResponseEntity<>("No result found", HttpStatus.OK);
	    } finally {
	        log.debug("getShiftSchedule() - End");
	    }
	    
	}
	   
	@GetMapping(path ="/getAllowedBreakTime", produces = "application/json")
	public ResponseEntity<?> getAllowedBreakMinutes() {
		System.out.println("getAllowedBreakMinutes()....1");
		log.debug(String.format("getAllowedBreakMinutes() - Start"));
		List<SystemCode> systemCodes = systemCodeService.getSystemCode("BREAK_TIME_ALLOWED");
		
		List<Map<String, Object>> maps = new ArrayList<>();

		for (SystemCode systemCode : systemCodes) {
			System.out.println("getAllowedBreakMinutes()....2");
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
		System.out.println("getAllowedBreakMinutes()....END");
		log.debug(String.format("getAllowedBreakMinutes() - End"));
		return new ResponseEntity<>(maps,HttpStatus.OK);
		
	}
		
	@PostMapping(path = "/start-break", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> startBreak(@RequestBody EndShiftBean breakShiftBean){
		log.debug("startBreak() - Start"); 
		
		ShiftSchedule userShift = shiftScheduleService.getCurrentShiftScheduleByUser(breakShiftBean.getUserId());
		
		log.debug("Creating BREAK STARTED entry");
		ShiftScheduleTime shiftScheduleTime = new ShiftScheduleTime();
		shiftScheduleTime.setSchedTime(LocalDateTime.now());
		shiftScheduleTime.setSchedTimeStatus("BREAK STARTED");
		shiftScheduleTime.setDateCreated(LocalDateTime.now());
		shiftScheduleTime.setDateLastUpdated(LocalDateTime.now());
		shiftScheduleTime.setCreatedBy(breakShiftBean.getUserId());
		shiftScheduleTime.setLastUpdatedBy(breakShiftBean.getUserId());
		shiftScheduleTime.setIsActive("Y");
		shiftScheduleTime.setShiftSchedule(userShift);
		
		shiftScheduleTimeService.saveShiftScheduleTime(shiftScheduleTime);
		
		log.debug("startBreak() - End");
		return new ResponseEntity<>("{\"data\":\"Break Started\"}", HttpStatus.OK);
	}
		
	@PostMapping(path = "/end-break", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> endBreak(@RequestBody EndShiftBean breakShiftBean){
		log.debug("endBreak() - Start"); 
		ShiftSchedule userShift = shiftScheduleService.getCurrentShiftScheduleByUser(breakShiftBean.getUserId());
		
		log.debug("Creating BREAK ENDED entry");
		ShiftScheduleTime shiftScheduleTime = new ShiftScheduleTime();
		shiftScheduleTime.setSchedTime(LocalDateTime.now());
		shiftScheduleTime.setSchedTimeStatus("BREAK ENDED");
		shiftScheduleTime.setDateCreated(LocalDateTime.now());
		shiftScheduleTime.setDateLastUpdated(LocalDateTime.now());
		shiftScheduleTime.setCreatedBy(breakShiftBean.getUserId());
		shiftScheduleTime.setLastUpdatedBy(breakShiftBean.getUserId());
		shiftScheduleTime.setIsActive("Y");
		shiftScheduleTime.setShiftSchedule(userShift);
		
		shiftScheduleTimeService.saveShiftScheduleTime(shiftScheduleTime);
		
		log.debug("endBreak() - End");
		return new ResponseEntity<>("{\"data\":\"Break Ended\"}", HttpStatus.OK);
	}

}

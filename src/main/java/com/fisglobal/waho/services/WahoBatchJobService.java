package com.fisglobal.waho.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fisglobal.waho.beans.MessageBean;
import com.fisglobal.waho.model.ShiftSchedule;
import com.fisglobal.waho.model.ShiftScheduleTime;
import com.fisglobal.waho.model.SystemCode;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.model.UserOption;
import com.fisglobal.waho.model.UserTempAccess;

@Service
@Transactional
public class WahoBatchJobService  extends EmitterService{
    static Logger log = LoggerFactory.getLogger(WahoBatchJobService.class);

    @Autowired
    private ShiftScheduleService shiftScheduleService;
    
	@Autowired
	private ShiftScheduleTimeService shiftScheduleTimeService;
	
	@Autowired
	private SystemCodeService systemCodeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserOptionService userOptionService;
	
	
	/***
	 * Every minute, system will check if there are schedule time that are already past the 
	 * current time and is still tagged as PENDING. Any matches fetched will be updated
	 * as MISSED.
	 */
	@Scheduled(cron = "${missed.notif.checker.schedule}") 
	public void runUpdateLoginTimeBatchJob() {
	    System.out.println("Running runUpdateLoginTimeBatchJob :: " + Calendar.getInstance().getTime());
	    
		SystemCode sysCode =  systemCodeService.getSystemCode("ALERT_CONFIG","ALERT_GRACE_PERIOD");
		int GRACE_PERIOD_MINUTES = Integer.parseInt(sysCode.getValue1()); 
		
	    List<ShiftScheduleTime> shiftScheduleTimes = shiftScheduleTimeService.findPendingAndExpiredSchedTime();
	    List<SseEmitter> deadEmitters = new ArrayList<>();
	    
		if(shiftScheduleTimes == null) {
			System.out.println("No PENDING shift schedule time");
			log.debug("No PENDING shift schedule time");
		} else {
			for (ShiftScheduleTime shiftScheduleTime : shiftScheduleTimes) {
		    	//System.out.println(shiftScheduleTime.toString());
		    	long min = ChronoUnit.MINUTES.between(shiftScheduleTime.getSchedTime(), LocalDateTime.now());
		    	System.out.println("Tracker1");
		    	
		    	//check if the selected time is past 15 minutes already
		    	int alertGracePeriod = GRACE_PERIOD_MINUTES;
		    	
		    	if(min >= alertGracePeriod) {
                    log.debug("MISSED Acknowledgement");
		    	    int id = shiftScheduleTime.getCreatedBy();
		    	    
		    	    int managerid = -1;
		    	    User currentUser = null;
		    	    Optional<User> optional = userService.findById(id);
					if(optional.isPresent()) {
						currentUser = optional.get();
						managerid = currentUser.getUserParentId();
					}
		    	    
		    	    boolean isMissedOvertime = LocalDateTime.now().isAfter(shiftScheduleTime.getShiftSchedule().getSchedEndDate())
		    	            && shiftScheduleTime.getSchedTime().isAfter(shiftScheduleTime.getShiftSchedule().getSchedEndDate());
		    	    String shiftStatus = isMissedOvertime ? "MISSED(END-SHIFT)" : "MISSED";
		    	    String emitterString = isMissedOvertime ? "missendshift" : "missalert"; 
		    	    String shiftRemarks = isMissedOvertime ? "SYSTEM: Did not end shift" : "";
		    	    
		    		shiftScheduleTime.setSchedTimeStatus(shiftStatus);
		    		shiftScheduleTime.setRemarks(shiftRemarks);
			    	shiftScheduleTimeService.saveShiftScheduleTime(shiftScheduleTime);
			    	
			    	// Call back-end EndShift in case front-end is logged off or unavailable
			    	ShiftSchedule userShift = shiftScheduleTime.getShiftSchedule();
			    	if (isMissedOvertime && !userShift.isShiftComplete()) {
			    	    try {
	                        log.debug("System End Shift Triggered");
    			    	    userShift.setActualEndDate(LocalDateTime.now());
                            shiftScheduleService.saveShiftSchedule(userShift);
                            
    			    	    shiftScheduleTimeService.createEndedEntry(shiftScheduleTime.getShiftSchedule(), isMissedOvertime);
			    	    }catch (Exception e) {
			    	        log.error("Error Encountered during System End Shift", e);
			    	    }
			    	}
			    	
			    	SseEmitter emitter = this.getEmittersMap().get(new Long(id));
			    	if (emitter != null) {
			    		try {
			    			HashMap<String, Object> emitterData = new HashMap<String, Object>();
			    			emitterData.put("emitterString", emitterString);
			    			emitterData.put("empName", "");
			    			emitterData.put("schedTime", shiftScheduleTime);
				    		emitter.send(SseEmitter.event().name(String.valueOf(id)).data(emitterData));
							emitter.complete();
						} catch (IOException e) {
	                        emitter.completeWithError(e);
						} catch (IllegalStateException e) {
							System.out.println("============IllegalStateException : WahoBatchJobService : runUpdateLoginTimeBatchJob() MISSED ============");
							emitter.completeWithError(e);
						}
			    	}
			    	
			    	SseEmitter emitterManager = this.getEmittersMap().get(new Long(managerid));
			    	if (emitterManager != null) {
			    		try {
			    			HashMap<String, Object> emitterData = new HashMap<String, Object>();
			    			emitterData.put("emitterString", emitterString + "Manager");
			    			emitterData.put("empName", currentUser.getFirstName() + " " + currentUser.getLastName());
			    			emitterData.put("schedTime", shiftScheduleTime);
			    			emitterManager.send(SseEmitter.event().name(String.valueOf(managerid)).data(emitterData));
			    			emitterManager.complete();
						} catch (IOException e) {
							emitterManager.completeWithError(e);
						} catch (IllegalStateException e) {
							System.out.println("============IllegalStateException : WahoBatchJobService : runUpdateLoginTimeBatchJob() MISSED ============");
							emitterManager.completeWithError(e);
						}
			    	}
		    	}
		    	//otherwise, do nothing
		    	else {
		    		System.out.println("No sched time updated!");
		    		log.debug("No sched time updated!");
		    	}
			}
		}
	}
	

	/***
	 * Every minute, system will check if there are users on break. System will deduct 
	 * 60 seconds or 1 minutes on the remaining break time for user user's current shift.
	 * DB field involved: wh_user_shift_schedules.remaining_break_time
	 */
	@Scheduled(cron = "${break.time.checker.schedule}") 
	public void runUpdateBreakTimeBatchJob() {
	    System.out.println("Running runUpdateBreakTimeBatchJob :: " + Calendar.getInstance().getTime());
	   
	    //1. get all the users with wh_user_options.option_code BREAK_TIME_DISABLE = 'N'
	    List<UserOption> userOptions = userOptionService.getUserOptions("BREAK_TIME_DISABLE", "N");
	    
	    //2. get the current and active shift schedule of that user 
	    for (UserOption userOption : userOptions) {
	    	int userId = userOption.getUserId();
	    	ShiftSchedule userShift = shiftScheduleService.getCurrentShiftScheduleByUser(userId);
	    	if (userShift!=null) {
	    		if (userShift.getRemainingBreakTime() > 0) {
		    		//deduct 60secs or 1 minute on the remaining_break_time
	    			int remaingBreakTime =  userShift.getRemainingBreakTime() - 60;
		    		userShift.setRemainingBreakTime(remaingBreakTime);
		    		userShift.setDateLastUpdated(LocalDateTime.now());
		    		shiftScheduleService.saveShiftSchedule(userShift);
		    		
		    		//send to client the remaining break time value
		    		SseEmitter emitter = this.getEmittersMap().get(new Long(userId));
		    		if (emitter!=null) {
						try {
							HashMap<String, Object> emitterData = new HashMap<String, Object>();
			    			emitterData.put("emitterString", "breakTimeBatchJob");
			    			emitterData.put("empName", "");
			    			emitterData.put("remainingBreakTime", remaingBreakTime);
			    			emitterData.put("userId", userId);
				    		emitter.send(SseEmitter.event().name(String.valueOf(userId)).data(emitterData));
							emitter.complete();
						} catch (IOException e) {
							emitter.completeWithError(e);
						} catch (IllegalStateException e) {
							System.out.println("============IllegalStateException WahoBatchJobService : runUpdateBreakTimeBatchJob() ============");
							emitter.completeWithError(e);
						}
					}
		    	} else {
		    		//if remaining_break_time == 0, then set the BREAK_TIME_DISABLE to 'Y
		    		userOption.setOptionValue("Y");
		    		userOption.setDateLastUpdated(LocalDateTime.now());
		    		userOptionService.saveOption(userOption);
		    	}
	    		
	    		//push notif to client that remaining_break_time is already 0 (zero).
	    	}
		}
	    
	}
}

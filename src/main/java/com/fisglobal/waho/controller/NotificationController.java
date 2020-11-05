package com.fisglobal.waho.controller;



import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

import com.fisglobal.waho.beans.AcknowledgeBean;
import com.fisglobal.waho.model.ShiftScheduleTime;
import com.fisglobal.waho.services.NotificationService;
import com.fisglobal.waho.services.ShiftScheduleEmitterService;
import com.fisglobal.waho.services.ShiftScheduleTimeService;
import com.fisglobal.waho.services.WahoBatchJobService;



@CrossOrigin
@RestController
@RequestMapping("/api")
public class NotificationController{
	static Logger log = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired
	WahoBatchJobService missService;
	
	@Autowired
	NotificationService service;
	
	@Autowired
	ShiftScheduleEmitterService shiftScheduleEmitterService;
	
	@Autowired
	ShiftScheduleTimeService scheduleTimeService;
	
	final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	
	
	@GetMapping("/notification/{id}")
	public ResponseEntity<SseEmitter> doNotify(@PathVariable Long id) throws InterruptedException, IOException{
		log.debug("doNotify() - Start");
		
		final SseEmitter emitter = new SseEmitter();
		try {
			if (service.getEmittersMap().get(id) != null) {
				service.removeEmitter(id);
			} 		
			service.addEmitter(id, emitter);
			
			emitter.onCompletion(() -> service.removeEmitter(id));
			emitter.onTimeout(() -> service.removeEmitter(id));
			
			ResponseEntity<SseEmitter> entity = new ResponseEntity<>(emitter, HttpStatus.OK);
			
			log.debug("doNotify() - End");
			return entity;
		} catch (IllegalStateException e) {
			System.out.println("============IllegalStateException NotificationController : doNotify() ============");
			emitter.completeWithError(e);
			
			ResponseEntity<SseEmitter> entity = new ResponseEntity<>(emitter, HttpStatus.SERVICE_UNAVAILABLE);
			return entity;
		}
	}
	
	@GetMapping(path = "/notification/missalert/{id}")
	public ResponseEntity<SseEmitter> doNotifyMiss(@PathVariable Long id) throws InterruptedException, IOException{
		log.debug("doNotifyMiss() - Start");
		
		final SseEmitter emitter = new SseEmitter();
		try {
			if (missService.getEmittersMap().get(id) != null) {
				missService.removeEmitter(id);
			} 		
			missService.addEmitter(id, emitter);
			
			emitter.onCompletion(() -> missService.removeEmitter(id));
			emitter.onTimeout(() -> missService.removeEmitter(id));
			
			ResponseEntity<SseEmitter> entity = new ResponseEntity<>(emitter, HttpStatus.OK);
			
			log.debug("doNotifyMiss() - End");
			return entity;
		} catch (IllegalStateException e) {
			System.out.println("============IllegalStateException NotificationController : doNotifyMiss() ============");
			emitter.completeWithError(e);
			
			ResponseEntity<SseEmitter> entity = new ResponseEntity<>(emitter, HttpStatus.SERVICE_UNAVAILABLE);
			return entity;
		}
	}
	
	@PostMapping(path = "/notification/ack", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> acknowledgeNotif(@RequestBody AcknowledgeBean ack){
		log.debug("acknowledgeNotif() - Start");
		
		ShiftScheduleTime scheduleTime = scheduleTimeService.getSchedBySchedTimeId(ack.getSchedTimeId());
		
		if(scheduleTime != null) {
			scheduleTime.setSchedTimeStatus("CONFIRMED");
			scheduleTimeService.saveShiftScheduleTime(scheduleTime);
			log.debug("acknowledgeNotif() - Notification Acknowledged");
			return new ResponseEntity<>("{\"data\": \"Notification Acknowledged\"}", HttpStatus.OK);
		}
		else {
			log.debug("acknowledgeNotif() - Nothing to acknowledge");
			return new ResponseEntity<>("{\"data\": \"Nothing to acknowledge\"}", HttpStatus.BAD_REQUEST);
		}
		
	}

	@PostMapping(path = "/notification/ackManager", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> acknowledgeNotifManager(@RequestBody AcknowledgeBean ack){
		log.debug("acknowledgeNotifManager() - Start");
		
		ShiftScheduleTime scheduleTime = scheduleTimeService.getSchedBySchedTimeId(ack.getSchedTimeId());
		
		if(scheduleTime != null) {
			scheduleTime.setIsAcknowledged("Y");
			scheduleTimeService.saveShiftScheduleTime(scheduleTime);
			log.debug("acknowledgeNotifManager() - Notification Acknowledged");
			return new ResponseEntity<>("{\"data\": \"Notification Acknowledged\"}", HttpStatus.OK);
		}
		else {
			log.debug("acknowledgeNotifManager() - Nothing to acknowledge");
			return new ResponseEntity<>("{\"data\": \"Nothing to acknowledge\"}", HttpStatus.BAD_REQUEST);
		}
		
	}
	
    @GetMapping("/notification/shiftnotif/{id}")
    public ResponseEntity<SseEmitter> doShiftNotify(@PathVariable Long id) throws InterruptedException, IOException{
        log.debug("doShiftNotify() - Start");
        
        final SseEmitter emitter = new SseEmitter();
        try {
            if (shiftScheduleEmitterService.getEmittersMap().get(id) != null) {
                shiftScheduleEmitterService.removeEmitter(id);
            }       
            shiftScheduleEmitterService.addEmitter(id, emitter);
            
            emitter.onCompletion(() -> shiftScheduleEmitterService.removeEmitter(id));
            emitter.onTimeout(() -> shiftScheduleEmitterService.removeEmitter(id));
            
            ResponseEntity<SseEmitter> entity = new ResponseEntity<>(emitter, HttpStatus.OK);
            
            log.debug("doShiftNotify() - End");
            return entity;
        } catch (IllegalStateException e) {
            System.out.println("============IllegalStateException NotificationController : doShiftNotify() ============");
            emitter.completeWithError(e);
            
            ResponseEntity<SseEmitter> entity = new ResponseEntity<>(emitter, HttpStatus.SERVICE_UNAVAILABLE);
            return entity;
        }
    }
	
}









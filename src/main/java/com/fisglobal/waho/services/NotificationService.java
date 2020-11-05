package com.fisglobal.waho.services;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fisglobal.waho.model.NotificationQueue;
import com.fisglobal.waho.model.ShiftSchedule;
import com.fisglobal.waho.model.ShiftScheduleTime;
import com.fisglobal.waho.model.SystemCode;
import com.fisglobal.waho.model.UserOption;
import com.fisglobal.waho.utils.TimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@Service
public class NotificationService extends EmitterService {

	
	@Autowired
	private ShiftScheduleService shiftScheduleService;
	@Autowired
	private NotificationQueueService notificationQueueService;
	@Autowired
	private ShiftScheduleTimeService shiftScheduleTimeService;
	@Autowired
	private SystemCodeService systemCodeService;
	@Autowired
	private UserOptionService userOptionService;
	
	/**
	 * 
	 * createNotifList()
	 * Creates notification list at every hour at 0 minute.
	 * It will also generate random time for every employee.
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "${create.notif.list.schedule}")
	public void createNotifList() throws Exception {
		int MAX_ALERTS_IN_HOUR = 0;
		int RANDOM_RANGE_FOR_ADDED_MINUTES = 0;
		
		List<SystemCode> sysCodes =  systemCodeService.getSystemCode("ALERT_CONFIG");
		for (SystemCode systemCode : sysCodes) {
			switch (systemCode.getSysCode()) {
				case "MAX_ALERTS_IN_HOUR" : MAX_ALERTS_IN_HOUR = Integer.parseInt(systemCode.getValue1()); break;
				case "RANDOM_RANGE_FOR_ADDED_MINUTES" : RANDOM_RANGE_FOR_ADDED_MINUTES = Integer.parseInt(systemCode.getValue1()); break;
			}
		}
		
		log.debug("createNotifList() - MAX_ALERTS_IN_HOUR : "+MAX_ALERTS_IN_HOUR);
		log.debug("createNotifList() - RANDOM_RANGE_FOR_ADDED_MINUTES : "+RANDOM_RANGE_FOR_ADDED_MINUTES);
		
		List<ShiftSchedule> userShiftSchedList = shiftScheduleService.getCurrentShiftScheduleAllUser();
		if(userShiftSchedList != null) {
			log.debug("createNotifList() - userShiftSched.size : "+userShiftSchedList.size());
			for(ShiftSchedule userShiftSched : userShiftSchedList) {
				log.debug("createNotifList() - ======= user id : "+userShiftSched.getCreatedBy()+" =======");
				int numAlerts = (int)(Math.random() * MAX_ALERTS_IN_HOUR) + 1; 
				log.debug("createNotifList() - numAlerts to create : "+numAlerts);
				HashMap<String,String> notifTimeSet = new HashMap<String,String>();
				for (int i=0; i < numAlerts; i++) {
					LocalTime notifTime = TimeUtils.hourlyRandomTime(RANDOM_RANGE_FOR_ADDED_MINUTES);
					String notifTimeStr = notifTime.getHour() + ":" + notifTime.getMinute();
					log.debug("createNotifList() - alert : "+(i+1)+" creating notif...time="+notifTimeStr);
					//check if generated notif time is not yet existing/duplicate
					if (notifTimeSet.get(notifTimeStr) == null) { 
						notifTimeSet.put(notifTimeStr, notifTimeStr);
						NotificationQueue queue = new NotificationQueue(0,userShiftSched.getUser().getUserId(), notifTime);
						notificationQueueService.saveNotif(queue);
						log.debug("createNotifList() - alert : "+(i+1)+" created notif time="+notifTimeStr+" success for user id:"+userShiftSched.getCreatedBy());
					} else {
						log.debug("createNotifList() - alert : "+(i+1)+" is duplicate time.");
					}
				}
				notifTimeSet.clear();
			}
			log.debug("createNotifList() - Notification List Queue for all active employees at home generated!");
		} else {	
			log.debug("createNotifList() - Nothing to notify");
		}
	}
	
	/**
	 * 
	 * doNotify()
	 * This service checks a messages list every minute and sends a notification whenever 
	 * a notification is ready to send
	 * 
	 * @throws IOException
	 */
	@Scheduled(cron = "${check.notif.list.schedule}")
	public void doNotify() throws IOException {
		List<NotificationQueue> notifQueueList = notificationQueueService.getNotifTime(LocalTime.now());
		if (notifQueueList == null) {
			System.out.println("No event to sent: Empty List");
		} else {		
			for(NotificationQueue notifQueue : notifQueueList) {
				//check if user is on break (BREAK_TIME_DISABLE='N')
				boolean isUserOnBreak = false;
				UserOption userOption = userOptionService.getUserOption(notifQueue.getUserId(), "BREAK_TIME_DISABLE");
				if (userOption != null && userOption.getOptionValue().equals("N")) {
					isUserOnBreak = true;
				}
				
				if (isUserOnBreak) { 
					//if yes, ignore and delete the notifQueue
					notificationQueueService.deleteNotif(notifQueue);
				} else {
					//if no, proceed
					ShiftSchedule sched = shiftScheduleService.getCurrentShiftScheduleByUser(notifQueue.getUserId());
					if(sched != null) {
						SseEmitter emitter = this.getEmittersMap().get(new Long(notifQueue.getUserId()));
						notificationQueueService.deleteNotif(notifQueue);
						
						ShiftScheduleTime shiftScheduleTime = new ShiftScheduleTime();
						shiftScheduleTime.setSchedTime(LocalDateTime.now());
						shiftScheduleTime.setSchedTimeStatus("PENDING");
						shiftScheduleTime.setDateCreated(LocalDateTime.now());
						shiftScheduleTime.setDateLastUpdated(LocalDateTime.now());
						shiftScheduleTime.setCreatedBy(notifQueue.getUserId());
						shiftScheduleTime.setLastUpdatedBy(notifQueue.getUserId());
						shiftScheduleTime.setIsActive("Y");
						shiftScheduleTime.setShiftSchedule(sched);
						shiftScheduleTimeService.saveShiftScheduleTime(shiftScheduleTime);
						if (emitter!=null) {
							try {
								emitter.send(SseEmitter.event().name(String.valueOf(notifQueue.getUserId())).data(shiftScheduleTime));
								emitter.complete();
							} catch (IOException e) {
								emitter.completeWithError(e);
							} catch (IllegalStateException e) {
								System.out.println("============IllegalStateException NotificationService : doNotify() ============");
								emitter.completeWithError(e);
							}
						}
					} else {
						System.out.println("Will not notify because employee's shift is already ended");
					} 
				}
			}
		}
	} 
	
		
}

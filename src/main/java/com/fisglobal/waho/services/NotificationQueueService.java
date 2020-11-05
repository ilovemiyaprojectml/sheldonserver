package com.fisglobal.waho.services;



import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fisglobal.waho.model.NotificationQueue;
import com.fisglobal.waho.repository.NotificationQueueRepository;
import com.fisglobal.waho.repository.NotificationQueueRepositoryCustom;

@Service
@Transactional
public class NotificationQueueService implements NotificationQueueRepositoryCustom {
	private final NotificationQueueRepository notificationQueueRepository;
	
	
	
	@PersistenceContext
	private EntityManager em;



	public NotificationQueueService(NotificationQueueRepository notificationQueueRepository) {
		this.notificationQueueRepository = notificationQueueRepository;
	}
	
	
	
	public void saveNotif(NotificationQueue queue) {
		notificationQueueRepository.save(queue);
	}
	
	
	public void deleteNotif(NotificationQueue queue) {
		queue.setDateLastUpdated(LocalDateTime.now());
		queue.setLastUpdatedBy(-1); //-1 is system
		queue.setIsActive("N");
		notificationQueueRepository.save(queue);
	}
	
	
	public List<NotificationQueue> getNotifTime(LocalTime time) {
		Query query = em.createQuery("from NotificationQueue notif where "
				+ "notif.isActive = 'Y' "
				+ "and notif.notifSched = :time")
				.setParameter("time", time);
		
		List<NotificationQueue> notifTimes = ((List<NotificationQueue>)query.getResultList());
		if(!notifTimes.isEmpty()) {
			return notifTimes;
		}
		else {
			return null;
		}
		
	}
	
	
}

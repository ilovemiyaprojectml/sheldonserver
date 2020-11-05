package com.fisglobal.waho.services;
 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.security.auth.x500.X500Principal;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;

import com.fisglobal.waho.beans.DateBeans;
import com.fisglobal.waho.beans.ShiftNotificationBeans;
import com.fisglobal.waho.beans.ShiftNotificationFinalBeans;
import com.fisglobal.waho.controller.ShiftScheduleController;
import com.fisglobal.waho.model.ShiftSchedule;
import com.fisglobal.waho.model.ShiftScheduleTime;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.repository.ShiftScheduleTimeRepository;
import com.fisglobal.waho.repository.ShiftScheduleTimeRepositoryCustom;

@Service
@Transactional
public class ShiftScheduleTimeService implements ShiftScheduleTimeRepositoryCustom{

    static Logger log = LoggerFactory.getLogger(ShiftScheduleTimeService.class);
    
    private final ShiftScheduleTimeRepository shiftScheduleTimeRepository;
	
	@PersistenceContext
	private EntityManager em;

	public ShiftScheduleTimeService(ShiftScheduleTimeRepository shiftScheduleTimeRepository) {
		super();
		this.shiftScheduleTimeRepository = shiftScheduleTimeRepository;
	}
	
	public void saveShiftScheduleTime(ShiftScheduleTime shiftScheduleTime) {
		shiftScheduleTime.setDateLastUpdated(LocalDateTime.now());
		shiftScheduleTimeRepository.save(shiftScheduleTime);
	}
	
	public List<ShiftScheduleTime> getAllShiftScheduleTimes() {
		List<ShiftScheduleTime> shiftScheduleTimes = new ArrayList<ShiftScheduleTime>();
		for (ShiftScheduleTime shiftScheduleTime : shiftScheduleTimeRepository.findAll()) {
			shiftScheduleTimes.add(shiftScheduleTime);
		}
		return shiftScheduleTimes;
	}
	
	public void deleteShiftSchedule(int schedTimeId) {
		shiftScheduleTimeRepository.deleteById(schedTimeId);
	}

	@Override
	public List<ShiftScheduleTime> findByShiftSchedule(ShiftSchedule shiftSchedule) {
		Query query = em.createQuery("Select st from ShiftScheduleTime st where st.shiftSchedule.shftSchedId = " + shiftSchedule.getShftSchedId());
		List<ShiftScheduleTime> shiftSchedTimes = ((List<ShiftScheduleTime>)query.getResultList());
		return shiftSchedTimes;
	}
	
	@Override
	public List<ShiftScheduleTime> findMissedTimeByShiftSchedId(Integer shiftSchedId) {
		Query query = em.createQuery("from ShiftScheduleTime st where st.shiftSchedule.shftSchedId = :shftSchedId "
				+ " and st.isActive = 'Y' "
				+ " and st.schedTimeStatus = 'MISSED' ")
				.setParameter("shftSchedId", shiftSchedId);
		List<ShiftScheduleTime> shiftSchedTimes = ((List<ShiftScheduleTime>)query.getResultList());
		return shiftSchedTimes;
	}
	
	@Override
	public ShiftScheduleTime findCurrentShiftScheduleTimeByShiftSchedId(Integer shiftSchedId) {
		Query query = em.createQuery("from ShiftScheduleTime st where st.shiftSchedule.shftSchedId = :shiftSchedId "
				+ " and st.isActive = 'Y' "
				+ " and (TIMESTAMPDIFF(MINUTE,st.schedTime,NOW()) > -15 "
				+ " 	and TIMESTAMPDIFF(MINUTE,st.schedTime,NOW()) <= 0 ) ")
				.setParameter("shiftSchedId", shiftSchedId);
		List<ShiftScheduleTime> shiftSchedTimes = ((List<ShiftScheduleTime>)query.getResultList());
		if (shiftSchedTimes.isEmpty()) {
			return null;
		} else {
			return (ShiftScheduleTime)shiftSchedTimes.get(0);
		}
	}
	
	@Override
	public List<ShiftScheduleTime> findPendingAndExpiredSchedTime() {
		Query query = em.createQuery("from ShiftScheduleTime st "
				+ " where st.isActive = 'Y' "
				+ " and st.schedTimeStatus = 'PENDING' "
				+ " and st.schedTime < now() ");
		List<ShiftScheduleTime> shiftSchedTimes = ((List<ShiftScheduleTime>)query.getResultList());
		return shiftSchedTimes; 
	}
	
	
	public List<ShiftScheduleTime> getPendingSchedTime(int userId){
		Query query = em.createQuery("from ShiftScheduleTime st "
				+ "where st.isActive = 'Y' "
				+ "and st.schedTimeStatus = 'PENDING' "
				+ "and st.createdBy = :userId order by st.schedTime asc")
				.setParameter("userId", userId);
		
		List<ShiftScheduleTime> shiftScheduleTimes = ((List<ShiftScheduleTime>)query.getResultList());
		return shiftScheduleTimes;
	}
	
	public List<ShiftScheduleTime> getPendingManagerSchedTime(int userId, List<User> employees){
		
		List<Integer> userIds = new ArrayList<Integer>();
		for (User employee : employees) {
			userIds.add(employee.getUserId());
		}

		Query query = em.createQuery("select st from ShiftScheduleTime st LEFT JOIN st.shiftSchedule ss "
				+ "where st.isActive = 'Y' "
				+ "and ss.actualEndDate IS NULL "
				+ "and ((st.schedTimeStatus = 'PENDING' and st.createdBy = :userId) "
				+ "or (st.schedTimeStatus = 'MISSED' and st.createdBy in (:userIds) and st.isAcknowledged IS NULL)) "
				+ "order by st.schedTime asc")
				.setParameter("userIds", userIds)
				.setParameter("userId", userId);
		
		List<ShiftScheduleTime> shiftScheduleTimes = ((List<ShiftScheduleTime>)query.getResultList());
		return shiftScheduleTimes;
	}
	
	public List<ShiftNotificationFinalBeans> findSchedTimeByShiftIdAndUserId(int shiftSchedId, int userId){
		List<ShiftNotificationFinalBeans> shiftSchedFinal = new ArrayList<ShiftNotificationFinalBeans>();
		Query query = em.createQuery("from ShiftScheduleTime st "
				+ "where st.isActive = 'Y' "
				+ "	and st.createdBy = :userId "
				+ "	and st.shiftSchedule.shftSchedId = :shiftSchedId  "
				+ "order by st.schedTime asc")
				.setParameter("userId", userId)
				.setParameter("shiftSchedId", shiftSchedId);
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		
		List<ShiftScheduleTime> shiftScheduleTimes = ((List<ShiftScheduleTime>)query.getResultList());
		for(ShiftScheduleTime shiftScheduleTime: shiftScheduleTimes) {
			shiftSchedFinal.add(new ShiftNotificationFinalBeans(
					shiftScheduleTime.getSchedTimeId()
					, shiftScheduleTime.getSchedTime().format(dateFormatter).toString()
					, shiftScheduleTime.getSchedTime().format(timeFormatter).toString()
					, shiftScheduleTime.getSchedTimeStatus()
					, (shiftScheduleTime.getRemarks() == null) ? "" : shiftScheduleTime.getRemarks())
			);
		}
		
		
		return shiftSchedFinal;
	}
	
	/**
	 * 
	 * getAllDistinctDateById(int userId)
	 * Get all date available for specific userId
	 * @param userId The ID of user
	 * 
	 * 
	 */
	public List<String> getAllDistinctDateById(int userId){
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<String> allDate = new ArrayList<String>();
		Query query = em.createQuery("select st.schedTime from ShiftScheduleTime st where "
				+ "st.createdBy = :userId order by st.schedTime asc")
				.setParameter("userId", userId);
		List<LocalDateTime> resultSet = query.getResultList();
		
		for (LocalDateTime rows : resultSet) {
			allDate.add(rows.format(dateFormatter).toString());
		}
		
		List<String> allDateDistinct = allDate.stream().distinct().collect(Collectors.toList());
		
		return allDateDistinct;
	}
	
	/**
	 * getSchedBySchedTimeId. Used to fetch schedule time based
	 * on schedTimeId
	 * 
	 * @param schedTimeId
	 * @return ShiftScheduleTime based on schedTimeId
	 */
	public ShiftScheduleTime getSchedBySchedTimeId(int schedTimeId) {
		Query query = em.createQuery("from ShiftScheduleTime st where st.schedTimeId = :schedTimeId")
				.setParameter("schedTimeId", schedTimeId);
		List<ShiftScheduleTime> shiftSchedTimes = ((List<ShiftScheduleTime>)query.getResultList());
		if (shiftSchedTimes.isEmpty()) {
			return null;
		} else {
			return (ShiftScheduleTime)shiftSchedTimes.get(0);
		}
		
	}
	 
    public void updateMissedPendingSchedTime(int schedId){
        log.debug("updateMissedPendingSchedTime() - Start");
        List<ShiftScheduleTime> allPending = getPendingSchedTime(schedId);
        for (ShiftScheduleTime shiftScheduleTime : allPending) {
            log.debug("updateMissedPendingSchedTime() - Updating");
            shiftScheduleTime.setSchedTimeStatus("MISSED(END-SHIFT)");
            shiftScheduleTime.setRemarks("SYSTEM: Did not end shift");
            saveShiftScheduleTime(shiftScheduleTime);
        }

        log.debug("updateMissedPendingSchedTime() - End");
    }

    public void createEndedEntry(ShiftSchedule userShift, boolean isShiftOvertime){
        log.debug("createEndedEntry() - Start");

        try {
            ShiftScheduleTime shiftScheduleTime = new ShiftScheduleTime();
            shiftScheduleTime.setSchedTime(LocalDateTime.now());
            shiftScheduleTime.setSchedTimeStatus(isShiftOvertime? "ENDED" : "ENDED(UNDERTIME)");
            shiftScheduleTime.setDateCreated(LocalDateTime.now());
            shiftScheduleTime.setDateLastUpdated(LocalDateTime.now());
            shiftScheduleTime.setCreatedBy(userShift.getCreatedBy());
            shiftScheduleTime.setLastUpdatedBy(userShift.getCreatedBy());
            shiftScheduleTime.setIsActive("Y");
            shiftScheduleTime.setShiftSchedule(userShift);
            
            saveShiftScheduleTime(shiftScheduleTime);
        }catch (Exception e) {
            log.error("Error Encountered during createEndedEntry", e);
        }

        log.debug("createEndedEntry() - End");
    }

	
}

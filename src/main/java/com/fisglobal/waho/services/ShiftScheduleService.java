package com.fisglobal.waho.services;

 

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fisglobal.waho.model.ShiftSchedule;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.repository.ShiftScheduleRepository;
import com.fisglobal.waho.repository.ShiftScheduleRepositoryCustom;

@Service
@Transactional
public class ShiftScheduleService implements ShiftScheduleRepositoryCustom {
	private final ShiftScheduleRepository shiftScheduleRepository;
	
	@PersistenceContext
	private EntityManager em;

	public ShiftScheduleService(ShiftScheduleRepository shiftScheduleRepository) {
		super();
		this.shiftScheduleRepository = shiftScheduleRepository;
	}
	
	public Optional<ShiftSchedule> findById(int shiftSchedId) {
		return shiftScheduleRepository.findById(shiftSchedId);
	}
	
	public ShiftSchedule saveShiftSchedule(ShiftSchedule shiftSchedule) {
		return shiftScheduleRepository.save(shiftSchedule);
	}
	
	public List<ShiftSchedule> getAllShiftSchedules() {
		List<ShiftSchedule> shiftSchedules = new ArrayList<ShiftSchedule>();
		for (ShiftSchedule shiftSchedule : shiftScheduleRepository.findAll()) {
			shiftSchedules.add(shiftSchedule);
		}
		
		return shiftSchedules;
	}
	
	public void deleteShiftSchedule(int shftSchedId) {
		shiftScheduleRepository.deleteById(shftSchedId);
	}

	@Override
	public List<ShiftSchedule> findByUser(User user) {
		Query query = em.createQuery("from ShiftSchedule sc where sc.user.id = :userId and isActive = 'Y' order by sc.shftSchedId desc ")
				.setParameter("userId", user.getUserId());
		List<ShiftSchedule> shiftScheds = ((List<ShiftSchedule>)query.getResultList());
		return shiftScheds;
	}
	
	@Override
	public ShiftSchedule getCurrentShiftScheduleByUser(int userId) {
		LocalDateTime today = LocalDateTime.now();
		Query query = em.createQuery("from ShiftSchedule sc where sc.user.id = :userId"
				+ " and sc.actualEndDate is null"
				//+ " and sc.schedEndDate > :today "
				+ " and sc.isActive = 'Y' "
				+ " order by sc.schedEndDate desc" )
				.setParameter("userId", userId)
				//.setParameter("today", today)
				.setMaxResults(1);
		List<ShiftSchedule> shiftScheds = ((List<ShiftSchedule>)query.getResultList());
		if (!shiftScheds.isEmpty()) {
			return shiftScheds.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public ShiftSchedule getPreviousShiftScheduleByUser(int userId) {
	  Query query = em.createQuery("from ShiftSchedule sc where sc.user.id = :userId"
                + " and :today >= sc.actualEndDate "
                + " and sc.isActive = 'Y' "
                + " order by sc.actualEndDate desc" )
                .setParameter("userId", userId)
                .setParameter("today", LocalDateTime.now())
                .setMaxResults(1);
        List<ShiftSchedule> shiftScheds = ((List<ShiftSchedule>)query.getResultList());
        if (!shiftScheds.isEmpty()) {
            return shiftScheds.get(0);
        } else {
            return null;
        }
    }
	
	/**
	 * 
	 * getCurrentShiftScheduleAllUser()
	 * Get all users with current shift based on if actual end 
	 * date is null.
	 * 
	 * 
	 * 
	 * @return All employees with current running shift 
	 */
	public List<ShiftSchedule> getCurrentShiftScheduleAllUser() {
		Query query = em.createQuery("from ShiftSchedule sc where "
				+ "sc.actualEndDate is null "
				+ " and sc.isActive = 'Y' "
				+ " order by sc.schedEndDate desc");
		List<ShiftSchedule> currentShift = ((List<ShiftSchedule>)query.getResultList());
		if(!currentShift.isEmpty()) {
			return currentShift;
		}
		else {
			return null;
		}
	}
	
	/**
	 * This method will fetched the schedule using back or forward shift based on the selectedShiftSchedId
	 * 
	 * @param selectedShiftSchedId basis schedule from where to adjust shift
	 * @param direction -1 = back shift, 0 = current selected, 1 = forward shift  
	 * @param userId
	 * @return
	 */
	public ShiftSchedule getShiftSchedule(int selectedShiftSchedId, int direction, int userId) {
		String dirSymbol = "";
		String sorting = "";
		if (direction < 0) {
			dirSymbol = "<";
			sorting = "desc";
		} else if (direction > 0) {
			dirSymbol = ">";
			sorting = "asc";
		} else {
			dirSymbol = "=";
		}
		
		Query query = null;
		if (selectedShiftSchedId!=0) {
			query = em.createQuery("from ShiftSchedule sc where "
				  	+ " sc.user.id = :userId "
	                + " and sc.isActive = 'Y' "
	                + " and sc.shftSchedId " + dirSymbol + " :shftSchedId "
	                + " order by sc.shftSchedId " + sorting )
	                .setParameter("userId", userId)
	                .setParameter("shftSchedId", selectedShiftSchedId)
	                .setMaxResults(1);
		} else {
			query = em.createQuery("from ShiftSchedule sc where "
				  	+ " sc.user.id = :userId "
	                + " and sc.isActive = 'Y' "
	                + " order by sc.shftSchedId " + sorting )
	                .setParameter("userId", userId)
	                .setMaxResults(1);
		}

        List<ShiftSchedule> shiftScheds = ((List<ShiftSchedule>)query.getResultList());
        if (!shiftScheds.isEmpty()) {
            return shiftScheds.get(0);
        } else {
            return null;
        }
    }
	
	public Object getFirsAndLastShiftSchedIds(int userId) {
		Object shiftScheds = em.createQuery("select min(sc.shftSchedId), max(sc.shftSchedId) "
				+ " from ShiftSchedule sc where "
			  	+ " sc.user.id = :userId "
                + " and sc.isActive = 'Y' ")
                .setParameter("userId", userId).getSingleResult();
		return shiftScheds;
	}
	

}

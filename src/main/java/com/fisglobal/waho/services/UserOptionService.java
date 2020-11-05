package com.fisglobal.waho.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fisglobal.waho.beans.UserOptionBean;
import com.fisglobal.waho.model.SystemCode;
import com.fisglobal.waho.model.UserOption;
import com.fisglobal.waho.repository.UserOptionRepository;


@Service
@Transactional
public class UserOptionService {
	private final UserOptionRepository userOptionRepository;
	
	@Autowired
	private SystemCodeService systemCodeService;
	
	@PersistenceContext
	private EntityManager em;
	
	public UserOptionService(UserOptionRepository userOptionRepository) {
		this.userOptionRepository = userOptionRepository;
	}
	
	public void saveOption(UserOption userOption) {
		UserOption existingUserOption = this.getUserOption(userOption.getUserId(), userOption.getOptionCode());
		
		if (existingUserOption !=null) {
			existingUserOption.setOptionValue(userOption.getOptionValue());
			existingUserOption.setLastUpdatedBy(userOption.getUserId());
			existingUserOption.setDateLastUpdated(LocalDateTime.now());
			existingUserOption.setIsActive(userOption.getIsActive());
			userOptionRepository.save(existingUserOption);
		} else {
			userOption.setDateCreated(LocalDateTime.now());
			userOption.setDateLastUpdated(LocalDateTime.now());
			userOption.setCreatedBy(userOption.getUserId());
			userOption.setLastUpdatedBy(userOption.getUserId());
			userOptionRepository.save(userOption);
		}
	}
	
	public void saveOptions(List<UserOption> userOptions) {
		for (UserOption userOption : userOptions) {
			this.saveOption(userOption);
		}
	}
	
	public void deleteUserOption(UserOption userOption) {
		userOption.setIsActive("N");
		userOption.setDateLastUpdated(LocalDateTime.now());
		userOption.setLastUpdatedBy(userOption.getUserId());
		userOptionRepository.save(userOption);
	}


	public List<UserOption> getUserOptions(int userId) {
		Query query = em.createQuery("from UserOption u where u.userId = :userId")
				.setParameter("userId", userId);
		List<UserOption> userOptions = ((List<UserOption>)query.getResultList());
		return userOptions;
	}
	
	public UserOption getUserOption(int userId, String optionCode) {
		Query query = em.createQuery("from UserOption u where u.userId = :userId and u.optionCode = :optionCode ")
				.setParameter("userId", userId)
				.setParameter("optionCode", optionCode);
		List<UserOption> userOptions = ((List<UserOption>)query.getResultList());
		if (userOptions.size()>0) {
			return userOptions.get(0);
		} else {
			return null;
		}
	}
	
	public List<UserOption> getUserOptions(String optionCode, String optionValue) {
		Query query = em.createQuery("from UserOption u where u.optionCode = :optionCode and u.optionValue = :optionValue ")
				.setParameter("optionCode", optionCode)
				.setParameter("optionValue", optionValue);
		List<UserOption> userOptions = ((List<UserOption>)query.getResultList());
		return userOptions;
	}
	
	public List<UserOptionBean> configureUserOption(int userId) {
		List<UserOptionBean> userOptionBeans = new ArrayList<UserOptionBean>();
		
		List<UserOption> userOptions = this.getUserOptions(userId);
		//admin-defined user options can override the specific user option
		List<SystemCode> sysCodes =  systemCodeService.getSystemCode("USER_OPTIONS");
		for (SystemCode systemCode : sysCodes) {
			
			String optionCode = systemCode.getSysCode();
			String optionValue = systemCode.getValue1();
			for (UserOption userOption : userOptions) {
				if (systemCode.getSysCode().equals(userOption.getOptionCode())) {
					//if system option is ENABLE, consider the user preference value. If DISABLE, ignore user preferences
					if (systemCode.getValue1().equals("Y")) {
						optionValue = userOption.getOptionValue();
					}
				}
			}
			UserOptionBean optionBean = new UserOptionBean(userId, optionCode, optionValue);
			userOptionBeans.add(optionBean);
		}
		return userOptionBeans;
		
	}

}

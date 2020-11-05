package com.fisglobal.waho.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fisglobal.waho.model.SystemCode;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.model.UserRole;
import com.fisglobal.waho.model.UserTempAccess;
import com.fisglobal.waho.repository.SystemCodeRepository;

@Service
@Transactional
public class SystemCodeService {
	private final SystemCodeRepository systemCodeRepository;
	
	@PersistenceContext
	private EntityManager em;

	public SystemCodeService(SystemCodeRepository systemCodeRepository) {
		this.systemCodeRepository = systemCodeRepository;
	}
	
	public void saveSystemCode(SystemCode systemCode) {
		systemCodeRepository.save(systemCode);
	}
	
	public List<SystemCode> getAllSystemCodes() {
		List<SystemCode> systemCodes = new ArrayList<SystemCode>();
		for (SystemCode systemCode : systemCodeRepository.findAll()) {
			systemCodes.add(systemCode);
		}
		
		return systemCodes;
	}
	
	public Optional<SystemCode> findById(int codeId) {
		return systemCodeRepository.findById(codeId);
	}
	
	
	public int editSysCodeWithReturnValue(SystemCode systemCode) {

		em.merge(systemCode);
		em.flush();

		return 1;
	}
	
	public SystemCode getSysCodesByName(String sysCode) {
		Query query = em.createQuery("from SystemCode u where u.sysCode = :sysCode")
				.setParameter("sysCode", sysCode);
		List<SystemCode> systemCodes = ((List<SystemCode>)query.getResultList());
		if (!systemCodes.isEmpty()) {
			return systemCodes.get(0);
		} else {
			return null;
		}
	}
	
	public int addSysCodeWithReturnValue(SystemCode systemCode) {

		em.persist(systemCode);
		em.flush();

		return systemCode.getSysCodeId();
	}
	
	public int deleteSysCodeWithReturnValue(SystemCode systemCode) {

		em.remove(systemCode);
		em.flush();

		return 1;
	}
	
	
	
	
	
	
	
	public void deleteSystemCode(int sysCodeId) {
		systemCodeRepository.deleteById(sysCodeId);
	}
	
	public List<SystemCode> getSystemCode(String category) {
		Query query = em.createQuery("from SystemCode sc where "
				+ " sc.sysCodeCategory= :category "
				+ " and sc.isActive = 'Y' ")
				.setParameter("category", category);
		
		List<SystemCode> sysCodes = ((List<SystemCode>)query.getResultList());
		if(!sysCodes.isEmpty()) {
			return sysCodes;
		} else {
			return null;
		}
		
	}
	
	public SystemCode getSystemCode(String category, String code) {
		Query query = em.createQuery("from SystemCode sc where "
				+ "sc.sysCodeCategory= :category "
				+ "and sc.sysCode= :code")
				.setParameter("category", category)
				.setParameter("code", code);
		
		List<SystemCode> sysCodes = ((List<SystemCode>)query.getResultList());
		if(!sysCodes.isEmpty()) {
			return sysCodes.get(0);
		} else {
			return null;
		}
		
	}
}

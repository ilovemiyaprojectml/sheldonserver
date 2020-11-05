package com.fisglobal.waho.services;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fisglobal.waho.model.User;
import com.fisglobal.waho.model.UserTempAccess;
import com.fisglobal.waho.repository.UserTempAccessRepository;
import com.fisglobal.waho.repository.UserTempAccessRepositoryCustom;

@Service
@Transactional
public class UserTempAccessService implements UserTempAccessRepositoryCustom {
	private UserTempAccessRepository userTempAccessRepository;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public UserTempAccess getUsersByUserEid(String userEid) {
		UserTempAccess access;
		Query query = em.createQuery("from UserTempAccess u where u.userEid = :userEid")
				.setParameter("userEid", userEid);
		List<UserTempAccess> userTempAccesses = ((List<UserTempAccess>)query.getResultList());
		if (!userTempAccesses.isEmpty()) {
			return userTempAccesses.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public UserTempAccess getUsersByUserId(int user_id) {
		UserTempAccess access;
		Query query = em.createQuery("from UserTempAccess u where u.userId = :user_id")
				.setParameter("user_id", user_id);
		List<UserTempAccess> userTempAccesses = ((List<UserTempAccess>)query.getResultList());
		if (!userTempAccesses.isEmpty()) {
			return userTempAccesses.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public int resetPassword(String tempPassword, String email) {
		String sql = "UPDATE wh_idp_users a, wh_users b SET a.resetpassword = :temppassword,a.date_last_updated = NOW() WHERE a.user_id = b.user_id AND  UPPER(b.email) = :email";
		Query query = em.createNativeQuery(sql);
		query.setParameter("temppassword", tempPassword);
		query.setParameter("email", email);
		int rowsUpdated = query.executeUpdate();
		return rowsUpdated;
	}
	
	@Override
	public int changePassword(String tempPassword, String userEid, String password, String type) {
		
		String sql;
		if ("change".equals(type)) {
			sql = "UPDATE wh_idp_users a SET a.password = :temppassword, a.resetpassword = NULL,a.date_last_updated = NOW(),a.last_updated_by = a.user_id where a.user_eid = :userEid and a.password = :password";
		} else {
			sql = "UPDATE wh_idp_users a SET a.password = :temppassword, a.resetpassword = NULL,a.date_last_updated = NOW(),a.last_updated_by = a.user_id where a.user_eid = :userEid and a.resetpassword = :password";
		}
		 
		Query query = em.createNativeQuery(sql);
		query.setParameter("temppassword", tempPassword);
		query.setParameter("userEid", userEid);
		query.setParameter("password", password);
		int rowsUpdated = query.executeUpdate();
		return rowsUpdated;
	}
	
	

}

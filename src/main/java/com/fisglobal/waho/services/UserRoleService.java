package com.fisglobal.waho.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fisglobal.waho.model.User;
import com.fisglobal.waho.model.UserRole;
import com.fisglobal.waho.model.UserTempAccess;
import com.fisglobal.waho.repository.UserRoleRepository;


@Service
@Transactional
public class UserRoleService {
	private final UserRoleRepository userRoleRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	public UserRoleService(UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}
	
	public void saveUserRole(UserRole userRole) {
		userRoleRepository.save(userRole);
	}
	
	public List<UserRole> getAllUserRoles() {
		List<UserRole> userRoles = new ArrayList<UserRole>();
		for (UserRole userRole : userRoleRepository.findAll()) {
			userRoles.add(userRole);
		}
		
		return userRoles;
	}
	
	public void deleteUserRole(int userRoleId) {
		userRoleRepository.deleteById(userRoleId);
	}
	
	public Optional<UserRole> findById(int userRoleId) {
		return userRoleRepository.findById(userRoleId);
	}
	

	public List<UserRole> getRoleByUserId(int userId) {
		List<UserRole>  userRoles = new ArrayList<UserRole>();
		String sql = "SELECT * FROM wh_user_roles WHERE user_id = :userId";
		Query query = em.createNativeQuery(sql);
		query.setParameter("userId", userId);
		

		if (!query.getResultList().isEmpty()) {
			
			List<Object> os = query.getResultList();
			for (Object o : os) {
				Object[] cols = (Object[]) o;
			    UserRole userRole = new UserRole();
			    userRole.setUserRoleId((int)cols[0]);
			    userRole.setRoleCd((String)cols[2]);
			    userRoles.add(userRole);
			}
			
			return userRoles;
		} else {
			return null;
		}
	}

}

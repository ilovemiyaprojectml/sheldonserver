package com.fisglobal.waho.services;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fisglobal.waho.model.ShiftSchedule;
import com.fisglobal.waho.model.User;
import com.fisglobal.waho.model.UserRole;
import com.fisglobal.waho.model.UserTempAccess;
import com.fisglobal.waho.repository.UserRepository;
import com.fisglobal.waho.repository.UserRepositoryCustom;

@Service
@Transactional
public class UserService implements UserRepositoryCustom{
	private final UserRepository userRepository;
	
	@PersistenceContext
	private EntityManager em;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		for (User user : userRepository.findAll()) {
			users.add(user);
		}
		return users;
	}
	
	public List<User> getAllManagers()  {
		Query query = em.createQuery("select u from User u LEFT JOIN u.userRoles r where r.roleCd = :roleCd order by u.firstName, u.lastName, u.userEid ASC")
				.setParameter("roleCd", "MANAGER");
		List<User> users = ((List<User>)query.getResultList());
		return users;
	}
	
	public List<User> getAllUsersDP()  {
		Query query = em.createQuery("select distinct u from User u LEFT JOIN u.userRoles r order by u.firstName, u.lastName, u.userEid ASC");
		List<User> users = ((List<User>)query.getResultList());
		return users;
	}
	
	public void addUser(User user) {
		em.persist(user);
	}


	public int addUserWithReturnValue(User user, UserTempAccess userTempAccess) {

		em.persist(user);
		em.flush();

		userTempAccess.setUserId(user.getUserId());
		em.persist(userTempAccess);
		em.flush();

		return user.getUserId();
	}

	public int editUserWithReturnValue(User user, List<UserRole> userRoles) {

		User usera =  em.merge(user);
		em.flush();

		return 1;
	}

	public int deleteUserWithReturnValue(User user, UserTempAccess currentUserAccess) {

		em.remove(user);
		em.flush();

		if (currentUserAccess != null) {
			em.remove(currentUserAccess);
			em.flush();
		}

		return 1;
	}

	public void deleteUser(int userId) {
		userRepository.deleteById(userId);
	}
	
	public Optional<User> findById(int userId) {
		return userRepository.findById(userId);
	}
	
	@Override
	public List<User> getUsersByUserParentId(int userParentId)  {
		Query query = em.createQuery("from User u where u.userParentId = :userParentId")
				.setParameter("userParentId", userParentId);
		List<User> users = ((List<User>)query.getResultList());
		return users;
	}

	@Override
	public User getUsersByUserEid(String userEid) {
		Query query = em.createQuery("from User u where u.userEid = :userEid")
				.setParameter("userEid", userEid);
		List<User> users = ((List<User>)query.getResultList());
		if (!users.isEmpty()) {
			return users.get(0);
		} else {
			return null;
		}
	}

	
	public List<User> getUsersWithShift() {
		Query query = em.createQuery("from User u where "
				+ "u.effectiveStartDate <> NULL AND u.effectiveEndDate <> NULL");
		List<User> users = ((List<User>)query.getResultList());
		if(!users.isEmpty()) {
			return users;
		}
		else {
			return null;
		}
	}
	

	
}

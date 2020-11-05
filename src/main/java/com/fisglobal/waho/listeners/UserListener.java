package com.fisglobal.waho.listeners;



import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import com.fisglobal.waho.model.User;


/*
 * 
 * These class are used to listen on anything happens on User Entity
 * this will serves like a trigger on entities 
 * 
 *
 */

public class UserListener {

	
	@PostUpdate
	public void updateUserListener(User user) {
		System.out.println("User: " + user.getUserEid() + " updated!");
	}
	
	
	
	@PostRemove
	public void deleteUserListener(User user) {
		//System.out.println("User: " + user.getUserEid() + " deleted!");
	
	}
	
	
	@PostPersist
	public void postPersistUserListener(User user) {
		System.out.println("User: " + user.getUserEid() + " added!");
	}




	
		
	
}


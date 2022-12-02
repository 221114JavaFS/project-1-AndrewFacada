package com.revature.services;

import java.util.List;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.User;

public class UserService {
	
	private UserDAO userDao = new UserDAOImpl();
	
	public List<User> getAllUsers(){
		return userDao.findAllUsers();
	}
	
	public User loggingIn(User user) {
		return userDao.login(user);
		
	}
	
	public boolean newAccount(User user) {
		return userDao.createAccount(user);
	}
	
	public User promoteRole(User user) {
		return userDao.updateRole(user);
	}

}

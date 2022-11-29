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
	
	public boolean loggingIn(User user) {
		return userDao.login(user);
		
	}
	
	public String loggingOut() {
		return userDao.signout();
	}
	
	public String whoAmI() {
		return userDao.session();
	}
	
	public boolean newAccount(User user) {
		return userDao.createAccount(user);
	}

}

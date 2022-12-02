package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface UserDAO {

	public abstract List<User> findAllUsers();
	
	public abstract User login(User user);
	
	public abstract boolean createAccount(User user);
	
	public abstract User updateRole(User user);
}

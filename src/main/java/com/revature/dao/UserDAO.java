package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public interface UserDAO {

	public abstract List<User> findAllUsers();
	
	public abstract User findCertainUser(String email);
	
	public abstract boolean createUser(User user);
	
	public abstract boolean login(User user);
	
	public abstract String signout();
}

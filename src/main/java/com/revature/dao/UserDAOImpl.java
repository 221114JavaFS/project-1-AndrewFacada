package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDAOImpl implements UserDAO{

	@Override
	public List<User> findAllUsers() {  //Lets managers see all users 
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "Select user_id, email, first_name, last_name, address, role FROM user_info;";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			List<User> list = new ArrayList<>(); //creates new list for user objects
			
			while(result.next()) {
				User u = new User( 
						result.getInt("user_id"),
						result.getString("email"),
						result.getString("first_name"),
						result.getString("last_name"),
						result.getString("address"),
						result.getString("role")
						);
				
						list.add(u); //adds user object to list
			}
			return list; //returns list of user object
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	@Override
	public User login(User user) { //Allows users to login 
		try(Connection connection = ConnectionUtil.getConnection()){
			
			PreparedStatement statement =  connection.prepareStatement("Select user_id, email, passw, first_name, last_name, role FROM user_info WHERE email = ? AND passw = ?;");
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			ResultSet result = statement.executeQuery();
			
			
			User a = new User(); //creates new object to return logged in user if credentials are correct
			//confirms that user exists and has been provided correct credentials 
			if(result.next()) {
				a.setId(result.getInt("user_id"));
				a.setEmail(result.getString("email"));
				a.setFirstName(result.getString("first_name"));
				a.setLastName(result.getString("last_name"));
				a.setRole(result.getString("role"));
				
				
				
				return a; //returns user object if credentials matched
			}else {
				return null; //returns null if credentials did not match
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean createAccount(User user) {
		try(Connection connection = ConnectionUtil.getConnection()){
			
			PreparedStatement statement = connection.prepareStatement("SELECT email FROM user_info WHERE email = ?;");
			statement.setString(1, user.getEmail());
			ResultSet result = statement.executeQuery();
			
			if(!result.next()) {
				PreparedStatement statementTwo = connection.prepareStatement("INSERT INTO user_info(email, passw, first_name, last_name, address, created_on) VALUES (?, ?, ?, ?, ?, now());");
				statementTwo.setString(1, user.getEmail());
				statementTwo.setString(2, user.getPassword());
				statementTwo.setString(3, user.getFirstName());
				statementTwo.setString(4, user.getLastName());
				statementTwo.setString(5, "null");
				int result2 = statementTwo.executeUpdate();
				return true;
			}
			return false;
				
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}



	@Override
	public User updateRole(User user) { //allows managers to update role of employees, but cannot revert manager to employee
		try(Connection connection = ConnectionUtil.getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT email, role FROM user_info WHERE email = ? AND role = ?;");
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getRole());
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				PreparedStatement statementTwo = connection.prepareStatement("UPDATE user_info SET role = 'manager' WHERE email = ?;");
				statementTwo.setString(1, user.getEmail());
				int  resultTwo = statementTwo.executeUpdate();
				
				User a = new User(); //creates new user object for employee that has been updated to manager
				a.setEmail(user.getEmail());
				a.setRole("manager");
				
				return a; //returns new manager
			}
			
			return null; //if it does not work or already manager
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	

}

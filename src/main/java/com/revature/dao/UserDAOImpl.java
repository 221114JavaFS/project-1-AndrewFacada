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
	public List<User> findAllUsers() { //WORKS
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "Select user_id, email, first_name, last_name, address, role FROM user_info;";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			List<User> list = new ArrayList<>();
			
			while(result.next()) {
				User u = new User(
						result.getInt("user_id"),
						result.getString("email"),
						result.getString("first_name"),
						result.getString("last_name"),
						result.getString("address"),
						result.getString("role")
						);
				
						list.add(u);
			}
			return list;
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	@Override
	public User login(User user) { //WORKS
		try(Connection connection = ConnectionUtil.getConnection()){
			
			PreparedStatement statement =  connection.prepareStatement("Select user_id, email, passw, first_name, last_name, role FROM user_info WHERE email = ? AND passw = ?;");
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			ResultSet result = statement.executeQuery();
			
			
			User a = new User();
			if(result.next()) {
				a.setId(result.getInt("user_id"));
				a.setEmail(result.getString("email"));
				a.setFirstName(result.getString("first_name"));
				a.setLastName(result.getString("last_name"));
				a.setRole(result.getString("role"));
				
				
				
				return a;
			}else {
				return null;
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

	

}

package com.revature.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class CompanyMembersDAO {
	
	static private final String url = "jdbc:postgresql://localhost:5433/Project_One";
	static private final String user = "postgres";
	static private final String password = "password";

	public static Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(CompanyMembersDAO.getUrl(), CompanyMembersDAO.getUser(), CompanyMembersDAO.getPassword());
			System.out.println("Connected to DB!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public static String getUrl() {
		return url;
	}

	public static String getUser() {
		return user;
	}

	public static String getPassword() {
		return password;
	}
	
	
}

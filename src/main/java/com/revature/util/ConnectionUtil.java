package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
private static Connection connection;
	
	public static Connection getConnection() throws SQLException {
		if(connection != null && !connection.isClosed()) {
			return connection;
		}else {
			
			/*For many frameworks, or in the case of using
			multiple SQL drivers. The application will need
			to register the active driver class.active The
			Class.forNamemethod can do this. Generally
			with small projects and the raw JDBC is not
			necessary but it is good practice.*/
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
			
			String url = "jdbc:postgresql://localhost:5433/Project_One";
			String username = "postgres"; //Raw credentials like this are not secure.
			String password = "password"; //if you are using a remote DB it is
			//preferable to use environment variable or config files for credentials to obscure them
			
			connection = DriverManager.getConnection(url, username, password);
			
			return connection;
		}
		
		
	}
	/*public static void main(String[] args) {
		try {
			getConnection();
			System.out.println("Connection successful");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	
	
	
}

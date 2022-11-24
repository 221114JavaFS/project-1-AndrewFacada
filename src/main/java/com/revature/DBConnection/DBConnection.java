package com.revature.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.javalin.http.Context;

public class DBConnection {
	
	public DBConnection() {
		super();
	}

	static private final String url = "jdbc:postgresql://localhost:5433/Project_One";
	static private final String user = "postgres";
	static private final String password = "password";
	static String conTest = "";
	static Connection conn = null;
	
	public static Context connect(Context ctx) {
		
		try {
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("Connected!");
			conTest = "It worked";
		}catch(SQLException e) {
			System.out.println("Connection did not work!");
			conTest = "It did not work";
		}
		return ctx.html(conTest);
	}
}

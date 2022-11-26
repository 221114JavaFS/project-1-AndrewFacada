package com.revature.signuplogin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.App;
import com.revature.daos.CompanyMembersDAO;
import com.revature.session.Session;

import io.javalin.http.Context;

public class SignupLoginSignout {

	public SignupLoginSignout() {
		super();
	}

	
	public static  void signup(Context ctx) throws SQLException {      //WORKS, BUT THROWS ERROR
		PreparedStatement selectStmt = App.connect.prepareStatement  
				("SELECT email FROM user_info WHERE email = ?");
		String email = ctx.formParam("email");
		selectStmt.setString(1, email);
		ResultSet rs = selectStmt.executeQuery();
		
		if(rs.next()){
			ctx.html("Account already exists under that email!");
			
		}else {
			PreparedStatement insertStmt = App.connect.prepareStatement
					("INSERT INTO user_info(email, passw, first_name, last_name, address, created_on) VALUES (?,?,?,?,?, now())");
			
			String password = ctx.formParam("password");
			String firstName = ctx.formParam("firstname");
			String lastName = ctx.formParam("lastname");
			String address = ctx.formParam("address");
			insertStmt.setString(1, email);
			insertStmt.setString(2, password);
			insertStmt.setString(3, firstName);
			insertStmt.setString(4, lastName);
			insertStmt.setString(5, address);
			rs = insertStmt.executeQuery();
			ctx.html("Account created under email: "+ email);
		}
	}{;
	}
	
	public static void login(Context ctx) throws SQLException {   //WORKS
		PreparedStatement selectStmt = App.connect.prepareStatement  
				("SELECT * FROM user_info where email = ? AND passw = ?");
		String email = ctx.formParam("email");
		String password = ctx.formParam("password");
		selectStmt.setString(1, email);
		selectStmt.setString(2, password);
		ResultSet rs = selectStmt.executeQuery();
		
		if(rs.next() && rs.getString("email").equals(email) && rs.getString("passw").equals(password)) {
			Session.setId(rs.getInt("user_id"));
			Session.setEmail(rs.getString("email"));
			Session.setFirstName(rs.getString("first_name"));
			Session.setLastName(rs.getString("last_name"));
			Session.setRole(rs.getString("role"));
			
			ctx.html("Successfully logged in as " + Session.getFirstName() + " " + Session.getLastName() + " with the role of " + Session.getRole() + " under the email: " + Session.getEmail());
		}else {
			ctx.html("Email or password do not match!");
		}
		
	}
	
	
	public static void signout(Context ctx) {  //WORKS
		if(Session.getRole().equals("signed out")) {
			ctx.html("Cannot signout if no one is logged in!");
		}else {
			ctx.html("Successfully signed out of: " + Session.getEmail());
			Session.setId(-1);
			Session.setEmail(null);
			Session.setFirstName(null);
			Session.setLastName(null);
			Session.setRole("signed out");
		}
	}
}
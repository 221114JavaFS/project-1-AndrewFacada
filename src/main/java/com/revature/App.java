package com.revature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.daos.CompanyMembersDAO;
import com.revature.signuplogin.SignupLogin;
import io.javalin.Javalin;

public class App {

	public static String session = "signed out";
	public static Javalin app;

	static private final String url = "jdbc:postgresql://localhost:5433/Project_One";
	static private final String user = "postgres";
	static private final String password = "password";

	public static Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to DB!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public static void main(String[] args) {

		app = Javalin.create(); // creates app
		App.connect();

		new CompanyMembersDAO();

		app.get("/allcompanymembers", CompanyMembersDAO::getAllMemberInfo); // See all members in company
		app.get("/login/{email}/{password}", SignupLogin::login); // login per email+password
		//app.get("/signup/{email}/{password}", SignupLogin::signup); // sign-up per email+password
		app.get("/session", SignupLogin::session); // checks who is logged in
		app.get("/signout", SignupLogin::signout); // signs out current user
		
		
		
		app.post("/signup", ctx -> {
			PreparedStatement selectStmt = App.connect().prepareStatement
					("SELECT email FROM user_info WHERE email = ?");
			String email = ctx.formParam("email");
			selectStmt.setString(1, email);
			ResultSet rs = selectStmt.executeQuery();
			
			if(rs.next()){
				ctx.html("Account already exists under that email!");
			}else {
				PreparedStatement insertStmt = App.connect().prepareStatement
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
		});

		app.start(8081);
	}
}

package com.revature;

import com.revature.daos.CompanyMembersDAO;
import com.revature.signuplogin.SignupLogin;
import io.javalin.Javalin;

public class App {
	
	public static String session = "signed out";
	public static Javalin app;
	
	public static void main(String[] args) {
		app = Javalin.create(); //creates app
		
		new CompanyMembersDAO();
		
		app.get("/allcompanymembers", CompanyMembersDAO::getAllMemberInfo); //See all members in company
		app.get("/login/{email}/{password}", SignupLogin::login); // login per email+password
		app.get("/signup/{email}/{password}", SignupLogin::signup); // sign-up per email+password
		app.get("/session", SignupLogin::session); //checks who is logged in
		app.get("/signout", SignupLogin::signout); // signs out current user
		
		app.start(8082);
	}
}

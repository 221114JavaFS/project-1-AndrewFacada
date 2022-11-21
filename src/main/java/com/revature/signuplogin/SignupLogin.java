package com.revature.signuplogin;

import com.revature.App;
import com.revature.companymemberinfo.CompanyMemberInfo;
import com.revature.daos.CompanyMembersDAO;

import io.javalin.http.Context;

public class SignupLogin {

	public SignupLogin() {
		super();
	}

	public static void login(Context ctx) { //for login based on email+password
		int temp = 0;
		for (CompanyMemberInfo a : CompanyMembersDAO.getCompanyMembers()) {
			String accountEmail = a.getEmail();
			String attemptedLoginEmail = ctx.pathParam("email");

			String accountPassword = a.getPassword();
			String attemptedLoginPassword = ctx.pathParam("password");
			
			if (attemptedLoginEmail.equals(accountEmail) && attemptedLoginPassword.equals(accountPassword)) {
				App.session = accountEmail;
				ctx.json("Login in successful");
				ctx.status(200);
				temp = 1;
			}
		}
		
		if(temp == 0) {
			ctx.json("Login unsuccessful");
			ctx.status(406);
		}
		
	}

	
	
	
	public static void signup(Context ctx) {  //for sign-up based on email+password
		int holder = 0;
		for (CompanyMemberInfo a : CompanyMembersDAO.getCompanyMembers()) {
			if (a.getEmail().equals(ctx.pathParam("email"))) {
				holder = 1;
				break;
			}
		}
		
		if(holder == 1) {
			ctx.html("<h1>An account has already been created with that email!</h1>");
			ctx.status(403);
		}
		else {
			CompanyMembersDAO.getCompanyMembers()
			.add(new CompanyMemberInfo(CompanyMembersDAO.getCompanyMembers().size() + 1,
					ctx.pathParam("email"), ctx.pathParam("password"), "employee"));
			ctx.html("Account created under email: " + ctx.pathParam("email"));
			ctx.status(201);
		}
	}

	public static Context session(Context ctx) { //to check who is logged in
		if(!App.session.equals("signed out")) {
			return ctx.json("Currently logged in as: "+App.session);
		}
		else {
			return ctx.json("No user is currently signed in");
		}
	}
	
	public static void signout(Context ctx) {
		if(App.session.equals("signed out")) {
			ctx.json("No user is currently signed in!");
			ctx.status(400);
		}
		else {
			ctx.json(App.session + " is now signed out.");
			App.session = "signed out";
			ctx.status(200);
		}
	}
}

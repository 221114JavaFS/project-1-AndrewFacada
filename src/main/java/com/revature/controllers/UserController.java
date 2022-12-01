package com.revature.controllers;

import java.util.List;


import com.revature.models.User;
import com.revature.services.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import jakarta.servlet.http.HttpSession;

public class UserController implements Controller{
	
	static HttpSession sess;
	private String tempEmail;
	private String tempRole;
	
	
	private UserService userService = new UserService();
	
	
	Handler allUsers = (ctx) ->{
		if(tempRole == null) {
			
			if(tempRole != null && sess.getAttribute("role").equals("employee")) {
				String temp = "You must be signed into a manager account to be able to view all users!";
				ctx.json(temp);
			}
				
			String temp = "You must be signed into a manager account to be able to view all users!";
			ctx.json(temp);
			
			
		}else {
			List<User> list = userService.getAllUsers();
			ctx.json(list);
			ctx.status(200);
		}
		
	};
	
	Handler login = (ctx) ->{ //need to make it so cannot login when already logged in
		User u = ctx.bodyAsClass(User.class);
		if(userService.loggingIn(u) != null) {
			sess = ctx.req().getSession();
			tempEmail = "not null";
			tempRole = "not null";
			sess.setAttribute("id", userService.loggingIn(u).getId());
			sess.setAttribute("email", userService.loggingIn(u).getEmail());
			sess.setAttribute("firstName", userService.loggingIn(u).getFirstName());
			sess.setAttribute("lastName", userService.loggingIn(u).getLastName());
			sess.setAttribute("role", userService.loggingIn(u).getRole());
			
			
			String info = "Successfully logged in under email: " + sess.getAttribute("email");
			ctx.json(info);
			ctx.status(201);
		}else {
			String info = "Email/password are incorrect!";
			ctx.json(info);
			ctx.status(400);
		}
	};
	
	Handler signout = (ctx) ->{
		if(tempEmail != null) {
			ctx.json("Successfully signed out of: " + sess.getAttribute("email"));
			sess.setAttribute("id", null);
			sess.setAttribute("email", null);
			sess.setAttribute("firstName", null);
			sess.setAttribute("lastName", null);
			sess.setAttribute("role", null);
			ctx.status(200);
		}else {
			ctx.json("Someone must be signed in to signout");
			ctx.status(400);
		}
	};
	
	Handler session = (ctx) ->{
		if(tempEmail == null) {
			ctx.json("No one is currently signed in!");
			//ctx.status(); find correct status
		}else {
			ctx.json("Signed in as " + sess.getAttribute("firstName") + " " + sess.getAttribute("lastName") + " with the role of " + sess.getAttribute("role") + " under the email: " + sess.getAttribute("email"));
			ctx.status(200);
		}
		
		
	};
	
	Handler newUser = (ctx) ->{
		User u = ctx.bodyAsClass(User.class);
		
		boolean createdOrNot = userService.newAccount(u);
		if(createdOrNot == false) {
			String temp = "An account already exists with that email!";
			ctx.json(temp);
			ctx.status(400);
		}else if(createdOrNot == true){
			String temp = "A new account has been created under the email: " + u.getEmail();
			ctx.json(temp);
			ctx.status(201);
		}
		
	};

	@Override
	public void addRoutes(Javalin app) {
		app.get("/users", allUsers);
		app.get("/login", login);
		app.get("/signout", signout);
		app.get("/session", session);
		app.post("/newuser", newUser);
		
	}

}

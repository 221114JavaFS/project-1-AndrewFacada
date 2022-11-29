package com.revature.controllers;

import java.util.List;

import com.revature.models.Session;
import com.revature.models.User;
import com.revature.services.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController implements Controller{
	
	private UserService userService = new UserService();
	
	Handler allUsers = (ctx) ->{
		if(Session.getRole().equals("manager")) {
			List<User> list = userService.getAllUsers();
			ctx.json(list);
			ctx.status(200);
		}else {
			String temp = "You must be signed into a manager account to be able to view all users!";
			ctx.json(temp);
		}
		
	};
	
	Handler login = (ctx) ->{
		User u = ctx.bodyAsClass(User.class);
		if(userService.loggingIn(u)) {
			String info = "Successfully logged in under email: " + Session.getEmail();
			ctx.json(info);
			ctx.status(201);
		}else {
			String info = "Email/password are incorrect!";
			ctx.json(info);
			ctx.status(400);
		}
	};
	
	Handler signout = (ctx) ->{
		ctx.json(userService.loggingOut());
		ctx.status(200);
	};
	
	Handler session = (ctx) ->{
		ctx.json(userService.whoAmI());
		ctx.status(200);
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

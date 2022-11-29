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
		List<User> list = userService.getAllUsers();
		ctx.json(list);
		ctx.status(200);
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

	@Override
	public void addRoutes(Javalin app) {
		app.get("/users", allUsers);
		app.get("/login", login);
		app.get("/signout", signout);
		
	}

}

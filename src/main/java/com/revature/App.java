package com.revature;

import com.revature.controllers.Controller;
import com.revature.controllers.UserAndTicketController;

import io.javalin.Javalin;

public class App {

	private static Javalin app;
	
	public static void main(String[] args) {
		app = Javalin.create();
		configure(new UserAndTicketController());
		app.start(8085);

	}
	
	public static void configure(Controller... controllers) {
		for(Controller c: controllers) {
			c.addRoutes(app);
		}
	}
}

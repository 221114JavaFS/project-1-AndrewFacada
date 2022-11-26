package com.revature;

import java.sql.Connection;
import com.revature.daos.CompanyMembersDAO;
import com.revature.session.Session;
import com.revature.signuplogin.SignupLoginSignout;
import com.revature.tickets.Tickets;

import io.javalin.Javalin;

public class App {

	public static Javalin app;
	public static Connection connect = CompanyMembersDAO.connect();

	public static void main(String[] args) {

		app = Javalin.create(); // creates app

		

		//app.get("/allcompanymembers", CompanyMembersDAO::getAllMemberInfo); // See all members in company
		
		
		
		
		app.post("/signup", SignupLoginSignout::signup);
		app.get("/login", SignupLoginSignout::login);
		app.get("/signout", SignupLoginSignout::signout);
		
		app.get("/session", Session::session);
		
		app.post("/ticket", Tickets::createTicket);
		//app.patch("/ticket", Tickets::reviewTicket);
		

		app.start(8083);
	}
}

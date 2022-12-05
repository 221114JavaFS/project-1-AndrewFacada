package com.revature.controllers;

import java.util.List;

import com.revature.dao.TicketDAO;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.services.TicketService;
import com.revature.services.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import jakarta.servlet.http.HttpSession;

public class UserAndTicketController implements Controller{
	
	static HttpSession sess;
	private static String tempEmail;
	private String tempRole;
	
	
	private UserService userService = new UserService();
	private TicketService ticketService = new TicketService();
	
	//USER
	Handler allUsers = (ctx) ->{
		if(tempRole == null) {
			
			if(tempRole != null && sess.getAttribute("role").equals("employee")) {
				String temp = "You must be signed into a manager account to be able to view all users!";
				ctx.json(temp);
			}
				
			String temp = "You must be signed into a manager account to view all users!";
			ctx.json(temp);
			
			
		}else {
			List<User> list = userService.getAllUsers();
			ctx.json(list);
			ctx.status(200);
		}
		
	};
	
	//USER
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
	
	
	//USER
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
	
	
	//USER
	Handler session = (ctx) ->{
		if(tempEmail == null) {
			ctx.json("No one is currently signed in!");
			//ctx.status(); find correct status
		}else {
			ctx.json("Signed in as " + sess.getAttribute("firstName") + " " + sess.getAttribute("lastName") + " with the role of " + sess.getAttribute("role") + " under the email: " + sess.getAttribute("email"));
			ctx.status(200);
		}
		
		
	};
	
	
	//USER
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
	
	
	//USER
	Handler roleUpdate = (ctx) ->{  
		if(tempRole != null) {
		
			if(sess.getAttribute("role").equals("manager")) {
				User u = ctx.bodyAsClass(User.class);
				
				User updatedUser = userService.promoteRole(u);
				
				if(updatedUser != null) {
					ctx.json(u.getEmail() + " has been updated to the role of manager!");
					ctx.status(200);
				}else {
					ctx.json("Are you sure that this account exists and that it has the role of employee?");
					ctx.status(400);
				}
			}else {
				ctx.json("You must be a manager to promote an employee");
				ctx.status(400);
			}
		
		
			
			
			
		}else {
			ctx.json("You must be logged in and have the role of manager to use this function!");
			ctx.status(400);
		}
		
		
	};
	
	
	
	Handler checkMyTickets = (ctx) ->{
		if(tempRole != null) {
			
			
			if(sess.getAttribute("id") != null) {
				
				int id = (int) sess.getAttribute("id");
				
				
				if(ticketService.seeMyTickets(id) != null) {
					ctx.json(ticketService.seeMyTickets(id));
					
					
				}else {
					ctx.json("You have not submitted any tickets!");
				}
				
				
			}else {
				ctx.json("You must be logged in to view your tickets!");
			}
			
			
			
		}else {
			ctx.json("You must be logged in to view your tickets!");
		}
	};
	
	
	Handler createTicket = (ctx) ->{ //need to limit and check if fields are filled
		Ticket ticket = ctx.bodyAsClass(Ticket.class);
		int id = (int) sess.getAttribute("id");
		
		ctx.json(ticketService.createTicket(ticket, id));
	};
	
	Handler seeMyPendingTickets = (ctx) ->{
		if(tempRole != null) {
			if(sess.getAttribute("id") != null) {
				int id = (int) sess.getAttribute("id");
				
				if(ticketService.seeMyPendingTickets(id) == null) {
					ctx.json("You have no pending tickets!");
				}else {
					ctx.json(ticketService.seeMyPendingTickets(id));
				}
			}
		}else {
			ctx.json("You must be logged in to view your pending tickets!");
		}
	};
	
	
	Handler seeMyApprovedTickets = (ctx) ->{
		if(tempRole != null) {
			if(sess.getAttribute("id") != null) {
				int id = (int) sess.getAttribute("id");
				
				if(ticketService.seeMyApprovedTickets(id) == null) {
					ctx.json("You have no approved tickets!");
				}else {
					ctx.json(ticketService.seeMyApprovedTickets(id));
				}
			}
		}else {
			ctx.json("You must be logged in to view your approved tickets!");
		}
	};
	
	
	Handler seeMyDeclinedTickets = (ctx) ->{
		if(tempRole != null) {
			if(sess.getAttribute("id") != null) {
				int id = (int) sess.getAttribute("id");
				
				if(ticketService.seeMyDeclinedTickets(id) == null) {
					ctx.json("You have no declined tickets!");
				}else {
					ctx.json(ticketService.seeMyDeclinedTickets(id));
				}
			}
		}else {
			ctx.json("You must be logged in to view your declined tickets!");
		}
	};
	
	
	
	
	

	@Override
	public void addRoutes(Javalin app) {
		//USER
		app.get("/users", allUsers);
		app.get("/login", login);
		app.get("/signout", signout);
		app.get("/session", session);
		app.post("/newuser", newUser);
		app.patch("/promote",roleUpdate);
		//TICKET
		app.get("/mytickets", checkMyTickets);
		app.post("/createticket", createTicket);
		app.get("/pendingtickets", seeMyPendingTickets);
		app.get("/approvedtickets", seeMyApprovedTickets);
		app.get("/declinedtickets", seeMyDeclinedTickets);
		
		
	}

}

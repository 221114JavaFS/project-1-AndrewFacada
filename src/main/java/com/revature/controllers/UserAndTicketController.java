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

public class UserAndTicketController implements Controller {

	static HttpSession sess;
	private static String tempEmail;
	private String tempRole;

	private UserService userService = new UserService();
	private TicketService ticketService = new TicketService();

	// USER
	Handler allUsers = (ctx) -> { //for manager t see all current users
		if (tempRole != null && sess.getAttribute("role").equals("manager")) {  //makes sure manager is logged in
				List<User> list = userService.getAllUsers(); //returns list of all users
				ctx.json(list);
				ctx.status(200);
			}else {
				String temp = "You must be signed into a manager account to view all users!";
				ctx.json(temp);
			}

			

		

	};

	// USER
	Handler login = (ctx) -> { // need to make it so cannot login when already logged in
		User u = ctx.bodyAsClass(User.class);

		if (u.getEmail().isEmpty() || u.getPassword().isEmpty()) { //makes sure fields are not empty
			ctx.json("Email and password fields cannot be empty!");
		} else {

			if (userService.loggingIn(u) != null) { //sets session attributes if successful login
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
			} else {
				String info = "Email/password are incorrect!";
				ctx.json(info);
				ctx.status(400);
			}

		}

	};

	// USER
	Handler signout = (ctx) -> {  //signs out current user and sets all session attributes to null
		if (tempEmail != null) {
			ctx.json("Successfully signed out of: " + sess.getAttribute("email"));
			sess.setAttribute("id", null);
			sess.setAttribute("email", null);
			sess.setAttribute("firstName", null);
			sess.setAttribute("lastName", null);
			sess.setAttribute("role", null);
			tempEmail = null;
			tempRole = null;
			ctx.status(200);
		} else {
			ctx.json("Someone must be signed in to signout");
			ctx.status(400);
		}
	};

	// USER
	Handler session = (ctx) -> {  //can view who is currently logged in
		if (tempEmail == null) {
			ctx.json("No one is currently signed in!");
			// ctx.status(); find correct status
		} else {
			ctx.json("Signed in as " + sess.getAttribute("firstName") + " " + sess.getAttribute("lastName") //displays current session
					+ " with the role of " + sess.getAttribute("role") + " under the email: "
					+ sess.getAttribute("email"));
			ctx.status(200);
		}

	};

	// USER
	Handler newUser = (ctx) -> { //creates new user
		User u = ctx.bodyAsClass(User.class);

		if (u.getEmail().isEmpty() || u.getPassword().isEmpty() || u.getFirstName().isEmpty() //makes sure fields are not empty
				|| u.getLastName().isEmpty() || u.getAddress().isEmpty()) {
			ctx.json("All fields must be filled in!");
		} else {
			boolean createdOrNot = userService.newAccount(u); 
			if (createdOrNot == false) {                                      //lets you know if new account created
				String temp = "An account already exists with that email!";
				ctx.json(temp);
				ctx.status(400);
			} else if (createdOrNot == true) {
				String temp = "A new account has been created under the email: " + u.getEmail();
				ctx.json(temp);
				ctx.status(201);
			}
		}
	};

	// USER
	Handler roleUpdate = (ctx) -> {  //lets managers update roles of employees to managers
		User u = ctx.bodyAsClass(User.class);
		if (u.getEmail().isEmpty() || u.getRole().isEmpty() || u.getId() <= 0) { //makes sure fields are not empty
			ctx.json("All fields must be filled in!");
		} else {
			if (tempRole != null) {

				if (sess.getAttribute("role").equals("manager")) { //makes sure a manager is attempting role update

					User updatedUser = userService.promoteRole(u);

					if (updatedUser != null) {
						ctx.json(u.getEmail() + " has been updated to the role of manager!");
						ctx.status(200);
					} else {
						ctx.json("Are you sure that this account exists and that it has the role of employee?");
						ctx.status(400);
					}
				} else {
					ctx.json("You must be a manager to promote an employee");
					ctx.status(400);
				}

			} else {
				ctx.json("You must be logged in and have the role of manager to use this function!");
				ctx.status(400);
			}
		}

	};

	Handler checkMyTickets = (ctx) -> { //checks if user has created any tickets
		if (tempRole != null) {

			if (sess.getAttribute("id") != null) {

				int id = (int) sess.getAttribute("id");

				if (ticketService.seeMyTickets(id) != null) { //returns list of all tickets
					ctx.json(ticketService.seeMyTickets(id));

				} else {
					ctx.json("You have not submitted any tickets!");
				}

			} else {
				ctx.json("You must be logged in to view your tickets!");
			}

		} else {
			ctx.json("You must be logged in to view your tickets!");
		}
	};

	Handler createTicket = (ctx) -> { // creates new ticket
		Ticket ticket = ctx.bodyAsClass(Ticket.class);
		if (ticket.getReimbursementType().isEmpty() || ticket.getAmount() <= 0 || ticket.getDescription().isEmpty()) { //makes sure all fields are filled
			ctx.json("All fields must be filled in!");
		} else {
			int id = (int) sess.getAttribute("id");
			ctx.json(ticketService.createTicket(ticket, id));
		}
	};

	Handler seeMyPendingTickets = (ctx) -> { //allows users to see their pending tickets if they have any
		if (tempRole != null) {
			if (sess.getAttribute("id") != null) {
				int id = (int) sess.getAttribute("id");

				if (ticketService.seeMyPendingTickets(id) == null) {
					ctx.json("You have no pending tickets!");
				} else {
					ctx.json(ticketService.seeMyPendingTickets(id));
				}
			}
		} else {
			ctx.json("You must be logged in to view your pending tickets!");
		}
	};

	Handler seeMyApprovedTickets = (ctx) -> {  //allows users to see their approved tickets if they have any
		if (tempRole != null) {
			if (sess.getAttribute("id") != null) {
				int id = (int) sess.getAttribute("id");

				if (ticketService.seeMyApprovedTickets(id) == null) {
					ctx.json("You have no approved tickets!");
				} else {
					ctx.json(ticketService.seeMyApprovedTickets(id));
				}
			}
		} else {
			ctx.json("You must be logged in to view your approved tickets!");
		}
	};

	Handler seeMyDeclinedTickets = (ctx) -> { //allows users to see their declined tickets if they have any
		if (tempRole != null) {
			if (sess.getAttribute("id") != null) {
				int id = (int) sess.getAttribute("id");

				if (ticketService.seeMyDeclinedTickets(id) == null) {
					ctx.json("You have no declined tickets!");
				} else {
					ctx.json(ticketService.seeMyDeclinedTickets(id));
				}
			}
		} else {
			ctx.json("You must be logged in to view your declined tickets!");
		}
	};

	Handler updateTicketStatus = (ctx) -> { //allows managers to approve/deny tickets (cannot update already decided on tickets (through ticketDAOImp))
		Ticket ticket = ctx.bodyAsClass(Ticket.class);
		if (ticket.getTicketid() <= 0 || ticket.getStatus().isEmpty()) { //makes sure fields are not empty
			ctx.json("All fields must be filled in!");
		} else {
			if (tempRole != null) { //makes sure someone is logged in
				if (sess.getAttribute("role").equals("manager")) { //makes sure a manager is logged in
					
					int id = (int) sess.getAttribute("id");
					ctx.json(ticketService.makeDecisionOnTicket(ticket, id));
					

				} else {
					ctx.json("You must be logged in to a manager account to be able to update the status of tickets!");
				}
			} else {
				ctx.json("You must be logged in to a manager account to be able to update the status of tickets!");
			}
		}

	};
	
	
	Handler seeAllTickets = (ctx) ->{
		if (tempRole != null) { //makes sure logged in
			if (sess.getAttribute("role").equals("manager")) { //makes sure logged into manager account
				ctx.json(ticketService.seeAllTickets()); //returns all tickets
			}else {
				ctx.json("Must be logged into a manger account for this function!");
			}
		}else {
			ctx.json("Must be logged into a manger account for this function!");
		}
		
	};

	@Override
	public void addRoutes(Javalin app) {
		// USER
		app.get("/users", allUsers); // done 
		app.get("/login", login); // done 
		app.get("/signout", signout); // done 
		app.get("/session", session); // done 
		app.post("/newuser", newUser); // done
		app.patch("/promote", roleUpdate); // done 
		// TICKET
		app.get("/mytickets", checkMyTickets); // done 
		app.post("/createticket", createTicket); // done 
		app.get("/pendingtickets", seeMyPendingTickets); // done 
		app.get("/approvedtickets", seeMyApprovedTickets); // done 
		app.get("/declinedtickets", seeMyDeclinedTickets); // done 
		app.patch("/decideonticket", updateTicketStatus); // done 
		app.get("/alltickets", seeAllTickets); //done 

	}

}

package com.revature.session;

import io.javalin.http.Context;

public class Session {
	private static int id;
	private static String email;
	private static String firstName;
	private static String lastName;
	private static String role = "signed out";

	public Session() {
		super();
	}

	public static void session(Context ctx) {
		if (Session.getRole().equals("signed out")) {
			ctx.html("No one is currently logged in!");
		} else {
			ctx.html("Currently logged in as " + Session.getFirstName() + " " + Session.getLastName() + " with the role of " + Session.getRole() + " under the email: " + Session.getEmail());
			
		}
	}
	
	
	
	
	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		Session.id = id;
	}

	public static String getEmail() {
		return email;
	}

	public static void setEmail(String email) {
		Session.email = email;
	}

	public static String getFirstName() {
		return firstName;
	}

	public static void setFirstName(String firstName) {
		Session.firstName = firstName;
	}

	public static String getLastName() {
		return lastName;
	}

	public static void setLastName(String lastName) {
		Session.lastName = lastName;
	}

	public static String getRole() {
		return role;
	}

	public static void setRole(String role) {
		Session.role = role;
	}

}

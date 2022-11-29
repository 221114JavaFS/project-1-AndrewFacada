package com.revature.models;

public class Session {
	
	private static int id;
	private static String email;
	private static String firstName;
	private static String lastName;
	private static String role = "signed out";
	
	public Session() {
		super();
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

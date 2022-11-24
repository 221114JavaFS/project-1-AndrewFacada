package com.revature.session;

public class Session {
	private static String email = null;
	private static String role = "signed out";
	
	public Session() {
		
	}

	public static String getEmail() {
		return email;
	}

	public static void setEmail(String mail) {
		mail = email;
	}

	public static String getRole() {
		return role;
	}

	public void setRole(String rl) {
		rl = role;
	}
	
	
}

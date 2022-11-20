package com.revature.daos;

import java.util.HashMap;

import io.javalin.http.Context;

public class CompanyMembersDAO {
	
	private static HashMap<String,String> info = new HashMap<String,String>();
	
	
	public CompanyMembersDAO() {
		info.put("admin@gmail.com","safepassword123");
	}
	
	
	public static void getAllCompanyMembers(Context ctx) {
		ctx.json(info);
		ctx.status(200);
		
	}
	
	public static void addCompanyMember(Context ctx) {
		if(info.containsKey(ctx.pathParam("email"))) {
			ctx.status(403);
		}
		else {
			info.put(ctx.pathParam("email"),ctx.pathParam("password"));
			ctx.status(200);
		}
		
		
		
		
	}
	

}

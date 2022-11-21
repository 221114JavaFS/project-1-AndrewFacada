package com.revature.daos;

import java.util.ArrayList;
import java.util.List;

import com.revature.App;
import com.revature.companymemberinfo.CompanyMemberInfo;

import io.javalin.http.Context;

public class CompanyMembersDAO {
	
	private static List<CompanyMemberInfo> companyMembers = new ArrayList<>();
	
	public CompanyMembersDAO() {
		getCompanyMembers().add(new CompanyMemberInfo(1,"andrew264@revature.net", "strongpassword123","manager"));
		getCompanyMembers().add(new CompanyMemberInfo(2,"a","b","employee"));
	}
	
	
	public static void getAllMemberInfo(Context ctx) { //Allows managers to view all company members
		int temp = 0;
		for (CompanyMemberInfo a : CompanyMembersDAO.getCompanyMembers()) {
			if(App.session.equals(a.getEmail()) && a.getRole().equals("manager")) {
				ctx.json(getCompanyMembers());
				ctx.status(200);
				temp = 1;
			}
		}
		if(temp == 0) {
			ctx.json("You must be a manager to access all company members!");
			ctx.status(403);
		}
		
		
		
		
		
	}


	public static List<CompanyMemberInfo> getCompanyMembers() {
		return companyMembers;
	}
}

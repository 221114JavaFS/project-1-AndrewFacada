package com.revature;

//import com.revature.controllers.GameConsoleController;
import com.revature.daos.CompanyMembersDAO;

import io.javalin.Javalin;

public class App {
	
	public static Javalin app;
	
	public static void main(String[] args) {
		app = Javalin.create(); //creates app
		new CompanyMembersDAO(); //creates mock database (HashMap)
		
		
		/*app.get("/hello", (ctx)->{   				//test
			ctx.html("<h1>WORKING OH YAAA</h1>");
			ctx.status(200);
		});*/
		
		app.get("/allcompanymembers", CompanyMembersDAO::getAllCompanyMembers); //view all company members
		
		app.get("/signup/{email}/{password}", CompanyMembersDAO::addCompanyMember);
		
		//app.get("/login", null)
		
		//app.get("/signout", null);
		
		app.start(8081);
	}
	
	
	
	

}

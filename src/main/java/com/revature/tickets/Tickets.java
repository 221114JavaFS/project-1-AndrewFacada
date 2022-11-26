package com.revature.tickets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.App;
import com.revature.session.Session;

import io.javalin.http.Context;

public class Tickets {

	public Tickets() {
		super();
	}
	
	public static void createTicket(Context ctx) throws SQLException {  //NEEDS TO BE WORKED ON
		if(Session.getRole().equals("employee")) {
			PreparedStatement insertStmt = App.connect.prepareStatement
					("INSERT INTO ticket(user_id, reimbursement_type, reimbursement_amount, description , ticket_created) VALUES(?, ?, ?, ?, now())");
			int id = Session.getId();
			String reimbursementType = ctx.formParam("reimbursement type");
			String reimbursementAmountNeedingToBeParsed = ctx.formParam("reimbursement amount");
			double reimbursementAmount = Double.parseDouble(reimbursementAmountNeedingToBeParsed);
			String description = ctx.formParam("description");
			
			insertStmt.setInt(1, id);
			insertStmt.setString(2, reimbursementType);
			insertStmt.setDouble(3, reimbursementAmount);
			insertStmt.setString(4, description);
			ResultSet rs = insertStmt.executeQuery();
			if(rs.next()) {
				ctx.html("Ticket Successfully submitted!");
			}
			
		}else {
			ctx.html("You must be logged into an employee account to create a ticket!");
		}
	}
}

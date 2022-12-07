package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class TicketDAOImpl implements TicketDAO{

	@Override
	public List<Ticket> findAllMyTickets(int id) { //lets user see all tickets
		try(Connection connection = ConnectionUtil.getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE user_id = ?;");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			
			List<Ticket> list = new ArrayList<>(); //new list for tickets
			
			while(result.next()) {
				Ticket ticket = new Ticket(); //new ticket object
				ticket.setUserid(result.getInt("user_id"));
				ticket.setTicketid(result.getInt("ticket_id"));
				ticket.setReimbursementType(result.getString("reimbursement_type"));
				ticket.setAmount(result.getDouble("reimbursement_amount"));
				ticket.setDescription(result.getString("description"));
				ticket.setStatus(result.getString("status"));
				ticket.setTimeCreated(result.getString("ticket_created"));
				
				if(result.getString("updates_upon_decision") != null) {
					ticket.setTimeDecided(result.getString("updates_upon_decision"));
				}
				
				list.add(ticket); //adds ticket object to list
			}
			
			if(list.isEmpty()) {
				return null;
			}
			
			return list; //returns list of tickets
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@Override
	public String createTicket(Ticket ticket, int id) { //for creating new ticket
		try(Connection connection = ConnectionUtil.getConnection()){
			PreparedStatement statement = connection.prepareStatement("INSERT INTO ticket(user_id, reimbursement_type, reimbursement_amount, description , ticket_created) VALUES (?, ?, ?, ?, now());");
			statement.setInt(1,id);
			statement.setString(2, ticket.getReimbursementType());
			statement.setDouble(3, ticket.getAmount());
			statement.setString(4, ticket.getDescription());
			int result = statement.executeUpdate();
			
			return "Ticket created!";
			
		}catch(SQLException e) {
			e.printStackTrace();
			return "Ticket not created!";
		}
		
	}

	@Override
	public String decideOnTicket(Ticket ticket, int id) { // for manager to decide on tickets
		try(Connection connection = ConnectionUtil.getConnection()){
			//Checks to see if ticket exists
			PreparedStatement statementCheckOne = connection.prepareStatement("SELECT * FROM ticket WHERE ticket_id = ?;");
			statementCheckOne.setInt(1,ticket.getTicketid());
			ResultSet resultOne = statementCheckOne.executeQuery();
			if(!resultOne.next()) {
				return "There are no tickets with that ticket id!";
				
			}
			
			//Makes sure manager cannot update status of own ticket
			PreparedStatement statementCheckIfDecidingOnOwnTicket = connection.prepareStatement("SELECT user_id FROM ticket WHERE ticket_id = ? AND user_id = ?;");
			statementCheckIfDecidingOnOwnTicket.setInt(1, ticket.getTicketid());
			statementCheckIfDecidingOnOwnTicket.setInt(2, id);
			ResultSet  resultForOwnTicket = statementCheckIfDecidingOnOwnTicket.executeQuery();
			if(resultForOwnTicket.next()) {
				return "Cannot update your own tickets!";
				
			}
			
			
			//checks to see if ticket already decided on
			PreparedStatement statementCheckTwo = connection.prepareStatement("SELECT status FROM ticket WHERE ticket_id = ?;");
			statementCheckTwo.setInt(1,ticket.getTicketid());
			ResultSet resultTwo = statementCheckTwo.executeQuery();
			if(resultTwo.next()) {
				if(!resultTwo.getString("status").equals("pending")) {
					return "Cannot change the status of already approved/declined tickets!";
				}
			}
			
			//ticket gets decided on
			PreparedStatement statement = connection.prepareStatement("UPDATE ticket SET status = ?, updates_upon_decision = now() WHERE ticket_id = ?;");
			statement.setString(1,ticket.getStatus());
			statement.setInt(2,ticket.getTicketid());
			int resultThree = statement.executeUpdate();
			return "The ticket status has been updated to " + ticket.getStatus() + " for ticket id: " + ticket.getTicketid();
			
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			return "Ticket not updated!";
		}
	}



	@Override
	public List<Ticket> findMyPendingTickets(int id) { //returns pending tickets for specific user (if any)
		try(Connection connection = ConnectionUtil.getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE user_id = ? AND status = 'pending';");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			List<Ticket> list = new ArrayList<>(); //new list for ticket
			while(result.next()) {
				Ticket ticket = new Ticket(); //creates new ticket object
				ticket.setUserid(result.getInt("user_id"));
				ticket.setTicketid(result.getInt("ticket_id"));
				ticket.setReimbursementType(result.getString("reimbursement_type"));
				ticket.setAmount(result.getDouble("reimbursement_amount"));
				ticket.setDescription(result.getString("description"));
				ticket.setStatus(result.getString("status"));
				ticket.setTimeCreated(result.getString("ticket_created"));
				
				list.add(ticket); //adds ticket to list
			}
			
			if(list.isEmpty()) {
				return null;
			}
			return list; //returns list of tickets
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public List<Ticket> findMyApprovedTickets(int id) {  //returns approved tickets for specific user (if any)
		try(Connection connection = ConnectionUtil.getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE user_id = ? AND status = 'approved';");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			List<Ticket> list = new ArrayList<>(); //new list for ticket
			while(result.next()) {
				Ticket ticket = new Ticket(); //creates new ticket object
				ticket.setUserid(result.getInt("user_id"));
				ticket.setTicketid(result.getInt("ticket_id"));
				ticket.setReimbursementType(result.getString("reimbursement_type"));
				ticket.setAmount(result.getDouble("reimbursement_amount"));
				ticket.setDescription(result.getString("description"));
				ticket.setStatus(result.getString("status"));
				ticket.setTimeCreated(result.getString("ticket_created"));
				ticket.setTimeDecided(result.getString("updates_upon_decision"));
				
				list.add(ticket); //adds ticket to list
			}
			
			if(list.isEmpty()) {
				return null;
			}
			return list; //returns list of tickets
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public List<Ticket> findMyDeclinedTickets(int id) { //returns declined tickets for specific user (if any)
		try(Connection connection = ConnectionUtil.getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE user_id = ? AND status = 'declined';");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			List<Ticket> list = new ArrayList<>(); //new list for ticket
			while(result.next()) {
				Ticket ticket = new Ticket(); //creates new ticket object
				ticket.setUserid(result.getInt("user_id"));
				ticket.setTicketid(result.getInt("ticket_id"));
				ticket.setReimbursementType(result.getString("reimbursement_type"));
				ticket.setAmount(result.getDouble("reimbursement_amount"));
				ticket.setDescription(result.getString("description"));
				ticket.setStatus(result.getString("status"));
				ticket.setTimeCreated(result.getString("ticket_created"));
				ticket.setTimeDecided(result.getString("updates_upon_decision"));
				
				list.add(ticket); //adds ticket to list
			}
			
			if(list.isEmpty()) {
				return null;
			}
			return list; //returns list of tickets
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public List<Ticket> findAllTickets() {
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM ticket;";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			List<Ticket> list = new ArrayList<>(); //Creates list to return all ticket objects
			
			while(result.next()) {
				Ticket ticket = new Ticket();
				ticket.setUserid(result.getInt("user_id"));
				ticket.setTicketid(result.getInt("ticket_id"));
				ticket.setReimbursementType(result.getString("reimbursement_type"));
				ticket.setAmount(result.getDouble("reimbursement_amount"));
				ticket.setDescription(result.getString("description"));
				ticket.setStatus(result.getString("status"));
				ticket.setTimeCreated(result.getString("ticket_created"));
				
				if(result.getString("updates_upon_decision") != null) { //if decided up, adds time decided on
					ticket.setTimeDecided(result.getString("updates_upon_decision"));
				}
				
				list.add(ticket); //returns list of ticket objects
			}
			
			if(list.isEmpty()) {
				return null;
			}
			
			return list;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
	}
		
		
	}

	

}

package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class TicketDAOImpl implements TicketDAO{

	@Override
	public List<Ticket> findAllMyTickets(int id) {
		try(Connection connection = ConnectionUtil.getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE user_id = ?;");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			
			List<Ticket> list = new ArrayList<>();
			
			while(result.next()) {
				Ticket ticket = new Ticket();
				
				ticket.setTicketid(result.getInt("ticket_id"));
				ticket.setReimbursementType(result.getString("reimbursement_type"));
				ticket.setAmount(result.getDouble("reimbursement_amount"));
				ticket.setDescription(result.getString("description"));
				ticket.setStatus(result.getString("status"));
				ticket.setTimeCreated(result.getString("ticket_created"));
				
				list.add(ticket);
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
	
	
	
	@Override
	public String createTicket(Ticket ticket, int id) {
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
	public String decideOnTicket(Ticket ticket) {
		
	}



	@Override
	public List<Ticket> findMyPendingTickets(int id) {
		try(Connection connection = ConnectionUtil.getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE user_id = ? AND status = 'pending';");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			List<Ticket> list = new ArrayList<>();
			while(result.next()) {
				Ticket ticket = new Ticket();
				ticket.setTicketid(result.getInt("ticket_id"));
				ticket.setReimbursementType(result.getString("reimbursement_type"));
				ticket.setAmount(result.getDouble("reimbursement_amount"));
				ticket.setDescription(result.getString("description"));
				ticket.setStatus(result.getString("status"));
				ticket.setTimeCreated(result.getString("ticket_created"));
				
				list.add(ticket);
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



	@Override
	public List<Ticket> findMyApprovedTickets(int id) {
		try(Connection connection = ConnectionUtil.getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE user_id = ? AND status = 'approved';");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			List<Ticket> list = new ArrayList<>();
			while(result.next()) {
				Ticket ticket = new Ticket();
				ticket.setTicketid(result.getInt("ticket_id"));
				ticket.setReimbursementType(result.getString("reimbursement_type"));
				ticket.setAmount(result.getDouble("reimbursement_amount"));
				ticket.setDescription(result.getString("description"));
				ticket.setStatus(result.getString("status"));
				ticket.setTimeCreated(result.getString("ticket_created"));
				
				list.add(ticket);
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



	@Override
	public List<Ticket> findMyDeclinedTickets(int id) {
		try(Connection connection = ConnectionUtil.getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE user_id = ? AND status = 'declined';");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			List<Ticket> list = new ArrayList<>();
			while(result.next()) {
				Ticket ticket = new Ticket();
				ticket.setTicketid(result.getInt("ticket_id"));
				ticket.setReimbursementType(result.getString("reimbursement_type"));
				ticket.setAmount(result.getDouble("reimbursement_amount"));
				ticket.setDescription(result.getString("description"));
				ticket.setStatus(result.getString("status"));
				ticket.setTimeCreated(result.getString("ticket_created"));
				
				list.add(ticket);
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

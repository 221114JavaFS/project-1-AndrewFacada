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
	public String createTicket(Ticket ticket) {
		
	}

	@Override
	public String decideOnTicket(Ticket ticket) {
		
	}

}

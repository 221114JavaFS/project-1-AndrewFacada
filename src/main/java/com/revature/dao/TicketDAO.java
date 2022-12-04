package com.revature.dao;

import java.util.List;

import com.revature.models.Ticket;

public interface TicketDAO {
	
	public abstract List<Ticket> findAllMyTickets(int id);
	
	public abstract String createTicket(Ticket ticket, int id);
	
	public abstract String decideOnTicket(Ticket ticket);

}

package com.revature.dao;

import java.util.List;

import com.revature.models.Ticket;

public interface TicketDAO {
	
	public abstract List<Ticket> findAllMyTickets(int id); //done
	
	public abstract List<Ticket> findMyPendingTickets(int id); //done
	
	public abstract List<Ticket> findMyApprovedTickets(int id); //done
	
	public abstract List<Ticket> findMyDeclinedTickets(int id); 
	
	public abstract String createTicket(Ticket ticket, int id); //done
	
	public abstract String decideOnTicket(Ticket ticket);

}

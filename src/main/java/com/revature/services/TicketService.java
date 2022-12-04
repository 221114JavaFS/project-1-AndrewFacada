package com.revature.services;

import java.util.List;

import com.revature.dao.TicketDAO;
import com.revature.dao.TicketDAOImpl;
import com.revature.models.Ticket;

public class TicketService {
	
	private TicketDAO ticketDao = new TicketDAOImpl();
	
	public List<Ticket> seeMyTickets(int id){
		return ticketDao.findAllMyTickets(id);
	}
	
	public String createTicket(Ticket ticket, int id) {
		return ticketDao.createTicket(ticket, id);
	}
	
	


}

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
	
	public List<Ticket> seeMyPendingTickets(int id){
		return ticketDao.findMyPendingTickets(id);
	}
	
	public List<Ticket> seeMyApprovedTickets(int id){
		return ticketDao.findMyApprovedTickets(id);
	}
	
	public List<Ticket> seeMyDeclinedTickets(int id){
		return ticketDao.findMyDeclinedTickets(id);
	}
	
	public String makeDecisionOnTicket(Ticket ticket, int id) {
		return ticketDao.decideOnTicket(ticket, id);
	}
	
	public List<Ticket> seeAllTickets(){
		return ticketDao.findAllTickets();
	}
	


}

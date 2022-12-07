package com.revature.models;

public class Ticket {
	private int userid;
	private int ticketid;
	private String reimbursementType;
	private double amount;
	private String description;
	private String status;
	private String timeCreated;
	private String timeDecided;
	
	
	
	
	public Ticket(int ticketid, String reimbursementType, double amount, String description, String status,
			String timeCreated, String timeDecided) {
		super();
		this.ticketid = ticketid;
		this.reimbursementType = reimbursementType;
		this.amount = amount;
		this.description = description;
		this.status = status;
		this.timeCreated = timeCreated;
		this.timeDecided = timeDecided;
	}

	public Ticket(int ticketid, String reimbursementType, double amount, String description, String status, String timeCreated) {
		super();
		this.ticketid = ticketid;
		this.reimbursementType = reimbursementType;
		this.amount = amount;
		this.description = description;
		this.setStatus(status);
		this.setTimeCreated(timeCreated);
	}
	
	public Ticket(int ticketid, String reimbursementType, double amount, String description) {
		super();
		this.ticketid = ticketid;
		this.reimbursementType = reimbursementType;
		this.amount = amount;
		this.description = description;
	}
	
	
	
	
	public Ticket(int ticketid, int userid , String status) {
		super();
		this.ticketid = ticketid;
		this.userid = userid;
		this.status = status;
	}

	public Ticket(int ticketid, String reimbursementType, double amount, String description, String status) {
		super();
		this.ticketid = ticketid;
		this.reimbursementType = reimbursementType;
		this.amount = amount;
		this.description = description;
		this.status = status;
	}
	
	

	public Ticket(String reimbursementType, double amount, String description) {
		super();
		this.reimbursementType = reimbursementType;
		this.amount = amount;
		this.description = description;
	}

	public Ticket() {
		super();
	}
	
	
	

	public int getTicketid() {
		return ticketid;
	}

	public void setTicketid(int ticketid) {
		this.ticketid = ticketid;
	}

	public String getReimbursementType() {
		return reimbursementType;
	}

	public void setReimbursementType(String reimbursementType) {
		this.reimbursementType = reimbursementType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userId) {
		this.userid = userId;
	}
	
	
	

	public String getTimeDecided() {
		return timeDecided;
	}

	public void setTimeDecided(String timeDecided) {
		this.timeDecided = timeDecided;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((reimbursementType == null) ? 0 : reimbursementType.hashCode());
		result = prime * result + ticketid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (reimbursementType == null) {
			if (other.reimbursementType != null)
				return false;
		} else if (!reimbursementType.equals(other.reimbursementType))
			return false;
		if (ticketid != other.ticketid)
			return false;
		return true;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}
	
	
	
	
	
	
	
}

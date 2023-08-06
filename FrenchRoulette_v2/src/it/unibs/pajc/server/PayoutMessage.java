package it.unibs.pajc.server;

import java.io.Serializable;

public class PayoutMessage implements Serializable {
	private double newBalance;

	public PayoutMessage(double newBalance) {
		super();
		this.newBalance = newBalance;
	}

	public double getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(double newBalance) {
		this.newBalance = newBalance;
	}
	
	
}

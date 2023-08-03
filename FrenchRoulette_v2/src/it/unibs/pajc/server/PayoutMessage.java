package it.unibs.pajc.server;

import java.io.Serializable;

public class PayoutMessage implements Serializable {
	private int newBalance;

	public PayoutMessage(int newBalance) {
		super();
		this.newBalance = newBalance;
	}

	public int getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(int newBalance) {
		this.newBalance = newBalance;
	}
	
	
}

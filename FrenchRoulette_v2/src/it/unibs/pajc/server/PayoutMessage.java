package it.unibs.pajc.server;

import java.io.Serializable;

public class PayoutMessage implements Serializable {
	private double newBalance;
	private double lastWin;

	public PayoutMessage(double newBalance, double lastWin) {
		super();
		this.newBalance = newBalance;
		this.lastWin = lastWin;
	}

	public double getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(double newBalance) {
		this.newBalance = newBalance;
	}

	public double getLastWin() {
		return lastWin;
	}

	public void setLastWin(double lastWin) {
		this.lastWin = lastWin;
	}
	
	
	
}

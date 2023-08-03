package it.unibs.pajc.server;

import java.util.LinkedList;
import java.util.List;

import it.unibs.pajc.WheelNumber;
public class BetsMessage implements Message{
	private List<WheelNumber> bets;
	private int totalAmount;

	public BetsMessage(List<WheelNumber> bets, int totalAmount) {
		super();
		this.bets = new LinkedList<WheelNumber>(bets);
		this.totalAmount = totalAmount;
	}

	public List<WheelNumber> getBets() {
		return bets;
	}

	public void setBets(List<WheelNumber> bets) {
		this.bets = bets;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	
	
}

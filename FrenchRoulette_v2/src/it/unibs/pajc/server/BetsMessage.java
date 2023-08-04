package it.unibs.pajc.server;

import java.util.LinkedList;
import java.util.List;

import it.unibs.pajc.WheelNumber;
public class BetsMessage implements Message{
	private List<WheelNumber> bets;
	private List<Zone> zoneBets;
	private int totalAmount;

	public BetsMessage(List<WheelNumber> bets, List<Zone> zoneBets, int totalAmount) {
		super();
		this.bets = new LinkedList<WheelNumber>(bets);
		this.zoneBets = new LinkedList<Zone>(zoneBets);
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

	public List<Zone> getZoneBets() {
		return zoneBets;
	}

	public void setZoneBets(List<Zone> zoneBets) {
		this.zoneBets = zoneBets;
	}
	
	
	
	
}

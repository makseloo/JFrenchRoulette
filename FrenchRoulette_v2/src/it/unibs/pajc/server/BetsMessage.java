package it.unibs.pajc.server;

import java.util.LinkedList;
import java.util.List;

import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
public class BetsMessage implements Message{
	private List<WheelNumber> bets;
	private List<Zone> zones;
	private int totalAmount;
	

	public BetsMessage(List<WheelNumber> bets, int totalAmount) {
		super();
		this.bets = new LinkedList<WheelNumber>(bets);
		this.totalAmount = totalAmount;
	}

	
	
	public BetsMessage(List<WheelNumber> bets, int totalAmount, List<Zone> zones) {
		super();
		this.bets = bets;
		this.zones = zones;
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

	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

	
	
	
	
}

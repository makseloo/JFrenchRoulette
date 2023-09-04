package it.unibs.pajc.server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
public class BetsMessage implements Serializable{
	private List<WheelNumber> bets;
	private List<Zone> zones;
	private double totalAmount;
	

	public BetsMessage(List<WheelNumber> bets, double totalAmount) {
		super();
		this.bets = new LinkedList<WheelNumber>(bets);
		this.totalAmount = totalAmount;
	}

	
	
	public BetsMessage(List<WheelNumber> bets, double totalAmount, List<Zone> zones) {
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

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}
	
}

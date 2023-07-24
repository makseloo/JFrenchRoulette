package it.unibs.pajc.server;

import java.util.List;

import it.unibs.pajc.WheelNumber;
public class BetsMessage implements Message{
	private List<WheelNumber> bets;

	public BetsMessage(List<WheelNumber> bets) {
		super();
		this.bets = bets;
	}

	public List<WheelNumber> getBets() {
		return bets;
	}

	public void setBets(List<WheelNumber> bets) {
		this.bets = bets;
	}
	
	
	
	
}

package it.unibs.pajc.server;

import java.util.LinkedList;
import java.util.List;

public class Zone {
	private String name;
	private List<Integer> numbers;
	private int betValue;
	private int zonePayout;
	
	public Zone(String name, List<Integer> numbers, int zonePayout) {
		super();
		this.name = name;
		this.numbers = new LinkedList<>(numbers);
		this.zonePayout = zonePayout;
	}
	
	//tier orphelins voisins have no specific payout
	public Zone(String name, List<Integer> numbers) {
		super();
		this.name = name;
		this.numbers = new LinkedList<>(numbers);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getNumbers() {
		return numbers;
	}
	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}
	public int getBetValue() {
		return betValue;
	}
	public void setBetValue(int betValue) {
		this.betValue = betValue;
	}

	public int getZonePayout() {
		return zonePayout;
	}

	public void setZonePayout(int zonePayout) {
		this.zonePayout = zonePayout;
	}
	
	
	
}

package it.unibs.pajc.server.messages;


import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

import it.unibs.pajc.WheelNumber;


public class StatsMessage implements Serializable {
	private List<WheelNumber> numbers;
	private Map<String, Integer> stats;
	

	
	public StatsMessage(List<WheelNumber> numbers) {
		super();
		this.numbers = new LinkedList<>(numbers);
	}
	
	//in case you want to send the % of the different zones
	/*
	public StatsMessage(List<WheelNumber> numbers, Map<String, Integer> stats) {
		super();
		this.numbers = new LinkedList<>(numbers);
		this.stats = new  HashMap<>(stats);
	}
	*/
	
	public List<WheelNumber> getNumbers() {
		return this.numbers;
	}

	public void setNumbers(List<WheelNumber> numbersRec) {
		this.numbers = numbersRec;

	}

	public Map<String, Integer> getStats() {
		return stats;
	}

	public void setStats(Map<String, Integer> stats) {
		this.stats = stats;
	}
	
}

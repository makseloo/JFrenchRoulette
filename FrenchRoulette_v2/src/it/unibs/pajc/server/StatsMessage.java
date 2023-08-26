package it.unibs.pajc.server;


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
	public StatsMessage(List<WheelNumber> numbers, Map<String, Integer> stats) {
		super();
		this.numbers = new LinkedList<>(numbers);
		this.stats = new  HashMap<>(stats);
	}
	
	public List<WheelNumber> getNumbers() {
		return this.numbers;
	}

	public void setNumbers(List<WheelNumber> numbersRec) {
		System.out.print("Received: \n");
		for(WheelNumber i : numbersRec) {
			System.out.print(i+",");
		}
		this.numbers = numbersRec;
		System.out.print("\nSent: \n");
		for(WheelNumber j : numbers) {
			System.out.print(j+",");
		}
	}

	public Map<String, Integer> getStats() {
		return stats;
	}

	public void setStats(Map<String, Integer> stats) {
		this.stats = stats;
	}
	
}

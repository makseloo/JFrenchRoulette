package it.unibs.pajc.server;

import java.util.List;
import java.util.Map;

import it.unibs.pajc.WheelNumber;

public class StatsMessage implements Message{
	private List<Integer> numbers;
	private Map<String, Integer> stats;

	
	
	public StatsMessage(List<Integer> numbers) {
		super();
		this.numbers = numbers;
	}

	public StatsMessage(List<Integer> numbers, Map<String, Integer> stats) {
		super();
		this.numbers = numbers;
		this.stats = stats;
	}
	
	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}

	public Map<String, Integer> getStats() {
		return stats;
	}

	public void setStats(Map<String, Integer> stats) {
		this.stats = stats;
	}
	
	
	
}

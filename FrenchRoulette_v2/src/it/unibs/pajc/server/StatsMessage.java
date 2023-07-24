package it.unibs.pajc.server;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import it.unibs.pajc.WheelNumber;

public class StatsMessage implements Message{
	private Queue<Integer> numbers;
	private Map<String, Integer> stats;

	
	
	public StatsMessage(Queue<Integer> numbers) {
		super();
		this.numbers = numbers;
	}

	public StatsMessage(Queue<Integer> numbers, Map<String, Integer> stats) {
		super();
		this.numbers = numbers;
		this.stats = stats;
	}
	
	public Queue<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(Queue<Integer> numbers) {
		this.numbers = numbers;
	}

	public Map<String, Integer> getStats() {
		return stats;
	}

	public void setStats(Map<String, Integer> stats) {
		this.stats = stats;
	}
	
	
	
}

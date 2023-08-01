package it.unibs.pajc.server;


import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class StatsMessage implements Serializable {
	private Queue<Integer> numbers;
	private Map<String, Integer> stats;
	
	public StatsMessage(Queue<Integer> numbers, Map<String, Integer> stats) {
		super();
		this.numbers = new LinkedList<>(numbers);
		this.stats = new  HashMap<>(stats);
	}
	
	public Queue<Integer> getNumbers() {
		return this.numbers;
	}

	public void setNumbers(Queue<Integer> numbersRec) {
		System.out.print("Received: \n");
		for(int i : numbersRec) {
			System.out.print(i+",");
		}
		this.numbers = numbersRec;
		System.out.print("\nSent: \n");
		for(int j : numbers) {
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

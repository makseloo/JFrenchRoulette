package it.unibs.pajc.server;

import java.util.List;

import it.unibs.pajc.WheelNumber;

public class StatsMessage implements Message{
	private List<Integer> numbers;

	
	
	public StatsMessage(List<Integer> numbers) {
		super();
		this.numbers = numbers;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}
	
	
}

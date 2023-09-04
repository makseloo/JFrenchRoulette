package it.unibs.pajc;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WheelNumber implements Serializable {	
	private int id;
	private int value;
	private List<String> zone;
	private Color color;
	private double betValue;
	
	public WheelNumber() {
		
	}
	
	public WheelNumber(int id, int value, List<String> zone, Color color) {
		this.id = id;
		this.value = value;
		this.zone = new ArrayList<>(zone);
		this.color = color;
		this.betValue = 0;
	}
	//quando devo aggiungere una puntata
	public WheelNumber(int id,int value, List<String> zone, Color color, double betValue) {
		this.id = id;
		this.value = value;
		this.zone = new ArrayList<>(zone);
		this.color = color;
		this.betValue = betValue;
	}
	
	public int getValue() {
		return value;
	}

	public Color getColor() {
		return color;
	}

	public void setValue(double value) {
		this.betValue += value;
	}

	public double getBettedValue() {
		return betValue;
	}
	
	public void setBetValue(int value) {
		this.betValue = value;
		
	}

	public List<String> getZone() {
		return zone;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	
	
}

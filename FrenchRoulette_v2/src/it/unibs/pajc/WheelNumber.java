package it.unibs.pajc;

import java.awt.Color;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;



public class WheelNumber implements Serializable {
	
	public static List<Integer> red = Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19 ,21, 23, 25, 27, 30, 32 ,26 ,34);
	public static  List<Integer> black = Arrays.asList(2, 4, 6, 8, 10, 11, 13, 15, 17, 20 ,22, 24, 26, 28, 29, 31 ,33 ,35);
	
	public static List<Integer> tier = Arrays.asList(33, 16, 24, 5, 10, 23, 8, 30, 11, 36 ,13, 27, 25, 27, 30, 32 ,26 ,34);
	public static List<Integer> orphelins = Arrays.asList(1, 20, 14, 31, 9, 6, 34, 17);
	public static List<Integer> voisins = Arrays.asList(22, 18, 29, 7, 28, 25, 2, 21, 4, 19,12, 35, 3, 26, 0, 32, 15);
	public static List<Integer> zero = Arrays.asList(12, 35, 3, 26, 0, 32, 15);
	//numbers order based on their position on the wheel
	public static List<Integer> numbers = Arrays.asList(0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26);
	
	private int value;
	private String zone;
	private Color color;
	private int bettedValue;
	
	public WheelNumber(int value, String zone, Color color) {
		this.value = value;
		this.zone = zone;
		this.color = color;
		this.bettedValue = 0;
	}
	
	public static List<Integer> getBlackNums() {
		return black;
	}
	public static List<Integer> getRedNums() {
		return red;
	}
	
	public static List<Integer> getRed() {
		return red;
	}

	public static List<Integer> getTier() {
		return tier;
	}

	public static List<Integer> getOrphelins() {
		return orphelins;
	}

	public static List<Integer> getVoisins() {
		return voisins;
	}

	public static List<Integer> getZero() {
		return zero;
	}
	
	public static List<Integer> getNums() {
		return numbers;
	}

	public int getValue() {
		return value;
	}

	public Color getColor() {
		return color;
	}

	public void setValue(double value) {
		this.bettedValue += value;
	}

	public int getBettedValue() {
		return bettedValue;
	}


}

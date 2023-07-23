package it.unibs.pajc;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
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
	//dozens
	public static List<Integer> dozen1 = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
	public static List<Integer> dozen2 = Arrays.asList(13,14,15,16,17,18,19,20,21,22,23,24);
	public static List<Integer> dozen3 = Arrays.asList(25,26,27,28,29,30,31,32,33,34,35,36);
	//cols
	public static List<Integer> col1 = Arrays.asList(1,4,7,10,13,16,19,22,25,28,31,34);
	public static List<Integer> col2 = Arrays.asList(2,5,8,11,14,17,20,23,26,29,32,35);
	public static List<Integer> col3 = Arrays.asList(3,6,9,12,15,18,21,24,27,30,33,36);
	//1-18 19-36
	public static List<Integer> eight = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
	public static List<Integer> six = Arrays.asList(19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36);
	//even odd
	public static List<Integer> even = Arrays.asList(2, 4, 6, 8, 10, 12, 14, 16, 18,20, 22, 24, 26, 28, 30, 32, 34, 36);
	public static List<Integer> odd = Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15, 17,19, 21, 23, 25, 27, 29, 31, 33, 35);
	
	public static List<String> zones = Arrays.asList("TIER","ORPHELINS","VOISINS","ZERO");
	
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

	public static List<Integer> getDozen1() {
		return dozen1;
	}

	public static List<Integer> getDozen2() {
		// TODO Auto-generated method stub
		return dozen2;
	}

	public static List<Integer> getDozen3() {
		return dozen3;
	}

	public static List<Integer> getCol1() {
		return col1;
	}

	public static List<Integer> getCol2() {
		return col2;
	}

	public static List<Integer> getCol3() {
		return col3;
	}

	public static List<Integer> getEight() {
		return eight;
	}

	public static List<Integer> getSix() {
		return six;
	}

	public static List<Integer> getEven() {
		return even;
	}

	public static List<Integer> getOdd() {
		return odd;
	}

	public String getZone() {
		return zone;
	}

	public static List<String> getZones() {
		return zones;
	}

	public static void setZones(List<String> zones) {
		WheelNumber.zones = zones;
	}

	
}

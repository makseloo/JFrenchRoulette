package it.unibs.pajc.server;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.naming.ldap.SortControl;

import it.unibs.pajc.Colors;
import it.unibs.pajc.WheelNumber;

public class Numbers {
	
	public static List<Integer> red = Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19 ,21, 23, 25, 27, 30, 32 ,26 ,34, 36);
	public static  List<Integer> black = Arrays.asList(2, 4, 6, 8, 10, 11, 13, 15, 17, 20 ,22, 24, 26, 28, 29, 31 ,33 ,35);
	
	public static List<Integer> tier = Arrays.asList(5, 8, 10, 11, 13, 16, 23, 24, 27, 30, 33,36);
	public static List<Integer> orphelins = Arrays.asList(1, 20, 14, 31, 9, 6, 34, 17);
	public static List<Integer> voisins = Arrays.asList(22, 18, 29, 7, 28, 12, 35, 3, 26, 0, 32, 15, 19, 4, 21, 2, 25);
	public static List<Integer> zero = Arrays.asList(12, 35, 3, 26, 0, 32, 15);
	//numbers order based on their position on the wheel
	public static List<Integer> orderedNumbers = Arrays.asList(17,34,6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26,0,32, 15, 19, 4, 21, 2, 25);
	//dozens
	public static List<Integer> dozen1 = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
	public static List<Integer> dozen2 = Arrays.asList(13,14,15,16,17,18,19,20,21,22,23,24);
	public static List<Integer> dozen3 = Arrays.asList(25,26,27,28,29,30,31,32,33,34,35,36);
	//cols
	public static List<Integer> col3 = Arrays.asList(1,4,7,10,13,16,19,22,25,28,31,34);
	public static List<Integer> col2 = Arrays.asList(2,5,8,11,14,17,20,23,26,29,32,35);
	public static List<Integer> col1 = Arrays.asList(3,6,9,12,15,18,21,24,27,30,33,36);
	//1-18 19-36
	public static List<Integer> eight = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
	public static List<Integer> six = Arrays.asList(19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36);
	//even odd
	public static List<Integer> even = Arrays.asList(2, 4, 6, 8, 10, 12, 14, 16, 18,20, 22, 24, 26, 28, 30, 32, 34, 36);
	public static List<Integer> odd = Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15, 17,19, 21, 23, 25, 27, 29, 31, 33, 35);
	
	public static List<String> zones = Arrays.asList("TIER","ORPHELINS","VOISINS","ZERO");
	
	public static List<String> dozAndCols = Arrays.asList("1°:12","2°:12","3°:12","1° row","2° row","3° row");
	
	public static List<String> othersStat = Arrays.asList("1-18","EVEN","RED","BLACK","ODD","19-36");
	
	public static List<Integer> up = Arrays.asList(16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12);
	public static List<Integer> downs = Arrays.asList(11, 36, 13, 27, 6, 34, 17, 25, 2, 21, 4, 19, 15);
	public static List<Integer> right = Arrays.asList(35,3,26,0,32);
	public static List<Integer> left = Arrays.asList(24, 5, 10, 23, 8, 30);
	
	
	public List<WheelNumber> numbers;
	public static List<WheelNumber> sortedNumbers;
	List<Integer> blacks = WheelNumber.getBlackNums();
	List<Integer> reds = WheelNumber.getRedNums();
	
	
	public Numbers() {
		numbers = new ArrayList<WheelNumber>();
		//numbers in the wheel order + the 0
		
		
		int result;
		Color color;
		
		List<String> zones = new ArrayList<>();

		 for (int row = 3; row > 0; row--) {
	            for (int column = 0; column < 36; column += 3) {
	            	result = row + column;
	            	
	            	if(blacks.contains(result))
	            		color = new Colors().getBlack();
	            	else
	            		color =  new Colors().getRed();
	            	
	            	if(tier.contains(result)) {
	            		zones.add("tier");
	            	}else if(orphelins.contains(result)) {
	            		zones.add("orphelins");
	            	}else if(voisins.contains(result)) {
	            		zones.add("voisins");
	            	}
	            	
	            	if(result <= 18 && result >= 0) {
	            		zones.add("1-18");
	            	}else if(result >= 19 ){
	            		zones.add("19-36");
	            	}
	            	
	            	if(result >=25) {
	            		zones.add("3°:12");
	            	}else if(result >=13){
	            		zones.add("2°:12");
	            	}else if(result >= 1){
	            		zones.add("1°:12");
	            	}
	            	
	            	if(col1.contains(result)) {
	            		zones.add("1° row");
	            	}else if(col2.contains(result)) {
	            		zones.add("2° row");
	            	}else if(col3.contains(result)) {
	            		zones.add("3° row");
	            	}
	            	
	            	if(result % 2 == 0) {
	            		zones.add("EVEN");
	            	}else {
	            		zones.add("ODD");
	            	}
	            	//separately because zero is included in voisins
	            	if(zero.contains(result))
	            		zones.add("z");
	            	WheelNumber number = new WheelNumber(result,result, zones, color);
	            	numbers.add(number);
	            	zones = new ArrayList<>();
	            	
	            }
	            
	            
	        }
		 sortedNumbers = sort(numbers);
		 
	}
	public List<WheelNumber> getNumbers() {
		return numbers;
	}
	
	private List<WheelNumber> sort(List<WheelNumber> numberList) {
		List<WheelNumber> sortedList = new ArrayList<>(numberList);
		List<Integer> desiredOrder = WheelNumber.getNums();
		List<String> zeroZone = new ArrayList<>();
		zeroZone.add("voisins");
		zeroZone.add("z");
		
		WheelNumber zero = new WheelNumber(0,0, zeroZone, Colors.getGreen());

		
		Collections.sort(sortedList, new Comparator<WheelNumber>() {
			 @Override
	            public int compare(WheelNumber w1, WheelNumber w2) {
	                Integer value1 = w1.getValue();
	                Integer value2 = w2.getValue();
	                return Integer.compare(desiredOrder.indexOf(value1), desiredOrder.indexOf(value2));
	            }
		});
		//perché in numberlist non c'è lo 0
		sortedList.add(zero);
		
		return sortedList;
	}
	
	public List<WheelNumber> getSortedNumbers() {
		return sortedNumbers;
	}
	
	public static int indexOf(WheelNumber lastNumber) {
		for (int i = 0; i < orderedNumbers.size(); i++) {
	        if (orderedNumbers.get(i) == lastNumber.getValue()) {
	            return i;
	        }
	    }
	    return -1; // Value not found
	}
	
}

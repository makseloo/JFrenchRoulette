package it.unibs.pajc.server;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import it.unibs.pajc.Colors;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.server.ServerTimer.TimerListener;

public class ServerStatistics {
    private static double NUMBER_OF_RANDOMS = 5;
    private static final int RANDOM_RANGE_MIN = 0;
    private static final int RANDOM_RANGE_MAX = 37;
    
    private int zero;
    private int[] dozens;
    private Queue<Integer> randomNumbers;
    private HashMap<String, Integer> stats;
    
    
    public ServerStatistics() {
    	this.stats = new HashMap<>();
    	this.randomNumbers = new LinkedList<>();
    	randomNumbers = generateRandomNumbers(NUMBER_OF_RANDOMS, RANDOM_RANGE_MIN, RANDOM_RANGE_MAX);
    	stats = generateStats();
    }


	private HashMap<String, Integer> generateStats() {
		double zero = countOccurrences(randomNumbers, 0);
    	int zeroPercentage = calculatePercentage(zero);
    	stats.put("zeros", zeroPercentage);
    	
    	double occInDoz1 = countOccurrencesInZone(randomNumbers, WheelNumber.getDozen1());
    	double occInDoz2 = countOccurrencesInZone(randomNumbers, WheelNumber.getDozen2());
    	double occInDoz3 = countOccurrencesInZone(randomNumbers, WheelNumber.getDozen3());
    	int doz1P = calculatePercentage(occInDoz1);
    	int doz2P = calculatePercentage(occInDoz2);
    	int doz3P = calculatePercentage(occInDoz3);
    	stats.put("1 DOZEN", doz1P);
    	stats.put("2 DOZEN", doz2P);
    	stats.put("3 DOZEN", doz3P);
    	
    	
    	double occInCol1 = countOccurrencesInZone(randomNumbers, WheelNumber.getCol1());
    	double occInCol2 = countOccurrencesInZone(randomNumbers, WheelNumber.getCol2());
    	double occInCol3 = countOccurrencesInZone(randomNumbers, WheelNumber.getCol3());
    	int col1P = calculatePercentage(occInCol1);
    	int col2P = calculatePercentage(occInCol2);
    	int col3P = calculatePercentage(occInCol3);
    	stats.put("1 COLUMN", col1P);
    	stats.put("2 COLUMN", col2P);
    	stats.put("3 COLUMN", col3P);
    	
    	double occInTier = countOccurrencesInZone(randomNumbers, WheelNumber.getTier());
    	double occInOrph = countOccurrencesInZone(randomNumbers, WheelNumber.getOrphelins());
    	double occInVois = countOccurrencesInZone(randomNumbers, WheelNumber.getVoisins());
    	double occInZeroZone = countOccurrencesInZone(randomNumbers, WheelNumber.getZero());
    	int tierP = calculatePercentage(occInTier);
    	int orphP = calculatePercentage(occInOrph);
    	int voisP = calculatePercentage(occInVois);
    	int zeroZoneP = calculatePercentage(occInZeroZone);
    	stats.put("TIER", tierP);
    	stats.put("ORPHELINS", orphP);
    	stats.put("VOISINS", voisP);
    	stats.put("ZERO", zeroZoneP);

    	double occIn1To18 = countOccurrencesInZone(randomNumbers, WheelNumber.getEight());
    	double occIn19To36 = countOccurrencesInZone(randomNumbers, WheelNumber.getSix());
    	int oneTo18P = calculatePercentage(occIn1To18);
    	int ninethTo36P = calculatePercentage(occIn19To36);
    	stats.put("1-18", oneTo18P);
    	stats.put("19-36", ninethTo36P);
    	
    
    	double occInEven = countOccurrencesInZone(randomNumbers, WheelNumber.getEven());
    	double occInOdd = countOccurrencesInZone(randomNumbers, WheelNumber.getOdd());
    	int evenP = calculatePercentage(occInEven);
    	int oddP = calculatePercentage(occInOdd);
    	stats.put("EVEN", evenP);
    	stats.put("ODD", oddP);
    	
    	double occInBlack = countOccurrencesInZone(randomNumbers, WheelNumber.getBlackNums());
    	double occInRed = countOccurrencesInZone(randomNumbers, WheelNumber.getRedNums());
    	int blackP = calculatePercentage(occInBlack);
    	int redP = calculatePercentage(occInRed);
    	stats.put("BLACK", blackP);
    	stats.put("RED", redP);
    	
		return stats;
	}

	private static Queue<Integer> generateRandomNumbers(double count, int min, int max) {
		Queue<Integer> randomNumbers = new LinkedList<>();

        Random random = new Random();

        for (int i = 0; i < count; i++) {
        	int randNum = random.nextInt(max - min + 1) + min;
        	randomNumbers.add(randNum);
        }
        
        return randomNumbers;
    }
	
	private static List<WheelNumber> turnIntoWheelNumbers(List<Integer> randomNumbers) {
		List<WheelNumber> wheelNumbers = new ArrayList<>();

       for(int i : randomNumbers) {

       }
        
        return wheelNumbers;
    }
    
    private static int countOccurrences(Queue<Integer> numbers, int target) {
    	
    	int count = 0;
        for (int num : numbers) {
            if (num == target) {
                count++;
            }
        }
        return count;
    }
    
    private static int calculatePercentage(double occurence) {
    	double res = (occurence/NUMBER_OF_RANDOMS) * 100;
        return (int)res;
    }
    //dozzine
    private static int countOccurrencesInZone(Queue<Integer> numbers, List<Integer> targetZone) {
        int count = 0;

        for (int num : numbers) {
            for (int target : targetZone) {
                if (num == target) {
                    count++;
                    break; // Optimization: stop searching if the number is found
                }
            }
        }
        return count;
    }
    
 
    
    //per più tardi quando c'è da giocare

    public void generateSingleNumber() {
    	int min = RANDOM_RANGE_MIN;
    	int max = RANDOM_RANGE_MAX;
    	Random random = new Random();
    	randomNumbers.poll();
    	randomNumbers.add(random.nextInt(max - min + 1) + min);
    	this.stats = generateStats();
    	
    }
    
    public int getLastNumber() {
    	return 3;
    	//return randomNumbers.peek();
    }
    
    public WheelNumber getLastWheelNumber() {
    	
    	//return randomNumbers.peek();
    	return new WheelNumber(3, "doz1", Colors.getRed());
    	
    }

	public HashMap<String, Integer> getStats(){
    	return this.stats;
    }
    
	public int getZero() {
		return zero;
	}


	
	public Queue<Integer> getNumbers(){
		return randomNumbers;
	}

	public void setZero(int zero) {
		this.zero = zero;
	}

	public int[] getDozens() {
		return dozens;
	}

	public void setDozens(int[] dozens) {
		this.dozens = dozens;
	}

}


package it.unibs.pajc.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import it.unibs.pajc.Colors;
import it.unibs.pajc.WheelNumber;

public class ServerStatistics {
    private static double NUMBER_OF_RANDOMS = 500;
    private static final int RANDOM_RANGE_MIN = 0;
    private static final int RANDOM_RANGE_MAX = 37;
    
    private int zero;
    private int[] dozens;
    List<Integer> randomNumbers;
    private HashMap<String, Integer> stats;
    
    
    public ServerStatistics() {
    	this.randomNumbers = generateRandomNumbers(NUMBER_OF_RANDOMS, RANDOM_RANGE_MIN, RANDOM_RANGE_MAX);
	}

    public static void main(String[] args) {
    	ServerStatistics server = new ServerStatistics();
    	List<Integer> randomNumbers = server.getNumbers();
    	
    	double zero = countOccurrences(randomNumbers, 0);
    	int zeroPercentage = calculatePercentage(zero);
    	
    	double occInDoz1 = countOccurrencesInZone(randomNumbers, WheelNumber.getDozen1());
    	double occInDoz2 = countOccurrencesInZone(randomNumbers, WheelNumber.getDozen2());
    	double occInDoz3 = countOccurrencesInZone(randomNumbers, WheelNumber.getDozen3());
    	int doz1P = calculatePercentage(occInDoz1);
    	int doz2P = calculatePercentage(occInDoz2);
    	int doz3P = calculatePercentage(occInDoz3);
    	
    	
    	double occInCol1 = countOccurrencesInZone(randomNumbers, WheelNumber.getCol1());
    	double occInCol2 = countOccurrencesInZone(randomNumbers, WheelNumber.getCol2());
    	double occInCol3 = countOccurrencesInZone(randomNumbers, WheelNumber.getCol3());
    	int col1P = calculatePercentage(occInCol1);
    	int col2P = calculatePercentage(occInCol2);
    	int col3P = calculatePercentage(occInCol3);
    	
    	double occInTier = countOccurrencesInZone(randomNumbers, WheelNumber.getTier());
    	double occInOrph = countOccurrencesInZone(randomNumbers, WheelNumber.getOrphelins());
    	double occInVois = countOccurrencesInZone(randomNumbers, WheelNumber.getVoisins());
    	double occInZeroZone = countOccurrencesInZone(randomNumbers, WheelNumber.getZero());
    	int tierP = calculatePercentage(occInTier);
    	int orphP = calculatePercentage(occInOrph);
    	int voisP = calculatePercentage(occInVois);
    	int zeroZoneP = calculatePercentage(occInZeroZone);
    	
    	
    	
    	double occIn1To18 = countOccurrencesInZone(randomNumbers, WheelNumber.getEight());
    	double occIn19To36 = countOccurrencesInZone(randomNumbers, WheelNumber.getSix());
    	int oneTo18P = calculatePercentage(occIn1To18);
    	int ninethTo36P = calculatePercentage(occIn19To36);
    
    	double occInEven = countOccurrencesInZone(randomNumbers, WheelNumber.getEven());
    	double occInOdd = countOccurrencesInZone(randomNumbers, WheelNumber.getOdd());
    	int evenP = calculatePercentage(occInEven);
    	int oddP = calculatePercentage(occInOdd);
    	
    	double occInBlack = countOccurrencesInZone(randomNumbers, WheelNumber.getBlackNums());
    	double occInRed = countOccurrencesInZone(randomNumbers, WheelNumber.getRedNums());
    	int blackP = calculatePercentage(occInBlack);
    	int redP = calculatePercentage(occInRed);

    	
    	for(int i : randomNumbers) {
    		System.out.print(","+ i);
    	}
    	
    	System.out.print("\n occurence of first dozen: "+ occInDoz1);
    	System.out.println("\n" + calculatePercentage(occInDoz1));
    }


	private static List<Integer> generateRandomNumbers(double count, int min, int max) {
		List<Integer> randomNumbers = new ArrayList<>();

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
    
    private static int countOccurrences(List<Integer> numbers, int target) {
    	
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
    private static int countOccurrencesInZone(List<Integer> numbers, List<Integer> targetZone) {
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

    private int generateSingleNumber(int min, int max) {
    	Random random = new Random();
    	return random.nextInt(max - min + 1) + min;
    }

    public HashMap<String, Integer> getStats(){
    	return this.stats;
    }
    
	public int getZero() {
		return zero;
	}


	
	public List<Integer> getNumbers(){
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


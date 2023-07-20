package it.unibs.pajc.server;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import it.unibs.pajc.WheelNumber;

public class ServerStatistics {
    private static double NUMBER_OF_RANDOMS = 20;
    private static final int RANDOM_RANGE_MIN = 0;
    private static final int RANDOM_RANGE_MAX = 37;
    
    private int zero;
    private int[] dozens;
    int[] randomNumbers;
    private HashMap<String, Integer> stats;
    
    
    public ServerStatistics() {
    	this.randomNumbers = generateRandomNumbers(NUMBER_OF_RANDOMS, RANDOM_RANGE_MIN, RANDOM_RANGE_MAX);
    	HashMap<String, Integer> stats = new HashMap<>();
		stats.put("1dozen", 10);
		stats.put("2dozen", 20);
		stats.put("3dozen", 30);
	}

    public static void main(String[] args) {
    	ServerStatistics server = new ServerStatistics();
    	int[] randomNumbers = server.getNumbers();
    	
    	double zero = countOccurrences(randomNumbers, 0);
    	
    	double occInDoz1 = countOccurrencesInZone(randomNumbers, WheelNumber.getDozen1());
    	double occInDoz2 = countOccurrencesInZone(randomNumbers, WheelNumber.getDozen2());
    	double occInDoz3 = countOccurrencesInZone(randomNumbers, WheelNumber.getDozen3());
    	
    	double occInCol1 = countOccurrencesInZone(randomNumbers, WheelNumber.getCol1());
    	double occInCol2 = countOccurrencesInZone(randomNumbers, WheelNumber.getCol2());
    	double occInCol3 = countOccurrencesInZone(randomNumbers, WheelNumber.getCol3());
    	
    	double occInTier = countOccurrencesInZone(randomNumbers, WheelNumber.getTier());
    	double occInOrph = countOccurrencesInZone(randomNumbers, WheelNumber.getOrphelins());
    	double occInVois = countOccurrencesInZone(randomNumbers, WheelNumber.getVoisins());
    	double occInZero = countOccurrencesInZone(randomNumbers, WheelNumber.getZero());
    	
    	double occIn1To18 = countOccurrencesInZone(randomNumbers, WheelNumber.getEight());
    	double occIn19To36 = countOccurrencesInZone(randomNumbers, WheelNumber.getSix());
    
    	double occInEven = countOccurrencesInZone(randomNumbers, WheelNumber.getEven());
    	double occInOdd = countOccurrencesInZone(randomNumbers, WheelNumber.getOdd());
    	
    	double occInBlack = countOccurrencesInZone(randomNumbers, WheelNumber.getBlackNums());
    	double occInRed = countOccurrencesInZone(randomNumbers, WheelNumber.getRedNums());
    	

    	
    	for(int i : randomNumbers) {
    		System.out.print(","+ i);
    	}
    	System.out.print("\n occurence of 0: "+ countOccurrences(randomNumbers, 0));
    	System.out.print("\n occurence of first dozen: "+ occInDoz1);
    	System.out.println("\n" + calculatePercentage(occInDoz1));
    }


	private static List generateRandomNumbers(double count, int min, int max) {
        int[] randomNumbers = new int[(int) count];
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            randomNumbers[i] = random.nextInt(max - min + 1) + min;
        }
        
        return randomNumbers;
    }
    
    private static int countOccurrences(int[] numbers, int target) {
    	
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
    private static int countOccurrencesInZone(int[] numbers, List<Integer> targetZone) {
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

	public int[] getNumbers() {
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


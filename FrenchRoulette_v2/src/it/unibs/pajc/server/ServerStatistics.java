package it.unibs.pajc.server;

import java.util.List;
import java.util.Random;

public class ServerStatistics {
    private static final int NUMBER_OF_RANDOMS = 500;
    private static final int RANDOM_RANGE_MIN = 0;
    private static final int RANDOM_RANGE_MAX = 37;
    
    private int zero;

    public static void main(String[] args) {
        int[] randomNumbers = generateRandomNumbers(NUMBER_OF_RANDOMS, RANDOM_RANGE_MIN, RANDOM_RANGE_MAX);
        
        System.out.println("Generated " + NUMBER_OF_RANDOMS + " random numbers between " + RANDOM_RANGE_MIN + " and " + RANDOM_RANGE_MAX + ":");
        for (int num : randomNumbers) {
            System.out.print(num + " ");
        }
        System.out.println();

    }

    private static int[] generateRandomNumbers(int count, int min, int max) {
        int[] randomNumbers = new int[count];
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            randomNumbers[i] = random.nextInt(max - min + 1) + min;
        }

        return randomNumbers;
    }
    
    //statistiche da fare, calcola lo zero, e poi te ne bastano 1 o due e basta che sottrai
    /*
    *0
    *dozzine
    *colonne
    *zone
    *1-18 e 19-36
    *pari dispari
    *rosso nero
    */
    
    private int countOccurrences(int[] numbers, int target) {
    	
    	int count = 0;
        for (int num : numbers) {
            if (num == target) {
                count++;
            }
        }
        return count;
    }
    
    
    private static int countOccurrencesInDozen(int[] numbers, List<Integer> targetDozen) {
        int count = 0;

        for (int num : numbers) {
            for (int target : targetDozen) {
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


}


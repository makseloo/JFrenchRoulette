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

    
    public ServerStatistics() {
    	this.randomNumbers = new LinkedList<>();
    	randomNumbers = generateRandomNumbers(NUMBER_OF_RANDOMS, RANDOM_RANGE_MIN, RANDOM_RANGE_MAX);
    }


	private Queue<Integer> generateRandomNumbers(double count, int min, int max) {
		Queue<Integer> randomNumbers = new LinkedList<>();

        Random random = new Random();

        for (int i = 0; i < count; i++) {
        	int randNum = random.nextInt(max - min + 1) + min;
        	randomNumbers.add(randNum);
        }
        
        return randomNumbers;
    }
	
	
	
	
    public void generateSingleNumber() {
    	int min = RANDOM_RANGE_MIN;
    	int max = RANDOM_RANGE_MAX;
    	Random random = new Random();
    	randomNumbers.poll();
    	randomNumbers.add(random.nextInt(max - min + 1) + min);
    	
    }
    
    public int getLastNumber() {
    	return randomNumbers.peek();
    	
    }
    
    public WheelNumber getLastWheelNumber() {
    	WheelNumber lastWheelNum = turnIntoWheelNumb(randomNumbers.peek());
    	List<String> zones = new ArrayList<>();
    	zones.add("1°:12");
    	zones.add("1° row");
    	zones.add("ODD");
    	zones.add("1-18");
    	zones.add("RED");
    	return new WheelNumber(3, zones, Colors.getRed());
    	
    }

    
	private WheelNumber turnIntoWheelNumb(int peek) {
		
		return null;
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


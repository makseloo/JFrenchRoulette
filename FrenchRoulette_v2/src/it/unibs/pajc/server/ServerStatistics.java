package it.unibs.pajc.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import it.unibs.pajc.WheelNumber;


public class ServerStatistics {
    private static double NUMBER_OF_RANDOMS = 5;
    private static final int RANDOM_RANGE_MIN = 0;
    private static final int RANDOM_RANGE_MAX = 37;
    
    private int zero;
    private int[] dozens;
    private Queue<WheelNumber> randomWheelNumbers;
    private List<WheelNumber> wheelNumbers;

    
    public ServerStatistics() {
    	this.randomWheelNumbers = new LinkedList<>();
    	Numbers numbers = new Numbers();//si potrebbe fare static e crearli da un'altra parte ma andrebbe fatto a manO?
    	this.wheelNumbers = numbers.getNumbers();
    	this.randomWheelNumbers = generateRandomNumbers(NUMBER_OF_RANDOMS, RANDOM_RANGE_MIN, RANDOM_RANGE_MAX);
    }


	private Queue<WheelNumber> generateRandomNumbers(double count, int min, int max) {
		Queue<WheelNumber> randomNumbers = new LinkedList<>();

        Random random = new Random();

        for (int i = 0; i < count; i++) {
        	int randNum = random.nextInt(max - min + 1) + min;
        	randomNumbers.add(turnIntoWheelNumb(randNum));
        }
        
        return randomNumbers;
    }
	
    public void generateSingleNumber() {
    	int min = RANDOM_RANGE_MIN;
    	int max = RANDOM_RANGE_MAX;
    	Random random = new Random();
    	int ran = random.nextInt(max - min + 1) + min;
    	randomWheelNumbers.poll();
    	randomWheelNumbers.add(turnIntoWheelNumb(ran));
    	
    }
   
    
    public WheelNumber getLastWheelNumber() {
    	return randomWheelNumbers.peek();
    }

    
	private WheelNumber turnIntoWheelNumb(int peek) {
		for(WheelNumber w : wheelNumbers) {
			if(w.getValue() == peek)
				return w;
		}
		return null;
	}


	public int getZero() {
		return zero;
	}

	public Queue<WheelNumber> getNumbers(){
		return randomWheelNumbers;
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


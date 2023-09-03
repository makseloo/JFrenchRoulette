package it.unibs.pajc.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import it.unibs.pajc.WheelNumber;


public class ServerStatistics {
    private static double NUMBER_OF_RANDOMS = 13;
    private static final int RANDOM_RANGE_MAX = 37;
    
    private int zero;
    private int[] dozens;
    private List<WheelNumber> randomWheelNumbers;
    private List<WheelNumber> wheelNumbers;

    
    public ServerStatistics() {
    	this.randomWheelNumbers = new LinkedList<>();
    	Numbers numbers = new Numbers();//si potrebbe fare static e crearli da un'altra parte ma andrebbe fatto a manO?
    	this.wheelNumbers = numbers.getSortedNumbers();
    	this.randomWheelNumbers = generateRandomNumbers(NUMBER_OF_RANDOMS, RANDOM_RANGE_MAX);
    }


	private List<WheelNumber> generateRandomNumbers(double count,int max) {
		List<WheelNumber> randomNumbers = new LinkedList<>();

        Random random = new Random();

        for (int i = 0; i < count; i++) {
        	int randNum = random.nextInt(37);
        	randomNumbers.add(turnIntoWheelNumb(randNum));
        }
		
        return randomNumbers;
    }
	
    public void generateSingleNumber() {
    	Random random = new Random();
    	int ran = random.nextInt(RANDOM_RANGE_MAX);
    	randomWheelNumbers.remove(0);
    	randomWheelNumbers.add(turnIntoWheelNumb(ran));
    	System.out.print("(SERVER STATISTIC)Num generated: " + ran +"\n");
    	
    }
   
    
    public WheelNumber getLastWheelNumber() {
    	int lastIndex = randomWheelNumbers.size() - 1;
    	return randomWheelNumbers.get(lastIndex);
    }

    
	private WheelNumber turnIntoWheelNumb(int randNum) {
		for(WheelNumber w : wheelNumbers) {
			if(w.getValue() == randNum)
				return w;
		}
		return null;
	}


	public int getZero() {
		return zero;
	}

	public List<WheelNumber> getNumbers(){
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


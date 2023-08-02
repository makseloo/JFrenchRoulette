package it.unibs.pajc.server;

import java.awt.EventQueue;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import it.unibs.pajc.FrenchRoulette_v2;
import it.unibs.pajc.WheelNumber;


public class Client {

	private static FrenchRoulette_v2 roulette;
	private static int seconds;
	private static String gameState;
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;	
	public static void main(String[] args) throws InterruptedException {
		
		
		roulette = new FrenchRoulette_v2();
		roulette.frame.setVisible(true);

		
		String hostName = "localhost";
		int port = 1234;
		
		
		try {
			Socket server = new Socket(hostName, port);
			ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            
			Object receivedObject;
	            
			
	            while ((receivedObject = ois.readObject()) != null) {
	                 // Check the type of the received object and handle it accordingly
	            	if (receivedObject instanceof TimerMessage) {
	                    TimerMessage timerMessage = (TimerMessage) receivedObject;
	                    seconds = timerMessage.getSeconds();
	 	                gameState = timerMessage.getGameState();
	 	                
	 	                roulette.updateTimer(seconds);
	 	                roulette.updateGameState(gameState);
	 	                
	 	                if(gameState.equals("SPINNING")) {
	 	                	List<WheelNumber> bets = roulette.getBets();
	 	                	BetsMessage betsMessage = new BetsMessage(bets);
	 	                	oos.writeObject(betsMessage);
	 	                    oos.flush();
	 	                }
	 	                
	                 } else if (receivedObject instanceof StatsMessage) {
	                     StatsMessage statsMessage = (StatsMessage) receivedObject;
	                     roulette.updateLast500(statsMessage.getNumbers());
	                     roulette.updateStats(statsMessage.getStats());
	                     
	                     
	                 } /*else if(receivedObject instanceof String){
	                	 System.out.println("Client 67 : Received String: " + receivedObject.toString());
	                 }
	                 */
	             }
	            System.out.print("fuori dal while");
	            	
			} catch (IOException | ClassNotFoundException exc) {
			    exc.printStackTrace();
			}
		
	}

	
}
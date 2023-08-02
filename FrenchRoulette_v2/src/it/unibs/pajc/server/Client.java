package it.unibs.pajc.server;

import java.awt.EventQueue;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import it.unibs.pajc.FrenchRoulette_v2;
import it.unibs.pajc.WheelNumber;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Client {

	private static FrenchRoulette_v2 roulette;
	private static int seconds;
	private static String gameState;
	private static SetupView setupView;
	
	public static void main(String[] args) throws InterruptedException {
	
		
		setupView = new SetupView();
		setupView.frame.setVisible(true);
		roulette = new FrenchRoulette_v2();
		
		String hostName = "localhost";
		int port = 1234;
		
		
		try {
			Socket server = new Socket(hostName, port);
			ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            
            
    		setupView.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                	roulette.frame.setVisible(true);
                	ClientInfoMessage clientInfoMsg = new ClientInfoMessage(setupView.getName(), setupView.getBalance());
        	    	try {
						oos.writeObject(clientInfoMsg);
						oos.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        	        
                }
            });
            
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
	            	
			} catch (IOException | ClassNotFoundException exc) {
				System.out.print("nessun server trovato");
			    //exc.printStackTrace();
			    
			}
		
		
	}

	
}
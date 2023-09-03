package it.unibs.pajc.server;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.List;

import it.unibs.pajc.FrenchRoulette_v2;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;

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
		boolean betsSent = false;
		
		try {
			Socket server = new Socket(hostName, port);
			ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            
            
    		setupView.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                	//aggiungi un controllo nel caso si chiuda la finestra senza fare nulla
                	
                	roulette.setBalance(setupView.getBalance());
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
	 	                
	 	               if (gameState.equals("BETTING")) {
	                        betsSent = false; // Reset the flag to false at the start of each cycle
	                    }
	 	                if(gameState.equals("SPINNING") && !betsSent) {
	 	                	List<WheelNumber> bets = roulette.getBets();
	 	                	
	 	                	List<Zone> zoneBets = roulette.getZoneBets();
	 	                	
	 	                	BetsMessage betsMessage = new BetsMessage(bets, roulette.getTotalBet(),zoneBets);
	 	                	
	 	                	oos.writeObject(betsMessage);
	 	                    oos.flush();
	 	                    betsSent = true;
	 	                }
	                 }else if(receivedObject instanceof PayoutMessage){
	                	 PayoutMessage payoutMessage = (PayoutMessage) receivedObject;
	                	 roulette.popup(payoutMessage.getLastWin());
	                	 roulette.setBalance(payoutMessage.getNewBalance());
	                	 
	                 }else if(receivedObject instanceof StatsMessage) {
	                	 StatsMessage statsMessage = (StatsMessage) receivedObject;
	                	 roulette.updateStats(statsMessage.getNumbers());
	                 }
	                 
	             }
	            	
			} catch (IOException | ClassNotFoundException exc) {
				System.out.print("nessun server trovato");
			    //exc.printStackTrace();
			    
			}
		
		
	}
}
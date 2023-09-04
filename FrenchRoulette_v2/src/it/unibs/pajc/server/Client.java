package it.unibs.pajc.server;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.List;

import it.unibs.pajc.FrenchRoulette_v2;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
import it.unibs.pajc.client.messages.BetsMessage;
import it.unibs.pajc.client.messages.ClientInfoMessage;
import it.unibs.pajc.server.messages.GameStateMessage;
import it.unibs.pajc.server.messages.PayoutMessage;
import it.unibs.pajc.server.messages.StatsMessage;
import it.unibs.pajc.server.messages.TimerMessage;

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
	            	if (receivedObject instanceof TimerMessage) {
	                    TimerMessage timerMessage = (TimerMessage) receivedObject;
	                    seconds = timerMessage.getSeconds();
	 	                roulette.updateTimer(seconds);
	                 }else if(receivedObject instanceof PayoutMessage){
	                	 PayoutMessage payoutMessage = (PayoutMessage) receivedObject;
	                	 roulette.updateLastWinLbl(payoutMessage.getLastWin());
	                	 roulette.setBalance(payoutMessage.getNewBalance());
	                 }else if(receivedObject instanceof StatsMessage) {
	                	 StatsMessage statsMessage = (StatsMessage) receivedObject;
	                	 roulette.updateStats(statsMessage.getNumbers());
	                 }else if(receivedObject instanceof GameStateMessage) {
	                	 GameStateMessage gameStateMessage = (GameStateMessage) receivedObject;
	                	 gameState = gameStateMessage.getGameState();
	                	 roulette.updateGameState(gameState); 
	 	                if(gameState.equals("SPINNING")) {
	 	                	List<WheelNumber> bets = roulette.getBets();
	 	                	
	 	                	List<Zone> zoneBets = roulette.getZoneBets();
	 	                	
	 	                	BetsMessage betsMessage = new BetsMessage(bets, roulette.getTotalBet(),zoneBets);
	 	                	
	 	                	oos.writeObject(betsMessage);
	 	                    oos.flush();
	 	                }
	                 }
	                 
	             }
	            	
			} catch (IOException | ClassNotFoundException exc) {
				System.out.print("nessun server trovato");
			    exc.printStackTrace();
			    
			}
		
		
	}
}
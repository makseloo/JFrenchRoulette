package it.unibs.client;

import java.awt.EventQueue;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;

import it.unibs.pajc.FrenchRoulette_v2;
import it.unibs.pajc.server.Message;


public class Client {

	private static FrenchRoulette_v2 roulette;
	 private static int seconds;
	    private static String gameState;
	
	public static void main(String[] args) throws InterruptedException {
		
		roulette = new FrenchRoulette_v2();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					roulette.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		String serverName = "localhost";
		int port = 1234;
		
		
		try (Socket server = new Socket(serverName, port);
				ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
	             ObjectInputStream ois = new ObjectInputStream(server.getInputStream())
			) {
			   
			 Message message;
	            
	            while ((message = (Message) ois.readObject()) != null) {
	                // Update GUI with the received time and game state
	                seconds = message.getSeconds();
	                gameState = message.getGameState();
	                roulette.updateTimer(seconds);
	                roulette.updateGameState(gameState);
	            }
			        

			        // Process the response based on your application logic
			    
			} catch (IOException | ClassNotFoundException exc) {
			    exc.printStackTrace();
			}

		
		

	}

}
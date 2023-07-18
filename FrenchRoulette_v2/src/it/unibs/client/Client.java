package it.unibs.client;

import java.awt.EventQueue;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.List;

import it.unibs.pajc.FrenchRoulette_v2;


public class Client {

	private static FrenchRoulette_v2 roulette;
	
	
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
			    PrintWriter out = new PrintWriter(server.getOutputStream(), true);
			    BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			) {
			   
			    String response;
			    while (true) {
			    	out.println("GET_TIME");
			    	if((response = in.readLine()) != null) {
			    		String[] parts = response.split(":");
			    		int remainingSeconds = Integer.parseInt(parts[0]);
		                String gameState = parts[1];
		                roulette.updateTimer(remainingSeconds);
		                roulette.updateGameState(gameState);

				        // Wait for the next response from the server
				        response = in.readLine();
				        System.out.println(response);
			    	}
			        

			        // Process the response based on your application logic
			    }
			} catch (IOException exc) {
			    exc.printStackTrace();
			}

		
		

	}

}
package it.unibs.pajc;

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
		
		
		try(
				Socket server = new Socket(serverName, port);
				PrintWriter out = new PrintWriter(server.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
				
		) {
			out.println("GET_TIME");
			String response;
			while((response= in.readLine())!= null) {
				int remainingSeconds = Integer.parseInt(response);
				roulette.startTimer(remainingSeconds);
				String request = in.readLine();
				System.out.println("ciaoo");
				
			}
			 
			
			String betString = roulette.getBets1();
			/*
			while(betString != null) {
				response = in.readLine();
				System.out.println(response);
				
				if (response != null && response.equals("Timer expired")) {
                    // Get the bets from the client
					System.out.println("ciaoo");
					/*
                    betString = roulette.getBets1();
                    System.out.println(betString);
                    out.println(betString);
                    //
                }

                // Sleep for a short duration to allow for server processing time
                Thread.sleep(100);
			}
			*/
			
			//System.out.println("richiesta inviata");
			/*
			while((request = consoleIn.readLine()) != null) {
				System.out.printf("\nRichesta: %s\n", request);
				out.println(request);
				String response = in.readLine();
				System.out.println(response);
				
				if("quit".equals(request))
					break;
				
				// Sleep for the specified interval
                
			}
			*/
			
			
		} catch(IOException exc) {
			exc.printStackTrace();
		}
		
		

	}

}
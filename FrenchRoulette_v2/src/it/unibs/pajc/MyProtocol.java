package it.unibs.pajc;

import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class MyProtocol implements Runnable {

	private Socket clientSocket;
	private String clientName;
	private boolean isTimerRunning;
    private boolean isTimerExpired;
    PrintWriter out;
    private Timer timer;
    private int remainingSeconds;
	
	public MyProtocol(Socket clientSocket, String clientName) {
		this.clientSocket = clientSocket;
		this.clientName = clientName;
		this.isTimerRunning = false;
        this.isTimerExpired = false;
        this.timer = new Timer();
	}
	
	public void run() {
		try(
				
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		) {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			System.out.printf("\nClient connesso: %s [%d] - Name: %s\n", 
					clientSocket.getInetAddress(), clientSocket.getPort(), clientName);
			
			String request = in.readLine();
			if (request.equals("GET_TIME")) {
			    int seconds = getRemainingSeconds();
			    out.println(seconds);
			}
			
			while(true) {
				
			}
			
			/*
		  while(true) {
			  scheduleTimer();
	            
				String betString;
				if(isTimerExpired) {
					betString = in.readLine();
						if (betString != null) {
						    System.out.printf("\nRichiesta ricevuta: %s [%s]", betString, clientName);
						    String[] betTokens = betString.split(",");
						    String response = "";
						    for (int i = 0; i < betTokens.length; i += 2) {
						        if (i + 1 < betTokens.length) {
						            int bettedValue = Integer.parseInt(betTokens[i].trim());
						            int value = Integer.parseInt(betTokens[i + 1].trim());
						            response += "Hai scommesso: " + bettedValue + " sul numero " + value + "\n";
						        }
						    }
						    out.println(response);
						}
						restartTimer();
	                    isTimerExpired = false;

				} 
		  }
			*/

		} catch(IOException ex) {
			
		}
		
		System.out.printf("\nSessione terminata, client: %s\n", clientName);
		
	}
	
    private void scheduleTimer() {
    	remainingSeconds = 10;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer expired. Sending response to client...");
                out.println("Timer expired!");

                isTimerRunning = false;
                isTimerExpired = true;
            }
        };

        timer.schedule(timerTask, 5000);
        isTimerRunning = true;
    }
    
	private void restartTimer() {
        // Cancel the previous timer
        timer.cancel();

        // Schedule a new timer
        timer = new Timer();
        scheduleTimer();
    }
	
	private int getRemainingSeconds() {
		return remainingSeconds;
	}

		
}
/*
*/
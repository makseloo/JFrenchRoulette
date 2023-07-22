package it.unibs.pajc.server;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.unibs.pajc.core.BaseModel.TimerExpiredEvent;

public class MyProtocol implements Runnable {

    private Socket clientSocket;
    private String clientName;
    private ServerModel serverModel;
    private PrintWriter out;
    private ObjectOutputStream oos;

    public MyProtocol(Socket clientSocket, String clientName, ServerModel serverModel) {
        this.clientSocket = clientSocket;
        this.clientName = clientName;
        this.serverModel = serverModel;
        serverModel.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(e instanceof TimerExpiredEvent) {
					test(e);
				}else {
					
				}
				
			}
		});
    }

    private void test(ChangeEvent e) {
    	if(e.getSource() == "SPINNING") {
    		
    	}if(e.getSource() == "SETTLING") {
    		
    	}
	}

	public void run() {
        try (
        		ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        ) {
        	 oos = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.printf("\nClient connesso: %s [%d] - Name: %s\n",
                    clientSocket.getInetAddress(), clientSocket.getPort(), clientName);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    sendTimeUpdate();
                }
            }, 0, 100); // Send time update every second

            while (true) {
                // Read any incoming requests from the client if necessary
                // ...

                // Wait for the next iteration
                //Thread.sleep(10);
            }
        } catch (IOException  ex) {
            ex.printStackTrace();
        }

        System.out.printf("\nSessione terminata, client: %s\n", clientName);
    }

    private void sendTimeUpdate() {
        int seconds = serverModel.getSeconds();
        List<Integer> numbers = serverModel.getNumbers();
        RouletteGameState gameState = serverModel.getPrevGameState();
        try {
            TimerMessage timerMessage = new TimerMessage(gameState.toString(), seconds);
            StatsMessage statsMessage = new StatsMessage(numbers);
            
            oos.writeObject(timerMessage);
            oos.writeObject(statsMessage);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

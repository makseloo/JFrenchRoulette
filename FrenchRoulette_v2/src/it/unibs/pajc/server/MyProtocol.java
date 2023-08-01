package it.unibs.pajc.server;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.core.BaseModel.GeneratedNumberEvent;
import it.unibs.pajc.core.BaseModel.UpdateBet;


public class MyProtocol implements Runnable {

    private Socket clientSocket;
    private ClientInfo clientInfo;
    private ServerModel serverModel;
    private boolean isConnected;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public MyProtocol(Socket clientSocket, ClientInfo clientInfo, ServerModel serverModel) {
        this.clientSocket = clientSocket;
        this.clientInfo = clientInfo;
        this.serverModel = serverModel;
        isConnected = true;
        
        serverModel.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(e instanceof GeneratedNumberEvent) {
					sendStats();
				}else if(e instanceof UpdateBet) {
					
				}
			}

		});
       
    }

    
	public void run() {
        try {
        	ois = new ObjectInputStream(clientSocket.getInputStream());
        	oos = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.printf("\nClient connesso: %s [%d] - Name: %s\n",
                    clientSocket.getInetAddress(), clientSocket.getPort(), clientInfo.getClientName());
            //mando le stat appena il client si connette
            sendStats();
            
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    sendTimeUpdate();
                }
            }, 0, 100); // Send time update every second
            
            //handel reception
            
            Object receivedObject;
            while ((receivedObject = ois.readObject()) != null) {
                // Read any incoming requests from the client if necessary
                // ...
            	if (receivedObject instanceof BetsMessage) {
            		BetsMessage betsMessage = (BetsMessage) receivedObject;
            		System.out.print("MyProtocol 73: valore ricevuto");
            		for(WheelNumber w : betsMessage.getBets()) {
            			System.out.print(w.getBettedValue());
            		}
            		serverModel.updateBets(betsMessage.getBets(),clientInfo.getClientName());
            	}

                // Wait for the next iteration
                //Thread.sleep(10);
            }
            
        } catch (IOException | ClassNotFoundException ex) {
        	System.out.printf("\nClient disconnected: %s [%d] - Name: %s\n",
                    clientSocket.getInetAddress(), clientSocket.getPort(), clientInfo.getClientName());

            // Clean up resources and remove client from server data structures
            try {
            	serverModel.removeClient(clientInfo);
                clientSocket.close();
                serverModel.removeClient(null);
                isConnected = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("\nSessione terminata, client: %s : %b\n", clientInfo.getClientName(),isConnected );
    }

    private void sendTimeUpdate() {
    	if(!isConnected)
    		return;
        int seconds = serverModel.getSeconds();
        
        RouletteGameState gameState = serverModel.getPrevGameState();
        try {
            TimerMessage timerMessage = new TimerMessage(gameState.toString(), seconds);
            oos.writeObject(timerMessage);
            oos.flush();
        }catch (SocketException e) {
            // Handle the SocketException, indicating that the client has closed the connection
            isConnected = false;
            try {
            	serverModel.removeClient(clientInfo);
                clientSocket.close();
                serverModel.removeClient(null);
                isConnected = false;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void sendStats() {
    	if(!isConnected)
    		return;
    	
    	try {
    		StatsMessage statsMessage = new StatsMessage(serverModel.getNumbers(), serverModel.getStats());
    		oos.writeObject(statsMessage);
            oos.flush();	
            
    	}catch (SocketException e) {
    		isConnected = false;
    		try {
            	serverModel.removeClient(clientInfo);
                clientSocket.close();
                serverModel.removeClient(null);
                isConnected = false;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
		} catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    

    
}

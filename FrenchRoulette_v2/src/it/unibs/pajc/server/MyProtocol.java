package it.unibs.pajc.server;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.unibs.pajc.ClientInfo;
import it.unibs.pajc.EventType;
import it.unibs.pajc.RouletteGameState;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.client.messages.BetsMessage;
import it.unibs.pajc.client.messages.ClientInfoMessage;
import it.unibs.pajc.core.CustomChangeEvent;
import it.unibs.pajc.server.messages.GameStateMessage;
import it.unibs.pajc.server.messages.PayoutMessage;
import it.unibs.pajc.server.messages.StatsMessage;
import it.unibs.pajc.server.messages.TimerMessage;


public class MyProtocol implements Runnable {

    private Socket clientSocket;
    private ClientInfo clientInfo;
    private ServerModel serverModel;
    private boolean isConnected;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private List<WheelNumber> stats;
    
    public MyProtocol(Socket clientSocket, ClientInfo clientInfo, ServerModel serverModel) {
        this.clientSocket = clientSocket;
        this.clientInfo = clientInfo;
        this.serverModel = serverModel;
        isConnected = true;
        
        serverModel.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
            	
            	if(e instanceof CustomChangeEvent) {
            		EventType eventType = ((CustomChangeEvent) e).getEventType();
                	
                	switch (eventType) {
                	case BETS_ANALYZED: {
    					sendPayouts();
    					break;
    				}
    				case GENERATED_NUMBER: {
    					sendStats();
    					break;
    				}
    				case UPDATE_GAME_STATE: {
    					sendGameState();
    					break;
    				}
    				case TIMER_TICK: {
    					sendTimeUpdate();
    					break;
    				}
    				default:
    					//myprotocol and Server are both working o the same model which can fire
    					//some event hanlded in one but not in the other one
    					break;
    					//throw new IllegalArgumentException("Unexpected value: " + eventType);
    				}
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
            //sending the stats and the game state when the client connects
            sendStats();
            sendGameState();
                        
            Object receivedObject;
            while ((receivedObject = ois.readObject()) != null) {
                // Read any incoming requests from the client
            	
            	if (receivedObject instanceof BetsMessage) {
            		BetsMessage betsMessage = (BetsMessage) receivedObject;
            		serverModel.updateBets(betsMessage.getBets(),clientInfo.getAccountId(), betsMessage.getTotalAmount(),betsMessage.getZones());
            	}else if(receivedObject instanceof ClientInfoMessage) {
            		ClientInfoMessage clientInfoMsg = (ClientInfoMessage) receivedObject;
            		serverModel.updateClientInfo(clientInfo.getAccountId(), clientInfoMsg.getName(), clientInfoMsg.getBalance());
            	}

            }
            
        } catch (IOException | ClassNotFoundException ex) {
        	System.out.printf("\nClient disconnected: %s [%d] - Name: %s\n",
                    clientSocket.getInetAddress(), clientSocket.getPort(), clientInfo.getClientName());
        	//removing the client from the client list if the client is disconnected
            try {
				clientSocket.close();
				serverModel.removeClient(clientInfo);
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
        TimerMessage timerMessage = new TimerMessage(seconds);
        try {
			sendPayload(timerMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void sendStats() {
    	if(!isConnected)
    		return;
    	this.stats = serverModel.getStats();
    	StatsMessage statsMessage = new StatsMessage(stats);
    	try {
			sendPayload(statsMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
    }
    
    private void sendPayouts(){
    	if(!isConnected)
    		return;
    	double newBalance = serverModel.getNewBalance(clientInfo.getAccountId());
		double lastWin = serverModel.getLastWin(clientInfo.getAccountId());
		PayoutMessage payoutMessage = new PayoutMessage(newBalance, lastWin);
		try {
			sendPayload(payoutMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private void sendGameState() {
		if(!isConnected)
    		return;
		RouletteGameState gameState = serverModel.getPrevGameState();
	    GameStateMessage gameStateMessage = new GameStateMessage( gameState.toString());
	    try {
			sendPayload(gameStateMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
    private void sendPayload(Object payload) throws IOException {
    	try {
    		oos.writeObject(payload);
            oos.flush();
    	}catch (SocketException e) {
    		// Handle the SocketException, indicating that the client has closed the connection
            serverModel.removeClient(clientInfo);
            clientSocket.close();
            isConnected = false;
        } catch (IOException ex) {
           System.out.print("Failed Game State message: ");
           ex.printStackTrace();
        }	
    }
}

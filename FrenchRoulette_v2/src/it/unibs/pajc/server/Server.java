package it.unibs.pajc.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
import it.unibs.pajc.core.CustomChangeEvent;
import it.unibs.pajc.core.EventType;
import it.unibs.pajc.server.panels.PnlCountdown;

import javax.swing.JLabel;

import javax.swing.JTextArea;

public class Server {
    private JFrame frame;
    private JPanel contentPane;

    private static ServerModel serverModel;
    private PnlCountdown pnlCountdown;
    private JLabel stateLbl;
    private JTextArea betTextArea;
    private JTextArea numTextArea;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Server window = new Server();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        serverModel = new ServerModel();
        serverModel.startTimer();
        
        //connectedClients = new ArrayList<>(); not needed anymore, handled in the servermodel
        
        int port = 1234;
        

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for client connection...");

            int id = 0;
            while (true) {
                Socket client = serverSocket.accept();
                //giving an unique name
                String clientName = "CLI#" + id++ + "\n";
                int balance = 0;
               
                handleNewClient(client, clientName, balance, id);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    
	private static void handleNewClient(Socket client, String clientName, int balance, int id) {
		
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setClientName(clientName);
		clientInfo.setAccountBalance(balance);
		clientInfo.setAccountId(id);
		serverModel.addClient(clientInfo);
		//the servermodel instace passed is the same for all the clients connected
		MyProtocol clientProtocol = new MyProtocol(client, clientInfo, serverModel);
		//connectedClients.add(clientName);
		
		Thread clientThread = new Thread(clientProtocol);
        clientThread.start();
		
	}

	public Server() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setPreferredSize(new Dimension(400,400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//THOSE PANELS ARE NOT STRICTLY NECESSARY, JUST HELP TO VISUALIZE WHAT'S HAPPENING
		frame.setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0};
		gbl_contentPane.columnWeights = new double[]{1.0};
		contentPane.setLayout(gbl_contentPane);
		
		pnlCountdown = new PnlCountdown();
		GridBagConstraints gbc_pnlBoard = new GridBagConstraints();
		gbc_pnlBoard.insets = new Insets(0, 0, 5, 0);
		gbc_pnlBoard.fill = GridBagConstraints.BOTH;
		gbc_pnlBoard.gridx = 0;
		gbc_pnlBoard.gridy = 0;
		contentPane.add(pnlCountdown, gbc_pnlBoard);
		
		stateLbl = new JLabel(""+serverModel.getGameState());
		GridBagConstraints gbc_stateLbl = new GridBagConstraints();
		gbc_stateLbl.insets = new Insets(0, 0, 5, 0);
		gbc_stateLbl.gridx = 0;
		gbc_stateLbl.gridy = 1;
		contentPane.add(stateLbl, gbc_stateLbl);
		
		betTextArea = new JTextArea();
		betTextArea.setEditable(false);
		GridBagConstraints gbc_betTextArea = new GridBagConstraints();
		gbc_betTextArea.insets = new Insets(0, 0, 5, 0);
		gbc_betTextArea.fill = GridBagConstraints.BOTH;
		gbc_betTextArea.gridx = 0;
		gbc_betTextArea.gridy = 2;
		
		contentPane.add(betTextArea, gbc_betTextArea);
		
		numTextArea = new JTextArea();
		numTextArea.setEnabled(false);
		numTextArea.setEditable(false);
		GridBagConstraints gbc_numTextArea = new GridBagConstraints();
		gbc_numTextArea.fill = GridBagConstraints.BOTH;
		gbc_numTextArea.gridx = 0;
		gbc_numTextArea.gridy = 3;
		contentPane.add(numTextArea, gbc_numTextArea);
				
		serverModel.addChangeListener(new ChangeListener() {
			
            @Override
            public void stateChanged(ChangeEvent e) {
            	
            	if(e instanceof CustomChangeEvent) {
            		EventType eventType = ((CustomChangeEvent) e).getEventType();
                	
                	switch (eventType) {
    				case UPDATE_BET: {
    					updateBet();
    					break;
    				}
    				case CLIENTS_UPDATE: {
    					updateClients();
    					break;
    				}
    				case GENERATED_NUMBER: {
    					updatedLast500area();
    					break;
    				}
    				case UPDATE_GAME_STATE: {
    					String state = ""+serverModel.getPrevGameState();
    					stateLbl.setText(state); 
    					break;
    				}
    				case TIMER_TICK: {
    					int seconds = serverModel.getSeconds();
    	                pnlCountdown.updateCountdown(seconds);
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
		
		frame.pack();

	}
	

     
	private void updateClients() {
		betTextArea.setText("");
		HashMap<Integer, ClientInfo> clientMap = serverModel.getConnectedClients();
		for(Integer key : clientMap.keySet()) {
			betTextArea.append(clientMap.get(key).getClientName()+":"+clientMap.get(key).getAccountBalance()+"\n");
		}
		betTextArea.setForeground(Color.black);
	}
	
	private void updatedLast500area() {
		numTextArea.setText("");
		List<WheelNumber> numbers = serverModel.getStats();
		if(numbers != null) {
			for(WheelNumber w : numbers) {
				numTextArea.append(w.getValue() +"\n");			}
		}
		
		numTextArea.setForeground(Color.black);
		
	}
	
	private void updateBet() {
		betTextArea.setText("");
		
		HashMap<Integer, ClientInfo> clientMap = serverModel.getConnectedClients();
		for(Integer key : clientMap.keySet()) {
			ClientInfo client = clientMap.get(key);
			betTextArea.append(clientMap.get(key).getClientName()+":"+clientMap.get(key).getAccountBalance()+"\n");
			if(client.getBetList() == null)
				return;
			for(WheelNumber w : client.getBetList()) {
				betTextArea.append(w.getValue() + ":"+ w.getBettedValue()+"\n");
			}
			if(client.getZoneBetList() == null)
				return;
			for(Zone z : client.getZoneBetList()) {
				betTextArea.append(z.getZoneName() + ":"+ z.getBetValue()+"\n");
			}
		}
		betTextArea.setForeground(Color.black);
		
	}
}


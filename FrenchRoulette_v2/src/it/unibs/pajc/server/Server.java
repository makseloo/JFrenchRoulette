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
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
import it.unibs.pajc.core.BaseModel.ClientsUpdateEvent;
import it.unibs.pajc.core.BaseModel.GeneratedNumberEvent;
import it.unibs.pajc.core.BaseModel.TimerExpiredEvent;
import it.unibs.pajc.core.BaseModel.UpdateBet;


import javax.swing.JLabel;

import javax.swing.JTextArea;

public class Server {
    private JFrame frame;
    private JPanel contentPane;

    private static ServerModel serverModel;
    private PnlCountdown pnlCountdown;
    private JLabel stateLbl;
    private JTextArea textArea;
    private JTextArea textArea_1;

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
        
        //connectedClients = new ArrayList<>();
        int port = 1234;
        

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for client connection...");

            int id = 0;
            while (true) {
                Socket client = serverSocket.accept();
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
		//balance and id has to be set
		
		MyProtocol clientProtocol = new MyProtocol(client, clientInfo, serverModel);
		//connectedClients.add(clientName);
		
		Thread clientThread = new Thread(clientProtocol);
        clientThread.start();
		
	}

	/**
	 * Create the application.
	 */
	public Server() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setPreferredSize(new Dimension(400,400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		frame.setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0};
		gbl_contentPane.columnWeights = new double[]{1.0};
		contentPane.setLayout(gbl_contentPane);
		
		pnlCountdown = new PnlCountdown();//è giusti dal punto di vista mvc?
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
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 2;
		
		contentPane.add(textArea, gbc_textArea);
		
		textArea_1 = new JTextArea();
		textArea_1.setEnabled(false);
		textArea_1.setEditable(false);
		GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
		gbc_textArea_1.fill = GridBagConstraints.BOTH;
		gbc_textArea_1.gridx = 0;
		gbc_textArea_1.gridy = 3;
		contentPane.add(textArea_1, gbc_textArea_1);
				
		serverModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	if(e instanceof ClientsUpdateEvent) {
            		updateClients();
            	}else if(e instanceof GeneratedNumberEvent) {
            		updatedLast500area();
            	}else if(e instanceof UpdateBet) {
					updateBet();
				}
            	updateView();  
            }
        });
		
		frame.pack();

	}
	
     void updateView() {
        int seconds = serverModel.getSeconds();
        String state = ""+serverModel.getPrevGameState();
        pnlCountdown.updateCountdown(seconds);
        stateLbl.setText(state);
        //pnlClients.updateTextArea();
    }
     
	private void updateClients() {
		textArea.setText("");
		HashMap<Integer, ClientInfo> clientMap = serverModel.getConnectedClients();
		for(Integer key : clientMap.keySet()) {
			textArea.append(clientMap.get(key).getClientName()+":"+clientMap.get(key).getAccountBalance()+"\n");
		}
		textArea.setForeground(Color.black);
	}
	
	private void updatedLast500area() {
		textArea_1.setText("");
		Queue<WheelNumber> numbers = serverModel.getStats();
		
		for(WheelNumber w : numbers) {
			//System.out.print(w.getValue()+"");
			//textArea_1.append(w.getValue()+"\n");
		}
		textArea_1.setForeground(Color.black);
		
	}
	
	private void updateBet() {
		textArea.setText("");
		
		HashMap<Integer, ClientInfo> clientMap = serverModel.getConnectedClients();
		for(Integer key : clientMap.keySet()) {
			ClientInfo client = clientMap.get(key);
			textArea.append(clientMap.get(key).getClientName()+":"+clientMap.get(key).getAccountBalance()+"\n");
			for(WheelNumber w : client.getBetList()) {
				textArea.append(w.getValue() + ":"+ w.getBettedValue()+"\n");
			}
			for(Zone z : client.getZoneBetList()) {
				textArea.append(z.getZoneName() + ":"+ z.getBetValue()+"\n");
			}
		}
		textArea.setForeground(Color.black);
		
	}
	
	
    

}


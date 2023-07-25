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
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
        
        serverModel.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(e instanceof TimerExpiredEvent) {
					test(e);
				}else if(e instanceof GeneratedNumberEvent){
					
				}
			}


		});
        //connectedClients = new ArrayList<>();
        int port = 1234;
        

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for client connection...");

            int id = 0;
            while (true) {
                Socket client = serverSocket.accept();
                String clientName = "CLI#" + id++ + "\n";
               
                handleNewClient(client, clientName);
                
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private static void test(ChangeEvent e) {
    	String state =e.getSource().toString();
    	if(state.equals("SPINNING")) {   		
    		serverModel.generateNumber();
    	}if(e.getSource() == "SETTLING") {
    		
    	}
	}
    
	private static void handleNewClient(Socket client, String clientName) {
		
		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setClientName(clientName);
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
		
		
		/*
		pnlClients = new PnlClients(connectedClients);
		GridBagConstraints gbc_pnlClients = new GridBagConstraints();
		gbc_pnlClients.insets = new Insets(0, 0, 5, 0);

		gbc_pnlClients.gridx = 0;
		gbc_pnlClients.gridy = 2;
		contentPane.add(pnlClients, gbc_pnlClients);	
		*/
		
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
		for(ClientInfo i : serverModel.getConnectedClients()) {
			textArea.append(i.getClientName()+"\n");
		}
		textArea.setForeground(Color.black);
	}
	
	private void updatedLast500area() {
		textArea_1.setText("");
		Queue<Integer> numbers = serverModel.getNumbers();
		for(int i : numbers) {
			textArea_1.append(i+"\n");
		}
		
	}
	

	private void updateBet() {
		textArea.setText("");
		for(ClientInfo i : serverModel.getConnectedClients()) {
			textArea.append(i.getClientName()+ i.getAccountBalance()+"\n");
		}
		textArea.setForeground(Color.black);
		
	}
    

}

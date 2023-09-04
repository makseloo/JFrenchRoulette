package it.unibs.pajc.client;

import java.awt.EventQueue;

import it.unibs.pajc.EventType;
import it.unibs.pajc.Numbers;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
import it.unibs.pajc.client.panels.PnlBetBoard;
import it.unibs.pajc.client.panels.PnlFiches;
import it.unibs.pajc.client.panels.PnlInfos;
import it.unibs.pajc.client.panels.PnlRange;
import it.unibs.pajc.client.panels.PnlWheel;
import it.unibs.pajc.client.panels.PnlZones;
import it.unibs.pajc.core.CustomChangeEvent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FrenchRoulette_v2 {

	public JFrame frame;
	private JPanel contentPane;
	private Model model;
	private PnlBetBoard pnlBetBoard;
	private PnlFiches pnlFiches;
	private PnlRange pnlRange;
	private PnlZones pnlZones;
	
	Thread timerThread;
	private PnlWheel pnlWheel;
	private PnlInfos pnlInfos;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrenchRoulette_v2 window = new FrenchRoulette_v2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrenchRoulette_v2() {
		model = new Model();
		//UI and its Listeners
		initialize();
		
		model.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	
            	if(e instanceof CustomChangeEvent) {
            		EventType eventType = ((CustomChangeEvent) e).getEventType();
                	
                	switch (eventType) {
    				case UPDATE_GAME_STATE: {
    					disableAndReset(e);
    					break;
    				}
    				case UPDATE_BET: {
    					//just putting a png fiche on the board
    					pnlBetBoard.updateNumBetBoard(getBets());
                    	pnlBetBoard.updateZoneBetBoard(getZoneBets());
                    	//writing down what the user bet on
                    	pnlInfos.updateBets(getBets(), getZoneBets());
    					break;
    				}
    				case LAST_TEN_CHANGED:{
    					pnlWheel.startAnimation(model.getLastNumber());
    					break;
    				}
    				case RANGE_SET:{
    					pnlRange.updateRange(model.getRange());	
    					break;
    				}
    				case BALANCE_SET:{
    					pnlInfos.updateBalanceLbl(model.getBalance());
    					pnlInfos.updateBetLbl(model.getBet());	
    					break;
    				}
    				default:
    					throw new IllegalArgumentException("Unexpected value: " + eventType);
    				}
            	}
            }		
        });
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		frame.setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		contentPane.setLayout(gbl_contentPane);
		
		pnlWheel = new PnlWheel(model.getWheelNumberList());
		GridBagConstraints gbc_pnlWheel = new GridBagConstraints();
		gbc_pnlWheel.insets = new Insets(0, 0, 5, 5);
		gbc_pnlWheel.fill = GridBagConstraints.BOTH;
		gbc_pnlWheel.gridx = 0;
		gbc_pnlWheel.gridy = 0;
		contentPane.add(pnlWheel, gbc_pnlWheel);
		
		pnlInfos = new PnlInfos();
		GridBagConstraints gbc_pnlInfos = new GridBagConstraints();
		gbc_pnlInfos.insets = new Insets(0, 0, 5, 5);
		gbc_pnlInfos.fill = GridBagConstraints.BOTH;
		gbc_pnlInfos.gridx = 1;
		gbc_pnlInfos.gridy = 0;
		contentPane.add(pnlInfos, gbc_pnlInfos);
		
		pnlBetBoard = new PnlBetBoard(model.getNumberList(), Numbers.othersStat, Numbers.dozAndCols);//è giusti dal punto di vista mvc?
		GridBagConstraints gbc_pnlBoard = new GridBagConstraints();
		gbc_pnlBoard.insets = new Insets(0, 0, 5, 5);
		gbc_pnlBoard.fill = GridBagConstraints.BOTH;
		gbc_pnlBoard.gridx = 0;
		gbc_pnlBoard.gridy = 1;
		contentPane.add(pnlBetBoard, gbc_pnlBoard);
		
		pnlZones = new PnlZones(model.getNumberList(), model.getRange());
		GridBagConstraints gbc_pnlZones = new GridBagConstraints();
		gbc_pnlZones.insets = new Insets(0, 0, 5, 5);
		gbc_pnlZones.fill = GridBagConstraints.BOTH;
		gbc_pnlZones.gridx = 1;
		gbc_pnlZones.gridy = 1;
		contentPane.add(pnlZones, gbc_pnlZones);
				
		
		pnlFiches = new PnlFiches(model.getFicheList());
		GridBagConstraints gbc_pnlFiches = new GridBagConstraints();
		gbc_pnlFiches.insets = new Insets(0, 0, 5, 5);
		gbc_pnlFiches.fill = GridBagConstraints.BOTH;
		gbc_pnlFiches.gridx = 0;
		gbc_pnlFiches.gridy = 2;
		contentPane.add(pnlFiches, gbc_pnlFiches);

		pnlRange = new PnlRange(model.getRange());
		GridBagConstraints gbc_pnlRange = new GridBagConstraints();
		gbc_pnlRange.insets = new Insets(0, 0, 5, 5);
		gbc_pnlRange.fill = GridBagConstraints.BOTH;
		gbc_pnlRange.gridx = 1;
		gbc_pnlRange.gridy = 2;
		contentPane.add(pnlRange, gbc_pnlRange);  
   				
		//LISTENERS
		
		pnlBetBoard.addActionListener(e -> {
			String command = e.getActionCommand().toString();
			//check if it's a number
			if (command.matches("^\\d+$")) {
				model.betNum(e);
			} else {
				//otherwise i bet on the other zones
				model.betOther(command);
			}	
		});
		
		pnlFiches.addActionListener(e -> this.takeFiche(e));
		//perché è qui?
		pnlWheel.addActionListener(e -> this.updateInfoStats());
		
		pnlRange.addActionListener(e -> this.changeRange(e));
		
		pnlZones.addActionListener(e -> {
			switch (e.getActionCommand()) {
			case "TIER": {
				model.betTier();
				break;
			}
			case "ORPHELINS":{
				model.betOrph();
				break;
			}
			case "VOISINS":{
				model.betVois();
				break;
			}
			case "Z":{
				model.betZero();
				break;
			}
			default:
				//se non trovo nulla vuol dire che ho usato il range
				model.betRange(e);	
			}
			
		});

		frame.pack();
		
	}

	 private void updateInfoStats() {
		 pnlInfos.updateStats(model.getLastTen());
	}

	 // changing the value of the range based on + - buttons
	void changeRange(ActionEvent e) {
		model.changeRange(e);
	}
	
	 void takeFiche(ActionEvent e) {
		 String ficheString = e.getActionCommand();
		 
		 double ficheTaken;
		 ficheTaken = Double.parseDouble(ficheString);
		 model.selectFiche(ficheTaken);
	 }
	 

	 
	// they are incapsulated like that beacuse Client has to use them
	 
	public List<WheelNumber> getBets() {
		return model.getBets();
	}
	 
	public void updateTimer(int remainingSeconds) {
		pnlInfos.updateCountdown(remainingSeconds);
	}

	public void updateGameState(String gameState) {
		model.setState(gameState);
		
	}

	public void updateStats(List<WheelNumber> stats) {
		model.updateLastTen(stats);
	}
	
	
	public void setBalance(double balance) {
		model.setBalance(balance);
	}

	public double getTotalBet() {
		return model.getBet();
	}

	public List<Zone> getZoneBets() {
		return model.getZonesBets();
	}
	
	
	public void updateLastWinLbl(double lastWin) {
	    int lastNum = model.getLastNumber().getValue();
	    String numMessage = "";
	    String winMessage = "";
	    if (lastWin != 0)
	    	winMessage = "Hai vinto : " + lastWin;
	    numMessage = "Ultimo numero uscito: " + lastNum;
	    
	    pnlInfos.updateLastWinLbl(winMessage,numMessage);
	    
	}

	private void disableAndReset(ChangeEvent e) {
		String gameState = e.getSource().toString();
		pnlInfos.updateState(gameState);

		if (gameState.equals("BETTING")) {
	        pnlBetBoard.enableButtons(true); // Enable betting buttons
	        pnlFiches.enableButtons(true); // Enable fiche buttons
	        pnlZones.enableButtons(true);
	        
	    } else if(gameState.equals("SPINNING")){
	        pnlBetBoard.enableButtons(false); // Disable betting buttons
	        pnlFiches.enableButtons(false); // Disable fiche buttons
	        pnlZones.enableButtons(false);
	        
	    } else if(gameState.equals("SETTLING")) {
        	pnlBetBoard.resetBoard();
        	pnlInfos.resetInfo();
        	pnlInfos.updateBetLbl(getTotalBet());
        	model.resetBet();
        	model.resetBets();
        	
        }
		
	}
}

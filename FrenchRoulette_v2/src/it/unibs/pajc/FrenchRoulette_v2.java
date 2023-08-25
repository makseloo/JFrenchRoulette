package it.unibs.pajc;

import java.awt.EventQueue;
import it.unibs.pajc.panels.PnlBetBoard;
import it.unibs.pajc.panels.PnlFiches;
import it.unibs.pajc.panels.PnlRange;
import it.unibs.pajc.panels.PnlWheel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.unibs.pajc.server.PnlCountdown;
import it.unibs.pajc.server.RouletteGameState;

import javax.swing.JTextArea;
import it.unibs.pajc.panels.PnlZones;
import it.unibs.pajc.panels.PnlInfos;

public class FrenchRoulette_v2 {

	public JFrame frame;
	private JPanel contentPane;
	private Model model;
	private PnlBetBoard pnlBetBoard;
	private PnlFiches pnlFiches;
	private PnlRange pnlRange;
	private PnlZones pnlZones;
	
	private JLabel lblBalance;
	private JLabel lblBet;
	
		//debug
	
	
	private JTextArea testBets;
	
	Thread timerThread;
	private PnlCountdown pnlCountdown;
	private JLabel stateLbl;
	private PnlWheel pnlWheel;
	private JLabel lblLastNum;
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
		initialize();
		
		model.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateView();
                updateTest();
                
            }

			private void updateTest() {
				pnlInfos.updateBets(getBets(),model.getZonesBets());
			}
        });
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		frame.setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, 0.0, 1.0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0};
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
		
		
		testBets = new JTextArea();
		testBets.setEditable(false);
		GridBagConstraints gbc_testBets = new GridBagConstraints();
		gbc_testBets.insets = new Insets(0, 0, 5, 0);
		gbc_testBets.fill = GridBagConstraints.BOTH;
		gbc_testBets.gridx = 2;
		gbc_testBets.gridy = 0;
		contentPane.add(testBets, gbc_testBets);
		
		
		pnlBetBoard = new PnlBetBoard(model.getNumberList(), WheelNumber.getOthersStat());//è giusti dal punto di vista mvc?
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
		//1 va passato dal model
		pnlRange = new PnlRange(model.getRange());
		GridBagConstraints gbc_pnlRange = new GridBagConstraints();
		gbc_pnlRange.insets = new Insets(0, 0, 5, 5);
		gbc_pnlRange.fill = GridBagConstraints.BOTH;
		gbc_pnlRange.gridx = 1;
		gbc_pnlRange.gridy = 2;
		contentPane.add(pnlRange, gbc_pnlRange);
		
		
		lblBalance = new JLabel("Saldo");
		GridBagConstraints gbc_lblBalance = new GridBagConstraints();
		gbc_lblBalance.insets = new Insets(0, 0, 5, 5);
		gbc_lblBalance.gridx = 1;
		gbc_lblBalance.gridy = 3;
		contentPane.add(lblBalance, gbc_lblBalance);
		
		pnlCountdown = new PnlCountdown();
		GridBagConstraints gbc_pnlCountdown = new GridBagConstraints();
		gbc_pnlCountdown.insets = new Insets(0, 0, 5, 5);
		gbc_pnlCountdown.fill = GridBagConstraints.BOTH;
		gbc_pnlCountdown.gridx = 0;
		gbc_pnlCountdown.gridy = 3;
		contentPane.add(pnlCountdown, gbc_pnlCountdown);
		
		lblBet = new JLabel("Puntata");
		GridBagConstraints gbc_lblBet = new GridBagConstraints();
		gbc_lblBet.insets = new Insets(0, 0, 5, 5);
		gbc_lblBet.gridx = 1;
		gbc_lblBet.gridy = 4;
		contentPane.add(lblBet, gbc_lblBet);    
   				
		pnlBetBoard.addActionListener(e -> {
			String command = e.getActionCommand().toString();
			//se è un numero scommetto su un numero
			if (command.matches("^\\d+$")) {
				this.bet(e);
			} else {
				model.betDoz(command);
				//pnlBetBoard.updateDoz(command);
			}
			
		});
		
		pnlFiches.addActionListener(e -> this.takeFiche(e));
		
		pnlWheel.addActionListener(e -> this.bet(e));
		
		pnlRange.addActionListener(e -> this.changeRange(e));
		
		pnlZones.addActionListener(e -> {
			switch (e.getActionCommand()) {
			case "TIER": {
				this.betTiers();
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
				this.betRange(e);
			}
			
			
		});
		
		pnlInfos.addActionListener(e -> bet(e));

		
		frame.pack();
		

	}

	 private void betTiers() {
		model.betTier();
		
	}

	void betRange(ActionEvent e) {
		//System.out.print(e.getActionCommand());
		int value = Integer.parseInt(e.getActionCommand());
		int range = model.getRange();
		int bet = model.getSelectedFicheVal() ;
		int betRange = bet*(range*2 +1);
		 if((model.getBalance() - betRange) >= 0) {
			 model.setNumberBet(value, bet);
			 model.setRangeNumberBet(e.getActionCommand(), bet, range);
			 model.setBet(betRange);
			 model.substractBalance(betRange); 
		 }else {
			 System.out.print("Saldo insufficiente\n");
		 }
	}

	void changeRange(ActionEvent e) {
		int range = model.getRange();
		
		if(e.getActionCommand().equals("+")) {
			model.setRange(++range);
		}else if(e.getActionCommand().equals("-")) {
			model.setRange(--range);
		}
		
	}

	void bet(ActionEvent e) {
		int value = Integer.parseInt(e.getActionCommand());
		int bet = model.getSelectedFicheVal();
		if((model.getBalance() - bet) >= 0) {
			 model.setNumberBet(value, bet);
			 model.setBet(bet);
			 model.substractBalance(bet); 
		 }else {
			 //System.out.print("Saldo insufficiente");
		 }
		 
	}
	 
	 void takeFiche(ActionEvent e) {
		 String ficheString = e.getActionCommand();
		 
		 double ficheTaken;
		 ficheTaken = Double.parseDouble(ficheString);
		 model.selectFiche(ficheTaken);
	 }
	 
	 public List<WheelNumber> getBets() {
		 return model.getBets();
	 }
	 
	
	public void updateTimer(int remainingSeconds) {
		pnlInfos.updateCountdown(remainingSeconds);
		pnlCountdown.updateCountdown(remainingSeconds);
	}

	public void updateGameState(String gameState) {
		model.setState(gameState);
		
	}
	
	private void updateView() {
		//model.setBet(0) non credo vada qui
		RouletteGameState gameState = model.getState();
		pnlInfos.updateState(gameState.toString());		
		lblBalance.setText("Saldo:"+model.getBalance());
    	lblBet.setText("Puntata: "+ model.getBet());
    	pnlRange.updateRange(model.getRange());
		 // Disable buttons based on the game state
	    if (gameState == RouletteGameState.BETTING) {
	        pnlBetBoard.enableButtons(true); // Enable betting buttons
	        pnlFiches.enableButtons(true); // Enable fiche buttons
	    } else {
	        pnlBetBoard.enableButtons(false); // Disable betting buttons
	        pnlFiches.enableButtons(false); // Disable fiche buttons	        
	        if(gameState == RouletteGameState.SETTLING) {
	        	model.resetBet();
	        	model.resetBets();
	        }
	    }
	    
		
	}
	public void updateStats(Queue<WheelNumber> stats) {
		pnlInfos.updateStats(stats);
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
}

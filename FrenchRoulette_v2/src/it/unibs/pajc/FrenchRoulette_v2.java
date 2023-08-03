package it.unibs.pajc;

import java.awt.EventQueue;
import it.unibs.pajc.panels.PnlBetBoard;
import it.unibs.pajc.panels.PnlFiches;
import it.unibs.pajc.panels.PnlStatitics;
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

public class FrenchRoulette_v2 {

	public JFrame frame;
	private JPanel contentPane;
	private Model model;
	private PnlBetBoard pnlBetBoard;
	private PnlFiches pnlFiches;
	private PnlStatitics pnlStatitics;
	
	private JLabel lblBalance;
	private JLabel lblBet;
	
		//debug
	
	
	private JTextArea testBets;
	
	Thread timerThread;
	private PnlCountdown pnlCountdown;
	private JLabel stateLbl;
	private PnlWheel pnlWheel;
	private JLabel lblLastNum;
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
				 testBets.setText("");
				 for(WheelNumber w : getBets()) {
					 testBets.append(w.getValue() + ":"+w.getBettedValue()+"\n");
				 }
				
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
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		contentPane.setLayout(gbl_contentPane);
		
		pnlWheel = new PnlWheel(model.getWheelNumberList());
		GridBagConstraints gbc_pnlWheel = new GridBagConstraints();
		gbc_pnlWheel.insets = new Insets(0, 0, 5, 5);
		gbc_pnlWheel.fill = GridBagConstraints.BOTH;
		gbc_pnlWheel.gridx = 0;
		gbc_pnlWheel.gridy = 0;
		contentPane.add(pnlWheel, gbc_pnlWheel);
		
		pnlStatitics = new PnlStatitics();
		GridBagConstraints gbc_pnlStatitics = new GridBagConstraints();
		gbc_pnlStatitics.insets = new Insets(0, 0, 5, 0);
		gbc_pnlStatitics.fill = GridBagConstraints.BOTH;
		gbc_pnlStatitics.gridx = 1;
		gbc_pnlStatitics.gridy = 0;
		contentPane.add(pnlStatitics, gbc_pnlStatitics);
		
		
		pnlBetBoard = new PnlBetBoard(model.getNumberList(), WheelNumber.getOthersStat());//è giusti dal punto di vista mvc?
		GridBagConstraints gbc_pnlBoard = new GridBagConstraints();
		gbc_pnlBoard.insets = new Insets(0, 0, 5, 5);
		gbc_pnlBoard.fill = GridBagConstraints.BOTH;
		gbc_pnlBoard.gridx = 0;
		gbc_pnlBoard.gridy = 1;
		contentPane.add(pnlBetBoard, gbc_pnlBoard);
		
		testBets = new JTextArea();
		testBets.setEditable(false);
		GridBagConstraints gbc_testBets = new GridBagConstraints();
		gbc_testBets.insets = new Insets(0, 0, 5, 0);
		gbc_testBets.fill = GridBagConstraints.BOTH;
		gbc_testBets.gridx = 1;
		gbc_testBets.gridy = 1;
		contentPane.add(testBets, gbc_testBets);
				
		
		pnlFiches = new PnlFiches(model.getFicheList());
		GridBagConstraints gbc_pnlFiches = new GridBagConstraints();
		gbc_pnlFiches.insets = new Insets(0, 0, 5, 5);
		gbc_pnlFiches.fill = GridBagConstraints.BOTH;
		gbc_pnlFiches.gridx = 0;
		gbc_pnlFiches.gridy = 2;
		contentPane.add(pnlFiches, gbc_pnlFiches);
		
		
		lblBalance = new JLabel("Saldo");
		GridBagConstraints gbc_lblBalance = new GridBagConstraints();
		gbc_lblBalance.insets = new Insets(0, 0, 5, 0);
		gbc_lblBalance.gridx = 1;
		gbc_lblBalance.gridy = 2;
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
		gbc_lblBet.insets = new Insets(0, 0, 5, 0);
		gbc_lblBet.gridx = 1;
		gbc_lblBet.gridy = 3;
		contentPane.add(lblBet, gbc_lblBet);
		
		lblLastNum = new JLabel("Ultimo numero uscito : ");
		GridBagConstraints gbc_lblLastNum = new GridBagConstraints();
		gbc_lblLastNum.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastNum.gridx = 0;
		gbc_lblLastNum.gridy = 4;
		contentPane.add(lblLastNum, gbc_lblLastNum);
		
		stateLbl = new JLabel(""+model.getState());
		GridBagConstraints gbc_stateLbl = new GridBagConstraints();
		gbc_stateLbl.insets = new Insets(0, 0, 0, 5);
		gbc_stateLbl.gridx = 0;
		gbc_stateLbl.gridy = 5;
		contentPane.add(stateLbl, gbc_stateLbl);

        
   				
		pnlBetBoard.addActionListener(e -> this.bet(e));
		
		pnlFiches.addActionListener(e -> this.takeFiche(e));
		
		pnlWheel.addActionListener(e -> this.bet(e));

		
		frame.pack();
		

	}

	 void bet(ActionEvent e) {
		 int bet = model.getSelectedFicheVal();
		 System.out.print(e.getActionCommand());
		 if((model.getBalance() - bet) >= 0) {
			 model.setNumberBet(e.getActionCommand(), bet);
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
	 
	void dump() {
		System.out.println("" + model.Dump());
	}

	public String getBets1() {
		return model.Dump();
	}
	
	public void updateTimer(int remainingSeconds) {
		pnlCountdown.updateCountdown(remainingSeconds);
	}

	public void updateGameState(String gameState) {
		model.setState(gameState);
		
	}
	
	private void updateView() {
		//model.setBet(0) non credo vada qui
		RouletteGameState gameState = model.getState();
		stateLbl.setText(gameState.toString());
		lblBalance.setText("Saldo:"+model.getBalance());
    	lblBet.setText("Puntata: "+ model.getBet());
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

	public void updateLast500(Queue<Integer> last500) {
		
		List<WheelNumber>coloredStats =  model.turnIntoColor(last500);
		lblLastNum.setText("Ultimo numero uscito: "+ coloredStats.get(coloredStats.size()-1).getValue());
		pnlStatitics.updateLast500(coloredStats);
		
	}

	public void updateStats(Map<String, Integer> stats) {
		pnlStatitics.updateStats(stats);
	}
	
	public void setBalance(int balance) {
		model.setBalance(balance);
	}

	public int getTotalBet() {
		return model.getBet();
	}
}

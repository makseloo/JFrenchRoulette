package it.unibs.pajc;

import java.awt.EventQueue;
import it.unibs.pajc.core.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import it.unibs.pajc.server.PnlCountdown;

public class FrenchRoulette_v2 {

	public JFrame frame;
	private JPanel contentPane;
	private Model model;
	private PnlBetBoard pnlBetBoard;
	private PnlFiches pnlFiches;
	
	private static int remainingTime = 10;
	//debug
	private JLabel lblBalance;
	
	private Timer timer;
	Thread timerThread;
	private PnlCountdown pnlCountdown;
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
		
		model.addChangeListener(e -> this.dump());
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
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0};
		gbl_contentPane.columnWeights = new double[]{1.0};
		contentPane.setLayout(gbl_contentPane);
		
		
		pnlBetBoard = new PnlBetBoard(model.getNumberList());//è giusti dal punto di vista mvc?
		GridBagConstraints gbc_pnlBoard = new GridBagConstraints();
		gbc_pnlBoard.insets = new Insets(0, 0, 5, 0);
		gbc_pnlBoard.fill = GridBagConstraints.BOTH;
		gbc_pnlBoard.gridx = 0;
		gbc_pnlBoard.gridy = 0;
		contentPane.add(pnlBetBoard, gbc_pnlBoard);
				
		
		pnlFiches = new PnlFiches(model.getFicheList());
		GridBagConstraints gbc_pnlFiches = new GridBagConstraints();
		gbc_pnlFiches.insets = new Insets(0, 0, 5, 0);
		gbc_pnlFiches.fill = GridBagConstraints.BOTH;
		gbc_pnlFiches.gridx = 0;
		gbc_pnlFiches.gridy = 1;
		contentPane.add(pnlFiches, gbc_pnlFiches);
		
		
		lblBalance = new JLabel("New label");
		GridBagConstraints gbc_lblBalance = new GridBagConstraints();
		gbc_lblBalance.insets = new Insets(0, 0, 5, 0);
		gbc_lblBalance.gridx = 0;
		gbc_lblBalance.gridy = 2;
		contentPane.add(lblBalance, gbc_lblBalance);
		
		pnlCountdown = new PnlCountdown();
		GridBagConstraints gbc_pnlCountdown = new GridBagConstraints();
		gbc_pnlCountdown.fill = GridBagConstraints.BOTH;
		gbc_pnlCountdown.gridx = 0;
		gbc_pnlCountdown.gridy = 3;
		contentPane.add(pnlCountdown, gbc_pnlCountdown);

        
   				
		pnlBetBoard.addActionListener(e -> this.bet(e));
		
		pnlFiches.addActionListener(e -> this.takeFiche(e));
		
				
		
		frame.pack();
		

	}

	 void bet(ActionEvent e) {
		 double bet = model.getSelectedFicheVal();
		 model.setNumberBet(e.getActionCommand(), bet);
		 lblBalance.setText(e.getActionCommand());
		 //System.out.println(e.getSource());
	}
	 
	 void takeFiche(ActionEvent e) {
		 String ficheString = e.getActionCommand();
		 
		 double ficheTaken;
		 ficheTaken = Double.parseDouble(ficheString);
		 model.selectFiche(ficheTaken);
		System.out.println(ficheTaken+"");
	 }
	 
	 List<WheelNumber> getBets() {
		 return model.getNumberList();
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
		
}

package it.unibs.pajc;

import java.awt.EventQueue;
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

public class FrenchRoulette_v2 {

	JFrame frame;
	private JPanel contentPane;
	private Model model;
	private PnlBetBoard PnlBetBoard;
	private PnlFiches PnlFiches;
	
	private static int remainingTime = 10;
	//debug
	private JLabel lblBalance;
	private JLabel lblTimer;
	
	private Timer timer;
	Thread timerThread;
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
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{1.0};
		contentPane.setLayout(gbl_contentPane);
		
		
		PnlBetBoard = new PnlBetBoard(model.getNumberList());//è giusti dal punto di vista mvc?
		GridBagConstraints gbc_pnlBoard = new GridBagConstraints();
		gbc_pnlBoard.insets = new Insets(0, 0, 5, 5);
		gbc_pnlBoard.fill = GridBagConstraints.BOTH;
		gbc_pnlBoard.gridx = 0;
		gbc_pnlBoard.gridy = 0;
		contentPane.add(PnlBetBoard, gbc_pnlBoard);
				
		
		PnlFiches = new PnlFiches(model.getFicheList());
		GridBagConstraints gbc_pnlFiches = new GridBagConstraints();
		gbc_pnlFiches.insets = new Insets(0, 0, 5, 5);
		gbc_pnlFiches.fill = GridBagConstraints.BOTH;
		gbc_pnlFiches.gridx = 0;
		gbc_pnlFiches.gridy = 1;
		contentPane.add(PnlFiches, gbc_pnlFiches);
		
		
		//debug
				lblBalance = new JLabel("New label");
				GridBagConstraints gbc_lblBalance = new GridBagConstraints();
				gbc_lblBalance.insets = new Insets(0, 0, 5, 5);
				gbc_lblBalance.gridx = 0;
				gbc_lblBalance.gridy = 2;
				contentPane.add(lblBalance, gbc_lblBalance);
				
				
				
				lblTimer = new JLabel("Tempo rimasto: " + remainingTime + " secondi");
				GridBagConstraints gbc_lblBet = new GridBagConstraints();
				gbc_lblBet.insets = new Insets(0, 0, 0, 5);
				gbc_lblBet.gridx = 0;
				gbc_lblBet.gridy = 3;
				contentPane.add(lblTimer, gbc_lblBet);

				int remainingSeconds = 10;
				TimerRunnable timerRunnable = new TimerRunnable(lblTimer);
		        timerThread = new Thread(timerRunnable);
		        
		    
				
				PnlBetBoard.addActionListener(e -> this.bet(e));
				
				PnlFiches.addActionListener(e -> this.takeFiche(e));
				
		
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

		public void startTimer(int seconds) {
			SwingUtilities.invokeLater(() -> {
		        lblTimer.setText("Tempo rimasto: " + seconds + " secondi");
		    });
		    timerThread.start();
			
		}
		
		class TimerRunnable implements Runnable {
		    private JLabel timerLabel;

		    public TimerRunnable(JLabel timerLabel) {
		        this.timerLabel = timerLabel;
		    }

		    @Override
		    public void run() {
		        int seconds = 10;

		        try {
		            while (seconds >= 0) {
		                Thread.sleep(1000);
		                String time = String.format("%02d", seconds);
		                timerLabel.setText(time);
		                seconds--;
		            }
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }
		}
		
}
package it.unibs.pajc.client.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
import it.unibs.pajc.core.PnlBase;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextArea;

public class PnlInfos extends PnlBase {

	private JPanel lastTenPnl;
	private JPanel betsPnl;
	
	private JLabel lblState;
	private JLabel lblBalance;
	private JLabel lblBet;
	
	private JLabel timerLabel;
	
	private JTextArea betsJTxt;
	private JLabel lblLastNum;
	private JLabel lblLastWin;
	
	
	public PnlInfos() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE, 0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		

		lastTenPnl = new JPanel();
		lastTenPnl.setLayout(new GridLayout(1,13));
		//Last 13 numbers
		GridBagConstraints gbc_lastTenPnl = new GridBagConstraints();
		gbc_lastTenPnl.insets = new Insets(0, 0, 5, 0);
		gbc_lastTenPnl.gridx = 0;
		gbc_lastTenPnl.gridy = 0;
		add(lastTenPnl, gbc_lastTenPnl);
		
		//Game state
		lblState = new JLabel("GAME STATE: ");
		GridBagConstraints gbc_lblState = new GridBagConstraints();
		gbc_lblState.insets = new Insets(0, 0, 5, 0);
		gbc_lblState.gridx = 0;
		gbc_lblState.gridy = 1;
		add(lblState, gbc_lblState);
		
		//TIMER
		timerLabel = new JLabel("0");
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		GridBagConstraints gbc_timerLabel = new GridBagConstraints();
		gbc_timerLabel.insets = new Insets(0, 0, 5, 0);
		gbc_timerLabel.gridx = 0;
		gbc_timerLabel.gridy = 2;
		
		add(timerLabel, gbc_timerLabel);
		
		//BALANCE
		lblBalance = new JLabel("Balance: ");
		GridBagConstraints gbc_lblBalance = new GridBagConstraints();
		gbc_lblBalance.insets = new Insets(0, 0, 5, 0);
		gbc_lblBalance.gridx = 0;
		gbc_lblBalance.gridy = 3;
		//gbc_lblState.fill = GridBagConstraints.HORIZONTAL;
		add(lblBalance, gbc_lblBalance);
		
		//BET
		lblBet = new JLabel("BET");
		
		GridBagConstraints gbc_lblBet = new GridBagConstraints();
		gbc_lblBet.insets = new Insets(0, 0, 5, 0);
		gbc_lblBet.gridx = 0;
		gbc_lblBet.gridy = 4;
		
		add(lblBet, gbc_lblBet);
		
		//LAST NUM
		lblLastNum = new JLabel("Last num:");
		GridBagConstraints gbc_lblLastNum = new GridBagConstraints();
		gbc_lblLastNum.insets = new Insets(0, 0, 5, 0);
		gbc_lblLastNum.gridx = 0;
		gbc_lblLastNum.gridy = 5;
		add(lblLastNum, gbc_lblLastNum);
		
		//LAST WIN
		lblLastWin = new JLabel("Last win:");
		GridBagConstraints gbc_lblWin = new GridBagConstraints();
		gbc_lblWin.insets = new Insets(0, 0, 5, 0);
		gbc_lblWin.gridx = 0;
		gbc_lblWin.gridy = 6;
		add(lblLastWin, gbc_lblWin);
		
		//THE BETS YOU PLACED
		betsPnl = new JPanel();
		betsPnl.setLayout(new GridBagLayout());
		GridBagConstraints gbc_betsPnl = new GridBagConstraints();
		gbc_betsPnl.gridx = 0;
		gbc_betsPnl.gridy = 7;
		gbc_betsPnl.fill = GridBagConstraints.BOTH;
		
		betsJTxt = new JTextArea();
		betsJTxt.setEditable(false);
		betsJTxt.setLineWrap(true); // Abilita il riavvolgimento automatico delle righe
		betsJTxt.setWrapStyleWord(true);
		
		JScrollPane scrollPane = new JScrollPane(betsJTxt);
		scrollPane.setPreferredSize(new Dimension(650, 200));

		betsPnl.add(scrollPane); // Add the scroll pane instead of the JTextArea
		
		add(betsPnl, gbc_betsPnl);

	}

	public void updateState(String gameState) {
		lblState.setText(gameState);
		repaint();
	}

	 public void updateCountdown(int seconds) {
		 timerLabel.setText(String.valueOf(seconds));
		 repaint();
	    }

	//the last 13 numbers are shown reversed because the last number generated has to go on the left
	public void updateStats(List<WheelNumber> stats) {
		
		lastTenPnl.removeAll();
		if(stats == null)
			return;
		for(int i = stats.size()-1; i > 0; i--) {
			JButton bw = createButton(stats.get(i).getValue()+"", Color.white);
			bw.setForeground(stats.get(i).getColor());
			lastTenPnl.add(bw);
		}

	}

	public void updateBets(List<WheelNumber> bets, List<Zone> list) {
		betsJTxt.setText("");
        for (WheelNumber w : bets) {
        	betsJTxt.append("bet on number " +w.getValue()+" : "+w.getBettedValue()+"\n");
        }
		 for(Zone z : list) {
			 betsJTxt.append("bet on zone " +z.getZoneName()+" : "+z.getBetValue()+"\n");
		 }
		 
		
	}

	public void updateBalanceLbl(double balance) {
		lblBalance.setText("Balance: "+balance);
    	
		
	}

	public void updateBetLbl(double bet) {
		lblBet.setText("Bet: "+ bet);
		
	}

	public void resetInfo() {
		betsJTxt.setText("");
	}

	public void updateLastWinLbl(String winMessage, String numMessage) {
		lblLastWin.setText(winMessage);
		lblLastNum.setText(numMessage);
		
	}

}

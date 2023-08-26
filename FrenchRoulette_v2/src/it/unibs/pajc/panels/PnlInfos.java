package it.unibs.pajc.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.unibs.pajc.Colors;
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
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.List;
import javax.swing.JTextArea;
import javax.swing.JTable;

public class PnlInfos extends PnlBase {

	private JPanel lastTenPnl;
	private JPanel betsPnl;
	private JPanel infoPnl;
	
	private JLabel lblState;
	private JLabel lblBalance;
	private JLabel lblBet;
	
	private JLabel timerLabel;
	
	private JTextArea betsJTxt;
	
	
	public PnlInfos() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		

		lastTenPnl = new JPanel();
		lastTenPnl.setLayout(new GridLayout(1,13));
		
		GridBagConstraints gbc_lastTenPnl = new GridBagConstraints();
		gbc_lastTenPnl.gridx = 0;
		gbc_lastTenPnl.gridy = 0;
		add(lastTenPnl, gbc_lastTenPnl);
		
		lblState = new JLabel("STATO");
		GridBagConstraints gbc_lblState = new GridBagConstraints();
		gbc_lblState.insets = new Insets(0, 0, 5, 0);
		gbc_lblState.gridx = 0;
		gbc_lblState.gridy = 1;
		//gbc_lblState.fill = GridBagConstraints.HORIZONTAL;
		add(lblState, gbc_lblState);
		
		timerLabel = new JLabel("0");
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		GridBagConstraints gbc_timerLabel = new GridBagConstraints();
		gbc_timerLabel.gridx = 0;
		gbc_timerLabel.gridy = 2;
		
		add(timerLabel, gbc_timerLabel);
		
		lblBalance = new JLabel("Saldo: ");
		GridBagConstraints gbc_lblBalance = new GridBagConstraints();
		gbc_lblBalance.insets = new Insets(0, 0, 5, 0);
		gbc_lblBalance.gridx = 0;
		gbc_lblBalance.gridy = 3;
		//gbc_lblState.fill = GridBagConstraints.HORIZONTAL;
		add(lblBalance, gbc_lblBalance);
		
		lblBet = new JLabel("Puntata");
		
		GridBagConstraints gbc_lblBet = new GridBagConstraints();
		gbc_lblBet.gridx = 0;
		gbc_lblBet.gridy = 4;
		
		add(lblBet, gbc_lblBet);
		
		
		betsPnl = new JPanel();
		betsPnl.setLayout(new GridBagLayout());
		GridBagConstraints gbc_betsPnl = new GridBagConstraints();
		gbc_betsPnl.gridx = 0;
		gbc_betsPnl.gridy = 5;
		gbc_betsPnl.fill = GridBagConstraints.BOTH;
		
		betsJTxt = new JTextArea();
		betsJTxt.setEditable(false);
		betsJTxt.setLineWrap(true); // Abilita il riavvolgimento automatico delle righe
		betsJTxt.setWrapStyleWord(true);
		
		JScrollPane scrollPane = new JScrollPane(betsJTxt);
		scrollPane.setPreferredSize(new Dimension(650, 200));

		betsPnl.add(scrollPane); // Add the scroll pane instead of the JTextArea
		
		add(betsPnl, gbc_betsPnl);
		/*
		infoPnl = new JPanel();
		infoPnl.setLayout(new GridBagLayout());
		GridBagConstraints gbc_infoPnl = new GridBagConstraints();
		gbc_infoPnl.gridx = 0;
		gbc_infoPnl.gridy = 3;
		gbc_infoPnl.fill = GridBagConstraints.BOTH;
		
		JTextArea infoJTxt = new JTextArea();
		infoJTxt.setPreferredSize(new Dimension(650,100));
		infoJTxt.setEditable(false);
		infoJTxt.append("infoJTxt");
		infoPnl.add(infoJTxt);
		
		add(infoPnl, gbc_infoPnl);
		*/

	}

	public void updateState(String gameState) {
		lblState.setText(gameState);
		repaint();
	}

	 public void updateCountdown(int seconds) {
		 timerLabel.setText(String.valueOf(seconds));
		 repaint();
	    }

	public void updateStats(List<WheelNumber> stats) {
		lastTenPnl.removeAll();
		
		List<WheelNumber> reversedStats = new LinkedList<>(stats);
	    Collections.reverse((List<?>) reversedStats);
		
		for(WheelNumber w : reversedStats) {
			//ogni tanto riga 104 esplode
			JButton bw = createButton(w.getValue()+"", Color.white);
			bw.setForeground(w.getColor());
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
			 //testBets.append("Bet Type: Zone:"+z.getZoneName()+", amount: "+z.getBetValue()+"\n");
		 }
		
	}

	public void updateBalanceLbl(double balance) {
		lblBalance.setText("Saldo:"+balance);
    	
		
	}

	public void updateBetLbl(double bet) {
		lblBet.setText("Puntata: "+ bet);
		
	}

}

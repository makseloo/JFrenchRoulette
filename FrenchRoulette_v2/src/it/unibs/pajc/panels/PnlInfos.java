package it.unibs.pajc.panels;

import javax.swing.JPanel;

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
	private JPanel lastOnehundredPnl;
	private JPanel betsPnl;
	
	private JLabel lblState;
	
	private JLabel timerLabel;
	private JTable table;
	
	
	
	public PnlInfos() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblState = new JLabel("New label");
		GridBagConstraints gbc_lblState = new GridBagConstraints();
		gbc_lblState.insets = new Insets(0, 0, 5, 0);
		gbc_lblState.gridx = 0;
		gbc_lblState.gridy = 0;
		//gbc_lblState.fill = GridBagConstraints.HORIZONTAL;
		add(lblState, gbc_lblState);
		
		timerLabel = new JLabel("0");
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		GridBagConstraints gbc_timerLabel = new GridBagConstraints();
		gbc_timerLabel.gridx = 0;
		gbc_timerLabel.gridy = 1;
		
		add(timerLabel, gbc_timerLabel);
		
		
		lastTenPnl = new JPanel();
		lastTenPnl.setLayout(new GridLayout(1,13));
		
		GridBagConstraints gbc_lastTenPnl = new GridBagConstraints();
		gbc_lastTenPnl.gridx = 0;
		gbc_lastTenPnl.gridy = 2;
		add(lastTenPnl, gbc_lastTenPnl);
		
		betsPnl = new JPanel();
		betsPnl.setLayout(new GridBagLayout());
		GridBagConstraints gbc_betsPnl = new GridBagConstraints();
		gbc_betsPnl.gridx = 0;
		gbc_betsPnl.gridy = 3;
		
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

	public void updateStats(List<WheelNumber> stats) {
		lastTenPnl.removeAll();
		
		List<WheelNumber> reversedStats = new LinkedList<>(stats);
	    Collections.reverse((List<?>) reversedStats);
		
		for(WheelNumber w : reversedStats) {
			//ogni tanto riga 104 esplode
			JButton bw = createButton(w.getValue()+"", Colors.getGray().brighter());
			bw.setForeground(w.getColor());
			lastTenPnl.add(bw);
		}
	}
/*
	public void updateBets(List<WheelNumber> bets, List<Zone> list) {
		betsPnl.removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
        gbc.gridy = 0;
        
        int columnCounter = 0;
        int itemsPerColumn = 12; 
        
        for (WheelNumber w : bets) {
            if (columnCounter >= itemsPerColumn) {
                columnCounter = 0; // Reset the column counter
                gbc.gridx +=4; // Move to the next column
                gbc.gridy = 0; // Reset the row counter for the new column
            }

            JButton numberBtn = createButton(w.getValue() + "", w.getColor());
            numberBtn.setPreferredSize(new Dimension(50,30));
            JButton betValue = createButton(w.getBettedValue() + "", Color.GREEN);
            betValue.setEnabled(false);
            betValue.setPreferredSize(new Dimension(50,30));

            // Add number on the left and value on the right
            betsPnl.add(numberBtn, gbc);

            gbc.gridx++;
            betsPnl.add(betValue, gbc);
            
            gbc.gridx--;
            
            gbc.gridy++;
            columnCounter++;
        }
		 for(Zone z : list) {
			 //testBets.append("Bet Type: Zone:"+z.getZoneName()+", amount: "+z.getBetValue()+"\n");
		 }
		
	}
    */
}

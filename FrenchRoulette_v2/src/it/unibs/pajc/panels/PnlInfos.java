package it.unibs.pajc.panels;

import javax.swing.JPanel;

import it.unibs.pajc.Colors;
import it.unibs.pajc.WheelNumber;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JTextArea;

public class PnlInfos extends JPanel {

	private JPanel lastTenPnl;
	private JPanel lastOnehundredPnl;
	
	private JLabel lblState;
	
	private JLabel timerLabel;
	private JTextArea textArea;
	
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
		gbc_timerLabel.gridx = 1;
		gbc_timerLabel.gridy = 0;
		
		add(timerLabel, gbc_timerLabel);
		
		
		lastTenPnl = new JPanel();
		lastTenPnl.setLayout(new GridLayout(3,10));
		
		GridBagConstraints gbc_lastTenPnl = new GridBagConstraints();
		gbc_lastTenPnl.gridx = 0;
		gbc_lastTenPnl.gridy = 1;
		add(lastTenPnl, gbc_lastTenPnl);
		

	}

	public void updateState(String gameState) {
		lblState.setText(gameState);
		repaint();
	}

	 public void updateCountdown(int seconds) {
		 timerLabel.setText(String.valueOf(seconds));
		 repaint();
	    }

	public void updateStats(Queue<WheelNumber> stats) {
		lastTenPnl.removeAll();
		
		Queue<WheelNumber> reversedStats = new LinkedList<>(stats);
	    Collections.reverse((List<?>) reversedStats);
		
		for(WheelNumber w : reversedStats) {
			JButton bw = new JButton(w.getValue()+"");
			bw.setBackground(Colors.getGray().brighter());
			bw.setForeground(w.getColor());
			lastTenPnl.add(bw);
		}
	}
}

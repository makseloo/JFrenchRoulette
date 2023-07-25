package it.unibs.pajc.server;


import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;


import it.unibs.pajc.core.*;

public class PnlCountdown extends PnlBase {
	
	 private JLabel timerLabel;

	/**
	 * Create the panel.
	 */
	public PnlCountdown() {
		super();
		initialize();
	}
	
	private void initialize() {
		
		setLayout(new GridBagLayout());
		
		timerLabel = new JLabel("0");
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		GridBagConstraints gbc_timerLabel = new GridBagConstraints();
		gbc_timerLabel.gridx = 0;
		gbc_timerLabel.gridy = 0;
		
		add(timerLabel, gbc_timerLabel);
	}
	
	 public void updateCountdown(int seconds) {
		 timerLabel.setText(String.valueOf(seconds));
		 repaint();
	    }
}

package it.unibs.pajc.server;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PnlStatitics extends JPanel {

	/**
	 * Create the panel.
	 */
	public PnlStatitics() {
		super();
		initialize();
	}

	private void initialize() {
		
		setLayout(new GridBagLayout());
		
		
		GridBagConstraints gbc_last500 = new GridBagConstraints();
		gbc_last500.gridx = 0;
		gbc_last500.gridy = 0;
		
	}
}

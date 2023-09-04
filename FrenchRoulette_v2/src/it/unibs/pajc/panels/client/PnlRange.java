package it.unibs.pajc.panels.client;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import it.unibs.pajc.Colors;
import it.unibs.pajc.core.PnlBase;

public class PnlRange extends PnlBase {
	private JLabel rangeLbl;

	public PnlRange(int range) {
		initialize(range);
	}
	
	private void initialize(int range) {
		
		JButton plusBtn = createButton("+", Colors.getGray());
		JButton minBtn = createButton("-", Colors.getGray());
		rangeLbl = new JLabel(range+"");
		
		add(plusBtn);
		add(rangeLbl);
		add(minBtn);
		
		
	}

	public void updateRange(int range) {
		this.rangeLbl.setText(range+"");
		
	}

}

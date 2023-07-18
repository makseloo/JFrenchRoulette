package it.unibs.pajc;

import javax.swing.JPanel;


import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import it.unibs.pajc.core.*;

public class PnlBetBoard extends PnlBase {
	
	
	public PnlBetBoard() {
		super();
	}
	
	public PnlBetBoard(List<WheelNumber> numbers) {//è giusti dal punto di vista mvc?
		initialize(numbers);
	}
	private void initialize(List<WheelNumber> numbers) {//è giusti dal punto di vista mvc?
		
		setLayout(new GridBagLayout());
		
		//pulsante dello ZERO
		GridBagConstraints gbc_zeroPnl = new GridBagConstraints();
		gbc_zeroPnl.gridx = 0;
		gbc_zeroPnl.gridy = 0;
		gbc_zeroPnl.fill = GridBagConstraints.VERTICAL;
		
		this.add(createButton("0", new Color(0,128,0)), gbc_zeroPnl);//this si riferisce a questo pannello
		
		
		//pulsanti dal 1 al 36
		JPanel numbersPnl = new JPanel(new GridLayout(3, 12));
		GridBagConstraints gbc_numberPnl = new GridBagConstraints();
		gbc_numberPnl.gridx = 1;
		gbc_numberPnl.gridy = 0;
		
		//prendo i numeri dal model
		for(WheelNumber n : numbers) {
			numbersPnl.add(createButton(n.getValue()+"", n.getColor()));
		}
		
		this.add(numbersPnl,gbc_numberPnl);
		
	}

}

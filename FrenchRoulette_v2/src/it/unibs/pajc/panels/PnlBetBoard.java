package it.unibs.pajc.panels;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import it.unibs.pajc.Colors;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
import it.unibs.pajc.core.*;

public class PnlBetBoard extends PnlBase {
	
	private List<JButton> numberButtons = new ArrayList<>();
	
	private JPanel numbersPnl;
	
	public PnlBetBoard() {
		super();
	}
	
	public PnlBetBoard(List<WheelNumber> numbers, List<String> otherStats) {//è giusti dal punto di vista mvc?
		initialize(numbers, otherStats);
	}
	private void initialize(List<WheelNumber> numbers, List<String> otherStats) {//è giusti dal punto di vista mvc?
		
		setLayout(new GridBagLayout());
		
		//pulsante dello ZERO
		GridBagConstraints gbc_zeroPnl = new GridBagConstraints();
		gbc_zeroPnl.gridx = 0;
		gbc_zeroPnl.gridy = 0;
		gbc_zeroPnl.fill = GridBagConstraints.VERTICAL;
		
		this.add(createButton("0", new Color(0,128,0)), gbc_zeroPnl);//this si riferisce a questo pannello
		
		
		//pulsanti dal 1 al 36
		numbersPnl = new JPanel(new GridLayout(3, 12));
		GridBagConstraints gbc_numberPnl = new GridBagConstraints();
		gbc_numberPnl.gridx = 1;
		gbc_numberPnl.gridy = 0;
		
		//prendo i numeri dal model
		for(WheelNumber n : numbers) {
			JButton numberBtn = createButton(n.getValue()+"", n.getColor());
			numberButtons.add(numberBtn);
			numbersPnl.add(numberBtn);
		}
		
		
		this.add(numbersPnl,gbc_numberPnl);
		
		//pannello dozzine
		JPanel dozenPnl = new JPanel(new GridLayout(1, 3));
		GridBagConstraints gbc_dozenPnl = new GridBagConstraints();
		gbc_dozenPnl.gridx = 1;
		gbc_dozenPnl.gridy = 1;
		gbc_dozenPnl.fill = GridBagConstraints.HORIZONTAL;
		
		for(int i = 1; i < 4; i++) {
			//ci piace come soluzione?
			dozenPnl.add(createButton(i + "°:12", Colors.getGray()));
		}
		this.add(dozenPnl, gbc_dozenPnl);
		
		//pannello righe
		JPanel rowPnl = new JPanel(new GridLayout(3, 1));
		GridBagConstraints gbc_rowPnl = new GridBagConstraints();
		gbc_rowPnl.gridx = 2;
		gbc_rowPnl.gridy = 0;
		
		for(int i = 1; i < 4; i++) {
			rowPnl.add(createButton(i + "° row", Colors.getGray()));
		}
		this.add(rowPnl, gbc_rowPnl);
		
		//pannello altri
		JPanel othersPnl = new JPanel(new GridLayout(1, 6));
		GridBagConstraints gbc_othersPnl = new GridBagConstraints();
		gbc_othersPnl.gridx = 1;
		gbc_othersPnl.gridy = 2;
		gbc_othersPnl.fill = GridBagConstraints.HORIZONTAL;
		
		for(String s : otherStats) {
			othersPnl.add(createButton(s, Colors.getGray()));
		}
		this.add(othersPnl, gbc_othersPnl);
	}

}






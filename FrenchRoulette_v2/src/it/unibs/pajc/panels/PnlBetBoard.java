package it.unibs.pajc.panels;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import it.unibs.pajc.Colors;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.core.*;

public class PnlBetBoard extends PnlBase {
	
	private List<JButton> numberButtons = new ArrayList<>();
	
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
		JPanel numbersPnl = new JPanel(new GridLayout(3, 12));
		GridBagConstraints gbc_numberPnl = new GridBagConstraints();
		gbc_numberPnl.gridx = 1;
		gbc_numberPnl.gridy = 0;
		
		//prendo i numeri dal model
		for(WheelNumber n : numbers) {
			JButton numberBtn = createButton(n.getValue()+"", n.getColor());
			numbersPnl.add(numberBtn);
			numberButtons.add(numberBtn);
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
			List<Integer> dozenNumbers = new ArrayList<>();
		    switch (i) {
		        case 1:
		            dozenNumbers = WheelNumber.getDozen1();
		            break;
		        case 2:
		            dozenNumbers = WheelNumber.getDozen2();
		            break;
		        case 3:
		            dozenNumbers = WheelNumber.getDozen3();
		            break;
		        default:
		            // Handle the case where i is not in the range [1, 3]
		            break;
		    }
		    dozenPnl.add(createTrigger(i + "°:12", Colors.getGray(), numberButtons, dozenNumbers));
		}
		this.add(dozenPnl, gbc_dozenPnl);
		
		//pannello righe
		JPanel rowPnl = new JPanel(new GridLayout(3, 1));
		GridBagConstraints gbc_rowPnl = new GridBagConstraints();
		gbc_rowPnl.gridx = 2;
		gbc_rowPnl.gridy = 0;
		
		for(int i = 1; i < 4; i++) {
			List<Integer> colNumbers = new ArrayList<>();
		    switch (i) {
		        case 3:
		        	colNumbers = WheelNumber.getCol1();
		            break;
		        case 2:
		        	colNumbers = WheelNumber.getCol2();
		            break;
		        case 1:
		        	colNumbers = WheelNumber.getCol3();
		            break;
		        default:
		            // Handle the case where i is not in the range [1, 3]
		            break;
		    }
		    rowPnl.add(createTrigger(i + "° row", Colors.getGray(), numberButtons, colNumbers));
		}
		this.add(rowPnl, gbc_rowPnl);
		
		//pannello altri
		JPanel othersPnl = new JPanel(new GridLayout(1, 6));
		GridBagConstraints gbc_othersPnl = new GridBagConstraints();
		gbc_othersPnl.gridx = 1;
		gbc_othersPnl.gridy = 2;
		gbc_othersPnl.fill = GridBagConstraints.HORIZONTAL;
		
		for(String s : otherStats) {
			List<Integer> otherNumbers = new ArrayList<>();
		    switch (s) {
		        case "1-18":
		        	otherNumbers = WheelNumber.getEight();
		            break;
		        case "19-36":
		        	otherNumbers = WheelNumber.getSix();
		            break;
		        case "EVEN":
		        	otherNumbers = WheelNumber.getEven();
		            break;
		        case "ODD":
		        	otherNumbers = WheelNumber.getOdd();
		            break;
		        case "BLACK":
		        	otherNumbers = WheelNumber.getBlackNums();
		            break;
		        case "RED":
		        	otherNumbers = WheelNumber.getRedNums();
		            break;
		        default:
		            // Handle the case where i is not in the range [1, 3]
		            break;
		    }
			othersPnl.add(createTrigger(s, Colors.getGray(),numberButtons,otherNumbers));
		}
		this.add(othersPnl, gbc_othersPnl);
	}
	

}

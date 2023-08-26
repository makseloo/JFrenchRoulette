package it.unibs.pajc.panels;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.awt.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
	
	private HashMap<Integer, JButton> numberButtons = new HashMap<>();
	private HashMap<String, JButton> ohtersButtons = new HashMap<>();
	
	private JPanel numbersPnl;
	private JPanel circlesPanel;
	private ImageIcon icon;
	private ImageIcon resizedIcon;
	private List<WheelNumber> numbers;
	private List<String> otherStats;
	private List<String> dozCols;
	
	public PnlBetBoard() {
		super();
	}
	
	public PnlBetBoard(List<WheelNumber> numbers, List<String> otherStats, List<String> dozCols) {//è giusti dal punto di vista mvc?
		this.numbers = new ArrayList<>(numbers);
		this.otherStats = new ArrayList<>(otherStats);
		this.dozCols = new ArrayList<>(dozCols);
		initialize();
	}
	private void initialize() {
		icon = new ImageIcon(getClass().getResource("/it/unibs/pajc/icons/wheel.png"));
		Image image = icon.getImage();
		Image resizedImage = image.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
		resizedIcon = new ImageIcon(resizedImage);
		
		setLayout(new GridBagLayout());
		
		//pulsante dello ZERO
		GridBagConstraints gbc_zeroPnl = new GridBagConstraints();
		gbc_zeroPnl.gridx = 0;
		gbc_zeroPnl.gridy = 0;
		gbc_zeroPnl.fill = GridBagConstraints.VERTICAL;
		JButton zeroBtn = createButton("0", Colors.getGreen());
		numberButtons.put(0,zeroBtn);
		this.add(zeroBtn, gbc_zeroPnl);//this si riferisce a questo pannello
		
		
		//pulsanti dal 1 al 36
		numbersPnl = new JPanel(new GridLayout(3, 12));
		GridBagConstraints gbc_numberPnl = new GridBagConstraints();
		gbc_numberPnl.gridx = 1;
		gbc_numberPnl.gridy = 0;
		
		//prendo i numeri dal model
		for(WheelNumber n : numbers) {
			JButton numberBtn = createButton(n.getValue()+"", n.getColor());
			numberButtons.put(n.getValue(),numberBtn);
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
			JButton dozenBtn = createButton(i + "°:12", Colors.getGray());
			ohtersButtons.put(i + "°:12",dozenBtn);
			dozenPnl.add(dozenBtn);
			
		}
		this.add(dozenPnl, gbc_dozenPnl);
		
		//pannello righe
		JPanel rowPnl = new JPanel(new GridLayout(3, 1));
		GridBagConstraints gbc_rowPnl = new GridBagConstraints();
		gbc_rowPnl.gridx = 2;
		gbc_rowPnl.gridy = 0;
		
		for(int i = 1; i < 4; i++) {
			JButton rowBtn = createButton(i + "° row", Colors.getGray());
			ohtersButtons.put(i + "° row",rowBtn);
			rowPnl.add(rowBtn);
		}
		this.add(rowPnl, gbc_rowPnl);
		
		//pannello altri
		JPanel othersPnl = new JPanel(new GridLayout(1, 6));
		GridBagConstraints gbc_othersPnl = new GridBagConstraints();
		gbc_othersPnl.gridx = 1;
		gbc_othersPnl.gridy = 2;
		gbc_othersPnl.fill = GridBagConstraints.HORIZONTAL;
		
		for(String s : otherStats) {
			JButton otherBtn = createButton(s, Colors.getGray());
			ohtersButtons.put(s,otherBtn);
			othersPnl.add(otherBtn);
		}
		this.add(othersPnl, gbc_othersPnl);
		
	}


	public void updateNumBetBoard(List<WheelNumber> numBets) {
		for (WheelNumber w : numBets) {
	        if (numberButtons.containsKey(w.getValue())) {
	        	numberButtons.get(w.getValue()).setText("");
	            numberButtons.get(w.getValue()).setIcon(resizedIcon);
	        }
	    }
		
	}
	public void updateZoneBetBoard(List<Zone> zoneBets) {		
		for (Zone z : zoneBets) {
	        if (ohtersButtons.containsKey(z.getZoneName())) {
	        	ohtersButtons.get(z.getZoneName()).setText("");
	        	ohtersButtons.get(z.getZoneName()).setIcon(resizedIcon);
	        }
	    }
	}
	public void resetBoard() {
		for (WheelNumber w : numbers) {
	        if (numberButtons.containsKey(w.getValue())) {
	        	numberButtons.get(w.getValue()).setText(w.getValue()+"");
	            numberButtons.get(w.getValue()).setIcon(null);
	        }
	    }
		
		for (String s : otherStats) {
			System.out.print(s);
	        if (ohtersButtons.containsKey(s)) {
	        	ohtersButtons.get(s).setText(s);
	        	ohtersButtons.get(s).setIcon(null);
	        }
	    }
		for (String dc : dozCols) {
			System.out.print(dc);
	        if (ohtersButtons.containsKey(dc)) {
	        	ohtersButtons.get(dc).setText(dc);
	        	ohtersButtons.get(dc).setIcon(null);
	        }
	    }
		
		
	}
}








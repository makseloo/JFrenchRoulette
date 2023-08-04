package it.unibs.pajc.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibs.pajc.Colors;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.core.PnlBase;

public class PnlZones extends PnlBase {
	//troppa logica che non riguarda la view?
	private List<JButton> numberButtons = new ArrayList<>();
	
	public PnlZones(List<WheelNumber> numbers) {
		initialize(numbers);
	}

	private void initialize(List<WheelNumber> numbers) {
		
		setLayout(null); // Set the layout to null (absolute layout)

        int buttonWidth = 50;
        int buttonHeight = 25;
        int panelWidth = buttonWidth * 13;
        int panelHeight = buttonHeight*6;

        setPreferredSize(new Dimension(panelWidth, panelHeight));

        int x = 0;
        int y = 0;

        // Create and add the buttons to the panel with custom bounds
        for(WheelNumber w : numbers) {
        	if(WheelNumber.getUp().contains(w.getValue())) {
        		JButton button = createButton(w.getValue()+"", w.getColor());
        		add(button);
                numberButtons.add(button);
                x += buttonWidth;
        	}
        	//
        }
        /*
        for (int i = 0; i < 13; i++) {
            JButton button = new JButton(String.valueOf(i + 1));
            button.setBounds(x, y, buttonWidth, buttonHeight);
            add(button);
            numberButtons.add(button);

            x += buttonWidth;
        }
        */
        x = 0;
        y = buttonHeight*5 ;
        for (int i = 0; i < 13; i++) {
            JButton button = new JButton(String.valueOf(i + 1));
            button.setBounds(x, y, buttonWidth, buttonHeight);
            add(button);
            numberButtons.add(button);

            x += buttonWidth;
        }
        x = 0;
        y = buttonHeight;
        for (int i = 0; i < 4; i++) {
            JButton button = new JButton(String.valueOf(i + 1));
            button.setBounds(x, y, buttonWidth, buttonHeight);
            add(button);
            numberButtons.add(button);

            y += buttonHeight;
        }
        x = buttonWidth*12;
        y = buttonHeight;
        buttonHeight = 33;
        for (int i = 0; i < 3; i++) {
            JButton button = new JButton(String.valueOf(i + 1));
            button.setBounds(x, y, buttonWidth, buttonHeight);
            add(button);
            numberButtons.add(button);

            y += buttonHeight;
        }
        
		
		/*
		JButton btnTier = new JButton("TIER");
		btnTier.setBackground(Colors.getGray());
		btnTier.setForeground(Color.white);
		GridBagConstraints gbc_btnTier = new GridBagConstraints();
		gbc_btnTier.insets = new Insets(0, 0, 0, 5);
		gbc_btnTier.gridx = 0;
		gbc_btnTier.gridy = 0;

		add(btnTier, gbc_btnTier);
		
		
		//numeri del TIER
		JPanel tierNumbersPnl = new JPanel(new GridLayout(1, 12));
		GridBagConstraints gbc_tierNumbersPnl = new GridBagConstraints();
		gbc_tierNumbersPnl.gridx = 0;
		gbc_tierNumbersPnl.gridy = 1;
		//prendo i numeri dal model
		for(WheelNumber n : numbers) {
			if(n.getZone().equals("tier")) {
				JButton tierNumberBtn = createButton(n.getValue()+"", n.getColor());
				tierNumberBtn.setToolTipText(n.getValue()+"");
				tierNumbersPnl.add(tierNumberBtn);
				numberButtons.add(tierNumberBtn);
			}
			
		}
		
		add(tierNumbersPnl,gbc_tierNumbersPnl);
		
		
		//VOISINS
		JButton btnOrph = new JButton("VOISINS");
		btnOrph.setBackground(Colors.getGray());
		btnOrph.setForeground(Color.white);
		GridBagConstraints gbc_btnOrph = new GridBagConstraints();
		gbc_btnOrph.insets = new Insets(0, 0, 0, 5);
		gbc_btnOrph.gridx = 0;
		gbc_btnOrph.gridy = 2;

		add(btnOrph, gbc_btnOrph);
		
		
		
		JPanel orphNumbersPnl = new JPanel(new GridLayout(1, 12));
		GridBagConstraints gbc_orphNumbersPnl = new GridBagConstraints();
		gbc_orphNumbersPnl.gridx = 0;
		gbc_orphNumbersPnl.gridy = 3;
		//prendo i numeri dal model
		for(WheelNumber n : numbers) {
			if(n.getZone().equals("orphelins")) {
				JButton orphNumberBtn = createButton(n.getValue()+"", n.getColor());
				orphNumberBtn.setToolTipText(n.getValue()+"");
				orphNumbersPnl.add(orphNumberBtn);
				numberButtons.add(orphNumberBtn);
			}
			
		}
		
		add(orphNumbersPnl,gbc_orphNumbersPnl);
		
		
		//ORPHELINS
				JButton btnVois = new JButton("ORPHELINS");
				btnVois.setBackground(Colors.getGray());
				btnVois.setForeground(Color.white);
				GridBagConstraints gbc_btnVois = new GridBagConstraints();
				gbc_btnVois.insets = new Insets(0, 0, 0, 5);
				gbc_btnVois.gridx = 0;
				gbc_btnVois.gridy = 4;

				add(btnVois, gbc_btnVois);
				
				
				
				JPanel voisNumbersPnl = new JPanel(new GridLayout(1, 12));
				GridBagConstraints gbc_voisNumbersPnl = new GridBagConstraints();
				gbc_voisNumbersPnl.gridx = 0;
				gbc_voisNumbersPnl.gridy = 5;
				//prendo i numeri dal model
				for(WheelNumber n : numbers) {
					if(n.getZone().equals("voisins")) {
						JButton voisNumberBtn = createButton(n.getValue()+"", n.getColor());
						voisNumberBtn.setToolTipText(n.getValue()+"");
						voisNumbersPnl.add(voisNumberBtn);
						numberButtons.add(voisNumberBtn);
					}
					
				}
				
				add(voisNumbersPnl,gbc_voisNumbersPnl);
				*/
	            
	}

}

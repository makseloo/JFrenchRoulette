package it.unibs.pajc.panels;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import it.unibs.pajc.WheelNumber;

public class PnlStatisticsAdvanced extends JPanel {
	public PnlStatisticsAdvanced() {
	}

	/**
	 * Create the panel.
	 */
    public static JPanel createInternalPanel(List<WheelNumber> statistiche) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc_last12 = new GridBagConstraints();
        gbc_last12.gridx = 0;
        gbc_last12.gridy = 0;
		
        JLabel last12Lbl = new JLabel("0 1 2 3 4 5 6 7 8 9 10 11");
        panel.add(last12Lbl, gbc_last12);
        
        JPanel dozensStatPnl = new JPanel(new GridLayout(1, 3));
        GridBagConstraints gbc_dozensStat = new GridBagConstraints();
        gbc_dozensStat.gridx = 0;
        gbc_dozensStat.gridy = 1;
        gbc_dozensStat.fill = GridBagConstraints.HORIZONTAL;
        
        for(int i = 1; i < 4; i++) {

        	JLabel dozenStatLbl = new JLabel("cIAO");
        	dozensStatPnl.add(dozenStatLbl);
        }
        panel.add(dozensStatPnl,gbc_dozensStat);
        
        
        JPanel dozensPnl = new JPanel(new GridLayout(1, 3));
        GridBagConstraints gbc_dozensPnl = new GridBagConstraints();
        gbc_dozensPnl.gridx = 0;
        gbc_dozensPnl.gridy = 2;

        gbc_dozensPnl.fill = GridBagConstraints.HORIZONTAL;
        
        
        for(int i = 1; i < 4; i++) {

        	JLabel dozenLbl = new JLabel(""+i);
        	dozensPnl.add(dozenLbl);
        }
        panel.add(dozensPnl,gbc_dozensPnl);

        return panel;
    }
    
    public static JScrollPane createlastPanel(List<WheelNumber> stats) {
    	JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(20,25));
        for(WheelNumber w : stats) {
        	JLabel num = new JLabel(w.getValue()+"");
        	num.setForeground(w.getColor());
        	Border coloredBorder = BorderFactory.createLineBorder(w.getColor(),1);
        	num.setBorder(coloredBorder);
        	num.setHorizontalAlignment(JLabel.CENTER);
        	panel.add(num);
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    	
    }

}

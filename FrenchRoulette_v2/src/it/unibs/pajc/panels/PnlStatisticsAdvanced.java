package it.unibs.pajc.panels;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import it.unibs.pajc.Colors;
import it.unibs.pajc.WheelNumber;

public class PnlStatisticsAdvanced extends JPanel {
	public PnlStatisticsAdvanced() {
	}

	/**
	 * Create the panel.
	 */
    public static JPanel createInternalPanel(Map<String, Integer> stats) {
    	
    	Set<String> keys = stats.keySet();
    	
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc_dozens = new GridBagConstraints();
        gbc_dozens.fill = GridBagConstraints.HORIZONTAL;
        gbc_dozens.weightx = 1.0;
        JLabel labelsoprazero = new JLabel(stats.get("zeros")+"");
    	JLabel labelsottozero = new JLabel("0");
    	Border coloredBorder = BorderFactory.createLineBorder(Colors.getGray(), 1);
    	labelsottozero.setBorder(coloredBorder);
    	labelsoprazero.setHorizontalAlignment(JLabel.CENTER);
    	labelsottozero.setHorizontalAlignment(JLabel.CENTER);
    	gbc_dozens.gridx = 0;
    	gbc_dozens.gridy = 0;
    	panel.add(labelsoprazero,gbc_dozens);
    	gbc_dozens.gridx = 0;
    	gbc_dozens.gridy = 1;
    	panel.add(labelsottozero,gbc_dozens);
        for(int i = 1; i < 4; i++) {
        	int j = 0;
        	JLabel labelsopra = new JLabel(stats.get(i + " DOZEN")+"");
        	JLabel labelsotto = new JLabel(i + " DOZEN ");
        	coloredBorder = BorderFactory.createLineBorder(Colors.getGray(), 1);
        	labelsotto.setBorder(coloredBorder);
        	labelsopra.setHorizontalAlignment(JLabel.CENTER);
        	labelsotto.setHorizontalAlignment(JLabel.CENTER);
        	gbc_dozens.gridx = i;
        	gbc_dozens.gridy = j++;
        	panel.add(labelsopra, gbc_dozens);
        	gbc_dozens.gridx = i;
        	gbc_dozens.gridy = j--;
        	panel.add(labelsotto,gbc_dozens);
        }
        
        GridBagConstraints gbc_cols = new GridBagConstraints();
        gbc_cols.fill = GridBagConstraints.HORIZONTAL;
        gbc_cols.weightx = 1.0;
        for(int i = 0; i < 3; i++) {
        	int j = 2;
        	JLabel labelsopra = new JLabel(stats.get((i+1) + " COLUMN")+"");
        	JLabel labelsotto = new JLabel((i+1) + " COLUMN ");
        	coloredBorder = BorderFactory.createLineBorder(Colors.getGray(), 1);
        	labelsotto.setBorder(coloredBorder);
        	labelsopra.setHorizontalAlignment(JLabel.CENTER);
        	labelsotto.setHorizontalAlignment(JLabel.CENTER);
        	gbc_cols.gridx = i;
        	gbc_cols.gridy = j++;
        	panel.add(labelsopra, gbc_cols);
        	gbc_cols.gridx = i;
        	gbc_cols.gridy = j--;
        	
        	panel.add(labelsotto,gbc_cols);
        }
        GridBagConstraints gbc_zones = new GridBagConstraints();
        gbc_zones.fill = GridBagConstraints.HORIZONTAL;
        gbc_zones.weightx = 1.0;
        int i = 0;
        for(String s : WheelNumber.getZones()) {
        	int j = 4;
        	JLabel labelsopra = new JLabel(stats.get(s)+"");
        	JLabel labelsotto = new JLabel(s);
        	labelsopra.setHorizontalAlignment(JLabel.CENTER);
        	labelsotto.setHorizontalAlignment(JLabel.CENTER);
        	gbc_zones.gridx = i;
        	gbc_zones.gridy = j++;
        	
        	panel.add(labelsopra, gbc_zones);
        	gbc_zones.gridx = i;
        	gbc_zones.gridy = j--;
        	panel.add(labelsotto,gbc_zones);
        	i++;
        }
        int k = 0;
        for(String s : WheelNumber.getOthersStat()) {
        	int j = 6;
        	JLabel labelsopra = new JLabel(stats.get(s)+"");
        	JLabel labelsotto = new JLabel(s);
        	labelsopra.setHorizontalAlignment(JLabel.CENTER);
        	labelsotto.setHorizontalAlignment(JLabel.CENTER);
        	gbc_zones.gridx = k;
        	gbc_zones.gridy = j++;
        	
        	panel.add(labelsopra, gbc_zones);
        	gbc_zones.gridx = k;
        	gbc_zones.gridy = j--;
        	panel.add(labelsotto,gbc_zones);
        	k++;
        }
        /*
        GridBagConstraints gbc_last12 = new GridBagConstraints();
        gbc_last12.gridx = 0;
        gbc_last12.gridy = 0;
		
        JLabel last12Lbl = new JLabel("0 1 2 3 4 5 6 7 8 9 10 11");
        last12Lbl.setText(TOOL_TIP_TEXT_KEY);
        panel.add(last12Lbl, gbc_last12);

*/
        return panel;
    }
    
    public static JPanel createlastPanel(List<WheelNumber> stats) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(20, 25)); // Set GridLayout for a 20x25 grid

        for (WheelNumber w : stats) {
            JLabel num = new JLabel(w.getValue() + "");
            num.setForeground(w.getColor());
            Border coloredBorder = BorderFactory.createLineBorder(w.getColor(), 1);
            num.setBorder(coloredBorder);
            num.setHorizontalAlignment(JLabel.CENTER);
            panel.add(num);
        }

        // Create the JScrollPane and set the preferred size
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return panel;
    }


}

package it.unibs.pajc.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;


import it.unibs.pajc.WheelNumber;

import javax.swing.JButton;


import java.awt.Insets;
import java.awt.event.ActionListener;

import java.util.List;
import java.awt.event.ActionEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PnlStatitics extends JPanel {

	private JPanel internalPanel;
	private JPanel lastPanel;
	private List<WheelNumber> last500;
	private Map<String, Integer> stats;
	/**
	 * Create the panel.
	 */
	public PnlStatitics() {
		super();
		initialize();
		
	}

	private void initialize() {
		
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(400, 400));
		JButton btnHotCold = new JButton("HOT/COLD");
		GridBagConstraints gbc_btnHotCold = new GridBagConstraints();
		gbc_btnHotCold.insets = new Insets(0, 0, 0, 5);
		gbc_btnHotCold.gridx = 0;
		gbc_btnHotCold.gridy = 0;
		gbc_btnHotCold.fill = GridBagConstraints.HORIZONTAL;
		
		add(btnHotCold, gbc_btnHotCold);
		
		JButton btnAdvanced = new JButton("AVANZATA");

		GridBagConstraints gbc_btnAdvanced = new GridBagConstraints();
		gbc_btnAdvanced.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdvanced.gridx = 1;
		gbc_btnAdvanced.gridy = 0;
		gbc_btnAdvanced.fill = GridBagConstraints.HORIZONTAL;
		add(btnAdvanced, gbc_btnAdvanced);
		
		JButton btnLast500 = new JButton("LAST 500");
		
		GridBagConstraints gbc_btnLast500 = new GridBagConstraints();
		gbc_btnLast500.gridx = 2;
		gbc_btnLast500.gridy = 0;
		gbc_btnLast500.fill = GridBagConstraints.HORIZONTAL;
		add(btnLast500, gbc_btnLast500);
		
			
		//addLastPanel();
		//creare l'internal frame o meno da un'altra parte ha i suoi pro e contro
		
		btnAdvanced.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (internalPanel == null || !internalPanel.isVisible()) {
					hideLastPanel();
					internalPanel = PnlStatisticsAdvanced.createInternalPanel(stats);

                    GridBagConstraints gbc_windowAdvanced = new GridBagConstraints();
                    gbc_windowAdvanced.gridx = 0;
                    gbc_windowAdvanced.gridy = 1;
                    gbc_windowAdvanced.gridwidth = 3;
                    gbc_windowAdvanced.fill = GridBagConstraints.HORIZONTAL;
                    add(internalPanel, gbc_windowAdvanced);

                    validate(); // Re-layout the panel to show the internal frame
                }
            }
		});
		
		btnLast500.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addLastPanel();
			}
		});
		
	}

	public void updateLast500(List<WheelNumber> last500) {
		this.last500 = new LinkedList<WheelNumber>(last500);
		repaint();
		
	}

	
	public void updateStats(Map<String, Integer> stats) {
		this.stats = stats;
		repaint();	
	}
	
    private void hideInternalPanel() {
        if (internalPanel != null && internalPanel.isVisible()) {
            internalPanel.setVisible(false);
            remove(internalPanel);
            internalPanel = null;
        }
    }

    private void hideLastPanel() {
        if (lastPanel != null && lastPanel.isVisible()) {
            lastPanel.setVisible(false);
            remove(lastPanel);
            lastPanel = null;
        }
    }
    
    private void addLastPanel() {
    	if (lastPanel == null || !lastPanel.isVisible()) {
    		
			hideInternalPanel();
			lastPanel = PnlStatisticsAdvanced.createlastPanel(last500);

            GridBagConstraints gbc_windowAdvanced = new GridBagConstraints();
            gbc_windowAdvanced.gridx = 0;
            gbc_windowAdvanced.gridy = 1;
            gbc_windowAdvanced.gridwidth = 3;
            
            gbc_windowAdvanced.fill = GridBagConstraints.BOTH;
            add(lastPanel, gbc_windowAdvanced);

            validate(); // Re-layout the panel to show the internal frame
    }
    	}


}

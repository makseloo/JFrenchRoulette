package it.unibs.pajc.panels;

import java.awt.GridLayout;

import it.unibs.pajc.Colors;
import it.unibs.pajc.Fiche;
import it.unibs.pajc.core.*;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;

public class PnlFiches extends PnlBase {
	
	ImageIcon icon;
	
	public PnlFiches() {
		super();
	}
	
	public PnlFiches(List<Fiche> fiches) {
		initialize(fiches);
	}

	private void initialize(List<Fiche> fiches) {
		
		
		setLayout(new GridLayout(1,8));
		setPreferredSize(new Dimension(400,40));
		for(Fiche f : fiches)
		{
			this.add(createButton(f.getValue()+"" ,new Color(60,60,60)));
		}
	}
	
    public void glowFiche(double ficheValue) {
        for (int i = 0; i < getComponentCount(); i++) {
            JButton button = (JButton) getComponent(i);
            // Assuming the text of the button is the fiche value represented as a string
            String buttonText = button.getText();
            double buttonValue = Double.parseDouble(buttonText);

            // Check if the button represents the ficheValue, and apply the glow effect
            if (buttonValue == ficheValue) {
                // Add your glowing effect here. You can change the button background color, border, etc.
                button.setBackground(Colors.getGray().brighter());
            } else {
                // Reset the appearance of other buttons (remove the glow effect)
                button.setBackground(new Color(60, 60, 60));
            }
        }
    }
	

}

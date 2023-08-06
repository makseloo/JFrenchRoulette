package it.unibs.pajc.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

import it.unibs.pajc.Colors;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.core.PnlBase;

public class PnlZones extends PnlBase {
    private List<JButton> numberButtons = new ArrayList<>();
    private int tierx1, tiery1, tierx2, tiery2;
    private int orhpx1, orhpy1, orhpx2, orhpy2;
    private int voisex1, voisey1, voisex2, voisey2;
    private int zerx1, zery1, zerx2, zery2;

    public PnlZones(List<WheelNumber> numbers, int range) {
        initialize(numbers, range);
    }

    private void initialize(List<WheelNumber> numbers, int range) {

        setLayout(null); // Set the layout to null (absolute layout)
        List<String> zeroZone = new ArrayList<>();
		zeroZone.add("voisins");
		
		WheelNumber zero = new WheelNumber(0, zeroZone, Colors.getGreen());
        numbers.add(zero);
        
        int buttonWidth = 50;
        int buttonHeight = 16;
        int panelWidth = buttonWidth * 13;
        int panelHeight = buttonHeight * 8;

        setPreferredSize(new Dimension(panelWidth, panelHeight));

        int x = 0;
        int y = 0;

        // Add buttons for up section
        x = addButtons(WheelNumber.getUp(), numbers, x, y, buttonWidth, buttonHeight,false);

        // Add buttons for down section
        y = buttonHeight * 7;
        x = addButtons(WheelNumber.getDowns(), numbers, x, y, buttonWidth, buttonHeight,false);

        // Add buttons for left section
        x = 0;
        y = buttonHeight;
        x = addButtons(WheelNumber.getLeft(), numbers, x, y, buttonWidth, buttonHeight,true);

        // Add buttons for right section
        y = buttonHeight;
        x = buttonWidth * 12;
        buttonHeight = 20;
        addButtons(WheelNumber.getRight(), numbers, x, y, buttonWidth, buttonHeight,true);

        buttonHeight = 16;
        //tier line
        tierx1 = buttonWidth*2;
        tiery1 = buttonHeight;
        tierx2 = buttonWidth*4;
        tiery2 = buttonHeight*7;
        
        orhpx1 = buttonWidth*7;
        orhpy1 = buttonHeight;
        orhpx2 = orhpx1; 
        orhpy2 = buttonHeight*7;

        JButton tierBtn = createButton("TIER",Colors.getGray());
        tierBtn.setBounds(buttonWidth, buttonHeight*3, 72, 30);
        add(tierBtn);
        
        JButton orphBtn = createButton("ORPHELINS",Colors.getGray());
        orphBtn.setBounds(buttonWidth*4, buttonHeight*3, 105, 30);
        add(orphBtn);
        
        JButton voisBtn = createButton("VOISINS",Colors.getGray());
        voisBtn.setBounds(buttonWidth*8, buttonHeight*3, 85, 30);
        add(voisBtn);
        
        JButton zeroBtn = createButton("0",Colors.getGray());
        zeroBtn.setBounds(buttonWidth*11, buttonHeight*3, 40, 30);
        add(zeroBtn);
        
    }
        
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the line from (lineX1, lineY1) to (lineX2, lineY2)
        g.drawLine(tierx1, tiery1, tierx2, tiery2);
        g.drawLine(orhpx1, orhpy1, orhpx2, orhpy2);
        
    }

    private int addButtons(List<Integer> list, List<WheelNumber> numbers, int x, int y, int buttonWidth, int buttonHeight, boolean leftOrRight) {
        for (int i : list) {
            for (WheelNumber w : numbers) {
                if (w.getValue() == i) {
                    JButton button = createButton(w.getValue() + "", w.getColor());
                    button.setBounds(x, y, buttonWidth, buttonHeight);
                    add(button);
                    numberButtons.add(button);
                    if(!leftOrRight) {
                    	x += buttonWidth;
                    }else {
                    	y += buttonHeight;
                    }
                    
                }
            }
        }
        y += buttonHeight;
        // Reset x-coordinate for the next section
        x = 0;
        return x;
    }
}

package it.unibs.pajc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

import it.unibs.pajc.core.PnlBase;

public class PnlWheel extends PnlBase {

    private ImageIcon icon;
    private List<WheelNumber> wheelNums;

    public PnlWheel(List<WheelNumber> wheelNums) {
        super();
        this.wheelNums = wheelNums;
        initialize(wheelNums);
    }

    private void initialize(List<WheelNumber> wheelNums) {
   
    	setLayout(null);
        setPreferredSize(new Dimension(400, 400));
        icon = new ImageIcon(getClass().getResource("/it/unibs/pajc/icons/wheel.png"));
    	
    	
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRouletteWheel(g);
    }

    private void drawRouletteWheel(Graphics g) {
    	JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, getWidth(), getHeight());
        
    	int width = getWidth();
    	int height = getHeight();
    	int centerX = width / 2;
    	int centerY = height / 2;
    	double radius = Math.min(width, height) *0.5;
    	int numCloves = 37;
    	double anglePerClove = 360.0 / numCloves;
    	
    	Font font = new Font("Arial", Font.BOLD, 12);
    	g.setFont(font);
    	
    	FontMetrics fontMetrics = g.getFontMetrics();
    	int i = 0;
    	for (WheelNumber w : this.wheelNums) {
    	    double startAngle = i * anglePerClove;
    	    i++;
    	    
    	    
    	    
    	    double textAngle = Math.toRadians(startAngle + anglePerClove / 2);
    	    double textX = centerX + Math.cos(textAngle) * radius * 0.8;
    	    double textY = centerY + Math.sin(textAngle) * radius * 0.8;

    	    
    	    
    	    int drawX = (int) Math.round(textX);
    	    int drawY = (int) Math.round(textY);
    	    double rotationAngle = -45.35;
    	    
    	    int rotatedX = (int) (centerX + (drawX - centerX) * Math.cos(rotationAngle) - (drawY - centerY) * Math.sin(rotationAngle));
    	    int rotatedY = (int) (centerY + (drawX - centerX) * Math.sin(rotationAngle) + (drawY - centerY) * Math.cos(rotationAngle));

    	    int moveLeftAmount = 10; // Adjust this value as needed
    	    rotatedX -= moveLeftAmount;
    	    
    	    int moveAmount = 10; // Adjust this value as needed
    	    rotatedY -= moveLeftAmount;
    	    
            // Add button to the panel
            JButton pocketButton = createButton(w.getValue() + "", w.getColor());
            pocketButton.setBounds(rotatedX,rotatedY, 22,22);
            layeredPane.add(pocketButton, JLayeredPane.DEFAULT_LAYER);
               
    	}
    	Graphics2D g2d = (Graphics2D) g.create();
    	int maxIconSize = Math.min(width, height); // Maximum size of the icon based on available space
        int iconWidth = icon.getIconWidth();
        int iconHeight = icon.getIconHeight();
        double scale = Math.min((double) maxIconSize / iconWidth, (double) maxIconSize / iconHeight);
        int scaledWidth = (int) (iconWidth * scale);
        int scaledHeight = (int) (iconHeight * scale);
        int iconX = centerX - scaledWidth / 2;
        int iconY = centerY - scaledHeight / 2;
        
        g2d.drawImage(icon.getImage(), iconX, iconY, scaledWidth, scaledHeight, null);
    	
    }

}

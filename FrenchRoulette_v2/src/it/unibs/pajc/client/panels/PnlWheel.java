package it.unibs.pajc.client.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer; // Import javax.swing.Timer

import javax.swing.ImageIcon;

import it.unibs.pajc.Numbers;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.core.PnlBase;

public class PnlWheel extends PnlBase {

    private ImageIcon icon;
    private List<WheelNumber> wheelNums;

    private int dotIndex = 0;
    private int animationCount = 0;
    private Timer animationTimer;
    
    private int numberLanded;
    public PnlWheel(List<WheelNumber> wheelNums) {
        super();
        this.wheelNums = wheelNums;
        initialize();
        
        animationTimer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				animationCount++;
				repaint();
				//it makes a full spin and then it lands on the chosen number
				if (animationCount >= numberLanded+37) {
					animationTimer.stop();
					fireActionListener(e);
		        }
			}
        });

        animationTimer.start();
    }

    private void initialize() {
   
    	setLayout(null);
        setPreferredSize(new Dimension(400, 400));
        icon = new ImageIcon(getClass().getResource("/it/unibs/pajc/icons/wheel.png"));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        drawRouletteWheel(g);
        int centerX = getWidth() / 2;
	    int centerY = getHeight() / 2;
	    //how far is the dot
	    int radius = 120;
	    //how big is the dot
	    int dotRadius = 10;
	    //divide a circle in 37 cloves and draw a dot in each one
	    if (dotIndex < 37) {
	        double angle = 2 * Math.PI * dotIndex / 37;
	        double startX = centerX + radius * Math.cos(angle);
	        double startY = centerY + radius * Math.sin(angle);

	        // Draw a white dot at the calculated point
	        g.setColor(Color.WHITE);
	        int dotX = (int) startX - dotRadius / 2;
	        int dotY = (int) startY - dotRadius / 2;
	        g.fillOval(dotX, dotY, dotRadius, dotRadius);

	        dotIndex++;
	    } else {
	        dotIndex = 0;
	    }
    }



	private void drawRouletteWheel(Graphics g) {
		int width = getWidth();
    	int height = getHeight();
    	int centerX = width / 2;
    	int centerY = height / 2;
    	
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
	

	public void startAnimation(WheelNumber lastNumber) {
		dotIndex = 0;
		this.numberLanded = Numbers.indexOf(lastNumber);
		animationCount = 0; // Reset animation count
        animationTimer.start();
        }

}
    


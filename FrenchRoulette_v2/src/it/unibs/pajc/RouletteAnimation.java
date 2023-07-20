package it.unibs.pajc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RouletteAnimation extends JPanel {

    private ImageIcon wheelIcon;
    private List<Point2D.Double> pocketPositions;
    private int ballPosition;
    private Timer timer;

    public RouletteAnimation() {
        wheelIcon = new ImageIcon(getClass().getResource("/it/unibs/pajc/icons/wheel.png"));
        pocketPositions = calculatePocketPositions();
        ballPosition = 0; // Starting position
        int timerDelay = 200; // Delay in milliseconds before each animation frame
        timer = new Timer(timerDelay, e -> {
            ballPosition = (ballPosition + 1) % pocketPositions.size(); // Move to the next pocket position
            repaint(); // Trigger repaint to update the animation
        });
        timer.start(); // Start the animation timer
    }

    private List<Point2D.Double> calculatePocketPositions() {
        List<Point2D.Double> positions = new ArrayList<>();
        double radius = 200.0;
        for(int i = 0; i < 37; i++) {
        	double angle = Math.toRadians(i * (360.0 / 37)); // Calculate the angle in radians

            // Calculate the x and y coordinates using trigonometry
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
        	 positions.add(new Point2D.Double(x, y));
        }
        return positions;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the wheel icon
        int maxIconSize = Math.min(width, height); // Maximum size of the icon based on available space
        int iconWidth = wheelIcon.getIconWidth();
        int iconHeight = wheelIcon.getIconHeight();
        double scale = Math.min((double) maxIconSize / iconWidth, (double) maxIconSize / iconHeight);
        int scaledWidth = (int) (iconWidth * scale);
        int scaledHeight = (int) (iconHeight * scale);
        int iconX = centerX - scaledWidth / 2;
        int iconY = centerY - scaledHeight / 2;
        wheelIcon.paintIcon(this, g2d, iconX, iconY);

        // Draw the ball
        double ballSize = 15; // Size of the ball
        Point2D.Double ballPosition = pocketPositions.get(this.ballPosition);
        double ballX = centerX + ballPosition.x * scale - ballSize / 2;
        double ballY = centerY + ballPosition.y * scale - ballSize / 2;
        Ellipse2D.Double ball = new Ellipse2D.Double(ballX, ballY, ballSize, ballSize);
        g2d.setColor(Color.RED);
        g2d.fill(ball);

        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Roulette Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false); // Disable window resizing

        RouletteAnimation animation = new RouletteAnimation();
        frame.add(animation);

        frame.pack();
        frame.setVisible(true);
    }
}

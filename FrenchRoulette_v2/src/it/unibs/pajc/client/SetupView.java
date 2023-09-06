package it.unibs.pajc.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

//ASKING FOR NAME AND BALANCE

public class SetupView {

	public JFrame frame;
	private JTextField nameTxt;
	private JTextField importTxt;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SetupView window = new SetupView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SetupView() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 436, 263);
		frame.getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel nameLbl = new JLabel("Name");
		nameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		panel.add(nameLbl);
		
		nameTxt = new JTextField();
		panel.add(nameTxt);
		nameTxt.setColumns(10);
		
		JLabel importLbl = new JLabel("Balance");
		panel.add(importLbl);
		
		importTxt = new JTextField();
		
		importTxt.addKeyListener(new KeyAdapter() {
	          public void keyPressed(KeyEvent ke) {
	             if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '\b') {
	            	importTxt.setEditable(true);
	             } else {
	            	importTxt.setEditable(false);
	            	System.out.println("* Enter only numeric digits(0-9)");
	             }
	          }
	       });
		
		panel.add(importTxt);
		importTxt.setColumns(10);
		
		JButton connectBtn = new JButton("OK");
		connectBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(connectBtn);
		
	}

	public String getName() {
		return nameTxt.getText();
	}

	public int getBalance() {
		String inputText = importTxt.getText();
	    if (inputText.isEmpty()) {
	        return 0; 
	    } else {
	        try {
	            return Integer.parseInt(inputText);
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input for balance. Using default value.");
	            return 0;
	        }
	    }
	}

}

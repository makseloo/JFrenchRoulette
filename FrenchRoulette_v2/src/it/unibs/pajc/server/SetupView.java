package it.unibs.pajc.server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetupView {

	JFrame frame;
	private JTextField textField;
	private JTextField nameTxt;
	private JTextField importTxt;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the application.
	 */
	public SetupView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 436, 263);
		frame.getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel nameLbl = new JLabel("Nome");
		nameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
		panel.add(nameLbl);
		
		nameTxt = new JTextField();
		panel.add(nameTxt);
		nameTxt.setColumns(10);
		
		JLabel importLbl = new JLabel("Importo");
		panel.add(importLbl);
		
		importTxt = new JTextField();
		panel.add(importTxt);
		importTxt.setColumns(10);
		
		JButton connectBtn = new JButton("Connetti");
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
		return Integer.parseInt(importTxt.getText());
	}

}

package it.unibs.pajc.server;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import it.unibs.pajc.PnlBetBoard;

import javax.swing.JLabel;

public class Server {

	private JFrame frame;
	private JPanel contentPane;

	private ServerModel serverModel;
	private PnlCountdown pnlCountdown;
	private JLabel lblNewLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server window = new Server();
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
	public Server() {
		serverModel = new ServerModel();
		
		serverModel.startTimer();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		frame.setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{1.0};
		gbl_contentPane.columnWeights = new double[]{1.0};
		contentPane.setLayout(gbl_contentPane);
		
		pnlCountdown = new PnlCountdown();//è giusti dal punto di vista mvc?
		GridBagConstraints gbc_pnlBoard = new GridBagConstraints();
		gbc_pnlBoard.insets = new Insets(0, 0, 5, 5);
		gbc_pnlBoard.fill = GridBagConstraints.BOTH;
		gbc_pnlBoard.gridx = 0;
		gbc_pnlBoard.gridy = 0;
		contentPane.add(pnlCountdown, gbc_pnlBoard);
		
		serverModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateView();
            }
        });
		
		frame.pack();

	}
	
    private void updateView() {
        int seconds = serverModel.getSeconds();
        pnlCountdown.updateCountdown(seconds);
    }
    

}

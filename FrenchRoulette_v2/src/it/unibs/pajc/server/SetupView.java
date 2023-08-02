package it.unibs.pajc.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupView extends JFrame {
    private JTextField nameField;
    private JTextField balanceField;

    public SetupView() {
        super("Setup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Balance:"));
        balanceField = new JTextField();
        panel.add(balanceField);

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int balance = Integer.parseInt(balanceField.getText());
                connectToServer(name, balance);
            }
        });

        panel.add(connectButton);

        add(panel, BorderLayout.CENTER);
        pack();
    }

    private void connectToServer(String name, int balance) {
        Client client = new Client(name, balance);
    
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SetupView setupView = new SetupView();
                setupView.setVisible(true);
            }
        });
    }
}

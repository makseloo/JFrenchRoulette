package it.unibs.pajc.server;

import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.unibs.pajc.core.PnlBase;
import java.awt.GridBagConstraints;
import javax.swing.JScrollBar;
import java.awt.Insets;
import javax.swing.JTextPane;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JTextArea;

public class PnlClients extends PnlBase {

    private List<String> connectedClients;
    private JTextArea textArea;


    public PnlClients(List<String> connectedClients) {
        super();
        
        
        initialize();
    }

    private void initialize() {
    	setLayout(new BorderLayout(0, 0));
        
        JLabel clientLbl = new JLabel("Client Connessi");
        clientLbl.setEnabled(true);
        add(clientLbl, BorderLayout.NORTH);
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        for(String s : connectedClients) {
        	textArea.append(s+"\n");
        }
        add(textArea, BorderLayout.CENTER);
    }
    
    public void updateTextArea() {
        textArea.setText("");
        for (String s : connectedClients) {
            textArea.append(s + "\n");
        }
    }


}

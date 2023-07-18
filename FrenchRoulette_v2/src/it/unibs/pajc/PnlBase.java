package it.unibs.pajc;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PnlBase extends JPanel {

	public PnlBase() {
		setLayout(new GridBagLayout());
	}

	private ArrayList<ActionListener> listenerList = new ArrayList<>();

	public void addActionListener(ActionListener l) {
		listenerList.add(l);
	}

	public void removeActionListener(ActionListener l) {
		listenerList.remove(l);
	}

	public void fireActionListener(ActionEvent e) {
		ActionEvent myEvent = new ActionEvent(this,
			ActionEvent.ACTION_PERFORMED,
			e.getActionCommand(),
			e.getWhen(),
			e.getModifiers());

		for (ActionListener l : listenerList) {
			l.actionPerformed(myEvent);
		}
	}
/*
	public JButton createButton(String symbol) {
		JButton btn = new JButton(symbol);
		btn.addActionListener(e -> fireActionListener(e));

		return btn;
	}
	*/
	public JButton createButton(String symbol, Color color) {
		JButton btn = new JButton(symbol);
		Color textColor = new Color(255,255,255);
		btn.setBackground(color);
		btn.setForeground(textColor);
		btn.addActionListener(e -> fireActionListener(e));

		return btn;
	}
	
	// addButton(createButton("0"), 0,0);
}

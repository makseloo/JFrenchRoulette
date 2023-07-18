package it.unibs.pajc;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import java.awt.Color;

public class PnlFiches extends PnlBase {

	public PnlFiches() {
		super();
	}
	
	public PnlFiches(List<Fiche> fiches) {
		initialize(fiches);
	}

	private void initialize(List<Fiche> fiches) {
		setLayout(new GridLayout(1,8));
		
		for(Fiche f : fiches) {
			this.add(createButton(f.getValue()+"" ,new Color(60,60,60)));
		}
		
	}

}

package it.unibs.pajc.panels;

import java.awt.GridLayout;

import it.unibs.pajc.Fiche;
import it.unibs.pajc.core.*;
import java.util.List;

import java.awt.Color;
import java.awt.Dimension;

public class PnlFiches extends PnlBase {

	public PnlFiches() {
		super();
	}
	
	public PnlFiches(List<Fiche> fiches) {
		initialize(fiches);
	}

	private void initialize(List<Fiche> fiches) {
		setLayout(new GridLayout(1,8));
		setPreferredSize(new Dimension(400,20));
		for(Fiche f : fiches) {
			this.add(createButton(f.getValue()+"" ,new Color(60,60,60)));
		}
		
	}

}

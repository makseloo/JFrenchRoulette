package it.unibs.pajc;

import java.awt.Color;
import it.unibs.pajc.core.*;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.event.ChangeEvent;




public class Model extends BaseModel{
	
	List<WheelNumber> numberList = new ArrayList<>();
	List<Fiche> ficheList = new ArrayList<>();
	
	List<Integer> blacks = WheelNumber.getBlackNums();
	List<Integer> reds = WheelNumber.getRedNums();
	
	List<Integer> tier = WheelNumber.getTier();
	List<Integer> orphelins = WheelNumber.getOrphelins();
	List<Integer> voisins = WheelNumber.getVoisins();
	//List<Integer> zero = WheelNumber.getZero(); da gestire perché zero e voisins si includono
	
	public Model() {	
		initializeWheelNumbers();
		initializeFiches();
	}

	private void initializeWheelNumbers() {
		int result;
		Color color;
		String zone;
		 for (int row = 3; row > 0; row--) {
	            for (int column = 0; column < 36; column += 3) {
	            	result = row + column;
	            	
	            	if(blacks.contains(result))
	            		color = new Colors().getBlack();
	            	else
	            		color =  new Colors().getRed();
	            	
	            	if(tier.contains(result)) {
	            		zone = "tier";
	            	}else if(orphelins.contains(result)) {
	            		zone = "orphelins";
	            	}else {
	            		zone = "voisins";
	            	}
	            	
	            	WheelNumber number = new WheelNumber(result, zone, color);
	            	numberList.add(number);
	                
	            }
	        }
	}

	
	private void initializeFiches() {

		 
		 Fiche oneFiche = new Fiche(1, true);
		 ficheList.add(oneFiche);
		 
		 Fiche twoFiche = new Fiche(2, false);
		 ficheList.add(twoFiche);
		 
		 Fiche fiveFiche = new Fiche(5, false);
		 ficheList.add(fiveFiche);
		 
		 Fiche twentyFiveFiche = new Fiche(25, false);
		 ficheList.add(oneFiche);
		 
		 Fiche oneHundredFiche = new Fiche(100, false);
		 ficheList.add(oneHundredFiche);
		
	}
	
	public List<WheelNumber> getNumberList() {
		return numberList;
	}

	public void setNumberList(List<WheelNumber> numberList) {
		this.numberList = numberList;
	}

	public List<Fiche> getFicheList() {
		return ficheList;
	}
	
	public double getSelectedFicheVal() {
		
		for(Fiche f : ficheList) {
			if (f.isSelected())
					return f.getValue();
		}
		return -1.0;
	}
	
	public void setNumberBet(String inputValue, double bettedAmount) {
		for(WheelNumber w : numberList) {
			if(w.getValue() == Integer.parseInt(inputValue)) {
				w.setValue(bettedAmount);
			}
		}
		fireValuesChange(new ChangeEvent(this));
	}
	
	public void selectFiche(double value) {
		deactivateFiches();
		for(Fiche f : ficheList) {
			if (f.getValue() == value)
				f.setSelected(true);
				
		}
	}


	private void deactivateFiches() {
		for(Fiche f : ficheList) {
			if (f.isSelected())
				f.setSelected(false);
		}
		
	}

	public String Dump() {
		StringBuffer sb = new StringBuffer();
		
		for(WheelNumber n : numberList) {
			
				sb.append(n.getValue());
				sb.append(",");
				sb.append(n.getBettedValue());
			
		}
		
		return sb.toString();
	}
	
	
	
	

}

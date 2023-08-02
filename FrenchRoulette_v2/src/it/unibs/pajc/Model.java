package it.unibs.pajc;

import java.awt.Color;
import it.unibs.pajc.core.*;
import it.unibs.pajc.server.RouletteGameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

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
	//numbers displayed as on the roulette
	List<WheelNumber> sortedList;
	private RouletteGameState gameState;
	
	public Model() {	
		initializeWheelNumbers();
		initializeFiches();
		this.sortedList = sort(numberList);
	}

	private List<WheelNumber> sort(List<WheelNumber> numberList) {
		List<WheelNumber> sortedList = new ArrayList<>(numberList);
		List<Integer> desiredOrder = WheelNumber.getNums();
		WheelNumber zero = new WheelNumber(0, "voisins", Colors.getGreen());

		
		Collections.sort(sortedList, new Comparator<WheelNumber>() {
			 @Override
	            public int compare(WheelNumber w1, WheelNumber w2) {
	                Integer value1 = w1.getValue();
	                Integer value2 = w2.getValue();
	                return Integer.compare(desiredOrder.indexOf(value1), desiredOrder.indexOf(value2));
	            }
		});
		sortedList.add(zero);
		
		
		return sortedList;
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
	
	public List<WheelNumber> getBets(){
		//creo una nuova lista in cui inserisco solo i numeri con valore diverso da 0
		List<WheelNumber> bets = new ArrayList<>();
		for(WheelNumber w : numberList) {
			if(w.getBettedValue() != 0)
				bets.add(new WheelNumber(w.getValue(), w.getZone(), w.getColor(), w.getBettedValue()));
		}
		return bets;
	}
	
	public List<WheelNumber> getWheelNumberList() {
		return sortedList;
	}

	public void setNumberList(List<WheelNumber> numberList) {
		this.numberList = numberList;
	}

	public List<Fiche> getFicheList() {
		return ficheList;
	}
	
	public int getSelectedFicheVal() {
		
		for(Fiche f : ficheList) {
			if (f.isSelected())
					return f.getValue();
		}
		return -1;
	}
	
	public void setNumberBet(String inputValue, int bettedAmount) {
		for(WheelNumber w : numberList) {
			if(w.getValue() == Integer.parseInt(inputValue)) {
				w.setValue(bettedAmount);
				System.out.print(w.getBettedValue());
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

	public RouletteGameState getState() {
		return gameState;
	}
	public void setState(String gameState) {
		this.gameState = RouletteGameState.valueOf(gameState);
		fireValuesChange(new ChangeEvent(this));
	}

	public List<WheelNumber> turnIntoColor(Queue<Integer> stats) {
		
		List<WheelNumber> coloredStats = new ArrayList<>();
		
		for(int i : stats) {
			
			for(WheelNumber w : sortedList) {
				
				if(i == w.getValue())
					coloredStats.add(w);
					
			}
		}		
		return coloredStats;
	}
	
	
	
	

}

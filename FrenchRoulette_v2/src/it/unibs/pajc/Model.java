package it.unibs.pajc;

import java.awt.Color;
import java.awt.event.ActionEvent;

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
	
	private int balance;
	private int bet = 0;
	private int range;
	
	public Model() {	
		initializeWheelNumbers();
		initializeFiches();
		this.sortedList = sort(numberList);
		this.range = 1;
	}
/*
	private void initializeZones() {
		for(String z : WheelNumber.getZones()) {
			Zone zone = new Zone(z, WheelNumber.getSpecificZone(z));
			zones.add(zone);
		}
		
		for(String o : WheelNumber.getOthersStat()) {
			Zone zone = new Zone(o, WheelNumber.getSpecificZone(o), 2);
			zones.add(zone);
		}
		
		for(String dc : WheelNumber.getDozAndCols()) {
			Zone zone = new Zone(dc, WheelNumber.getSpecificZone(dc), 3);
			zones.add(zone);
		}
	}
*/
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
		//perché il metodo sort non calcola lo zero
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
		 ficheList.add(twentyFiveFiche);
		 
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
	
	
	public void resetBets() {
		for(WheelNumber w : numberList) {
			if(w.getBettedValue() != 0)
				w.setBetValue(0);
		}
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
	
	public void setRangeNumberBet(String inputValue, int bettedAmount, int range) {
		int index = findIndex(inputValue);
		
	    List<Integer> numsToAdd = new ArrayList<>();
	    int fixedRange = range;
	    //aggiungo i precedenti

	    for (int i = (index - 1)%37; range > 0; i--) {
	    	if(i == -1) {
            	i = sortedList.size()-1;
            }
	    	numsToAdd.add(sortedList.get(i).getValue());
	    	System.out.print("prec agg: "+ sortedList.get(i).getValue() + "\n");
	    	range--;
        }
	    //resetto range
	    range = fixedRange;
	    //se sforo dall'altra parte? uso il modulo altrimenti resetto l'indice
	    
	    //aggiungo i successivi
	    for (int i = (index + 1)%37; i < sortedList.size() && range > 0; i++) {
	    	numsToAdd.add(sortedList.get(i).getValue());
	    	System.out.print("succ agg: "+ sortedList.get(i).getValue() + "\n");
            range--;
            if(i == sortedList.size()-1) {
            	//-1 perché lo incrementa alla fine del ciclo
            	i = -1;
            }
        }
		
	    for(int i : numsToAdd) {
	    	setNumberBet(i+"",bettedAmount);
	    }
	    System.out.print("Fine Giro\n");
		fireValuesChange(new ChangeEvent(this));
	}
	
	private int findIndex(String inputValue) {
		
		for (int i = 0; i < sortedList.size(); i++) {
			
            if (sortedList.get(i).getValue() == Integer.parseInt(inputValue)) {
                return i;
            }
        }
		return -1;
	}
	
	public void setNumberBet(String inputValue, int bettedAmount) {
		for(WheelNumber w : numberList) {
			if(w.getValue() == Integer.parseInt(inputValue)) {
				w.setValue(bettedAmount);
				//System.out.print(w.getBettedValue());
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

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
		fireValuesChange(new ChangeEvent(this));
	}
	
	public void substractBalance(int balance) {
		this.balance -= balance;
		fireValuesChange(new ChangeEvent(this));
	}
	
	public int getBet() {
		return bet;
	}
	
	public void setBet(int bet) {
		this.bet += bet;
	}
	
	public void resetBet() {
		this.bet = 0;
	}
//mi sa che action event qui è cannato
	public void betDoz(ActionEvent e) {
		int bet = getSelectedFicheVal();
		System.out.print(e.getActionCommand());
		if((balance - (bet))>=0) {
			setBet(bet);
			substractBalance(bet); 
			String numberString = e.getActionCommand().substring(11);
		    int number = Integer.parseInt(numberString);
		    /*
			for(Zone z : zones) {
				if(z.getName().equals("doz"+number)) {
					z.setBetValue(bet);
				}
			}
			*/
		}else {
			System.out.print("Model: Saldo insufficiente");
		}
	}

	public void betNum(ActionEvent e) {
		int bet = getSelectedFicheVal();
		 if((getBalance() - bet) >= 0) {
			 setNumberBet(e.getActionCommand(), bet);
			 setBet(bet);
			 substractBalance(bet); 
		 }else {
			 System.out.print("Saldo insufficiente");
		 }
		
	}

	
	public void betCol(ActionEvent e) {
		int bet = getSelectedFicheVal();
		int bet12 = bet * 12;
		System.out.print(e.getActionCommand());
		if((balance - (bet12))>=0) {
			setBet(bet12);
			substractBalance(bet12); 
			for(WheelNumber w : numberList) {
				switch (e.getActionCommand().toString()){
				case "Trigger col1": {
	
					if(WheelNumber.getCol3().contains(w.getValue())) {
						w.setValue(bet);
					}
					break;
				}
				case "Trigger col2": {
					
					if(WheelNumber.getCol2().contains(w.getValue())) {
						w.setValue(bet);
					}
					break;
				}
				case "Trigger col3": {
					
					if(WheelNumber.getCol1().contains(w.getValue())) {
						w.setValue(bet);
					}
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + e.getActionCommand());
				}
				
			}
			
		}else {
			System.out.print("Model: Saldo insufficiente");
		}
	}

	public void betOther(ActionEvent e) {
		int bet = getSelectedFicheVal();
		int bet18 = bet * 18;
		System.out.print(e.getActionCommand());
		if((balance - (bet))>=0) {
			setBet(bet18);
			substractBalance(bet18); 
			for(WheelNumber w : numberList) {
				switch (e.getActionCommand().toString()){
				case "Trigger col1": {
	
					if(WheelNumber.getCol3().contains(w.getValue())) {
						w.setValue(bet);
					}
					break;
				}
				case "Trigger col2": {
					
					if(WheelNumber.getCol2().contains(w.getValue())) {
						w.setValue(bet);
					}
					break;
				}
				case "Trigger col3": {
					
					if(WheelNumber.getCol1().contains(w.getValue())) {
						w.setValue(bet);
					}
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + e.getActionCommand());
				}
				
			}
			
		}else {
			System.out.print("Model: Saldo insufficiente");
		}
		
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
		fireValuesChange(new ChangeEvent(range));
	}
	

}

package it.unibs.pajc;

import java.awt.event.ActionEvent;

import it.unibs.pajc.core.*;
import it.unibs.pajc.server.Numbers;
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
	
	//numbers displayed as on the roulette
	List<WheelNumber> sortedList;
	List<Zone> zones = new ArrayList<>();
	
	private List<WheelNumber> lastTen;

	private RouletteGameState gameState;
	
	private double balance;
	private double bet = 0;
	private int range;
	
	public Model() {	
		Numbers numbers = new Numbers();
		numberList = numbers.numbers;
		initializeFiches();
		initializeZones();
		this.sortedList = sort(numberList);
		this.range = 1;
	}

	private void initializeZones() {
		for(String dc :  Numbers.dozAndCols) {
			Zone zone = new Zone(dc, Numbers.getZoneNumbers(dc),3);
			zones.add(zone);
		}
		
		for(String o : Numbers.othersStat) {
			Zone zone = new Zone(o, Numbers.getZoneNumbers(o),2);
			zones.add(zone);
		}
	}

	private List<WheelNumber> sort(List<WheelNumber> numberList) {
		List<WheelNumber> sortedList = new ArrayList<>(numberList);
		List<Integer> desiredOrder = Numbers.sortedIntNumbers;
		List<String> zeroZone = new ArrayList<>();
		zeroZone.add("voisins");
		zeroZone.add("z");
		
		WheelNumber zero = new WheelNumber(0,0, zeroZone, Colors.getGreen());

		
		Collections.sort(sortedList, new Comparator<WheelNumber>() {
			 @Override
	            public int compare(WheelNumber w1, WheelNumber w2) {
	                Integer value1 = w1.getValue();
	                Integer value2 = w2.getValue();
	                return Integer.compare(desiredOrder.indexOf(value1), desiredOrder.indexOf(value2));
	            }
		});
		//perché in numberlist non c'è lo 0
		sortedList.add(zero);
		
		return sortedList;
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
				bets.add(new WheelNumber(w.getValue(),w.getValue(), w.getZone(), w.getColor(), w.getBettedValue()));
		}
		return bets;
	}
	
	public List<Zone> getZonesBets(){
		List<Zone> zonesBets = new ArrayList<>();
		Zone nz = null;
		for(Zone z : zones) {
			if(z.getBetValue() != 0) {
				nz = new Zone(z.getZoneName(),z.getZoneNumbers(), z.getPayout());
				nz.setBetValue(z.getBetValue());
				zonesBets.add(nz);
			}
		}
		return zonesBets;
	}
	
	
	public void resetBets() {
		for(WheelNumber w : numberList) {
			if(w.getBettedValue() != 0)
				w.setBetValue(0);
		}
		for(Zone z : zones) {
			if(z.getBetValue() != 0) {
				z.resetBetValue();
			}
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
	    	
	    	range--;
        }
	    //resetto range
	    range = fixedRange;
	    //se sforo dall'altra parte? uso il modulo altrimenti resetto l'indice
	    
	    //aggiungo i successivi
	    for (int i = (index + 1)%37; i < sortedList.size() && range > 0; i++) {
	    	numsToAdd.add(sortedList.get(i).getValue());
	    	
            range--;
            if(i == sortedList.size()-1) {
            	//-1 perché lo incrementa alla fine del ciclo
            	i = -1;
            }
        }
		
	    for(int i : numsToAdd) {
	    	setNumberBet(i,bettedAmount);
	    }
	    
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
	
	public void setNumberBet(int inputValue, double bettedAmount) {
		for(WheelNumber w : numberList) {
			if(w.getValue() == inputValue) {
				w.setValue(bettedAmount);
			}
		}
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
		fireValuesChange(new CustomChangeEvent(gameState, EventType.UPDATE_STATE));
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

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
		fireValuesChange(new CustomChangeEvent(this, EventType.BALANCE_SET));
	}
	
	public void substractBalance(double balance) {
		this.balance -= balance;
		fireValuesChange(new CustomChangeEvent(this, EventType.BALANCE_SET));
	}
	
	public double getBet() {
		return bet;
	}
	
	public void setBet(double bet) {
		this.bet += bet;
	}
	
	public void resetBet() {
		this.bet = 0;
	}
	public void betDoz(String zone) {
		int bet = getSelectedFicheVal();
		if((balance - (bet))>=0) {
			setBet(bet);
			substractBalance(bet); 
			
		    
			for(Zone z : zones) {
				if(z.getZoneName().equals(zone)) {
					z.setBetValue(bet);
				}
			}
			
		}else {
			System.out.print("Model: Saldo insufficiente");
		}
		fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
	}
	

	public void betNum(ActionEvent e) {
		int value = Integer.parseInt(e.getActionCommand());
		int bet = getSelectedFicheVal();
		 if((getBalance() - bet) >= 0) {
			 setNumberBet(value, bet);
			 setBet(bet);
			 substractBalance(bet); 
		 }else {
			 System.out.print("Saldo insufficiente");
		 }
		 fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
	}

	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
		fireValuesChange(new CustomChangeEvent(range, EventType.RANGE_SET));
	}
	
	public List<Zone> getZones() {
		// TODO Auto-generated method stub
		return this.zones;
	}
	
	public void betTier() {
		int bet = getSelectedFicheVal();
		double totalBet = bet*6;
		double splitBet = bet  / 2.0;
		if((getBalance() - totalBet) >= 0) {
			for(int i : Numbers.tier) {
				setNumberBet(i, splitBet);
			}
			setBet(totalBet);
			substractBalance(totalBet); 
		 }else {
			 System.out.print("Saldo insufficiente");
		 }
		fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
	}



	public void betOrph() {
		int bet = getSelectedFicheVal();
		double totalBet = bet*5;
		double splitBet = bet  / 2.0;
		if((getBalance() - totalBet) >= 0) {
			for(int i : Numbers.orphelins) {
				if(i == 1) {
					setNumberBet(i, bet);
				}else {
					setNumberBet(i, splitBet);
				}
			}
			setBet(totalBet);
			substractBalance(totalBet); 
		 }else {
			 System.out.print("Saldo insufficiente");
		 }
		fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
	}

	public void betVois() {
		int bet = getSelectedFicheVal();
		double totalBet = bet*9;
		double splitBet = bet / 2.0;
		double threedBet = (bet * 2) / 3.0;
		double fourBet = (bet * 2) / 4.0;
		if((getBalance() - totalBet) >= 0) {
			for(int i : Numbers.voisins) {
				if(i == 0 || i == 2 || i == 3) {
					setNumberBet(i, threedBet);
				}else if(i == 25 || i == 26 || i == 28 || i == 29){
					setNumberBet(i, fourBet);
				}else {
					setNumberBet(i, splitBet);
				}
			}
			setBet(totalBet);
			substractBalance(totalBet); 
		 }else {
			 System.out.print("Saldo insufficiente");
		 }
		fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
	}

	public void betZero() {
		int bet = getSelectedFicheVal();
		double totalBet = bet*4;
		double splitBet = bet  / 2.0;
		if((getBalance() - totalBet) >= 0) {
			for(int i : Numbers.zero) {
				if(i == 26) {
					setNumberBet(i, bet);
				}else {
					setNumberBet(i, splitBet);
				}
			}
			setBet(totalBet);
			substractBalance(totalBet); 
		 }else {
			 System.out.print("Saldo insufficiente");
		 }
		fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
		
	}

	public void updateLastTen(List<WheelNumber> stats) {
		this.lastTen = new ArrayList<>(stats);
		fireValuesChange(new CustomChangeEvent(this, EventType.LAST_TEN_CHANGED));
	}
	
	public WheelNumber getLastNumber() {
		return lastTen.get(lastTen.size()-1);
	}

	public List<WheelNumber> getLastTen() {
		return lastTen;
	}

	public void betRange(ActionEvent e) {
		int value = Integer.parseInt(e.getActionCommand());
		int range = getRange();
		int bet = getSelectedFicheVal() ;
		int betRange = bet*(range*2 +1);
		 if((getBalance() - betRange) >= 0) {
			 setNumberBet(value, bet);
			 setRangeNumberBet(e.getActionCommand(), bet, range);
			 setBet(betRange);
			 substractBalance(betRange); 
		 }else {
			 System.out.print("Saldo insufficiente\n");
		 }
		 fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
	}
}

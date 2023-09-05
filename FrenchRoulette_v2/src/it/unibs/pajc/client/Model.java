package it.unibs.pajc.client;

import java.awt.event.ActionEvent;

import it.unibs.pajc.EventType;
import it.unibs.pajc.Fiche;
import it.unibs.pajc.Numbers;
import it.unibs.pajc.RouletteGameState;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
import it.unibs.pajc.core.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

import javax.swing.event.ChangeEvent;

public class Model extends BaseModel{
	
	List<WheelNumber> numberList = new ArrayList<>();
	List<Fiche> ficheList = new ArrayList<>();
	
	List<WheelNumber> sortedList;
	List<Zone> zones = new ArrayList<>();
	
	private List<WheelNumber> lastTen;

	private RouletteGameState gameState;
	
	private double balance;
	private double bet = 0;
	private int range;
	
	public Model() {	
		//initializing numbers of the roulette(in the contstructor of Numbers)
		Numbers numbers = new Numbers();
		//sorted as on the betBoard
		this.numberList = numbers.numbers;
		//sorted as on the wheel
		this.sortedList = numbers.sortedWheelNumbers;
		
		initializeZones();
		initializeFiches();
		
		this.range = 1;
	}
	//Creating zones Voisins, Orpehlins, dozens etc
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

	//Creating fiches with which the user will bet
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
	
	//////VARIOUS SETTERS AND GETTERS
	
	
	
	public List<WheelNumber> getNumberList() {
		return numberList;
	}
	//returning a NEW bet list with only the numbers with a bet value != 0
	public List<WheelNumber> getBets(){
		List<WheelNumber> bets = new ArrayList<>();
		for(WheelNumber w : numberList) {
			if(w.getBettedValue() != 0)
				bets.add(new WheelNumber(w.getValue(),w.getValue(), w.getZone(), w.getColor(), w.getBettedValue()));
		}
		return bets;
	}
	//same as getBets but for the zones
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
	
	public RouletteGameState getState() {
		return gameState;
	}
	
	public void setState(String gameState) {
		this.gameState = RouletteGameState.valueOf(gameState);
		fireValuesChange(new CustomChangeEvent(gameState, EventType.UPDATE_GAME_STATE));
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
	
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
		fireValuesChange(new CustomChangeEvent(range, EventType.RANGE_SET));
	}
	
	public List<Zone> getZones() {
		return this.zones;
	}
	
	public WheelNumber getLastNumber() {
		return lastTen.get(lastTen.size()-1);
	}

	public List<WheelNumber> getLastTen() {
		return lastTen;
	}
	
	//////
	
	

	public List<Integer> getNumsToBetOn(String numberValue, int range) {
		
		for(WheelNumber w : sortedList) {
			System.out.print(w.getValue()+"\n");
		}
		
		
		int index = findIndex(numberValue);
		
	    List<Integer> numsToAdd = new ArrayList<>();
	    int fixedRange = range;
	    //adding the numbers before the selected one
	    for (int i = (index - 1)%37; range > 0; i--) {
	    	//i case i'm at the beginning of the list i have to start from the last element
	    	if(i == -1) {
            	i = sortedList.size()-1;
            }
	    	numsToAdd.add(sortedList.get(i).getValue());
	    	
	    	range--;
        }
	    //restting range
	    range = fixedRange;
	    
	    
	    //adding the next numbers
	    for (int i = (index + 1)%37; i < sortedList.size() && range > 0; i++) {
	    	numsToAdd.add(sortedList.get(i).getValue());
            range--;
          //i case i'm at the end of the list i have to start from the start
            if(i == sortedList.size()-1) {
            	//-1 because i it's incremented at the end of the cycle
            	i = -1;
            }
        }
	    
	    return numsToAdd;	    
	}
	//return the index of the searched value(in the wheel)
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
	
	//betting on the numbers based on the selected range
	public void betRange(ActionEvent e) {
		int value = Integer.parseInt(e.getActionCommand());
		int range = getRange();
		int bet = getSelectedFicheVal() ;
		int betRange = bet*(range*2 +1);
		 if((getBalance() - betRange) >= 0) {
			 //betting on the number i clicked
			 setNumberBet(value, bet);
			 //betting on the neighbors numbers
			 for(int i : getNumsToBetOn(e.getActionCommand(), range)) {
		    	setNumberBet(i,bet);
			 }
			 setBet(betRange);
			 substractBalance(betRange); 
		 }else {
			 System.out.print("Saldo insufficiente\n");
		 }
		 fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
	}
	

	
	//BETTING ON doz cols red black even odd
	public void betOther(String zone) {
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
			System.out.print("Other: balance it's not enough");
		}
		fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
	}
	
	//SINGLE NUMBER BETTING
	public void betNum(ActionEvent e) {
		int value = Integer.parseInt(e.getActionCommand());
		int bet = getSelectedFicheVal();
		 if((getBalance() - bet) >= 0) {
			 setNumberBet(value, bet);
			 setBet(bet);
			 substractBalance(bet); 
		 }else {
			 System.out.print("Number: balance it's not enough");
		 }
		 fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
	}

	//MAIN Zones betting: each of them requires a specific split bet based on the Roulette rules
	
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

	
	
	public void updateLastNumbers(List<WheelNumber> stats) {
		this.lastTen = new ArrayList<>(stats);
		fireValuesChange(new CustomChangeEvent(this, EventType.LAST_TEN_CHANGED));
	}
	
	public void updateFirstStats(List<WheelNumber> numbers) {
		this.lastTen = new ArrayList<>(numbers);
	}
	
	public void updateLastTen(List<WheelNumber> stats) {
		this.lastTen = new ArrayList<>(stats);
		fireValuesChange(new CustomChangeEvent(this, EventType.LAST_TEN_CHANGED));
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
	public void changeRange(ActionEvent e) {
		int range = getRange();
		
		if(e.getActionCommand().equals("+")) {
			setRange(++range);
		}else if(e.getActionCommand().equals("-")) {
			setRange(--range);
		}
		
	}

}

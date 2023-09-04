package it.unibs.pajc;

import java.util.LinkedList;
import java.util.List;

public class ClientInfo {
	private String clientName;
	private int accountId;
    private double accountBalance;
    private double lastWin;
    private List<WheelNumber> betList;
    private List<Zone> zoneBetList;
    
    public ClientInfo() {
    	
    }
    
	public ClientInfo(String clientName, int accountId, double accountBalance) {
		super();
		this.clientName = clientName;
		this.accountId = accountId;
		this.accountBalance = accountBalance;
		this.lastWin = 0;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public void addAccountBalance(double accountBalance) {
		this.accountBalance += accountBalance;
	}
	
	public List<WheelNumber> getBetList() {
		return betList;
	}

	public void setBetList(List<WheelNumber> betList) {
		this.betList = new LinkedList<WheelNumber>(betList);
		// da fare in un metodo a parte
		
	}
	
	public void subAccountBalance(double totBet) {
		this.accountBalance -= totBet;
	}

	public void resetBetList() {
		this.betList = new LinkedList<WheelNumber>();
		this.zoneBetList = new LinkedList<Zone>();
	}

	public void setZoneBetList(List<Zone> betList) {
		this.zoneBetList = new LinkedList<Zone>(betList);
		// da fare in un metodo a parte
		
	}

	public List<Zone> getZoneBetList() {
		return this.zoneBetList;
	}

	public double getLastWin() {
		return lastWin;
	}
    
	public void setLastWin(double win) {
		this.lastWin += win;
	}
    
}

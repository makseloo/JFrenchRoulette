package it.unibs.pajc.server;

import java.util.LinkedList;
import java.util.List;

import it.unibs.pajc.WheelNumber;

public class ClientInfo {
	private String clientName;
	private int accountId;
    private int accountBalance;
    private List<WheelNumber> betList;
    
    public ClientInfo() {
    	
    }
    
	public ClientInfo(String clientName, int accountId, int accountBalance) {
		super();
		this.clientName = clientName;
		this.accountId = accountId;
		this.accountBalance = accountBalance;
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
	public int getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}

	public void addAccountBalance(int accountBalance) {
		this.accountBalance += accountBalance;
	}
	
	public List<WheelNumber> getBetList() {
		return betList;
	}

	public void setBetList(List<WheelNumber> betList) {
		this.betList = new LinkedList<WheelNumber>(betList);
		// da fare in un metodo a parte
		
	}
	
	public void subAccountBalance(int totBet) {
		this.accountBalance -= totBet;
	}

	public void resetBetList() {
		this.betList = new LinkedList<WheelNumber>();
		
	}
	
	
    
    
}

package it.unibs.pajc.server;

import java.util.LinkedList;
import java.util.List;

import it.unibs.pajc.WheelNumber;

public class ClientInfo {
	private String clientName;
	private int accountId;
    private double accountBalance;
    private List<WheelNumber> betList;
    
    public ClientInfo() {
    	
    }
    
	public ClientInfo(String clientName, int accountId, double accountBalance) {
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
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public List<WheelNumber> getBetList() {
		return betList;
	}

	public void setBetList(List<WheelNumber> betList) {
		this.betList = new LinkedList<WheelNumber>(betList);
	}
	
	
    
    
}

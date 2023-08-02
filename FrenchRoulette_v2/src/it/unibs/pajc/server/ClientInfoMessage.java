package it.unibs.pajc.server;

import java.io.Serializable;

public class ClientInfoMessage implements Serializable{

	private String name; 
	private int balance;
	public ClientInfoMessage(String name, int balance) {
		super();
		this.name = name;
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	

}

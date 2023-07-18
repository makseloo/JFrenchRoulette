package it.unibs.pajc;

public class Player {
	private String name;
	private double balance;
	private double puntata;
	
	
	public Player(String name, double balance, double puntata) {
		this.name = name;
		this.balance = balance;
		this.puntata = puntata;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getPuntata() {
		return puntata;
	}
	public void setPuntata(double puntata) {
		this.puntata = puntata;
	}
	
	
}

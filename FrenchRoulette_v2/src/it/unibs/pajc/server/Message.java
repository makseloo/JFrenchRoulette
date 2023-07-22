package it.unibs.pajc.server;

import java.io.Serializable;

public class Message implements Serializable{
	private String gameState;
	private int seconds;
	public Message(String gameState, int seconds) {
		super();
		this.gameState = gameState;
		this.seconds = seconds;
	}
	public String getGameState() {
		return gameState;
	}
	public void setGameState(String gameState) {
		this.gameState = gameState;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	
}

package it.unibs.pajc.server.messages;

import java.io.Serializable;

public class GameStateMessage implements Serializable{
	private String gameState;

	public GameStateMessage(String gameState) {
		super();
		this.gameState = gameState;
	}

	public String getGameState() {
		return gameState;
	}

	public void setGameState(String gameState) {
		this.gameState = gameState;
	}
	
	
}

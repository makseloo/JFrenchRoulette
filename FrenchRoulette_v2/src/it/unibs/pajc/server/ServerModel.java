package it.unibs.pajc.server;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.ChangeEvent;

import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.core.BaseModel;
public class ServerModel extends BaseModel implements ServerTimer.TimerListener {
    private static final int BETTING_TIMER_DURATION = 10; // Duration of the timer in seconds
    private static final int SPIN_TIMER_DURATION = 5; // the time the ball needs to spin around the wheel
    private static final int SETTLE_TIMER_DURATION = 7; // the time the ball needs to spin around the wheel

    private RouletteGameState gameState;
    private RouletteGameState previous_gameState;
	private ServerTimer serverTimer;
	private ServerStatistics serverStats;
	private List<ClientInfo> connectedClients;
    
    public ServerModel() {
        // Initialize the initial state of the server
        gameState = RouletteGameState.BETTING;
        previous_gameState = RouletteGameState.BETTING;
        serverStats = new ServerStatistics();
        connectedClients = new ArrayList<>();
    }
    
    public ServerStatistics getServerStats() {
    	return serverStats;
    }

	public RouletteGameState getGameState() {
        return gameState;
    }

    public RouletteGameState getPrevGameState() {
        return previous_gameState;
    }
    public void setGameState(RouletteGameState gameState) {
        this.gameState = gameState;
    }

    public int getTimerDuration() {
        return BETTING_TIMER_DURATION;
    }

    public int getSpinTimerDuration() {
        return SPIN_TIMER_DURATION;
    }
    
    public void startTimer() {
        if (gameState == RouletteGameState.BETTING) {
            serverTimer = new ServerTimer(BETTING_TIMER_DURATION, RouletteGameState.BETTING);
            gameState = RouletteGameState.SPINNING;
        } else if (gameState == RouletteGameState.SPINNING) {
            serverTimer = new ServerTimer(SPIN_TIMER_DURATION, RouletteGameState.SPINNING);
            gameState = RouletteGameState.SETTLING;
        } else if (gameState == RouletteGameState.SETTLING){
        	serverTimer = new ServerTimer(SETTLE_TIMER_DURATION, RouletteGameState.SETTLING);
        	
        	gameState = RouletteGameState.BETTING;
        }
        serverTimer.setTimerListener(this);
        serverTimer.start();
    }
    
    @Override
    public void onTick(int remainingSeconds) {
    	 
    	 fireValuesChange(new ChangeEvent(this));
    }
    
    public int getSeconds() {
        return serverTimer.getRemainingSeconds();
    }

	@Override
	public void onTimerExpired() {
		previous_gameState = gameState;
		startTimer();
		fireTimerExpiredEvent(previous_gameState);
	}
	
	public List<Integer> getNumbers(){
		return serverStats.getNumbers();
	}

	public Map<String, Integer> getStats() {
		return serverStats.getStats();
	}
	//managing clients
    public void addClient(ClientInfo clientInfo) {
        connectedClients.add(clientInfo);
        fireClientsUpdateEvent(new ChangeEvent(this));
    }

    public void removeClient(ClientInfo clientInfo) {
        connectedClients.remove(clientInfo);
        fireClientsUpdateEvent(new ChangeEvent(this));
    }

    public List<ClientInfo> getConnectedClients() {
        return connectedClients;
    }

}

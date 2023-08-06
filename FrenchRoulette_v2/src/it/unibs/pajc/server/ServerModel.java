package it.unibs.pajc.server;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.event.ChangeEvent;

import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
import it.unibs.pajc.core.BaseModel;
public class ServerModel extends BaseModel implements ServerTimer.TimerListener {
    private static final int BETTING_TIMER_DURATION = 15; // Duration of the timer in seconds
    private static final int SPIN_TIMER_DURATION = 5; // the time the ball needs to spin around the wheel
    private static final int SETTLE_TIMER_DURATION = 3; // the time the ball needs to spin around the wheel

    private RouletteGameState gameState;
    private RouletteGameState previous_gameState;
	private ServerTimer serverTimer;
	private ServerStatistics serverStats;
	//mappa perché è più veloce trovare un client specifico
	private HashMap<Integer, ClientInfo> connectedClients;
    
    public ServerModel() {
        // Initialize the initial state of the server
        gameState = RouletteGameState.BETTING;
        previous_gameState = RouletteGameState.BETTING;
        serverStats = new ServerStatistics();
        connectedClients = new HashMap<>();
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
    	 switch (gameState) {
         case BETTING:
             serverTimer = new ServerTimer(BETTING_TIMER_DURATION);
             gameState = RouletteGameState.SPINNING;
             break;
         case SPINNING:
             serverTimer = new ServerTimer(SPIN_TIMER_DURATION);
             gameState = RouletteGameState.SETTLING;
             serverStats.generateSingleNumber();
             fireGeneratedNumberEvent(new ChangeEvent(this));
             
             break;
         case SETTLING:
        	//da errore è da sistemare
        	 analyzeBets();
             serverTimer = new ServerTimer(SETTLE_TIMER_DURATION);
             resetBets();
             gameState = RouletteGameState.BETTING;
             fireStateChangedEvent(new ChangeEvent(this));
             break;
         default:
             throw new IllegalArgumentException("Unexpected value: " + gameState);
     }
        serverTimer.setTimerListener(this);
        serverTimer.start();
    }
    //version one where you can just bet on numbers
    //possibile tipo avere una tabella in cui per ogni numero ho il client e la bet puntata?
    
    private void analyzeBets() {
    	
    	WheelNumber lastNum = serverStats.getLastWheelNumber();
		for(Integer key : connectedClients.keySet()) {
			for(WheelNumber w : connectedClients.get(key).getBetList()) {
				if(w.getValue() == lastNum.getValue()) {
					payout(key,w.getBettedValue(), 36);
				}
			}
			
			
			for(Zone z : connectedClients.get(key).getZoneBetList()) {
				
				if(z.getZoneName().equals(lastNum.getZone())) {
					payout(key,z.getBetValue(), z.getPayout());
				}
			}
			
			
		}
		
	}
	

	private void resetBets() {
		for(int key : connectedClients.keySet()) {
			connectedClients.get(key).resetBetList();
		}
		fireUpdateBet(this);
		
	}

	private void payout(Integer key, int bet, int multiplier) {
		connectedClients.get(key).addAccountBalance(bet*multiplier);
		//fire something
		
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
	
	public Queue<Integer> getNumbers(){
		return serverStats.getNumbers();
	}

	public Map<String, Integer> getStats() {
		return serverStats.getStats();
	}
	//managing clients
    public void addClient(ClientInfo clientInfo) {
        connectedClients.put(clientInfo.getAccountId(),clientInfo);
        fireClientsUpdateEvent(new ChangeEvent(this));
    }

    public void removeClient(ClientInfo clientInfo) {
        connectedClients.remove(clientInfo.getAccountId());
        fireClientsUpdateEvent(new ChangeEvent(this));
    }

    public HashMap<Integer, ClientInfo> getConnectedClients() {
        return connectedClients;
    }

	public void updateBets(List<WheelNumber> bets, int id, int totBet, List<Zone> zones)  {
		connectedClients.get(id).setBetList(bets);
		connectedClients.get(id).subAccountBalance(totBet);
		connectedClients.get(id).setZoneBetList(zones);
		fireUpdateBet(new ChangeEvent(this));
	}

	public void updateClientInfo(int id, String name, int balance) {
		connectedClients.get(id).setClientName(name);
		connectedClients.get(id).setAccountBalance(balance);
	}

	public int getPayout(int id) {
		
		return connectedClients.get(id).getAccountBalance();
	}


}

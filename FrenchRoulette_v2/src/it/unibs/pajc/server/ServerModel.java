package it.unibs.pajc.server;

import java.util.HashMap;
import java.util.List;

import javax.swing.event.ChangeEvent;

import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
import it.unibs.pajc.core.BaseModel;
import it.unibs.pajc.core.CustomChangeEvent;
import it.unibs.pajc.core.EventType;
public class ServerModel extends BaseModel implements ServerTimer.TimerListener {
    private static final int BETTING_TIMER_DURATION = 10; // Duration of the timer in seconds
    private static final int SPIN_TIMER_DURATION = 5; // the time the ball needs to spin around the wheel
    private static final int SETTLE_TIMER_DURATION = 5; 

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
        fireValuesChange(new CustomChangeEvent(gameState, EventType.UPDATE_GAME_STATE));
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
             setGameState(RouletteGameState.SPINNING);
             break;
         case SPINNING:
             serverTimer = new ServerTimer(SPIN_TIMER_DURATION);
             setGameState(RouletteGameState.SETTLING);
             serverStats.generateSingleNumber();
             fireValuesChange(new CustomChangeEvent(this, EventType.GENERATED_NUMBER));
            
             break;
         case SETTLING:
        	 analyzeBets();
        	 fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_STATE));
             serverTimer = new ServerTimer(SETTLE_TIMER_DURATION);
             resetBets();
             setGameState(RouletteGameState.BETTING);          
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
    	List<String> zone = lastNum.getZone();
    	int lastNumValue = lastNum.getValue();
    	if(connectedClients != null) {
    		for(Integer key : connectedClients.keySet()) {
    			if(connectedClients.get(key).getZoneBetList() != null) {
    				boolean won = false;
    				for(Zone z : connectedClients.get(key).getZoneBetList()) {
        				for(String s : zone) {
        					if(z.getZoneName().equals(s)) {
        						payout(key,z.getBetValue(), z.getPayout());
        						won = true;
        					}
        				}
        			}
    				for(WheelNumber w : connectedClients.get(key).getBetList()) {
        				if(w.getValue() == lastNumValue) {
        					
        					payout(key,w.getBettedValue(), 36);
        					won = true;
        				}
        			}
    				//se non vince ho messo l'ultima vincita a 0
    				if(!won) {
    					setLastWin(key,0);
    				}
    			}
    		}
    	}
		
	}
	private void setLastWin(Integer key, int i) {
		connectedClients.get(key).setLastWin(i);
		
	}

	private void resetBets() {
		for(int key : connectedClients.keySet()) {
			connectedClients.get(key).resetBetList();
		}
		fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
		
	}

	private void payout(Integer key, double bet, int multiplier) {
		connectedClients.get(key).addAccountBalance(bet*multiplier);
		connectedClients.get(key).setLastWin(bet*multiplier);
		//fire something
		
	}

	@Override
    public void onTick(int remainingSeconds) {
    	 fireValuesChange(new CustomChangeEvent(this, EventType.TIMER_TICK));
    }
    
    public int getSeconds() {
        return serverTimer.getRemainingSeconds();
    }

	@Override
	public void onTimerExpired() {
		previous_gameState = gameState;
		startTimer();
		fireValuesChange(new CustomChangeEvent(previous_gameState, EventType.TIMER_EXPIRED));
	}
	

	//managing clients
    public void addClient(ClientInfo clientInfo) {
        connectedClients.put(clientInfo.getAccountId(),clientInfo);
		fireValuesChange(new CustomChangeEvent(this, EventType.CLIENTS_UPDATE));
    }

    public void removeClient(ClientInfo clientInfo) {
        connectedClients.remove(clientInfo.getAccountId());
        fireValuesChange(new CustomChangeEvent(this, EventType.CLIENTS_UPDATE));
        }

    public HashMap<Integer, ClientInfo> getConnectedClients() {
        return connectedClients;
    }

	public void updateBets(List<WheelNumber> bets, int id, double totBet, List<Zone> zones)  {
		connectedClients.get(id).setBetList(bets);
		connectedClients.get(id).subAccountBalance(totBet);
		connectedClients.get(id).setZoneBetList(zones);
		fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
	}

	public void updateClientInfo(int id, String name, int balance) {
		connectedClients.get(id).setClientName(name);
		connectedClients.get(id).setAccountBalance(balance);
	}

	public double getNewBalance(int id) {
		
		return connectedClients.get(id).getAccountBalance();
	}

	public List<WheelNumber> getStats() {

		return serverStats.getNumbers();
		
	}

	public double getLastWin(int id) {
		return connectedClients.get(id).getLastWin();
	}


}

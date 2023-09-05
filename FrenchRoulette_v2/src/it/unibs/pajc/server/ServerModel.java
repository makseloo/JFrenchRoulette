package it.unibs.pajc.server;

import java.util.HashMap;
import java.util.List;

import javax.swing.event.ChangeEvent;

import it.unibs.pajc.ClientInfo;
import it.unibs.pajc.EventType;
import it.unibs.pajc.RouletteGameState;
import it.unibs.pajc.WheelNumber;
import it.unibs.pajc.Zone;
import it.unibs.pajc.core.BaseModel;
import it.unibs.pajc.core.CustomChangeEvent;
public class ServerModel extends BaseModel implements ServerTimer.TimerListener {
    private static final int BETTING_TIMER_DURATION = 10; // Duration of the timer in seconds
    private static final int SPIN_TIMER_DURATION = 10; // the time the ball needs to spin around the wheel
    private static final int SETTLE_TIMER_DURATION = 5; //time to send all the bets

    private RouletteGameState gameState;
    private RouletteGameState previous_gameState;
	private ServerTimer serverTimer;
	private ServerStatistics serverStats;
	//it's easier to find a specific client with a map
	private HashMap<Integer, ClientInfo> connectedClients;
    
    public ServerModel() {
        gameState = RouletteGameState.BETTING;
        previous_gameState = RouletteGameState.BETTING;
        serverStats = new ServerStatistics();
        connectedClients = new HashMap<>();
    }
    
    //SETTERS AND GETTERS
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
    
	public double getNewBalance(int id) {
		
		return connectedClients.get(id).getAccountBalance();
	}

	public List<WheelNumber> getStats() {

		return serverStats.getNumbers();
		
	}

	public double getLastWin(int id) {
		return connectedClients.get(id).getLastWin();
	}
	
    public int getSeconds() {
        return serverTimer.getRemainingSeconds();
    }
    
    ////
    
	//different operations based on the state of the game
    public void startTimer() {
    	 switch (gameState) {
         case BETTING:
             serverTimer = new ServerTimer(BETTING_TIMER_DURATION);
             setGameState(RouletteGameState.SPINNING);
             break;
         case SPINNING:
        	 //i generate the new number when the ball spins
        	 //the animation is leaded by the generated number, not vice-versa
             serverTimer = new ServerTimer(SPIN_TIMER_DURATION);
             setGameState(RouletteGameState.SETTLING);
             serverStats.generateSingleNumber();
             fireValuesChange(new CustomChangeEvent(this, EventType.GENERATED_NUMBER));
             break;
         case SETTLING:
        	 //analyze users bets to see if they win and eventually modify their balance if they won
        	 analyzeBets();
        	 //the fire is more clear here then in the analyzeBets method
        	 fireValuesChange(new CustomChangeEvent(this, EventType.BETS_ANALYZED));
             serverTimer = new ServerTimer(SETTLE_TIMER_DURATION);
             //with the new cylce all the bets have to be set to 0
             resetBets();
             setGameState(RouletteGameState.BETTING);          
             break;
         default:
             throw new IllegalArgumentException("Unexpected value: " + gameState);
     }
        serverTimer.setTimerListener(this);
        serverTimer.start();
    }
    
    //get the client zone and num list on which he bet, and just compare it to the last num generated
    //if so pay the client by increasing it's balance based on the payout of the zone/number
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
    					resetLastWin(key);
    				}
    			}
    		}
    	}
   	 //fireValuesChange(new CustomChangeEvent(this, EventType.BETS_ANALYZED));

		
	}
	private void resetLastWin(Integer key) {
		connectedClients.get(key).resetLastWin();
		
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
	}

	@Override
    public void onTick(int remainingSeconds) {
    	 fireValuesChange(new CustomChangeEvent(this, EventType.TIMER_TICK));
    }

	@Override
	public void onTimerExpired() {
		previous_gameState = gameState;
		//when the timer ends it means that the gamestate change so i execute the operations needed
		//also it starts a new timer for the new gamestate
		startTimer();
		
		//fireValuesChange(new CustomChangeEvent(previous_gameState, EventType.TIMER_EXPIRED));
	}
	

	//managing clients
    public synchronized void addClient(ClientInfo clientInfo) {
        connectedClients.put(clientInfo.getAccountId(),clientInfo);
		fireValuesChange(new CustomChangeEvent(this, EventType.CLIENTS_UPDATE));
    }

    public synchronized void removeClient(ClientInfo clientInfo) {
        connectedClients.remove(clientInfo.getAccountId());
        fireValuesChange(new CustomChangeEvent(this, EventType.CLIENTS_UPDATE));
        }

    public HashMap<Integer, ClientInfo> getConnectedClients() {
        return connectedClients;
    }

	public synchronized void updateBets(List<WheelNumber> bets, int id, double totBet, List<Zone> zones)  {
		connectedClients.get(id).setBetList(bets);
		connectedClients.get(id).subAccountBalance(totBet);
		connectedClients.get(id).setZoneBetList(zones);
		fireValuesChange(new CustomChangeEvent(this, EventType.UPDATE_BET));
	}

	public synchronized void updateClientInfo(int id, String name, int balance) {
		connectedClients.get(id).setClientName(name);
		connectedClients.get(id).setAccountBalance(balance);
	}



}

package it.unibs.pajc.server;

import it.unibs.pajc.server.ServerTimer.TimerListener;

import javax.swing.event.ChangeEvent;

import it.unibs.pajc.core.BaseModel;
public class ServerModel extends BaseModel implements ServerTimer.TimerListener {
    private static final int TIMER_DURATION = 10; // Duration of the timer in seconds
    private static final int SPIN_TIMER_DURATION = 10; // the time the ball needs to spin around the wheel

    private RouletteGameState gameState;
	private ServerTimer serverTimer;
    
    public ServerModel() {
        // Initialize the initial state of the server
        gameState = RouletteGameState.BETTING;
    }

    public RouletteGameState getGameState() {
        return gameState;
    }

    public void setGameState(RouletteGameState gameState) {
        this.gameState = gameState;
    }

    public int getTimerDuration() {
        return TIMER_DURATION;
    }

    public int getSpinTimerDuration() {
        return SPIN_TIMER_DURATION;
    }
    
    public void startTimer() {
       

        serverTimer = new ServerTimer(TIMER_DURATION);
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
		// TODO Auto-generated method stub
	}

}

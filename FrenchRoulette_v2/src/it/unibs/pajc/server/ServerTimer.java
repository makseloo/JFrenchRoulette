package it.unibs.pajc.server;

import java.util.Timer;
import java.util.TimerTask;

public class ServerTimer {
    private Timer timer;
    private int remainingSeconds;
    private int durationSeconds;
    private RouletteGameState state;
    private TimerListener timerListener;

    public ServerTimer(int durationSeconds, RouletteGameState state) {
    	this.durationSeconds = durationSeconds;
    	this.remainingSeconds = durationSeconds;
    	this.state = state;
        this.timer = new Timer();
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingSeconds <= 0) {
                	timer.cancel();
                	if (timerListener != null) {
                        timerListener.onTimerExpired();
                    }
                	
                } else {
                    remainingSeconds--;
                    if (timerListener != null) {
                        timerListener.onTick(remainingSeconds);
                    }
                }
            }
        }, 0, 1000);
    }
    public interface TimerListener {
        void onTick(int remainingSeconds);
        
        void onTimerExpired();
    }
    
    public void setTimerListener(TimerListener listener) {
        this.timerListener = listener;
    }

	public int getRemainingSeconds() {
		return this.remainingSeconds;
	}

    
}

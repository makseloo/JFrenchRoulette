package it.unibs.pajc.server;

import java.util.Timer;
import java.util.TimerTask;

public class ServerTimer {
    private Timer timer;
    private int remainingSeconds;
    private int durationSeconds;
    private TimerListener timerListener;

    public ServerTimer(int durationSeconds) {
    	this.durationSeconds = durationSeconds;
    	this.remainingSeconds = durationSeconds;
        this.timer = new Timer();
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingSeconds <= 0) {
                	timer.cancel();
                	timer = new Timer(); // Create a new instance of Timer
                    remainingSeconds = durationSeconds;
                    start();
                	
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

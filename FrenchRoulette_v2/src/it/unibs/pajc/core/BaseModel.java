package it.unibs.pajc.core;
import javax.swing.event.*;

public class BaseModel {
	protected EventListenerList listenerList = new EventListenerList();
	
	public void addChangeListener(ChangeListener l) {
		listenerList.add(ChangeListener.class, l);
	}
	
	public void removeChangeListener(ChangeListener l) {
		listenerList.add(ChangeListener.class, l);
	}
	
	public void fireValuesChange(ChangeEvent e) {
		for(ChangeListener l : listenerList.getListeners(ChangeListener.class)) {
			l.stateChanged(e);
		}
	}
	
    public void fireTimerExpiredEvent(Object source) {
        TimerExpiredEvent timerExpiredEvent = new TimerExpiredEvent(source);
        fireValuesChange(timerExpiredEvent);
    }
    
    public class TimerExpiredEvent  extends ChangeEvent {
        public TimerExpiredEvent(Object source) {
            super(source);
        }
    }
}



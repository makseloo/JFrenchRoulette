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
    
    public void fireClientsUpdateEvent(Object source) {
    	ClientsUpdateEvent clientsUpdateEvent = new ClientsUpdateEvent(source);
        fireValuesChange(clientsUpdateEvent);
    }
    
    public class ClientsUpdateEvent  extends ChangeEvent {
        public ClientsUpdateEvent(Object source) {
            super(source);
        }
    }
    
    public class GeneratedNumberEvent  extends ChangeEvent {
        public GeneratedNumberEvent(Object source) {
            super(source);
        }
    }
    
    public void fireGeneratedNumberEvent(Object source) {
    	GeneratedNumberEvent clientsUpdateEvent = new GeneratedNumberEvent(source);
        fireValuesChange(clientsUpdateEvent);
    }
    
    public class UpdateBet  extends ChangeEvent {
        public UpdateBet(Object source) {
            super(source);
        }
    }
    
    public void fireUpdateBet(Object source) {
    	UpdateBet updateBetEvent = new UpdateBet(source);
        fireValuesChange(updateBetEvent);
    }
    
    public class UpdateState  extends ChangeEvent {
        public UpdateState(Object source) {
            super(source);
        }
    }
    
    public void fireStateChangedEvent(Object source) {
    	UpdateState updateBetEvent = new UpdateState(source);
        fireValuesChange(updateBetEvent);
    }
    
    public class lastTenChanged  extends ChangeEvent {
        public lastTenChanged(Object source) {
            super(source);
        }
    }
    
    public void fireLastTenChanged(Object source) {
    	lastTenChanged lastTenChangedEvent = new lastTenChanged(source);
        fireValuesChange(lastTenChangedEvent);
    }
    
}



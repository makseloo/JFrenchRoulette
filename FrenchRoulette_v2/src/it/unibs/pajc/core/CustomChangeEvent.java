package it.unibs.pajc.core;

import javax.swing.event.ChangeEvent;

import it.unibs.pajc.EventType;
//used to fire specific event 
public class CustomChangeEvent extends ChangeEvent {
    private EventType eventType;
    
    public CustomChangeEvent(Object source, EventType eventType) {
        super(source);
        this.eventType = eventType;
    }
    
    public EventType getEventType() {
        return eventType;
    }
}


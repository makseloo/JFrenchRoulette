package it.unibs.pajc.core;

public enum EventType {
	//used in the model
    TIMER_EXPIRED, 
    UPDATE_STATE, 
    LAST_TEN_CHANGED,
    BALANCE_SET, 
    RANGE_SET,
    
    //used in the server model
    GENERATED_NUMBER, 
    CLIENTS_UPDATE, 
    TIMER_TICK, 
    //used in both
    UPDATE_BET, UPDATE_GAME_STATE, 
}

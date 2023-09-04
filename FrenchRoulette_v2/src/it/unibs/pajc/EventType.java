package it.unibs.pajc;

public enum EventType {

    TIMER_EXPIRED, 
    LAST_TEN_CHANGED,
    BALANCE_SET, 
    RANGE_SET,
    

    GENERATED_NUMBER, 
    CLIENTS_UPDATE, 
    TIMER_TICK, 
    
    UPDATE_BET, 
    UPDATE_GAME_STATE, 
    BETS_ANALYZED, 
}

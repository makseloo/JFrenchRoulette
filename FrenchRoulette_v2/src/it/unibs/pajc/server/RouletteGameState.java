package it.unibs.pajc.server;
// BETTING-> SPINNING->SETTLING
public enum RouletteGameState {
    BETTING("BETTING"),
    SPINNING("SPINNING"),
    SETTLING("SETTLING"),
    GAME_OVER("GAME_OVER");

    private final String displayName;

    RouletteGameState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}


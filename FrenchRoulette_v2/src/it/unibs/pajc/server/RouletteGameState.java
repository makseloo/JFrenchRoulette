package it.unibs.pajc.server;
// BETTING-> SPINNING->SETTLING
public enum RouletteGameState {
    BETTING("Betting Phase"),
    SPINNING("Spinning Phase"),
    SETTLING("Settling Phase"),
    GAME_OVER("Game Over");

    private final String displayName;

    RouletteGameState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}


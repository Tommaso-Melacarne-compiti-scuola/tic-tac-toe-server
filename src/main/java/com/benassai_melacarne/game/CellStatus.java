package com.benassai_melacarne.game;

public enum CellStatus {
    EMPTY("0"),
    PLAYER_ONE("1"),
    PLAYER_TWO("2");

    private CellStatus(String message) {
        this.message = message;
    }

    public final String message;

    @Override
    public String toString() {
        return message;
    }
}
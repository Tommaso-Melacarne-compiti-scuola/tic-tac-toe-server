package com.benassai_melacarne.game;

public enum MoveResult {
    OK("OK"),
    KO("KO"),
    WIN("W"),
    DRAW("P");

    private MoveResult(String message) {
        this.message = message;
    }

    public final String message;

    @Override
    public String toString() {
        return message;
    }

    public boolean isFinished() {
        return this == WIN || this == DRAW;
    }
}
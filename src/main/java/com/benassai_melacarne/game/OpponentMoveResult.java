package com.benassai_melacarne.game;

public enum OpponentMoveResult {
    CONTINUE(""),
    LOSE("L"),
    DRAW("P");

    private OpponentMoveResult(String message) {
        this.message = message;
    }

    public final String message;

    @Override
    public String toString() {
        return message;
    }

    public static OpponentMoveResult fromMoveResult(MoveResult moveResult) {
        return switch (moveResult) {
            case WIN -> LOSE;
            case DRAW -> DRAW;
            case OK, KO -> CONTINUE;
            default -> CONTINUE;
        };
    }
}
package com.benassai_melacarne.game;

public class TicTacToeGame {
    private String player1, player2;

    private CellStatus[][] board;

    public TicTacToeGame(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;

        board = new CellStatus[3][3];

        emptyBoard(board);
    }

    public CellStatus[][] getBoard() {
        return board;
    }

    private static void emptyBoard(CellStatus[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = CellStatus.EMPTY;
            }
        }
    }

    public MoveResult move(String player, int pos) {        
        if (pos < 0 || pos > 8)
            return MoveResult.KO;

        int x = pos / 3;
        int y = pos % 3;

        if (board[x][y] != CellStatus.EMPTY)
            return MoveResult.KO;
        
        int playerIndex;
        if (player.equals(player1)) {
            board[x][y] = CellStatus.PLAYER_ONE;
            playerIndex = 1;
        }
        else if (player.equals(player2)) {
            board[x][y] = CellStatus.PLAYER_TWO;
            playerIndex = 2;
        }
        else {
            return MoveResult.KO;
        }

        switch (checkWinner()) {
            case 0:
                return MoveResult.DRAW;
            case 1:
                return playerIndex == 1 ? MoveResult.WIN : MoveResult.KO;
            case 2:
                return playerIndex == 2 ? MoveResult.WIN : MoveResult.KO;
            case -1:
                return MoveResult.OK;
            default:
                return MoveResult.KO;
        }
    }

    /**
    * @return
    * 1/2: Player 1/2 has won;
    * 0: Game ended in a draw;
    * -1: Game hasn't ended
    */
    private int checkWinner() {
        boolean winPlayer1, winPlayer2;

        // Horizontal
        for (int x = 0; x < 3; x++) {
            winPlayer1 = true;
            winPlayer2 = true;
            for (int y = 0; y < 3; y++) {
                if (board[x][y] != CellStatus.PLAYER_ONE)
                    winPlayer1 = false;
                if (board[x][y] != CellStatus.PLAYER_TWO)
                    winPlayer2 = false;
            }
            if (winPlayer1)
                return 1;
            if (winPlayer2)
                return 2;
        }

        // Vertical
        for (int y = 0; y < 3; y++) {
            winPlayer1 = true;
            winPlayer2 = true;
            for (int x = 0; x < 3; x++) {
                if (board[x][y] != CellStatus.PLAYER_ONE)
                    winPlayer1 = false;
                if (board[x][y] != CellStatus.PLAYER_TWO)
                    winPlayer2 = false;
            }
            if (winPlayer1)
                return 1;
            if (winPlayer2)
                return 2;
        }

        // Diagonals
        winPlayer1 = true;
        winPlayer2 = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] != CellStatus.PLAYER_ONE)
                winPlayer1 = false;
            if (board[i][i] != CellStatus.PLAYER_TWO)
                winPlayer2 = false;
        }
        if (winPlayer1)
            return 1;
        if (winPlayer2)
            return 2;
        

        winPlayer1 = true;
        winPlayer2 = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][2-i] != CellStatus.PLAYER_ONE)
                winPlayer1 = false;
            if (board[i][2-i] != CellStatus.PLAYER_TWO)
                winPlayer2 = false;
        }
        if (winPlayer1)
            return 1;
        if (winPlayer2)
            return 2;

        // Draw
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == CellStatus.EMPTY)
                    return -1;
            }
        }
        return 0;
    }
}
package com.benassai_melacarne.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import com.benassai_melacarne.game.CellStatus;
import com.benassai_melacarne.game.MoveResult;
import com.benassai_melacarne.game.OpponentMoveResult;
import com.benassai_melacarne.game.TicTacToeGame;

public class Server {
    final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            do {
                String p1Name = "Player 1", p2Name = "Player 2";

                PlayerSocketWrapper player1 = new PlayerSocketWrapper(p1Name, serverSocket.accept());
                player1.out.println("WAIT");
                System.out.println("First client connected");

                PlayerSocketWrapper player2 = new PlayerSocketWrapper(p2Name, serverSocket.accept());
                System.out.println("Second client connected");
                player1.out.println("READY");
                player2.out.println("READY");

                Thread watchDogThread = new Thread(new WatchDogRunnable(player1, player2));
                watchDogThread.start();

                TicTacToeGame game = new TicTacToeGame("Player 1", "Player 2");

                while (true) {
                    String move;
                    MoveResult result;

                    do {
                        move = player1.in.readLine();

                        try {
                            int pos = Integer.parseInt(move);
                            result = game.move(player1.name, pos);
                        } catch (NumberFormatException e) {
                            result = MoveResult.KO;
                        }

                        player1.out.println(result.toString());
                    } while (result == MoveResult.KO);

                    player2.out.println(buildOpponentResponse(result, game.getBoard()));

                    if (result.isFinished()) {
                        break;
                    }

                    do {
                        move = player2.in.readLine();

                        try {
                            int pos = Integer.parseInt(move);
                            result = game.move(player2.name, pos);
                        } catch (NumberFormatException e) {
                            result = MoveResult.KO;
                        }

                        player2.out.println(result.toString());
                    } while (result == MoveResult.KO);

                    player1.out.println(buildOpponentResponse(result, game.getBoard()));

                    if (result.isFinished()) {
                        break;
                    }
                }

                watchDogThread.interrupt();

                player1.socket.close();
                player2.socket.close();
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildOpponentResponse(MoveResult moveResult, CellStatus[][] board) {
        OpponentMoveResult opponentMoveResult = OpponentMoveResult.fromMoveResult(moveResult);

        StringBuilder response = new StringBuilder();

        response.append(parseMessageBoard(board));
        response.append("," + opponentMoveResult.toString());

        return response.toString();
    }

    private static String parseMessageBoard(CellStatus[][] board) {
        List<String> list = new ArrayList<>();

        for (CellStatus[] row : board) {
            for (CellStatus cell : row) {
                list.add(cell.toString());
            }
        }

        return String.join(",", list);
    }
}

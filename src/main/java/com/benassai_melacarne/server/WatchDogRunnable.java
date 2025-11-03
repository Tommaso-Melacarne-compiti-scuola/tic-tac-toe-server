package com.benassai_melacarne.server;

public class WatchDogRunnable implements Runnable {
    private static int TIMEOUT_MS = 1000;

    private PlayerSocketWrapper player1;
    private PlayerSocketWrapper player2;

    public WatchDogRunnable(PlayerSocketWrapper player1, PlayerSocketWrapper player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        System.out.println("WatchDog started for players " + player1.name + " and " + player2.name);

        while (true) {
            System.out.println("WatchDog checking sockets for players " + player1.name + " and " + player2.name);

            try {
                Thread.sleep(TIMEOUT_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            checkSocketAndCloseOtherHalf(player1, player2);
            checkSocketAndCloseOtherHalf(player2, player1);
        }
    }

    private static void checkSocketAndCloseOtherHalf(PlayerSocketWrapper toCheck, PlayerSocketWrapper toClose) {
        if (toCheck.socket.isInputShutdown()) {
            try {
                toClose.out.println("DISCONNECTED");
                toClose.socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

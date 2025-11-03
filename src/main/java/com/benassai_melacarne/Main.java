package com.benassai_melacarne;

import com.benassai_melacarne.server.Server;

public class Main {
    private static final int PORT = 3000;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        
        server.start();
    }
}
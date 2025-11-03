package com.benassai_melacarne.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerSocketWrapper {
    public final String name;
    public final Socket socket;
    public final BufferedReader in;
    public final PrintWriter out;

    public PlayerSocketWrapper(String name, Socket socket) throws IOException {
        this.name = name;
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }
}

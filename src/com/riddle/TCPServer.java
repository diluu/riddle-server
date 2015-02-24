package com.riddle;

import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.List;

public class TCPServer {
    private static TCPServer self;
    private static PrintWriter out;
    private static List<Riddle> allRiddles;

    public static void main(String args[]) {
        if (self == null) {
            self = new TCPServer();
        }
        RiddleContext.readRiddlesFile();
        allRiddles = RiddleContext.getAllRiddles();
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Socket socket = serverSocket.accept();
            System.out.println("Server has connected!");

            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));
            while (true) {
                String input = in.readLine();

                System.out.println("Received string: " + input);

                if (input.equalsIgnoreCase("hello")) {
                    sendString("" + allRiddles.size());
                } else if (input.equalsIgnoreCase("bye")) {
                    sendString("bye");
                    in.close();
                    out.close();
                    socket.close();
                    serverSocket.close();
                } else {
                    sendString(RiddleContext.processClientCommands(input));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendString(String data) {
        System.out.println("Sending string: " + data);
        out.println(data);
    }
}
package com.riddle;

import java.io.*;
import java.net.*;
import java.util.List;

class UDPServer {
    private static UDPServer self;
    private static DatagramSocket serverSocket;
    private static byte[] receiveData;
    private static byte[] sendData;
    private static List<Riddle> allRiddles;
    private static InetAddress IPAddress;
    private static int port;

    public static void main(String args[]) throws Exception {
        serverSocket = new DatagramSocket(9876);
        if (self == null) {
            self = new UDPServer();
        }
        RiddleContext.readRiddlesFile();
        allRiddles = RiddleContext.getAllRiddles();

        try {
            System.out.println("Server is Up!");
            while (true) {
                receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String input = new String(receivePacket.getData()).trim();
                IPAddress = receivePacket.getAddress();
                port = receivePacket.getPort();
                System.out.println("Received string: " + input);

                if (input.equalsIgnoreCase("hello")) {
                    sendString("" + allRiddles.size());
                } else if (input.equalsIgnoreCase("bye")) {
                    sendString("bye");
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
        try {
            System.out.println("Sending string: " + data);
            sendData = data.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
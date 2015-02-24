package com.riddle;

import java.net.*;
import java.util.Scanner;

class UDPClient {
    private static Scanner scanner;
    private static DatagramSocket clientSocket;
    private static InetAddress IPAddress;
    private static byte[] sendData;
    private static byte[] receiveData;

    public static void main(String args[]) throws Exception {

        try {
            scanner = new Scanner(System.in);
            System.out.println("=================== Welcome to the Riddle Server! ====================");
            if (connectToServer()) {
                String command = "";
                while (!command.equalsIgnoreCase("Bye")) {
                    System.out.println("Please type a command: ");
                    command = scanner.nextLine();
                    receiveData = new byte[1024];
                    sendData = RiddleContext.getClientMessage(command).getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                    clientSocket.send(sendPacket);
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String response = new String(receivePacket.getData()).trim();
                    System.out.println(response);
                }
                scanner.close();
                clientSocket.close();
            } else {
                System.out.println("Server Connection Failed......");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean connectToServer() {
        try {
            System.out.print("Say \"Hello\" to connect to Riddle Server: ");
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("Hello")) {
                clientSocket = new DatagramSocket();
                IPAddress = InetAddress.getByName("localhost");
                receiveData = new byte[1024];
                sendData = command.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                clientSocket.send(sendPacket);
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                String response = new String(receivePacket.getData()).trim();
                try {
                    Integer.parseInt(response);
                    System.out.println(response);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            } else {
                return connectToServer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

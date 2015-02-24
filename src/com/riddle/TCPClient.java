package com.riddle;

import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    private static Scanner scanner;
    private static PrintWriter out;
    private static BufferedReader in;
    private static Socket skt;

    public static void main(String args[]) {
        try {
            scanner = new Scanner(System.in);
            System.out.println("=================== Welcome to the Riddle Server! ====================");
            if (connectToServer()) {
                String command = "";
                while (!command.equalsIgnoreCase("Bye")) {
                    System.out.println("Please type a command: ");
                    command = scanner.nextLine();
                    out.println(RiddleContext.getClientMessage(command.toLowerCase()));
                    String response = in.readLine();
                    System.out.println(response);
                }
                in.close();
                out.close();
                scanner.close();
                skt.close();
            } else{
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
                skt = new Socket("localhost", 1234);
                out = new PrintWriter(skt.getOutputStream(), true);
                out.println(command);
                in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
                String response = in.readLine();
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
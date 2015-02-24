package com.riddle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class RiddleContext {

    private static final String RIDDLE = "riddle";
    private static final String MESSAGE_SEPARATOR = "#";
    private static final String ANSWER = "answer";
    private static final String UNKNOWN_MESSAGE = "Unknown Message";
    private static final String CORRECT = "Correct";
    private static final String INCORRECT = "Incorrect";
    private static List<Riddle> allRiddles = new ArrayList<Riddle>();

    public static String getClientMessage(String command){
       if(command.startsWith(RIDDLE)){
           StringBuilder messageBuilder = new StringBuilder();
           StringTokenizer commandTokenizer = new StringTokenizer(command, " ");
           String riddleToken = commandTokenizer.nextToken();
           if(riddleToken.equals(RIDDLE) && commandTokenizer.hasMoreTokens()){
               String idToken = commandTokenizer.nextToken();
               try {
                   int id = Integer.parseInt(idToken);
                   if(!commandTokenizer.hasMoreTokens()){
                       messageBuilder.append(riddleToken);
                       messageBuilder.append(MESSAGE_SEPARATOR);
                       messageBuilder.append(idToken);
                       return messageBuilder.toString();
                   }
               } catch (NumberFormatException e) {
               }
           }
       } else if(command.startsWith(ANSWER)){
           StringBuilder messageBuilder = new StringBuilder();
           StringTokenizer commandTokenizer = new StringTokenizer(command, " ");
           String answerToken = commandTokenizer.nextToken();
           if(answerToken.equals(ANSWER) && commandTokenizer.hasMoreTokens()){
              String idToken = commandTokenizer.nextToken();
               try {
                   int id = Integer.parseInt(idToken);
                   if(commandTokenizer.hasMoreTokens()){
                       String answerString = command.substring(answerToken.length() + idToken.length() + 2, command.length());
                       messageBuilder.append(answerToken);
                       messageBuilder.append(MESSAGE_SEPARATOR);
                       messageBuilder.append(idToken);
                       messageBuilder.append(MESSAGE_SEPARATOR);
                       messageBuilder.append(answerString);
                       return messageBuilder.toString();
                   }
               } catch (NumberFormatException e) {
               }
           }
       }
        return command;
    }

    public static String processClientCommands(String input) {
        StringTokenizer tokenizer = new StringTokenizer(input, "#");
        String token1 = tokenizer.nextToken();
        if (token1.equalsIgnoreCase(RIDDLE)) {
            if (tokenizer.hasMoreTokens()) {
                String idToken = tokenizer.nextToken();
                try {
                    int id = Integer.valueOf(idToken) - 1;
                    if (id>= 0 && id < allRiddles.size()) {
                        return allRiddles.get(id).getQuestion();
                    } else {
                       return UNKNOWN_MESSAGE;
                    }
                } catch (NumberFormatException e) {
                    return UNKNOWN_MESSAGE;
                }
            } else {
                int random = (int) (Math.random() * allRiddles.size());
                String randomRiddle = allRiddles.get(random).getQuestion();
                int riddleNo = random + 1;
                return "(" + riddleNo + ") " + randomRiddle;
            }
        } else if (token1.equalsIgnoreCase(ANSWER)) {
            if (tokenizer.hasMoreTokens()) {
                String idToken = tokenizer.nextToken();
                try {
                    int id = Integer.valueOf(idToken) - 1;
                    if (id >= 0 && id < allRiddles.size() && tokenizer.hasMoreTokens()) {
                        String answer = tokenizer.nextToken();
                        if (answer.equalsIgnoreCase(allRiddles.get(id).getAnswer())) {
                            return CORRECT;
                        } else {
                            return INCORRECT;
                        }
                    } else {
                        return UNKNOWN_MESSAGE;
                    }
                } catch (NumberFormatException e) {
                    return UNKNOWN_MESSAGE;
                }
            } else {
                return UNKNOWN_MESSAGE;
            }
        } else {
            return UNKNOWN_MESSAGE;
        }
    }

    public static void readRiddlesFile() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("riddles.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                StringTokenizer riddleTokenizer = new StringTokenizer(str, "|");
                Riddle riddle = new Riddle(riddleTokenizer.nextToken(), riddleTokenizer.nextToken());
                allRiddles.add(riddle);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Riddle> getAllRiddles() {
        return allRiddles;
    }
}

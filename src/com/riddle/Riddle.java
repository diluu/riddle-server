package com.riddle;

public class Riddle {
    private String question;
    private String answer;

    public Riddle(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}

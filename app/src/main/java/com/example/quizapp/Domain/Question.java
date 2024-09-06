package com.example.quizapp.Domain;

public class Question {
    String answer;
    String question;
    String[] options;

    public Question(String question, String ans, String[] choices) {
        this.question = question;
        this.answer = ans;
        this.options = choices;
    }
    public Question(){}

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}

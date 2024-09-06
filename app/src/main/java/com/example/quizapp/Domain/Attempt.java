package com.example.quizapp.Domain;

public class Attempt {
    String question;
    String[] options;
    String selectedOption;
    String answer;

    public Attempt(String question, String[] options, String selectedOption, String answer) {
        this.question = question;
        this.options = options;
        this.selectedOption = selectedOption;
        this.answer = answer;
    }

    public Attempt() {
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

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

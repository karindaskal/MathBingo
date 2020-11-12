package com.example.androidbingoproject;

public class QA {

    private int firstNumber1;
    private int secondNumber1;
    private int answer;
    private char operation;
    private String questionPharse;
    private int deleted = 0;

    public QA() {
    }

    public QA(int firstNumber, int secondNumber, int answer, char operation, int deleted) {
        this.firstNumber1 = firstNumber;
        this.secondNumber1 = secondNumber;
        this.answer = answer;
        this.operation = operation;
        this.questionPharse = firstNumber1 + operation + secondNumber1 + " = ";
        this.deleted = deleted;
    }

    public char getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }

    public String getQuestionPharse() {
        return questionPharse;
    }

    public void setQuestionPharse(String questionPharse) {
        this.questionPharse = questionPharse;
    }

    public int getFirstNumber1() {
        return firstNumber1;
    }

    public void setFirstNumber1(int firstNumber) {
        this.firstNumber1 = firstNumber;
    }

    public int getSecondNumber1() {
        return secondNumber1;
    }

    public void setSecondNumber1(int secondNumber) {
        this.secondNumber1 = secondNumber;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}

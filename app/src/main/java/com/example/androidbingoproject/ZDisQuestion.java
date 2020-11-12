package com.example.androidbingoproject;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ZDisQuestion {

    /*private int firstNumber;
    private int secondNumber;
    private int answer;*/
    private QA classQa;

    //there are 25 possible choices for the user to pick from
    private List<Integer> answerArray = new ArrayList<Integer>();

    private List<QA> arrayList = new ArrayList<QA>();

    //the maximum value of firstNumber or secondNumber
    private int upperLimit;

    //string output of the problem e.g 4+9 =
    private String questionPharse;


    public ZDisQuestion(int upperLimit, String oper1, int board){

        this.upperLimit = upperLimit;

        Random randomNumberMaker = new Random();

        classQa = new QA();

        switch (oper1){
            case "+":
                for(int i = 0; i < board; i++){

                    classQa = new QA();
                    int num1 = randomNumberMaker.nextInt(upperLimit);
                    classQa.setFirstNumber1(num1);
                    int num2 = randomNumberMaker.nextInt(upperLimit);
                    classQa.setSecondNumber1(num2);
                    classQa.setAnswer(num1 + num2);
                    classQa.setQuestionPharse(num1 + " + " + num2 + " = ");

                    if(arrayList == null) {
                        arrayList = new ArrayList<QA>();
                    }

                    arrayList.add(classQa);

                }
                break;
            case "-":
                for(int i = 0; i < board; i++){

                    classQa = new QA();
                    int num1 = randomNumberMaker.nextInt(upperLimit);
                    classQa.setFirstNumber1(num1);
                    int num2 = randomNumberMaker.nextInt(upperLimit);
                    classQa.setSecondNumber1(num2);
                    int big, small;

                    int difference = num1 > num2 ? num1 - num2 : num2 - num1;

                    if(num1 > num2) {
                        difference = num1 - num2;
                        big = num1;
                        small = num2;
                    }
                    else {
                        difference = num2 - num1;
                        big = num2;
                        small = num1;
                    }

                    classQa.setAnswer(difference);

                    classQa.setQuestionPharse(big + " - " + small + " = ");

                    if(arrayList == null) {
                        arrayList = new ArrayList<QA>();
                    }

                    arrayList.add(classQa);

                }
                break;

            case "*":
                for(int i = 0; i < board; i++){

                    classQa = new QA();
                    int num1 = randomNumberMaker.nextInt(upperLimit);
                    classQa.setFirstNumber1(num1);
                    int num2 = randomNumberMaker.nextInt(upperLimit);
                    classQa.setSecondNumber1(num2);
                    classQa.setAnswer(num1 * num2);
                    classQa.setQuestionPharse(num1 + " X " + num2 + " = ");

                    if(arrayList == null) {
                        arrayList = new ArrayList<QA>();
                    }

                    arrayList.add(classQa);

                }
                break;

            case "/":
                for(int i = 0; i < board; i++){

                    classQa = new QA();
                    int num1;
                    int num2;

                    ArrayList<Integer> factors = new ArrayList<Integer>();

                    do {
                        num1 = randomNumberMaker.nextInt(upperLimit);
                        classQa.setFirstNumber1(num1);

                        int limitDiv = num1;
                        for (int j = 2; j < limitDiv; j++) {
                            if (num1 % j == 0) {
                                factors.add(j);
                                limitDiv = num1 / j;
                                factors.add(limitDiv);
                            }
                        }
                    } while (factors.size() == 0); //if num1 is prime

                    int index = randomNumberMaker.nextInt(factors.size());
                    num2 = factors.get(index);
                    //ans = num1 / num2;

                    //int num2 = randomNumberMaker.nextInt(upperLimit);
                    classQa.setSecondNumber1(num2);
                    classQa.setAnswer(num1 / num2);
                    classQa.setQuestionPharse(num1 + " : " + num2 + " = ");

                    if(arrayList == null) {
                        arrayList = new ArrayList<QA>();
                    }

                    arrayList.add(classQa);

                }
                break;

        }

    }



    public QA getClassQa() {
        return classQa;
    }

    public void setClassQa(QA classQa) {
        this.classQa = classQa;
    }

    public List<Integer> getAnswerArray() {
        return answerArray;
    }

    public void setAnswerArray(List<Integer> answerArray) {
        this.answerArray = answerArray;
    }

    public List<QA> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<QA> arrayList) {
        this.arrayList = arrayList;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getQuestionPharse() {
        return questionPharse;
    }

    public void setQuestionPharse(String questionPharse) {
        this.questionPharse = questionPharse;
    }






}


 /*   public Question(int firstNumber, int secondNumber, int answer) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.answer = answer;
    }
*/

//which of the 25 position is correct 0,1,2,....
//private int[][] answerPosition;

//array that contain all answer
//private Question[][] questionArray;



    /*public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }*/

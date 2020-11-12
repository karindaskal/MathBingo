package com.example.androidbingoproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game  {

    private List<QA> questionList;

    private int score;
    private List<QA> arrayList = new ArrayList<>();
    private QA classQa;
    private String oper;
    private int limit;
    private int board;

    public Game(int levelLimit, String opi1, int board1){
        score = 0;
        oper = opi1;
        limit = levelLimit;
        board = board1;
        questionList = Question(limit,oper,board);
    }

    public List<QA> Question(int upperLimit, String oper1, int board){

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
        return arrayList;

    }









/*
    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }*/


    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getBoard() {
        return board;
    }

    public void setBoard(int board) {
        this.board = board;
    }

    public List<QA> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QA> questionList) {
        this.questionList = questionList;
    }

    public List<QA> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<QA> arrayList) {
        this.arrayList = arrayList;
    }

    public QA getClassQa() {
        return classQa;
    }

    public void setClassQa(QA classQa) {
        this.classQa = classQa;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}



/*

    public void makeNewQuestion(){
        currentQuestion = new Question(totalQuestions * 2 + 5,oper,board);
        totalQuestions++;
        questionList.add(currentQuestion);
    }
*/
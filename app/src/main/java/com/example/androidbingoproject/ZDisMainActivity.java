package com.example.androidbingoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ZDisMainActivity extends AppCompatActivity {

    private static final int[] idArray =
            {R.id.btn_0_0, R.id.btn_0_1, R.id.btn_0_2, R.id.btn_0_3, R.id.btn_0_4
            , R.id.btn_1_0, R.id.btn_1_1, R.id.btn_1_2, R.id.btn_1_3, R.id.btn_1_4
            , R.id.btn_2_0, R.id.btn_2_1, R.id.btn_2_2, R.id.btn_2_3, R.id.btn_2_4
            , R.id.btn_3_0, R.id.btn_3_1, R.id.btn_3_2, R.id.btn_3_3, R.id.btn_3_4
            , R.id.btn_4_0, R.id.btn_4_1, R.id.btn_4_2, R.id.btn_4_3, R.id.btn_4_4};

    private Button[] buttons = new Button[idArray.length];

    TextView mathTv;
    ImageView mathIv;

    String opi;
    String level;
    Game g;

    int i = 0;

    String buttonsText;
    int buttonsValue;


    String answerText;
    int answerNum;

    int finalI;
    ArrayList numbers;

    int score;

    int counterDiagonalRight = 0;
    int counterDiagonalLeft = 0;
    int counterRow1 = 0;
    int counterRow2 = 0;
    int counterRow3 = 0;
    int counterRow4 = 0;
    int counterRow5 = 0;
    int counterCol1 = 0;
    int counterCol2 = 0;
    int counterCol3 = 0;
    int counterCol4 = 0;
    int counterCol5 = 0;

    int counterDiagonalRightMoves = 5;
    int counterDiagonalLeftMoves = 5;
    int counterRow1Moves = 5;
    int counterRow2Moves = 5;
    int counterRow3Moves = 5;
    int counterRow4Moves = 5;
    int counterRow5Moves = 5;
    int counterCol1Moves = 5;
    int counterCol2Moves = 5;
    int counterCol3Moves = 5;
    int counterCol4Moves = 5;
    int counterCol5Moves = 5;

    ImageView img;

    MediaPlayer mpCorrect;
    MediaPlayer mpWrong;

    Boolean noMusic;

    int questionArraySize;

    SharedPreferences sp1;
    SharedPreferences sp2;

    List<Map<String,Object>> datae;
    List<Map<String,Object>> datam ;
    List<Map<String,Object>> datah;
    List<List<Map<String,Object>>> data ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        level = getIntent().getExtras().getString("level");

        if(level.equals("easy")){
            setContentView(R.layout.board4x4);
            g = new Game(10,opi,16);


        }

        else if(level.equals("medium")){
            setContentView(R.layout.activity_main);
            g = new Game(10,opi,25);

        }

        else if(level.equals("hard")){
            setContentView(R.layout.board6x6);
            g = new Game(10,opi,36);

        }


        sp1 = getSharedPreferences("dialog1",MODE_PRIVATE);
        sp2 = getSharedPreferences("name",MODE_PRIVATE);

        noMusic = sp2.getBoolean("noMusic",false);


        if ((sp2.getBoolean("noMusic",false))){
            stopService(new Intent(ZDisMainActivity.this, myMusic.class));
        }
        else {
            startService(new Intent(ZDisMainActivity.this, myMusic.class));
        }


        score = getIntent().getExtras().getInt("score");
        opi = getIntent().getExtras().getString("oper");

        level = getIntent().getExtras().getString("level");

        /*if(level.equals("easy")){
            g = new Game(10,opi);
        }

        else if(level.equals("medium")){
            g = new Game(25, opi);
        }

        else if(level.equals("hard")){
            g = new Game(50, opi);
        }*/

        mpCorrect = MediaPlayer.create(this, R.raw.correct_short);
        mpWrong = MediaPlayer.create(this, R.raw.wrong_short);

        mathTv = findViewById(R.id.math_text_view);
        mathIv = findViewById(R.id.math_iv);

        mathTv.setText(g.getArrayList().get(0).getQuestionPharse());

        questionArraySize = g.getArrayList().size();

        finalI = 0;
        //array list to get uniqe random number for the buttons
        numbers = new ArrayList();
        for (int i = 0; i < 25; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        if((sp1.getBoolean("first_run",true))){

            explainDialog(ZDisMainActivity.this);
        }

        try {
            FileInputStream fis = openFileInput("player");
            ObjectInputStream ois =new ObjectInputStream(fis);
            data = (List<List<Map<String,Object>>>)ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (i = 0; i < questionArraySize; i++) {

            buttons[i] = (Button) findViewById(idArray[i]);

            buttonsValue = g.getArrayList().get((Integer) numbers.get(i)).getAnswer();
            buttonsText = Integer.toString(buttonsValue);

            buttons[i].setText(buttonsText);

            buttons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (v.getId()) {

                        case R.id.btn_0_0:
                            if (buttons[0].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {

                                buttons[0].setEnabled(false);

                                correctBtnPressed(v,0);

                                counterDiagonalRight++;
                                counterCol1++;
                                counterRow1++;

                                if (counterDiagonalRight == 5 || counterCol1 == 5 || counterRow1 == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[0].setVisibility(View.INVISIBLE);

                                counterDiagonalRightMoves--;
                                counterCol1Moves--;
                                counterRow1Moves--;

                                notCorectBtn(0);
                            }
                            break;

                        case R.id.btn_0_1:
                            if (buttons[1].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[1].setEnabled(false);
                                correctBtnPressed(v, 1);

                                counterCol2++;
                                counterRow1++;

                                if (counterCol2 == 5 || counterRow1 == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[1].setVisibility(View.INVISIBLE);

                                counterCol2Moves--;
                                counterRow1Moves--;

                                notCorectBtn(1);
                            }
                            break;

                        case R.id.btn_0_2:
                            if (buttons[2].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[2].setEnabled(false);
                                correctBtnPressed(v, 2);

                                counterCol3++;
                                counterRow1++;

                                if (counterCol3 == 5 || counterRow1 == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }
                            }
                            else{
                                buttons[2].setVisibility(View.INVISIBLE);

                                counterCol3Moves--;
                                counterRow1Moves--;

                                notCorectBtn(2);
                            }
                            break;


                        case R.id.btn_0_3:
                            if (buttons[3].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[3].setEnabled(false);
                                correctBtnPressed(v, 3);

                                counterCol4++;
                                counterRow1++;

                                if (counterCol4 == 5 || counterRow1 == 5) {
                                    bingoAlert();
                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[3].setVisibility(View.INVISIBLE);

                                counterCol4Moves--;
                                counterRow1Moves--;

                                notCorectBtn(3);
                            }
                            break;


                        case R.id.btn_0_4:
                            if (buttons[4].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[4].setEnabled(false);
                                correctBtnPressed(v, 4);

                                counterCol5++;
                                counterRow1++;
                                counterDiagonalLeft++;

                                if (counterCol5 == 5 || counterRow1 == 5 || counterDiagonalLeft == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[4].setVisibility(View.INVISIBLE);

                                counterCol5Moves--;
                                counterRow1Moves--;
                                counterDiagonalLeftMoves--;
                                notCorectBtn(4);
                            }
                            break;

                        case R.id.btn_1_0:
                            if (buttons[5].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[5].setEnabled(false);
                                correctBtnPressed(v, 5);

                                counterCol1++;
                                counterRow2++;

                                if (counterCol1 == 5 || counterRow2 == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }
                            }
                            else{
                                buttons[5].setVisibility(View.INVISIBLE);

                                counterCol1Moves--;
                                counterRow2Moves--;

                                notCorectBtn(5);
                            }
                            break;


                        case R.id.btn_1_1:
                            if (buttons[6].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[6].setEnabled(false);
                                correctBtnPressed(v, 6);

                                counterDiagonalRight++;
                                counterRow2++;
                                counterCol2++;

                                if (counterCol2 == 5 || counterRow2 == 5 || counterDiagonalRight == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }
                            }
                            else{
                                buttons[6].setVisibility(View.INVISIBLE);

                                counterDiagonalRightMoves--;
                                counterRow2Moves--;
                                counterCol2Moves--;

                                notCorectBtn(6);

                            }
                            break;


                        case R.id.btn_1_2:
                            if (buttons[7].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[7].setEnabled(false);
                                correctBtnPressed(v, 7);

                                counterRow2++;
                                counterCol3++;

                                if (counterCol3 == 5 || counterRow2 == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[7].setVisibility(View.INVISIBLE);

                                counterRow2Moves--;
                                counterCol3Moves--;

                                notCorectBtn(7);
                            }
                            break;

                        case R.id.btn_1_3:
                            if (buttons[8].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[8].setEnabled(false);
                                correctBtnPressed(v, 8);

                                counterDiagonalLeft++;
                                counterCol4++;
                                counterRow2++;

                                if (counterCol4 == 5 || counterRow2 == 5 || counterDiagonalLeft == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[8].setVisibility(View.INVISIBLE);

                                counterDiagonalLeftMoves--;
                                counterCol4Moves--;
                                counterRow2Moves--;

                                notCorectBtn(8);
                            }
                            break;

                        case R.id.btn_1_4:
                            if (buttons[9].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[9].setEnabled(false);
                                correctBtnPressed(v, 9);

                                counterCol5++;
                                counterRow2++;

                                if (counterCol5 == 5 || counterRow2 == 5) {
                                    bingoAlert();
                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[9].setVisibility(View.INVISIBLE);

                                counterCol5Moves--;
                                counterRow2Moves--;

                                notCorectBtn(9);
                            }
                            break;


                        case R.id.btn_2_0:
                            if (buttons[10].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[10].setEnabled(false);
                                correctBtnPressed(v, 10);

                                counterRow3++;
                                counterCol1++;

                                if (counterCol1 == 5 || counterRow3 == 5) {
                                    bingoAlert();
                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[10].setVisibility(View.INVISIBLE);

                                counterRow3Moves--;
                                counterCol1Moves--;

                                notCorectBtn(10);
                            }
                            break;


                        case R.id.btn_2_1:
                            if (buttons[11].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[11].setEnabled(false);
                                correctBtnPressed(v, 11);

                                counterCol2++;
                                counterRow3++;

                                if (counterCol2 == 5 || counterRow3 == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[11].setVisibility(View.INVISIBLE);

                                counterCol2Moves--;
                                counterRow3Moves--;

                                notCorectBtn(11);

                            }
                            break;

                        case R.id.btn_2_2:
                            if (buttons[12].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[12].setEnabled(false);
                                correctBtnPressed(v, 12);

                                counterDiagonalLeft++;
                                counterDiagonalRight++;
                                counterCol3++;
                                counterRow3++;

                                if (counterCol3 == 5 || counterRow3 == 5 || counterDiagonalLeft == 5 || counterDiagonalRight == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }
                            }
                            else{
                                buttons[12].setVisibility(View.INVISIBLE);

                                counterDiagonalLeftMoves--;
                                counterDiagonalRightMoves--;
                                counterCol3Moves--;
                                counterRow3Moves--;

                                notCorectBtn(12);
                            }
                            break;

                        case R.id.btn_2_3:
                            if (buttons[13].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[13].setEnabled(false);
                                correctBtnPressed(v, 13);

                                counterCol4++;
                                counterRow3++;

                                if (counterCol4 == 5 || counterRow3 == 5) {
                                    bingoAlert();
                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[13].setVisibility(View.INVISIBLE);

                                counterCol4Moves--;
                                counterRow3Moves--;

                                notCorectBtn(13);
                            }
                            break;

                        case R.id.btn_2_4:
                            if (buttons[14].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[14].setEnabled(false);
                                correctBtnPressed(v, 14);

                                counterCol5++;
                                counterRow3++;

                                if (counterCol5 == 5 || counterRow3 == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[14].setVisibility(View.INVISIBLE);

                                counterCol5Moves--;
                                counterRow3Moves--;

                                notCorectBtn(14);
                            }
                            break;

                        case R.id.btn_3_0:
                            if (buttons[15].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[15].setEnabled(false);
                                correctBtnPressed(v, 15);

                                counterCol1++;
                                counterRow4++;

                                if (counterCol1 == 5 || counterRow4 == 5) {
                                    bingoAlert();
                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[15].setVisibility(View.INVISIBLE);

                                counterCol1Moves--;
                                counterRow4Moves--;

                                notCorectBtn(15);
                            }
                            break;

                        case R.id.btn_3_1:
                            if (buttons[16].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[16].setEnabled(false);
                                correctBtnPressed(v, 16);

                                counterCol2++;
                                counterRow4++;
                                counterDiagonalLeft++;

                                if (counterCol2 == 5 || counterRow4 == 5 || counterDiagonalLeft == 5) {
                                    bingoAlert();
                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }


                            }
                            else{
                                buttons[16].setVisibility(View.INVISIBLE);

                                counterCol2Moves--;
                                counterRow4Moves--;
                                counterDiagonalLeftMoves--;

                                notCorectBtn(16);
                            }
                            break;

                        case R.id.btn_3_2:
                            if (buttons[17].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[17].setEnabled(false);
                                correctBtnPressed(v, 17);

                                counterCol3++;
                                counterRow4++;

                                if (counterCol3 == 5 || counterRow4 == 5) {
                                    bingoAlert();
                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[17].setVisibility(View.INVISIBLE);

                                counterCol3Moves--;
                                counterRow4Moves--;

                                notCorectBtn(17);
                            }
                            break;

                        case R.id.btn_3_3:
                            if (buttons[18].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[18].setEnabled(false);
                                correctBtnPressed(v, 18);

                                counterDiagonalRight++;
                                counterCol4++;
                                counterRow4++;

                                if (counterCol4 == 5 || counterRow4 == 5 || counterDiagonalRight == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[18].setVisibility(View.INVISIBLE);

                                counterDiagonalRightMoves--;
                                counterCol4Moves--;
                                counterRow4Moves--;

                                notCorectBtn(18);
                            }
                            break;

                        case R.id.btn_3_4:
                            if (buttons[19].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[19].setEnabled(false);
                                correctBtnPressed(v, 19);

                                counterRow4++;
                                counterCol5++;

                                if (counterCol5 == 5 || counterRow4 == 5) {
                                    bingoAlert();
                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[19].setVisibility(View.INVISIBLE);

                                counterRow4Moves--;
                                counterCol5Moves--;

                                notCorectBtn(19);
                            }
                            break;


                        case R.id.btn_4_0:
                            if (buttons[20].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[20].setEnabled(false);
                                correctBtnPressed(v, 20);

                                counterCol1++;
                                counterRow5++;
                                counterDiagonalLeft++;

                                if (counterCol1 == 5 || counterRow5 == 5 || counterDiagonalLeft == 5) {
                                    bingoAlert();
                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }
                            }
                            else{
                                buttons[20].setVisibility(View.INVISIBLE);

                                counterCol1Moves--;
                                counterRow5Moves--;
                                counterDiagonalLeftMoves--;

                                notCorectBtn(20);
                            }
                            break;


                        case R.id.btn_4_1:
                            if (buttons[21].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[21].setEnabled(false);
                                correctBtnPressed(v, 21);

                                counterCol2++;
                                counterRow5++;

                                if (counterCol2 == 5 || counterRow5 == 5) {
                                    bingoAlert();
                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }


                            }
                            else{
                                buttons[21].setVisibility(View.INVISIBLE);

                                counterCol2Moves--;
                                counterRow5Moves--;

                                notCorectBtn(21);
                            }
                            break;

                        case R.id.btn_4_2:
                            if (buttons[22].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[22].setEnabled(false);
                                correctBtnPressed(v, 22);

                                counterCol3++;
                                counterRow5++;

                                if (counterCol3 == 5 || counterRow5 == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[22].setVisibility(View.INVISIBLE);

                                counterCol3Moves--;
                                counterRow5Moves--;

                                notCorectBtn(22);
                            }
                            break;


                        case R.id.btn_4_3:
                            if (buttons[23].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {


                                buttons[23].setEnabled(false);
                                correctBtnPressed(v, 23);

                                counterCol4++;
                                counterRow5++;

                                if (counterCol4 == 5 || counterRow5 == 5) {
                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[23].setVisibility(View.INVISIBLE);

                                counterCol4Moves--;
                                counterRow5Moves--;

                                notCorectBtn(23);
                            }
                            break;


                        case R.id.btn_4_4:
                            if (buttons[24].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {

                                buttons[24].setEnabled(false);
                                correctBtnPressed(v, 24);

                                counterDiagonalRight++;
                                counterCol5++;
                                counterRow5++;

                                if (counterCol5 == 5 || counterRow5 == 5 || counterDiagonalRight == 5) {

                                    bingoAlert();

                                }
                                else {
                                    if(g.getArrayList().get(finalI) == null){
                                        for(int k = finalI; k < questionArraySize; k++){
                                            finalI= k;
                                            if(g.getArrayList().get(finalI) != null){
                                                mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        mathTv.setText(g.getArrayList().get(finalI).getQuestionPharse());
                                    }
                                }

                            }
                            else{
                                buttons[24].setVisibility(View.INVISIBLE);

                                counterDiagonalRightMoves--;
                                counterCol5Moves--;
                                counterRow5Moves--;

                                notCorectBtn(24);
                            }
                            break;

                    }
                }
            });
        }
    }


   public void correctBtnPressed(View vbtn, int indexBtn){

        if(!(sp2.getBoolean("no_noise",false))){
            mpCorrect.start();

        }

       buttons[indexBtn].setText("");
       YoYo.with(Techniques.Bounce).duration(700).repeat(1).playOn(buttons[indexBtn]);
       vbtn.setBackground(getResources().getDrawable(R.drawable.bear));

       score +=5;
       g.setScore(score);

       finalI++;

    }

    public void notCorectBtn(int indexBtn){

        if(!(sp2.getBoolean("no_noise",false))){
            mpWrong.start();
        }

        if (noMoreMoves(counterDiagonalRightMoves,counterDiagonalLeftMoves,counterRow1Moves,counterRow2Moves,
                counterRow3Moves, counterRow4Moves,counterRow5Moves,counterCol1Moves,counterCol2Moves,
                counterCol3Moves,counterCol4Moves,counterCol5Moves)){
            mathTv.setText(R.string.no_more_moves);

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    showDialog(ZDisMainActivity.this,"dialog");
                }
            }, 3000);
        }

        for(int i = 0 ; i < questionArraySize ; i++){
            if((g.getArrayList().get(i) != null) && buttons[indexBtn].getText().toString().equals(Integer.toString(g.getArrayList().get(i).getAnswer()))){
                if(g.getArrayList().get(i) != null){
                    //g.getArrayList().remove(i);
                    g.getArrayList().set(i,null);
                    break;
                }

            }
        }
    }

    public void bingoAlert() {
        mathTv.setText("");
        mathIv.setVisibility(View.INVISIBLE);
        YoYo.with(Techniques.Tada).duration(1000).repeat(1).playOn(mathTv);
        mathTv.setBackground(getResources().getDrawable(R.drawable.bingo1));
        score +=100;
        g.setScore(score);



        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                showDialog(ZDisMainActivity.this,"dialog");
            }
        }, 3000);


        if(data==null) {
            data =new ArrayList<>();
            datae =new ArrayList<>();
            datam =new ArrayList<>();
            datah =new ArrayList<>();
            data.add(datae);
            data.add(datam);
            data.add(datah);
        }
        //add to easy
        HashMap<String,Object> plyer =new HashMap<>();

        plyer.put("name",sp2.getString("name_player", String.valueOf(R.string.name_player)));
        plyer.put("kind",opi);
        plyer.put("score",Integer.toString(score));

        if(level.equals("easy")){
            if(datae==null){
                datae =new ArrayList<>();
            }
            data.get(0).add(plyer);

        }
        else if(level.equals("medium")){
            if(datam==null)
                datam =new ArrayList<>();

            data.get(1).add(plyer);

        }
        else if(level.equals("hard")){
            if(datah==null)
                datah =new ArrayList<>();
            data.get(2).add(plyer);
        }




    }


    public boolean noMoreMoves(int counterDiagonalRightMoves,int counterDiagonalLeftMoves, int counterRow1Moves, int counterRow2Moves,
            int counterRow3Moves, int counterRow4Moves, int counterRow5Moves, int counterCol1Moves, int counterCol2Moves,
            int counterCol3Moves, int counterCol4Moves, int counterCol5Moves){

        if (counterDiagonalRightMoves < 5 && counterDiagonalLeftMoves < 5 && counterRow1Moves < 5 &&
        counterRow2Moves < 5 && counterRow3Moves < 5 && counterRow4Moves < 5 && counterRow5Moves < 5 && counterCol1Moves < 5 &&
        counterCol2Moves < 5 && counterCol3Moves < 5 && counterCol4Moves < 5 &&  counterCol5Moves < 5){

           return true;
        }
        else{
            return false;
        }

    }

    //@SuppressLint("SetTextI18n")
    public void showDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.scroll_paper_img);

        TextView scoreTv = (TextView)dialog.findViewById(R.id.score_tv);

        Button continueBtn = (Button)dialog.findViewById(R.id.continue_btn);
        Button exitBtn = (Button)dialog.findViewById(R.id.exit_btn);

      continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("score",score);
                finish();
                startActivity(intent);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //imageView.setBackground(getResources().getDrawable(R.drawable.scroll_paper));

//        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //score = Integer.parseInt(num_of_ball.getText().toString());
        score = g.getScore();
        String scoreString = getString(R.string.Your_score_is);
        scoreTv.setText(scoreString + " " + score);



        dialog.show();
    }

    public void explainDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bingo_explain_dialog);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.explain_iv);

        TextView scoreTv = (TextView)dialog.findViewById(R.id.five_tv);

        Button continueBtn = (Button)dialog.findViewById(R.id.lets_play_btn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }


    @Override
    protected void onPause() {
        super.onPause();

        stopService(new Intent(ZDisMainActivity.this, myMusic.class));


        SharedPreferences.Editor editor = sp1.edit();
        editor.putBoolean("first_run", false);
        editor.apply();

        try {
            FileOutputStream fos =openFileOutput("player",MODE_PRIVATE);
            ObjectOutputStream oos =new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        stopService(new Intent(ZDisMainActivity.this, myMusic.class));

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (noMusic) {
            stopService(new Intent(ZDisMainActivity.this, myMusic.class));
        } else {
            startService(new Intent(ZDisMainActivity.this, myMusic.class));
        }
    }
}

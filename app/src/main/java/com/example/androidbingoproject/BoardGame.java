package com.example.androidbingoproject;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

public class BoardGame extends Activity{

    private static final int[] idArray =
            {R.id.btn_0_0, R.id.btn_0_1, R.id.btn_0_2, R.id.btn_0_3, R.id.btn_1_0
                    , R.id.btn_1_1, R.id.btn_1_2, R.id.btn_1_3, R.id.btn_2_0, R.id.btn_2_1
                    , R.id.btn_2_2, R.id.btn_2_3, R.id.btn_3_0, R.id.btn_3_1, R.id.btn_3_2
                    , R.id.btn_3_3};

    private Button[] buttons ;

    TextView mathTv;
    ImageView mathIv;
    TableRow row1;
    TableLayout table1;


    String opi;
    String level;

    int levelNumber;

    int levelToOpenPlus;
    int levelToOpenMinus;
    int levelToOpenMult;
    int levelToOpenDiv;

    Game g;

    int btnTxt;

    int i = 0;

    String buttonsText;
    int buttonsValue;

    String playerName;

    int finalI;
    ArrayList numbers;
    ArrayList numbers2;

    int score;

    //counters for bingo
    int counterDiagonalRight = 0;
    int counterDiagonalLeft = 0;
    int counterRow1 = 0;
    int counterRow2 = 0;
    int counterRow3 = 0;
    int counterRow4 = 0;
    int counterCol1 = 0;
    int counterCol2 = 0;
    int counterCol3 = 0;
    int counterCol4 = 0;


    //counters for out of moves
    int counterDiagonalRightMoves = 4;
    int counterDiagonalLeftMoves = 4;
    int counterRow1Moves = 4;
    int counterRow2Moves = 4;
    int counterRow3Moves = 4;
    int counterRow4Moves = 4;
    int counterCol1Moves = 4;
    int counterCol2Moves = 4;
    int counterCol3Moves = 4;
    int counterCol4Moves = 4;
    int sizeB;

    String nameOfPlayer;

    EditText namePlayer;

    ImageView img;

    MediaPlayer mpCorrect;
    MediaPlayer mpWrong;

    Boolean noMusic;

    int questionArraySize;

    SharedPreferences sp1;
    SharedPreferences sp2;
    bingoR bingoR;

    //for record table
    List<Map<String,Object>> datae;
    List<Map<String,Object>> datam ;
    List<Map<String,Object>> datah;
    List<List<Map<String,Object>>> data ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.board4x4);
        Button b =new Button(BoardGame.this);
        table1=(TableLayout)findViewById(R.id.Table);


        sp1 = getSharedPreferences("dialog1", MODE_PRIVATE);
        sp2 = getSharedPreferences("name", MODE_PRIVATE);
        nameOfPlayer = sp1.getString("name_player", "");

        playerName = sp2.getString("name_player", String.valueOf(R.string.name_player));

        levelToOpenPlus = sp1.getInt("level_to_open_+", 0);
        levelToOpenMinus = sp1.getInt("level_to_open_-", 0);
        levelToOpenMult = sp1.getInt("level_to_open_*", 0);
        levelToOpenDiv = sp1.getInt("level_to_open_/", 0);

        noMusic = sp2.getBoolean("noMusic", false);
        mathTv = findViewById(R.id.math_text_view);
        mathIv = findViewById(R.id.math_iv);


        score = getIntent().getExtras().getInt("score");
        opi = getIntent().getExtras().getString("oper");

        level = getIntent().getExtras().getString("level");
        switch (level){
            case "easy":

               sizeB=4;
                break;
            case "medium":
               sizeB=5;
                break;
            case "hard":
              sizeB=6;
                break;


        }
         bingoR =new bingoR(sizeB);
        buttons =new Button[sizeB*sizeB];

        //for 1-6
        levelNumber = getIntent().getExtras().getInt("game_level");

        TextView levelName = findViewById(R.id.level_tv);
        levelName.setText(getResources().getString(R.string.level_number) + " " + (levelNumber + 1));

        switch (levelNumber) {
            case 0:
                g = new Game(10, opi, sizeB*sizeB);
                break;
            case 1:
                g = new Game(15, opi, sizeB*sizeB);
                break;
            case 2:
                g = new Game(20, opi, sizeB*sizeB);
                break;
            case 3:
                g = new Game(25, opi, sizeB*sizeB);
                break;
            case 4:
                g = new Game(30, opi, sizeB*sizeB);
                break;
            case 5:
                g = new Game(35, opi, sizeB*sizeB);
                break;
        }


        mpCorrect = MediaPlayer.create(this, R.raw.correct_short);
        mpWrong = MediaPlayer.create(this, R.raw.wrong_short);



        mathTv.setText(g.getArrayList().get(0).getQuestionPharse());

        questionArraySize = g.getArrayList().size();


        finalI = 0;
        //array list to get uniqe random number for the buttons
        numbers = new ArrayList();
        for (int i = 0; i < sizeB*sizeB; i++) {
            numbers.add(i);
        }

        numbers2 = new ArrayList();
        numbers2 = numbers;

        Collections.shuffle(numbers);

        if ((sp1.getBoolean("first_run", true))) {

            explainDialog(BoardGame.this);
        }

        try {
            FileInputStream fis = openFileInput("player");
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (List<List<Map<String, Object>>>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        table1.setWeightSum(sizeB);
        int l=0;
        for (i=0;i<sizeB;i++){
            row1=new TableRow(BoardGame.this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,0,1f);
            row1.setLayoutParams(lp);
            for(int j=0;j<sizeB;j++){
                buttons[l] =new Button(BoardGame.this);
                buttons[l].setId(l);
                TableRow.LayoutParams layoutParams =new TableRow.LayoutParams(0,100,1f);
                layoutParams.setMargins(50,10,0,0);
                buttons[l].setLayoutParams(layoutParams);

               // buttons[l].setScaleX(Button.SCALE_X.);


               // buttons[l].setPadding(1,1,1,1);
                buttons[l].setBackground(getResources().getDrawable(R.drawable.bingo_buttons));


                row1.addView(buttons[l]);
                l++;



            }
            table1.addView(row1);
        }


        for (i = 0; i < questionArraySize; i++) {




            buttonsValue = g.getArrayList().get((Integer) numbers.get(i)).getAnswer();
            buttonsText = Integer.toString(buttonsValue);

            buttons[i].setText(buttonsText);

            buttons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int k=0;
                /*    Button b =(Button)v;
                    for(int j=0;j<idArray.length;j++){
                        if(idArray[j]==v.getId()){
                            k=j;
                        }

                    }*/
                k=v.getId();
                    Toast toast = Toast.makeText(BoardGame.this, Integer.toString(g.getArrayList().get(finalI).getAnswer() ), Toast.LENGTH_SHORT);
                    toast.show();
                    if (buttons[k].getText().toString().equals(Integer.toString(g.getArrayList().get(finalI).getAnswer()))) {

                        buttons[k].setEnabled(false);
                        bingoR.correctAnswer(k);
                        correctBtnPressed(v, k);

                        if (bingoR.bingo(buttons)) {
                            bingoAlert();
                        } else {
                           finalI = setMathTvFunc(finalI);

                        }

                    } else {
                        buttons[k].setVisibility(View.INVISIBLE);
                        bingoR.notCorrectAnswer(k);
                        btnTxt = Integer.parseInt(buttons[k].getText().toString());
                        notCorectBtn(k, btnTxt);

                    }
                }
            });


        }
    }


    public int setMathTvFunc(int finalIndex){
        if(g.getArrayList().get(finalIndex).getDeleted() == -1){
            for(int k = finalIndex; k < questionArraySize; k++){
                finalIndex = k;
                if(g.getArrayList().get(finalIndex).getDeleted() != -1){
                    mathTv.setText(g.getArrayList().get(finalIndex).getQuestionPharse());
                    break;
                }
            }

        }
        else
        mathTv.setText(g.getArrayList().get(finalIndex).getQuestionPharse());
        return finalIndex;


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

    public void notCorectBtn(int indexBtn,int btnTxt){

        if(!(sp2.getBoolean("no_noise",false))){
            mpWrong.start();
        }


        for(int i = finalI ; i < questionArraySize ; i++){
            if((g.getArrayList().get(i).getDeleted() != -1) && (btnTxt == (g.getArrayList().get(i).getAnswer()))){
                g.getArrayList().get(i).setDeleted(-1);
                break;
            }
        }

        if (bingoR.lose()){
            mathTv.setText(R.string.no_more_moves);

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    showDialogLose(BoardGame.this,"dialog");
                }
            }, 1500);
        }


    }

    public void bingoAlert() {
        mathTv.setText("");
        mathIv.setVisibility(View.INVISIBLE);
        YoYo.with(Techniques.Tada).duration(1000).repeat(1).playOn(mathTv);
        mathTv.setBackground(getResources().getDrawable(R.drawable.bingo1));
        score +=100;
        g.setScore(score);

        //if we pass the level(bingo), so set the next level to open

        switch(opi){
            case "+":

                if(levelToOpenPlus%10 == 5&&levelToOpenPlus/10==(sizeB-4)){
                    levelToOpenPlus +=5;
                }
                else if (levelToOpenPlus%10 == levelNumber&&levelToOpenPlus/10==(sizeB-4)){
                    levelToOpenPlus++;
                }
                break;

            case"-":
                if(levelToOpenMinus%10 == 5&&levelToOpenMinus/10==(sizeB-4)){
                    levelToOpenMinus+=5;
                }
                else if (levelToOpenMinus == levelNumber&&levelToOpenMinus/10==(sizeB-4)){
                    levelToOpenMinus++;
                }
                break;

            case "*":
                if(levelToOpenMult%10 == 5&&levelToOpenMult/10==(sizeB-4)){
                    levelToOpenMult +=5;
                }
                else if (levelToOpenMult%10 ==levelNumber&&levelToOpenMult/10==(sizeB-4)){
                    levelToOpenMult++;
                }
                break;

            case "/":
                if(levelToOpenDiv%10 == 5&&levelToOpenDiv/10==(sizeB-4)){
                    levelToOpenDiv +=5;
                }
                else if (levelToOpenDiv%10 == levelNumber&&levelToOpenDiv/10==(sizeB-4)){
                    levelToOpenDiv++;
                }
                break;
        }

        final Handler handler = new Handler();

        //for waiting 3 sec before showing dialog
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                if(levelNumber < 5){
                    showDialog(BoardGame.this,"dialog");
                }
                else {
                    showDialogWin(BoardGame.this,"dialog");
                }
            }
        }, 3000);

        //for the bingo bears to pop


    }




    //@SuppressLint("SetTextI18n")
    public void showDialog(Activity activity, String name) {
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

                intent.putExtra("level_to_open_+",levelToOpenPlus);
                intent.putExtra("level_to_open_-",levelToOpenMinus);
                intent.putExtra("level_to_open_*",levelToOpenMult);
                intent.putExtra("level_to_open_/",levelToOpenDiv);


                /*SharedPreferences.Editor editor = sp1.edit();
                //to add
                editor.putInt("level_to_open",levelToOpen);
                editor.apply();*/


                if (levelNumber < 5){
                    intent.putExtra("game_level", levelNumber+1);
                    finish();
                    startActivity(intent);
                }
               /* else if(levelNumber == 5){
                    Intent intent1 = new Intent(BoardGame4x4.this, beforeActivity.class);
                    finish();
                    startActivity(intent1);
                }*/
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardGame.this, beforeActivity.class);
                startActivity(intent);
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

    //@SuppressLint("SetTextI18n")
    public void showDialogWin(Activity activity, final String name) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.win_mode_dialog);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.scroll_paper_img);
        TextView nameTv = (TextView)dialog.findViewById(R.id.score_tv);
        Button continueBtn = (Button)dialog.findViewById(R.id.continue_btn);
        Button exitBtn = (Button)dialog.findViewById(R.id.exit_btn);

        namePlayer =(EditText)dialog.findViewById(R.id.ed_name);
        namePlayer.setText(sp1.getString("name_player", ""));
        LinearLayout line1 =(LinearLayout)dialog.findViewById(R.id.line);
        if(sizeB==6){
        line1.removeView(continueBtn);}

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameOfPlayer = namePlayer.getText().toString();

                if (levelNumber == 5) {

                    if (data == null) {
                        data = new ArrayList<>();
                        datae = new ArrayList<>();
                        datam = new ArrayList<>();
                        datah = new ArrayList<>();
                        data.add(datae);
                        data.add(datam);
                        data.add(datah);
                    }
                    //add to easy
                    HashMap<String, Object> plyer = new HashMap<>();

                    plyer.put("name", nameOfPlayer);
                    plyer.put("kind", opi);
                    plyer.put("score", Integer.toString(score));




                    if (datae == null) {
                        datae = new ArrayList<>();
                    }

                    data.get(0).add(plyer);
                }
                Intent intent = new Intent(BoardGame.this, LevelsActivity.class);
                if(sizeB==4)
                intent.putExtra("level", "medium");
               else if(sizeB==5)
                    intent.putExtra("level", "hard");
                intent.putExtra("oper", opi);
                startActivity(intent);

            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameOfPlayer = namePlayer.getText().toString();

                if(levelNumber == 5){


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

                    plyer.put("name",nameOfPlayer);
                    plyer.put("kind",opi);
                    plyer.put("score",Integer.toString(score));


                    if(datae==null){
                        datae =new ArrayList<>();
                    }

                    data.get(0).add(plyer);
                }
                Intent intent = new Intent(BoardGame.this, beforeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //imageView.setBackground(getResources().getDrawable(R.drawable.scroll_paper));

//        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //score = Integer.parseInt(num_of_ball.getText().toString());

        String nameString = getString(R.string.win_dialog);
        //nameTv.setText(nameString + " " + playerName);

        dialog.show();

    }

    public void showDialogLose(Activity activity, final String name) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_more_moves_dialog);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.scroll_paper_img);
        TextView nameTv = (TextView)dialog.findViewById(R.id.score_tv);


        Button tryAgainBtn = (Button)dialog.findViewById(R.id.continue_btn);
        Button exitBtn = (Button)dialog.findViewById(R.id.exit_btn);

        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                intent.putExtra("score",score);

                intent.putExtra("level_to_open_+",levelToOpenPlus);
                intent.putExtra("level_to_open_-",levelToOpenMinus);
                intent.putExtra("level_to_open_*",levelToOpenMult);
                intent.putExtra("level_to_open_/",levelToOpenDiv);


                /*SharedPreferences.Editor editor = sp1.edit();
                //to add
                editor.putInt("level_to_open",levelToOpen);
                editor.apply();*/

                intent.putExtra("game_level", levelNumber);
                startActivity(intent);
                finish();
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BoardGame.this, beforeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //imageView.setBackground(getResources().getDrawable(R.drawable.scroll_paper));

//        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //score = Integer.parseInt(num_of_ball.getText().toString());

        String nameString = getString(R.string.win_dialog);
        //nameTv.setText(nameString + " " + playerName);
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

        SharedPreferences.Editor editor = sp1.edit();
        editor.putBoolean("first_run", false);

        editor.putInt("level_to_open_+",levelToOpenPlus);
        editor.putInt("level_to_open_-",levelToOpenMinus);
        editor.putInt("level_to_open_*",levelToOpenMult);
        editor.putInt("level_to_open_/",levelToOpenDiv);



        editor.putString("name_player", nameOfPlayer);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BoardGame.this, beforeActivity.class);
        intent.putExtra("level",level);
        intent.putExtra("oper", opi);


        startActivity(intent);

    }
}

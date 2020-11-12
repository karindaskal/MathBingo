package com.example.androidbingoproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LevelsActivity extends Activity {

    private static final int[] idArray =
            {R.id.level_1, R.id.level_2, R.id.level_3, R.id.level_4, R.id.level_5, R.id.level_6};

    private ImageButton[] levels = new ImageButton[idArray.length];

    int i;
    String operation;

    //easy medium hard
    String emh;
    int levelNum = 0;

    //to add
    SharedPreferences sp2;
    int levelToOpenPlus;
    int levelToOpenMinus;
    int levelToOpenMult;
    int levelToOpenDiv;

    int levelToOpen;
    TextView modeTv;

    ImageButton backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.levels_activity);

        sp2 = getSharedPreferences("dialog1", MODE_PRIVATE);

        //to knok which level we need to show as open
        levelToOpenPlus = sp2.getInt("level_to_open_+", 0);
        levelToOpenMinus = sp2.getInt("level_to_open_-", 0);
        levelToOpenMult = sp2.getInt("level_to_open_*", 0);
        levelToOpenDiv = sp2.getInt("level_to_open_/", 0);

        modeTv = findViewById(R.id.mode_tv);

        levels[0] = findViewById(R.id.level_1);
        levels[1] = findViewById(R.id.level_2);
        levels[2] = findViewById(R.id.level_3);
        levels[3] = findViewById(R.id.level_4);
        levels[4] = findViewById(R.id.level_5);
        levels[5] = findViewById(R.id.level_6);

        //emh = easy medium hard, we get the current mode
        emh = getIntent().getExtras().getString("level");
        operation = getIntent().getExtras().getString("oper");

        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelsActivity.this,beforeActivity.class);
                startActivity(intent);

            }
        });

        switch (operation) {

            case "+":
                levelToOpen = levelToOpenPlus;
                break;

            case "-":
                levelToOpen = levelToOpenMinus;
                break;

            case "*":
                levelToOpen = levelToOpenMult;
                break;

            case "/":
                levelToOpen = levelToOpenDiv;
                break;
        }


        if (emh.equals("easy")) {
            modeTv.setText(R.string.easy);

            //if we passed all 6 levels in easy mode
            if (levelToOpen >= 6) {
                levelToOpen = 5;
            }

        }
        else if (emh.equals("medium")) {

            modeTv.setText(R.string.medium);

            //if we passed all 6 levels in medium mode
            if (levelToOpen >= 20) {
                levelToOpen = 5;
            }

            else {
                levelToOpen = levelToOpen % 10;
            }
        }
        else if(emh.equals("hard")){
            modeTv.setText(R.string.hard);

            //if we passed all 6 levels in hard mode
            if (levelToOpen >= 30) {
                levelToOpen = 5;
            }
            else {
                levelToOpen = levelToOpen % 10;
            }

        }


        for (int i = 5; i > levelToOpen % 10; i--) {

            switch (i) {
                case 0:
                    levels[i].setBackground(getResources().getDrawable(R.drawable.level1gray));
                    break;
                case 1:
                    levels[i].setBackground(getResources().getDrawable(R.drawable.level2gray));
                    break;
                case 2:
                    levels[i].setBackground(getResources().getDrawable(R.drawable.level3gray));
                    break;
                case 3:
                    levels[i].setBackground(getResources().getDrawable(R.drawable.level4gray));
                    break;
                case 4:
                    levels[i].setBackground(getResources().getDrawable(R.drawable.level5gray));
                    break;
                case 5:
                    levels[i].setBackground(getResources().getDrawable(R.drawable.level6gray));
                    break;
            }
            levels[i].setEnabled(false);
        }


        for (i = 0; i < 6; i++) {

            levels[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {

                        case R.id.level_1:
                            levelNum = 0;
                            break;

                        case R.id.level_2:
                            levelNum = 1;
                            break;

                        case R.id.level_3:
                            levelNum = 2;
                            break;

                        case R.id.level_4:
                            levelNum = 3;
                            break;

                        case R.id.level_5:
                            levelNum = 4;
                            break;

                        case R.id.level_6:
                            levelNum = 5;
                            break;
                    }

                    //getting the information from before activity
                    operation = getIntent().getExtras().getString("oper");
                    emh = getIntent().getExtras().getString("level");


                        Intent intent = new Intent(LevelsActivity.this, BoardGame.class);
                        intent.putExtra("oper", operation);
                        intent.putExtra("game_level", levelNum);
                        intent.putExtra("level", emh);

                        startActivity(intent);

                }
            });

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LevelsActivity.this,beforeActivity.class);
        startActivity(intent);
        finish();
    }
}
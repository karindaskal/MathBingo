package com.example.androidbingoproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class beforeActivity extends Activity {

    Dialog dialogView;
    String oper = "";
    String type = "";
    String level = "";
    AlertDialog alertDialog;

    Boolean noMusic;
    int plusS;
    int subS;
    int divS;
    int mulS;


    MediaPlayer btnClicked;
    MediaPlayer music_1;

    SharedPreferences sp3;
    SharedPreferences sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_before);

        Button addBtn = findViewById(R.id.add_btn);
        final Button subBtn = findViewById(R.id.sub_btn);
        Button multBtn = findViewById(R.id.mult_btn);
        Button divBtn = findViewById(R.id.div_btn);
        Button exp = findViewById(R.id.explain);
        Button house = findViewById(R.id.house);

        btnClicked = MediaPlayer.create(this, R.raw.correct_short);
        music_1 = MediaPlayer.create(this, R.raw.back_music);


        sp3 = getSharedPreferences("name", MODE_PRIVATE);
        sp2 = getSharedPreferences("dialog1", MODE_PRIVATE);

        //which mode we need to show as open for each math activity(+-*\)
        plusS = sp2.getInt("level_to_open_+", 0);
        subS = sp2.getInt("level_to_open_-", 0);
        mulS = sp2.getInt("level_to_open_*", 0);
        divS = sp2.getInt("level_to_open_/", 0);


        addBtn.setOnClickListener(new clickOper());

        subBtn.setOnClickListener(new clickOper());

        multBtn.setOnClickListener(new clickOper());

        divBtn.setOnClickListener(new clickOper());

        exp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!(sp3.getBoolean("no_noise", false))) {
                    btnClicked.start();
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(beforeActivity.this);

                final View dialogView = getLayoutInflater().inflate(R.layout.explain_dialog, null);
                builder.setView(dialogView);

                //TextView textView=dialogView.findViewById(R.id.exp_text);
                Button okBtn = dialogView.findViewById(R.id.ok_btn);
                final AlertDialog alertDialog = builder.create();

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                    }
                });
                alertDialog.show();

            }
        });

        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(sp3.getBoolean("no_noise", false))) {
                    btnClicked.start();
                }
                Intent intent = new Intent(beforeActivity.this, HomeScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public class clickOper implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!(sp3.getBoolean("no_noise", false))) {
                btnClicked.start();
            }
            int operS = plusS;
            switch (v.getId()) {
                case R.id.add_btn:
                    operS = plusS;
                    oper = "+";
                    break;
                case R.id.sub_btn:
                    operS = subS;
                    oper = "-";
                    break;
                case R.id.div_btn:
                    operS = divS;
                    oper = "/";
                    break;
                case R.id.mult_btn:
                    operS = mulS;
                    oper = "*";
                    break;


            }
            AlertDialog.Builder builder = new AlertDialog.Builder(beforeActivity.this, R.style.myDialogTheme);
            View dialogView = getLayoutInflater().inflate(R.layout.levels_dialog, null);
            Button easyBtn = (Button) dialogView.findViewById(R.id.easy);
            Button mediumBtn = (Button) dialogView.findViewById(R.id.medium);
            Button hardBtn = (Button) dialogView.findViewById(R.id.hard);
            builder.setView(dialogView);
            alertDialog = builder.create();
            if (operS < 6) {
                mediumBtn.setEnabled(false);
                hardBtn.setEnabled(false);
                mediumBtn.setBackground(getResources().getDrawable(R.drawable.button_shape_gray));
                hardBtn.setBackground(getResources().getDrawable(R.drawable.button_shape_gray));
            }
            if (operS < 20) {
                hardBtn.setEnabled(false);
                hardBtn.setBackground(getResources().getDrawable(R.drawable.button_shape_gray));
            }


            easyBtn.setOnClickListener(new clickLevel());
            alertDialog.show();

            mediumBtn.setOnClickListener(new clickLevel());
            alertDialog.show();

            hardBtn.setOnClickListener(new clickLevel());
            alertDialog.show();


        }
    }

        public class clickLevel implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                if (!(sp3.getBoolean("no_noise", false))) {
                    btnClicked.start();
                }

                alertDialog.dismiss();

                switch (v.getId()) {
                    case R.id.easy:
                        level = "easy";
                        break;
                    case R.id.medium:
                        level = "medium";
                        break;
                    case R.id.hard:
                        level = "hard";
                        break;


                }

             ;   Intent intent = new Intent(beforeActivity.this, LevelsActivity.class);
                intent.putExtra("level", level);
                intent.putExtra("oper", oper);

                //intent.setClass(beforeActivity.this,boardGame6x6.class);

                startActivity(intent);

            }
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getOper() {
            return oper;
        }

        public void setOper(String oper) {
            this.oper = oper;
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            Intent intent = new Intent(beforeActivity.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }
    }








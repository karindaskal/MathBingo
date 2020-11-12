package com.example.androidbingoproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HomeScreen extends AppCompatActivity {

    SharedPreferences sp;

    ImageButton cemaraIB;
    Bitmap bitmap;
    AlertDialog alertDialog;
    Button player;
    Boolean noMusic;

    Boolean noNoise;
    Boolean show = false;

    ToggleButton musicTB;
    ToggleButton noiseTb;

    MediaPlayer btnClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

       // ImageButton emailB =findViewById(R.id.email_button);
        final ImageView horseI = findViewById(R.id.horse);
        final ImageView sheepI = findViewById(R.id.sheep);
        //Button newPlayer= findViewById(R.id.new_player);
        player =findViewById(R.id.player);

        musicTB =findViewById(R.id.music);
        noiseTb =findViewById(R.id.nouis);


        Button leader = findViewById(R.id.record_table);

        btnClicked = MediaPlayer.create(this, R.raw.correct_short);

        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!noNoise){
                    btnClicked.start();
                }

                Intent intent = new Intent(HomeScreen.this, leaderActivity.class);
                startActivity(intent);
            }
        });


        //change
        ImageView logoIv = findViewById(R.id.logo);

        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.move);
        final Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.show_anim);

       // YoYo.with(Techniques.StandUp).duration(2000).repeat(0).repeatMode(1000).playOn(newPlayer);
        YoYo.with(Techniques.StandUp).duration(2000).repeat(0).repeatMode(1000).playOn(player);
        YoYo.with(Techniques.StandUp).duration(2000).repeat(0).repeatMode(1000).playOn(leader);


        YoYo.with(Techniques.ZoomInUp).duration(700).repeat(1).repeatMode(1000).playOn(logoIv);

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                YoYo.with(Techniques.FadeInLeft).duration(700).repeat(1).repeatMode(700).playOn(horseI);
                YoYo.with(Techniques.FadeInRight).duration(700).repeat(1).repeatMode(700).playOn(sheepI);
            }
        }, 2000);

        ImageButton settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!show){
                    musicTB.setVisibility(View.VISIBLE);
                    noiseTb.setVisibility(View.VISIBLE);
                    show = true;
                    YoYo.with(Techniques.Swing).duration(1000).repeat(Animation.INFINITE).playOn(musicTB);
                    YoYo.with(Techniques.Swing).duration(1000).repeat(Animation.INFINITE).playOn(noiseTb);
                }
                else{
                    musicTB.setVisibility(View.INVISIBLE);
                    noiseTb.setVisibility(View.INVISIBLE);
                    show=false;
                }
            }
        });

        //horseI.startAnimation(animation);
        //sheepI.startAnimation(animation);

        sp = getSharedPreferences("name",MODE_PRIVATE);

        if(!(sp.getBoolean("no_first_run",false))){
            noMusic=false;
            startService(new Intent(HomeScreen.this, myMusic.class));
        }
        else noMusic = sp.getBoolean("no_music",false);

        noNoise = sp.getBoolean("no_noise",false);

        if(noNoise){
            noiseTb.setChecked(true);
        }
        else{
            noiseTb.setChecked(false);
        }


        if((sp.getBoolean("no_first_run",false))) {
            //player.setText(sp.getString("name_player", "plater1"));
            if (noMusic) {
                musicTB.setChecked(true);
                stopService(new Intent(HomeScreen.this, myMusic.class));
            } else {
                musicTB.setChecked(false);
                startService(new Intent(HomeScreen.this, myMusic.class));
            }
        }

        musicTB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked) {
                   stopService(new Intent(HomeScreen.this, myMusic.class));
                   noMusic = true;
               }
               else{
                   startService(new Intent(HomeScreen.this,myMusic.class));
                   noMusic = false;
               }
           }
       });

        noiseTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) noNoise=true;
                else noNoise = false;
            }
        });


       /* newPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1=new AlertDialog.Builder(HomeScreen.this);
                View dialogView1 = getLayoutInflater().inflate(R.layout.enter_name,null);
                builder1.setView(dialogView1);
                final Button okB =dialogView1.findViewById(R.id.button_ok);
                final EditText edName =dialogView1.findViewById(R.id.ed_name);
                final AlertDialog alertDialog=builder1.create();
                okB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name =  edName.getText().toString();
                        player.setText(name);
                        alertDialog.dismiss();

                        Intent intent = new Intent(HomeScreen.this, beforeActivity.class);
                        startActivity(intent);

                    }
                });alertDialog.show();
            }
        });*/

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!noNoise){
                    btnClicked.start();
                }

                Intent intent = new Intent(HomeScreen.this, beforeActivity.class);
                intent.setClass(HomeScreen.this,beforeActivity.class);
                startActivity(intent);
            }
        });
    }

   @Override
    protected void onPause() {
        super.onPause();

       if(this.isFinishing()){
           stopService(new Intent(HomeScreen.this,myMusic.class));}


       SharedPreferences.Editor editor = sp.edit();
        editor.putString("name_player",player.getText().toString());
        editor.putBoolean("no_first_run",true);
        editor.putBoolean("no_music",noMusic);
        editor.putBoolean("no_noise",noNoise);

       editor.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
   }
}

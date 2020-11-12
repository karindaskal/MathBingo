package com.example.androidbingoproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class myMusic extends Service {
    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(player==null)
        {
            player = MediaPlayer.create(this,R.raw.back_music);
            // Intent intent = new Intent(MainActivity.this, savePa.class);
            player.setLooping(true);
        }

        player.start();
        return START_STICKY;

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }


}

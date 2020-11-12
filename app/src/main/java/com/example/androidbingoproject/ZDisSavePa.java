package com.example.androidbingoproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZDisSavePa extends Activity {
    List<Map<String,Object>> datae;
    List<Map<String,Object>> datam ;
    List<Map<String,Object>> datah;
    List<List<Map<String,Object>>> data ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save);
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

        Button save =findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first time
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

                plyer.put("name","lila");
                plyer.put("kind","sub");
                plyer.put("score","100");
                data.get(0).add(plyer);
                //add to medium
                HashMap<String,Object> plyer4 =new HashMap<>();

                plyer4.put("name","lila");
                plyer4.put("kind","sub");
                plyer4.put("score","200");
                data.get(1).add(plyer4);


                HashMap<String,Object> plyer3 =new HashMap<>();

                plyer3.put("name","lila");
                plyer3.put("kind","sub");
                plyer3.put("score","50");
                data.get(2).add(plyer3);
                //add to hard
                HashMap<String,Object> plyer2 =new HashMap<>();

                plyer2.put("name","lila");
                plyer2.put("kind","sub");
                plyer2.put("score","200");
                data.get(2).add(plyer2);


            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
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
}

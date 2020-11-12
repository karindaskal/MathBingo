package com.example.androidbingoproject;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class leaderActivity extends Activity {
    ListView listView;
    List< List<Map<String, Object>> >data;

    String [] from ={"rate","name","kind","score"};
    int[] ids ={R.id.rate_s,R.id.nike_s,R.id.type_s,R.id.score_s};
    TextView levelTV ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_board);

        listView = findViewById(R.id.my_list);
        levelTV =findViewById(R.id.level);
        Button easyB =findViewById(R.id.easy_button);
        Button mediumB =findViewById(R.id.medium_button);
        Button hardB =findViewById(R.id.hard_button);

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

        //for sorting the table by score
        if(data != null) {
            Collections.sort(data.get(0),new comparePlayer());

        for(int i=0;i<data.get(0).size();i++){
            data.get(0).get(i).put("rate",i+1);
        }


        SimpleAdapter simpleAdapter = new SimpleAdapter(leaderActivity.this,data.get(0),R.layout.leader_shape,from,ids);
            listView.setAdapter(simpleAdapter);
        }

        easyB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @   Override
            public void onClick(View v) {
                levelTV.setText(R.string.easy);
                if(data !=null){
                    SimpleAdapter simpleAdapter =new SimpleAdapter(leaderActivity.this,data.get(0),R.layout.leader_shape,from,ids);
                    listView.setAdapter(simpleAdapter);}
            }
        });

        mediumB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                levelTV.setText(R.string.medium);

                if(data!=null){
                    Collections.sort(data.get(1),new comparePlayer());

                for(int i=0; i<data.get(1).size();i++){
                    data.get(1).get(i).put("rate",i+1);
                }

                SimpleAdapter simpleAdapter =new SimpleAdapter(leaderActivity.this,data.get(1),R.layout.leader_shape,from,ids);
                listView.setAdapter(simpleAdapter);
                }
            }
        });

        hardB.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                levelTV.setText(R.string.hard);

               if(data!=null){
                     Collections.sort(data.get(2),new comparePlayer());

                     for(int i=0; i < data.get(2).size(); i++) {
                         data.get(2).get(i).put("rate", i + 1);
                     }

                    SimpleAdapter simpleAdapter =new SimpleAdapter(leaderActivity.this,data.get(2),R.layout.leader_shape,from,ids);
                    listView.setAdapter(simpleAdapter);
                }
            }
        });

    }

}

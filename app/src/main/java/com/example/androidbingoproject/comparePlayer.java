package com.example.androidbingoproject;

import java.util.Comparator;
import java.util.Map;

public class comparePlayer implements Comparator<Map<String, Object>> {
    @Override
    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
        String p1= (String) o1.get("score");
        String p2= (String) o2.get("score");
        int np1=Integer.parseInt(p1);
        int np2=Integer.parseInt(p2);
        if( np1>np2 ) return -1;
        else if( np1<np2 ) return 1;
        else if( np1== np2 ) return 0;
        return 0;
    }



}

package com.example.androidbingoproject;

import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

public class bingoR {
    private int[] countRow ;
    private int[] countCol ;
    int countDiagonalRight;
    int countDiagonalLeft;
    private int[] countRowM ;
    private int[] countColM ;
    int countDiagonalRightM;
    int countDiagonalLeftM;
    int size;

    public bingoR(int size) {

        this.size = size;
        countDiagonalRight=0;
        countDiagonalLeft=0;
        countDiagonalRightM=size;
        countDiagonalLeftM=size;
        countRow=new int[size];
        countCol=new int[size];
        countRowM=new int[size];
        countColM=new int[size];

        for(int i=0;i<size;i++){
            countRow[i]=0;
            countCol[i]=0;
            countRowM[i]=size;
            countColM[i]=size;


        }
    }
    public void correctAnswer(int i){
        countRow[i%size]++;
        countCol[i/size]++;

        if(i%(size+1)==0){
            countDiagonalRight++;
        }
        if(i%(size-1)==0&&i!=(size*size-1)){
            countDiagonalLeft++;
        }

    }
    public boolean bingo(Button [] b){
        if(countDiagonalRight ==size ){
            for (int i=0;i<size;i++){
                YoYo.with(Techniques.Bounce).duration(700).repeat(1).playOn(b[i*(size+1)]);

            }
            return true;}
        for (int i=0;i<size;i++)
            if(countDiagonalLeft==size ){
                YoYo.with(Techniques.Bounce).duration(700).repeat(1).playOn(b[i*(size-1)]);

                return true;}

        for (int i=0;i<size;i++){
            if(countCol[i]==size){
                for(int j=0;j<size;j++)
                YoYo.with(Techniques.Bounce).duration(700).repeat(1).playOn(b[size*i+j]);
                return true;
            }
            if( countRow[i]==size) {
                for(int j=0;j<size;j++)
                    YoYo.with(Techniques.Bounce).duration(700).repeat(1).playOn(b[i%size+size*j]);
                return true;
            }

        }
        return false;

    }
    public void notCorrectAnswer(int i){
        countRowM[i%size]--;
        countColM[i/size]--;

        if(i%(size+1)==0){
            countDiagonalRightM--;
        }
        if(i%(size-1)==0&&(size*size-1)!=i){
            countDiagonalLeftM--;
        }

    }
    public boolean lose(){
        boolean f = false;
        if(countDiagonalRightM !=size&&countDiagonalLeftM!=size )f=true;
        if(f) {

            for (int i = 0; i < size; i++) {
                if (countColM[i] != size && countRowM[i] != size) {

                }
                else
                {
                    f=false;
                    break;
                }
            }
        }
        return f;

    }
}

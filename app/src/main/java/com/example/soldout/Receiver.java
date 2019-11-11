package com.example.soldout;

import android.util.Log;

import java.io.BufferedReader;

public class Receiver extends Thread{

    private BufferedReader bufferedReader;
    private boolean isContinue;

    public Receiver(BufferedReader bufferedReader){
        this.bufferedReader = bufferedReader;
        this.isContinue = true;
    }

    @Override
    public void run() {

        try {

            String receivedText;
            while(this.isContinue && ((receivedText = this.bufferedReader.readLine()) != null)){
                //受信処理
                Log.d("user", "run: " + receivedText);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void disconnect(){

        try {

            this.bufferedReader.close();
            this.isContinue = false;

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}

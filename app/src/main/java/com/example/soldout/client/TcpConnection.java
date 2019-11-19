package com.example.soldout.client;

import android.util.Log;

import com.example.soldout.manager.IntentManager;

import java.io.*;
import java.net.Socket;

import leftfoot.FoodData;
import leftfoot.FoodThread;

public class TcpConnection{

    public static String ip;
    public static int port;

    public static void Open(String ip, int port) {

        TcpConnection.ip = ip;
        TcpConnection.port = port;

    }

    public static FoodData Send(final String text){

        FoodData foodData = null;

        //データリクエスト
        FoodThread foodThread = new FoodThread(text);
        foodThread.start();

        try {

            //同期:スレッド終了待ち
            foodThread.join();

            //データ取得
            foodData = foodThread.foodData;

        }catch (Exception e){
            e.printStackTrace();
        }

        return foodData;

    }

}

package com.example.soldout.client;

import android.util.Log;

import com.example.soldout.manager.IntentManager;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import leftfoot.FoodData;
import leftfoot.FoodThread;

public class TcpConnection{

    public static String ip = "";
    public static int port = 0;

    public static void Open(String ip, int port) {

        TcpConnection.ip = ip;
        TcpConnection.port = port;

        FoodData[] foodData = TcpConnection.RequestList();

    }

    public static boolean isOpen(){
        return (TcpConnection.ip != "") && (TcpConnection.port != 0);
    }

    public static FoodData RequestID(final String id){

        if(!TcpConnection.isOpen()) return null;

        FoodData foodData = null;

        //データリクエスト
        FoodThread foodThread = new FoodThread("FD:" + id);
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

    public static FoodData[] RequestList(){

        if(!TcpConnection.isOpen()) return new FoodData[0];

        final List<FoodData> foodList = new ArrayList<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    //ソケット作成
                    Socket socket = new Socket(TcpConnection.ip, TcpConnection.port);
                    //出力ストリーム作成
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    //リクエスト送信
                    objectOutputStream.writeObject("LST:_");
                    objectOutputStream.flush();

                    //入力ストリーム作成
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    //入力待ち
                    Object readObject;
                    while((readObject = objectInputStream.readObject()) != null){
                        if(readObject instanceof  FoodData){
                            foodList.add((FoodData) readObject);
                            Log.d("user", "read: " + readObject.toString());
                        }else{
                            Log.d("user", "read: end");
                            break;
                        }
                    }

                    //ストリーム破棄
                    objectOutputStream.close();
                    objectInputStream.close();

                    socket.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //スレッド開始
        thread.start();

        try{
            //スレッド終了待ち
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        FoodData[] foodData = new FoodData[foodList.size()];
        for (int index = 0; index < foodData.length; index++){
            foodData[index] = foodList.get(index);
        }

        return foodData;

    }

}

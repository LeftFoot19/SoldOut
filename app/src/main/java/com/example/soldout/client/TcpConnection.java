package com.example.soldout.client;

import android.util.Log;

import java.io.*;
import java.net.Socket;

import leftfoot.FoodData;

public class TcpConnection{

    private static String ip;
    private static int port;

    public static void Open(String ip, int port) {

        TcpConnection.ip = ip;
        TcpConnection.port = port;

    }

    public static void Send(final String text){

        Thread transmitter = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //ソケット作成
                    Socket socket = new Socket(TcpConnection.ip, TcpConnection.port);

                    //出力ストリーム作成
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                    //リクエスト送信
                    objectOutputStream.writeObject(text);   //ストリーム書き込み
                    objectOutputStream.flush();             //書き込み内容送信

                    //入力ストリーム作成
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                    //返信待ち
                    Object received;
                    if((received = objectInputStream.readObject()) != null){
                        //型確認
                        if (received instanceof FoodData){
                            FoodData foodData = (FoodData) received;
                            Log.d("user", "received: \n" + foodData.toString());
                        }else{
                            Log.d("user", "received: not FoodData");
                        }
                    }

                    //閉じる
                    socket.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        transmitter.start();

    }

}

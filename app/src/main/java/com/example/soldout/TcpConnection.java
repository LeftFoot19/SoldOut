package com.example.soldout;

import android.util.Log;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.file.OpenOption;

public class TcpConnection extends Thread{

    private static String ip;
    private static int port;

    private Socket socket;

    private TcpConnection(String ip, int port){ }

    @Override
    public void run() {

        try{

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void Open(String ip, int port) {

        TcpConnection.ip = ip;
        TcpConnection.port = port;

    }

    public static void Send(final String text){

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //ストリーム作成
                    Socket socket = new Socket(TcpConnection.ip, TcpConnection.port);
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    //送信
                    bufferedWriter.write(text);
                    bufferedWriter.flush();

                    //閉じる
                    bufferedWriter.close();
                    socket.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        sender.start();

    }

    public void disconnect() {

        try{

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

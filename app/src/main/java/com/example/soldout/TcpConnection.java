package com.example.soldout;

import android.util.Log;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.file.OpenOption;

public class TcpConnection extends Thread{

    private static TcpConnection tcpConnection;

    private String ip;
    private int port;

    private Socket socket;
    public Receiver receiver;

    private TcpConnection(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {

        try{

            Log.d("user", "run: piyo");

            //ソケット作成
            this.socket = new Socket(ip, port);

            Log.d("user", "run: huga");

            //Receiver
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.receiver = new Receiver(bufferedReader);
            this.receiver.start();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void Open(String ip, int port) {

        //既に開いている接続を破棄
        if(TcpConnection.getInstance() != null) TcpConnection.getInstance().disconnect();

        //接続
        TcpConnection.tcpConnection = new TcpConnection(ip, port);
        tcpConnection.start();

    }

    public static void Send(final String text){

        if(TcpConnection.getInstance().socket != null){
            Thread sender = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(TcpConnection.getInstance().socket.getOutputStream(), "UTF-8"));
                        bufferedWriter.write(text);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            sender.start();

        }
    }

    public static TcpConnection getInstance(){
        return TcpConnection.tcpConnection;
    }

    public void disconnect() {

        try{

            if(this.receiver != null) this.receiver.disconnect();
            if(this.socket != null) this.socket.close();

            this.receiver = null;
            this.socket = null;

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

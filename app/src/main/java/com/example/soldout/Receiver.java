package com.example.soldout;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver extends Thread{

    private Socket socket;

    public Receiver(Socket socket){
        this.socket = socket;
        this.start();
    }

    @Override
    public void run() {

        try{

            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            Object receivedObject = objectInputStream.readObject();
            Log.d("user", "receiver: " + receivedObject.toString());
            this.socket.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

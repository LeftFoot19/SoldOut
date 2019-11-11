package com.example.soldout;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class QRManager {

    public static void Entry(Activity activity, String readString){

        String tag = "";
        String data = "";
        //Split
        try{
            String[] split = readString.split(":");
            tag = split[0];
            data = split[1];
        }catch (Exception e){
            e.printStackTrace();
        }

        //tag分岐
        switch (tag){

            case "FoodData":
                Log.d("debug", "Entry: " + data);
                TcpConnection.Send(data);
                break;

            case "Address":
                String ip = "";
                int port = 0;
                try {
                    String[] split = data.split("/");
                    ip = split[0];
                    port = Integer.parseInt(split[1]);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            default:
                Log.d("debug", "Tag \"" + tag + "\" is not supported");
                break;
        }

    }

}

package com.example.soldout;

import android.app.Activity;
import android.os.Debug;
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
                Toast.makeText(activity, "Scanned: " + data, Toast.LENGTH_LONG).show();
                break;

            default:
                Log.d("debug", "Tag \"" + tag + "\" is not supported");
                break;
        }

    }

}

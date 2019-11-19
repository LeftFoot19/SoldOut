package com.example.soldout.manager;

import android.app.Activity;
import android.content.Intent;

import com.example.soldout.activity.FoodViewer;
import com.google.zxing.integration.android.IntentIntegrator;

import leftfoot.FoodData;

public class IntentManager {

    public static void RunQRScanner(Activity returnTargetActivity){
        new IntentIntegrator(returnTargetActivity)
                .setBeepEnabled(false)  //コード読み取り時のBeep音を鳴らさない
                .initiateScan();        //読み取り開始
    }

    public static void OpenFoodViewer(Activity fromActivity, FoodData foodData){
        Intent toFoodViewer = new Intent(fromActivity, FoodViewer.class);
        toFoodViewer.putExtra("FoodData", foodData);
        fromActivity.startActivity(toFoodViewer);
    }

}

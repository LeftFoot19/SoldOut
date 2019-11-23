package com.example.soldout.manager;

import android.app.Activity;
import android.content.Intent;

import com.example.soldout.activity.FoodViewer;
import com.google.zxing.integration.android.IntentIntegrator;

import leftfoot.FoodData;

public class IntentManager {

    public static void RunQRScanner(Activity returnTargetActivity){
        new IntentIntegrator(returnTargetActivity)
                .setBeepEnabled(false)                                  //読み取り時に音を鳴らさない
                .setOrientationLocked(false)                            //画面の向きを固定しない(機能してない気がする)
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)  //QRコードのみ読み取り
                .initiateScan();                                        //初期化・起動
    }

}

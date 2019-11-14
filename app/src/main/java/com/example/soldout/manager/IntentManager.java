package com.example.soldout.manager;

import android.app.Activity;

import com.google.zxing.integration.android.IntentIntegrator;

public class IntentManager {

    public static void RunQRScanner(Activity returnTargetActivity){
        new IntentIntegrator(returnTargetActivity).initiateScan();
    }

}

package com.example.soldout.activity;

import java.util.TimerTask;

public class FoodViewerTimerTask extends TimerTask {

    private FoodViewer foodViewer;

    public FoodViewerTimerTask(FoodViewer foodViewer){
        super();

        this.foodViewer = foodViewer;

    }

    @Override
    public void run() {

        foodViewer.timeReflesh();

    }
}

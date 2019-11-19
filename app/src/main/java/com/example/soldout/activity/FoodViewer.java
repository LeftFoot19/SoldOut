package com.example.soldout.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.soldout.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import leftfoot.FoodData;

public class FoodViewer extends AppCompatActivity {

    private FoodData foodData;
    private TextView productNameText;
    private TextView currentPriceText;
    private TextView timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_viewer);

        //メンバー取得
        this.loadMember();

        //インテント取得
        Intent intent = this.getIntent();
        if((intent != null)){

            this.foodData = (FoodData)intent.getSerializableExtra("FoodData");

        }

        //タイマー
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new FoodViewerTimerTask(this), 0, 1000);

        this.refleshData();

    }

    //xmlからメンバーを取得
    private void loadMember(){

        this.productNameText = this.findViewById(R.id.productNameText);
        this.currentPriceText = this.findViewById(R.id.currentPriceText);
        this.timeText = this.findViewById(R.id.timeText);

    }

    public void refleshData(){
        if(this.foodData != null){

            this.productNameText.setText(this.foodData.productName);
            this.currentPriceText.setText(this.foodData.iniPrice + "円");

            this.timeReflesh();

        }
    }

    public void timeReflesh(){

        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());

        //カレンダー取得
        Calendar calendar = Calendar.getInstance();

        //文字組み立て
        String timeStr = String.format("%d:%d:%d", calendar.HOUR, calendar.MINUTE, calendar.SECOND);

        //textView反映
        this.timeText.setText(df.format(date));

    }

}

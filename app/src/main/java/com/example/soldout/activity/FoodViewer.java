package com.example.soldout.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.CellIdentity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.soldout.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import leftfoot.FoodData;

public class FoodViewer extends AppCompatActivity {

    //
    private Handler handler;

    //コントロール
    private FoodData foodData;
    private TextView productNameText;
    private TextView currentPriceText;
    private ImageView meterImageView;
    private TextView remainTimeTextView;

    //定数
    private final float startAngle = -60.0f;
    private final float endAngle = 60.0f;
    private Calendar discount0Time; //一回目の割引時間
    private Calendar discount1Time; //二回目の割引時間
    private Calendar minDate;
    private Calendar maxDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_viewer);

        //インスタンス割当
        this.handler = new Handler();

        //定数設定
        //インスタンス割当
        this.discount0Time = Calendar.getInstance();
        this.discount1Time = Calendar.getInstance();
        this.minDate = Calendar.getInstance();
        this.maxDate = Calendar.getInstance();
        long zeroDate;
        try {
            //時間設定
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            zeroDate = simpleDateFormat.parse("1970/01/01 00:00:00").getTime();
            this.discount0Time.setTimeInMillis(simpleDateFormat.parse("1970/01/01 06:00:00").getTime() - zeroDate);
            this.discount1Time.setTimeInMillis(simpleDateFormat.parse("1970/01/01 03:00:00").getTime() - zeroDate);
            this.minDate.setTimeInMillis(simpleDateFormat.parse("1970/01/01 00:00:00").getTime() - zeroDate);
            this.maxDate.setTimeInMillis(simpleDateFormat.parse("1970/01/01 09:00:00").getTime() - zeroDate);
        }catch (Exception e) {
            e.printStackTrace();
        }

        //メンバー取得
        this.loadMember();

        //インテント取得
        Intent intent = this.getIntent();
        if((intent != null)){

            this.foodData = (FoodData)intent.getSerializableExtra("FoodData");

        }

        //タイマー
        Timer timer = new Timer();
        /*
        Androidのアクティビティ操作はシングルスレッドであり、他スレッドから操作すると例外を返す
        Handlerでアクティビティのスレッドへ処理を送信(キューイング)することで他スレッドからアクティビティを操作することができる
         */
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //アクティビティスレッドへ処理を送信
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timeUpdate();
                    }
                });
            }
        }, 0, 1000);

        this.dataUpdate();

    }

    //xmlからメンバーを取得
    private void loadMember(){

        this.productNameText = this.findViewById(R.id.productNameText);
        this.currentPriceText = this.findViewById(R.id.currentPriceText);
        this.meterImageView = this.findViewById(R.id.meterImageView);
        this.remainTimeTextView = this.findViewById(R.id.remainTimeTextView);

    }

    public void dataUpdate(){
        if(this.foodData != null){

            this.productNameText.setText(this.foodData.productName);
            this.currentPriceText.setText(this.foodData.iniPrice + "円");

            this.timeUpdate();

        }
    }

    public void timeUpdate(){

        /*メータ計算*/{

            //メータ最大時間設定(9時間)
            long meterRange = this.maxDate.getTimeInMillis() - this.minDate.getTimeInMillis();

            //割合計算
            //残り時間
            long remainMillis = Math.max(minDate.getTimeInMillis(), Math.min(this.foodData.bestBeforeDate.getTime() - System.currentTimeMillis(), maxDate.getTimeInMillis()));
            //メータに占める残り時間の割合
            float remainRatio = 1 - ((float) remainMillis / meterRange);
            //メータ角度の計算(線形補間)
            float angle = this.startAngle + (this.endAngle - this.startAngle) * remainRatio;

            //角度設定
            this.meterImageView.setRotation(angle);

        }

        /*現在価格計算*/ {

            long zeroTime = 0;{
                SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
                    zeroTime = fullDateFormat.parse("1970/01/01 00:00:00").getTime();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //残り時間
            long remainMillis = Math.max(minDate.getTimeInMillis(), this.foodData.bestBeforeDate.getTime() - System.currentTimeMillis());

            //割引なし
            if(0 < (remainMillis - this.discount0Time.getTimeInMillis())){

                //価格表示
                this.currentPriceText.setText(this.foodData.iniPrice + "円");
                this.currentPriceText.setTextColor(this.getResources().getColor(R.color.colorNoDiscount));

                //次の割引までの時間
                Calendar remainToDiscount = Calendar.getInstance();
                remainToDiscount.setTimeInMillis((remainMillis - this.discount0Time.getTimeInMillis()) + zeroTime);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                this.remainTimeTextView.setText(String.format("%s\n%s", this.getResources().getText(R.string.remainHeaderNoDiscount), timeFormat.format(remainToDiscount.getTime())));
                this.remainTimeTextView.setTextColor(this.getResources().getColor(R.color.colorNoDiscount));

            //一回割引
            } else if(0 < (remainMillis - this.discount1Time.getTimeInMillis())){

                //価格表示
                this.currentPriceText.setText(this.foodData.discountPrice1 + "円");
                this.currentPriceText.setTextColor(this.getResources().getColor(R.color.colorOneDiscount));

                //次の割引までの時間
                Calendar remainToDiscount = Calendar.getInstance();
                remainToDiscount.setTimeInMillis((remainMillis - this.discount1Time.getTimeInMillis()) + zeroTime);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                this.remainTimeTextView.setText(String.format("%s\n%s", this.getResources().getText(R.string.remainHeaderOneDiscount), timeFormat.format(remainToDiscount.getTime())));
                this.remainTimeTextView.setTextColor(this.getResources().getColor(R.color.colorOneDiscount));

            //二回割引
            } else {

                //価格表示
                this.currentPriceText.setText(this.foodData.discountPrice2 + "円");
                this.currentPriceText.setTextColor(this.getResources().getColor(R.color.colorTwoDiscount));

                //賞味期限までの時間
                Calendar remainToDiscount = Calendar.getInstance();
                remainToDiscount.setTimeInMillis(remainMillis + zeroTime);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                this.remainTimeTextView.setText(String.format("%s\n%s", this.getResources().getText(R.string.remainHeaderTwoDiscount), timeFormat.format(remainToDiscount.getTime())));
                this.remainTimeTextView.setTextColor(this.getResources().getColor(R.color.colorTwoDiscount));

            }

        }

    }

}

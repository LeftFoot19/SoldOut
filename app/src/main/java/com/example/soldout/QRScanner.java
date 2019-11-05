package com.example.soldout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

public class QRScanner extends AppCompatActivity {

    DecoratedBarcodeView decoratedBarcodeView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        this.decoratedBarcodeView = findViewById(R.id.decoratedBarcodeView);
        if(this.decoratedBarcodeView != null) this.StartScan();

    }

    public void StartScan(){

        this.decoratedBarcodeView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {

            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });

        this.decoratedBarcodeView.resume();

    }

}

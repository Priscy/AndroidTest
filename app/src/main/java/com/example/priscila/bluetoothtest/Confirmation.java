package com.example.priscila.bluetoothtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Confirmation extends AppCompatActivity {
    TextView tipoAccidente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                    startActivity(new Intent(Confirmation.this, Home.class));
            }
        }, 2000);

    }



}

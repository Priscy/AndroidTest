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

        tipoAccidente = (TextView)findViewById(R.id.tipodeAccidente);
        if(Constants.typeEvent=="Caida"){
            tipoAccidente.setText("Caída");
        } else if (Constants.typeEvent=="Emergencia"){
            tipoAccidente.setText("Emergencia");
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                    startActivity(new Intent(Confirmation.this, Home.class));
            }
        }, 2000);

    }

}

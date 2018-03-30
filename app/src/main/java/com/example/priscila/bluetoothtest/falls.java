package com.example.priscila.bluetoothtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.widget.ProgressBar;
import android.os.Handler;

import com.example.priscila.bluetoothtest.controller.DatabaseHelper;

public class falls extends AppCompatActivity {
    DatabaseHelper dbHelper = new DatabaseHelper(this);

    TextView receive;
    ProgressBar pb;
    ObjectAnimator objectAnimator;
    String idEvento;
    int contEvento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_falls);


        //receive = (TextView)findViewById(R.id.tv1);

       // receive.setText(getIntent().getStringExtra("EdiTtEXTvALUE"));
        pb = (ProgressBar)findViewById(R.id.progressBar);
        objectAnimator = ObjectAnimator.ofInt(pb, "progress", 0,100);
        objectAnimator.setDuration(10000);
        objectAnimator.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(falls.this, Confirmation.class));
            }
        }, 10000);
        registrarCaida();

    }

    public void registrarCaida(){
        generateIdEvento();
        dbHelper.createRegistroEventos(idEvento, "Accidente_01", pacienteSignup.idPat, 0);

    }

    public void generateIdEvento(){
        String lastidEvento = dbHelper.getLastEvent();
        if(lastidEvento == "null"){
            contEvento = -1;
        } else {
            contEvento = Integer.parseInt(lastidEvento);
        }
        contEvento ++;

        idEvento = "Evento";
        idEvento = new StringBuilder(idEvento).append("_").append(Integer.toString(contEvento)).toString();

    }



}

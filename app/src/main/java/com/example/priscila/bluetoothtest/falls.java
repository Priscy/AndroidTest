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

    TextView text;
    ProgressBar pb;
    ObjectAnimator objectAnimator;
    String idEvento;
    int contEvento;
    int duration = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_falls);

       text = (TextView)findViewById(R.id.tv1);

        pb = (ProgressBar)findViewById(R.id.progressBar);
        if (Constants.typeEvent =="Caida"){
            text.setText("Caída detectada");
            duration=10000;
            registrarCaida();
        }else if (Constants.typeEvent=="Emergencia"){
            text.setText("Emergencia detectada");
            duration=2000;
            registrarEmergencia();
        }

        objectAnimator = ObjectAnimator.ofInt(pb, "progress", 0,100);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(falls.this, Confirmation.class));
            }
        }, duration);


    }

    public void registrarCaida(){
        generateIdEvento();
        dbHelper.createRegistroEventos(idEvento, "Accidente_01", pacienteSignup.idPat, 0);
        Constants.caidasRegistradas +=1;

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

    public void registrarEmergencia(){
        generateIdEvento();
        dbHelper.createRegistroEventos(idEvento, "Accidente_02", pacienteSignup.idPat, 0);
        Constants.emerRegistradas +=1;

    }



}

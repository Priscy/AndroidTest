package com.example.priscila.bluetoothtest.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.animation.ObjectAnimator;
import android.widget.ProgressBar;
import android.os.Handler;

import com.example.priscila.bluetoothtest.R;

public class falls extends AppCompatActivity {
    DatabaseHelper dbHelper = new DatabaseHelper(this);

    TextView text;
    ProgressBar pb;
    ObjectAnimator objectAnimator;
    String idEvento;
    int contEvento;
    int duration = 0;
    Button btCancelar;
    boolean canceled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_falls);

        text = (TextView)findViewById(R.id.tv1);
        pb = (ProgressBar)findViewById(R.id.progressBar);
        btCancelar = (Button)findViewById(R.id.btnCancelar);

        if (Constants.typeEvent =="Caida"){
            text.setText("Caída detectada");
            duration=10000;
            registrarCaida();
        }else if (Constants.typeEvent=="Emergencia"){
            text.setText("Emergencia detectada");
            duration=2000;
            registrarEmergencia();
        }

        btCancelar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                cancelarEvento(contEvento);
            }
        });

        objectAnimator = ObjectAnimator.ofInt(pb, "progress", 0,100);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(canceled != true) {
                    startActivity(new Intent(falls.this, Confirmation.class));
                }
            }
        }, duration);


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

    public void registrarEmergencia(){
        generateIdEvento();
        dbHelper.createRegistroEventos(idEvento, "Accidente_02", pacienteSignup.idPat, 0);

    }

    public void cancelarEvento(int cont){
        canceled = true;
        dbHelper.updateEvent(cont);
        finish();
        startActivity(new Intent(falls.this, Home.class));

    }



}

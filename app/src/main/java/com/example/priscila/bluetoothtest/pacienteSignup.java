package com.example.priscila.bluetoothtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.priscila.bluetoothtest.controller.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class pacienteSignup extends AppCompatActivity {
    SimpleDateFormat dtfm = new SimpleDateFormat("dd-MM-yyyy");

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    EditText nombre;
    DatePicker fechaNac;
    RadioButton hombre;
    RadioButton mujer;
    EditText dir;


    String dayStr;
    String monthStr;
    String yearStr;
    String dateStr;
    Date date;
    String sexo;
    int contPat;
    public static String nombreStr;
    public static String idPat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_signup);

        nombre = (EditText)findViewById(R.id.nombre);
        fechaNac=(DatePicker)findViewById(R.id.fechaNac);
        hombre=(RadioButton)findViewById(R.id.hombre);
        mujer=(RadioButton)findViewById(R.id.mujer);
        dir=(EditText)findViewById(R.id.dir);


    }

    public void registrarPaciente(View view){
        generateIdPaciente();
        dayStr = Integer.toString(fechaNac.getDayOfMonth());
        monthStr= Integer.toString(fechaNac.getMonth() + 1);
        yearStr = Integer.toString(fechaNac.getYear());
        dateStr = new StringBuilder(yearStr).append("-").append(monthStr).append("-").append(dayStr).toString();

        if(hombre.isActivated()){
            sexo="H";
        }else {
            sexo="M";
        }

       nombreStr = nombre.getText().toString();
        String dirStr = dir.getText().toString();
        Log.d(Constants.TAG,idPat);
        Log.d(Constants.TAG,nombreStr);
        Log.d(Constants.TAG,dateStr);
        Log.d(Constants.TAG,sexo);
        Log.d(Constants.TAG,dirStr);

        dbHelper.createPaciente(idPat, nombreStr,dateStr,sexo,dirStr);
        //Constants.typeEvent="Caida";
       // startActivity(new Intent(pacienteSignup.this, falls.class));
        startActivity(new Intent(pacienteSignup.this, Home.class));
    }

    public void generateIdPaciente(){
        String lastidPat = dbHelper.getLastPaciente();
        if(lastidPat == "null"){
            contPat = -1;
        } else {
            contPat = Integer.parseInt(lastidPat);
        }
        contPat ++;

        idPat="Paciente";
        idPat = new StringBuilder(idPat).append("_").append(Integer.toString(contPat)).toString();

    }
}

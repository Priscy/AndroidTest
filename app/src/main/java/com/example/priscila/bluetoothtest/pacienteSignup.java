package com.example.priscila.bluetoothtest;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Button;

import com.example.priscila.bluetoothtest.controller.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class pacienteSignup extends AppCompatActivity {
    private static final String TAG = "---PRISCILA---";
    SimpleDateFormat dtfm = new SimpleDateFormat("dd-MM-yyyy");

    DatabaseHelper dbHelper = new DatabaseHelper(this);
    EditText nombre;
    DatePicker fechaNac;
    RadioButton hombre;
    RadioButton mujer;
    EditText dir;

    public static String nombreStr;
    String dayStr;
    String monthStr;
    String yearStr;
    String dateStr;
    Date date;
    String sexo;
    int idCont;
    String idPat = "Paciente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_signup);

        nombre = (EditText)findViewById(R.id.nombre);
        fechaNac=(DatePicker)findViewById(R.id.fechaNac);
        hombre=(RadioButton)findViewById(R.id.hombre);
        mujer=(RadioButton)findViewById(R.id.mujer);
        dir=(EditText)findViewById(R.id.dir);
        idCont=0;


    }

    public void registrarPaciente(View view){
        generateIdPaciente();
        dayStr = Integer.toString(fechaNac.getDayOfMonth());
        monthStr= Integer.toString(fechaNac.getMonth() + 1);
        yearStr = Integer.toString(fechaNac.getYear());
        dateStr = new StringBuilder(yearStr).append("-").append(monthStr).append("-").append(dayStr).toString();
//        try {
//
//            date = dtfm.parse(dateStr);
//            Log.d(TAG,"Date parsed correctly");
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        if(hombre.isActivated()){
            sexo="H";
        }else {
            sexo="M";
        }

       nombreStr = nombre.getText().toString();
        String dirStr = dir.getText().toString();
        Log.d(TAG,idPat);
        Log.d(TAG,nombreStr);
        Log.d(TAG,dateStr);
        Log.d(TAG,sexo);
        Log.d(TAG,dirStr);

        dbHelper.createPaciente(idPat, nombreStr,dateStr,sexo,dirStr);

        startActivity(new Intent(pacienteSignup.this, Home.class));
    }

    public void generateIdPaciente(){
        idPat="Paciente";
        idPat = new StringBuilder(idPat).append("_").append(Integer.toString(idCont)).toString();
        idCont++;
    }
}

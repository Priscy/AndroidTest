package com.example.priscila.bluetoothtest.controller;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.priscila.bluetoothtest.AlarmReceiver;
import com.example.priscila.bluetoothtest.R;
import com.example.priscila.bluetoothtest.model.Medicina;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AgendarMedicamento extends AppCompatActivity{
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    CustomAutoCompleteView myAutoComplete;
    ArrayAdapter<String> adapterMedicinas;
    ArrayAdapter<CharSequence> adapterFrecuencia;
    String[] item = new String[] {"Please search..."};
    Button addMed, btnAgendarMed;
    Spinner frecuencia;
    EditText etHoraInicio;

    TimePickerDialog mTimePicker;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    int mHour, mMinute;
    Context context;
    Intent myIntent;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_medicamento);
        addMed= (Button)findViewById(R.id.addMedicamento);
        addMed.setBackgroundResource(R.drawable.disabled_button);
        addMed.setTextColor(Color.parseColor("#B8B8B8"));

        btnAgendarMed = (Button)findViewById(R.id.btnAgenda);
        etHoraInicio = (EditText)findViewById(R.id.hora_inicio);

        frecuencia = (Spinner)findViewById(R.id.frecuencia_spinner);
        adapterFrecuencia = ArrayAdapter.createFromResource(this,R.array.frecuencia_array, android.R.layout.simple_spinner_item);
        adapterFrecuencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frecuencia.setAdapter(adapterFrecuencia);

        myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);
        myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));
        adapterMedicinas = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        myAutoComplete.setAdapter(adapterMedicinas);

        etHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                mTimePicker = new TimePickerDialog(AgendarMedicamento.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etHoraInicio.setText( selectedHour + ":" + selectedMinute);
                        mHour = selectedHour;
                        mMinute = selectedMinute;
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Selecciona");
                mTimePicker.show();

            }
        });

        this.context = this;
        myIntent = new Intent(AgendarMedicamento.this, AlarmReceiver.class);
        Constants.saveIntent = myIntent;
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Calendar calendar = Calendar.getInstance();

        btnAgendarMed.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)

            @Override
            public void onClick(View view) {
                final int hour = mHour;
                final int minute = mMinute;

                Log.d(Constants.TAG, "In the receiver with " + hour + " and " + minute);

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                myIntent.putExtra("extra", "yes");
                Constants.saveIntent = myIntent;
                pendingIntent = PendingIntent.getBroadcast(AgendarMedicamento.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Toast.makeText(getApplicationContext(), "Medicamento Agendado", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(AgendarMedicamento.this, Home.class);
                startActivity(loginIntent);
            }
        });

    }

    public String[] getMedFromDb(String searchTerm){
        Log.d(Constants.TAG, searchTerm);
        // add items on the array dynamically
        List<Medicina> medicinas = dbHelper.getMedicinas(searchTerm);
        int rowCount = medicinas.size();
        String[] item = new String[rowCount];

        if(rowCount == 0){
            addMed.setEnabled(true);
            addMed.setBackgroundResource(R.drawable.color_border_button);
            addMed.setTextColor(Color.parseColor("#bd9758"));

        } else {
            addMed.setEnabled(false);
            addMed.setBackgroundResource(R.drawable.disabled_button);
            addMed.setTextColor(Color.parseColor("#B8B8B8"));
            int x = 0;

            for (Medicina medicina : medicinas) {

                item[x] = medicina.getNombre();
                x++;
            }
        }
        return item;
    }

    public void agregarMedicina(View view){
        dbHelper.createMedicine(myAutoComplete.getText().toString());
        Toast toast = Toast.makeText(this, "Medicamento " + myAutoComplete.getText().toString() + " agregado", Toast.LENGTH_SHORT);
        toast.show();
        addMed.setEnabled(false);
        addMed.setBackgroundResource(R.drawable.disabled_button);
        addMed.setTextColor(Color.parseColor("#B8B8B8"));


    }

    public void apagarAlarma(){
        Constants.saveIntent.putExtra("extra", "no");
        sendBroadcast(Constants.saveIntent);
        alarmManager.cancel(pendingIntent);
    }




}

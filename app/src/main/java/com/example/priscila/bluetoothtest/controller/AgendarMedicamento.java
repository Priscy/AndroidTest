package com.example.priscila.bluetoothtest.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.priscila.bluetoothtest.R;
import com.example.priscila.bluetoothtest.model.Medicina;

import java.util.List;

public class AgendarMedicamento extends AppCompatActivity {
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    CustomAutoCompleteView myAutoComplete;
    ArrayAdapter<String> adapterMedicinas;
    ArrayAdapter<CharSequence> adapterFrecuencia;
    String[] item = new String[] {"Please search..."};
    Button addMed;
    Spinner frecuencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_medicamento);
        addMed= (Button)findViewById(R.id.addMedicamento);
        frecuencia = (Spinner)findViewById(R.id.frecuencia_spinner);
        adapterFrecuencia = ArrayAdapter.createFromResource(this,R.array.frecuencia_array, android.R.layout.simple_spinner_item);
        adapterFrecuencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frecuencia.setAdapter(adapterFrecuencia);

        myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);
        myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));
        adapterMedicinas = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        myAutoComplete.setAdapter(adapterMedicinas);
    }

    public String[] getMedFromDb(String searchTerm){
        Log.d(Constants.TAG, searchTerm);
        // add items on the array dynamically
        List<Medicina> medicinas = dbHelper.getMedicinas(searchTerm);
        int rowCount = medicinas.size();
        String[] item = new String[rowCount];

        if(rowCount == 0){
            addMed.setVisibility(View.VISIBLE);
        } else {
            addMed.setVisibility(View.INVISIBLE);
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
        addMed.setVisibility(View.INVISIBLE);


    }



}

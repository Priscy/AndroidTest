package com.example.priscila.bluetoothtest.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.priscila.bluetoothtest.R;
import com.example.priscila.bluetoothtest.model.Medicina;

import java.util.List;

public class AgendarMedicamento extends AppCompatActivity {
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    CustomAutoCompleteView myAutoComplete;
    ArrayAdapter<String> myAdapter;
    String[] item = new String[] {"Please search..."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_medicamento);

        myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);

        dbHelper.createMedicine("Ibuprofeno");

        // add the listener so it will tries to suggest while the user types
        myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));

        // set our adapter
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        myAutoComplete.setAdapter(myAdapter);
    }

    public String[] getMedFromDb(String searchTerm){

        Log.d(Constants.TAG, searchTerm);
        // add items on the array dynamically
        List<Medicina> medicinas = dbHelper.getMedicinas(searchTerm);
        int rowCount = medicinas.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (Medicina medicina : medicinas) {

            item[x] = medicina.getNombre();
            x++;
        }

        return item;
    }



}

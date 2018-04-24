package com.example.priscila.bluetoothtest.controller;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.priscila.bluetoothtest.R;
import com.example.priscila.bluetoothtest.model.Medicina;

import java.util.List;

public class Tab1Medicamentos extends Fragment {
    BluetoothController bluetoothController = new BluetoothController(getActivity());
    Button btnAgendar;
    DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
    CustomAutoCompleteView myAutoComplete;
    ArrayAdapter<String> myAdapter;
    String[] item = new String[] {"Please search..."};
    Context c;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab1medicamentos, container, false);
        btnAgendar = rootView.findViewById(R.id.btnAgendarMed);
        btnAgendar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AgendarMedicamento.class));
            }
        });

        return rootView;
    }


    public void vibrate(){
       Constants.vibrate = true;
       bluetoothController.setValueVibration();

    }



}

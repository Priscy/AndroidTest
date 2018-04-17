package com.example.priscila.bluetoothtest;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.priscila.bluetoothtest.BluetoothController;
import com.example.priscila.bluetoothtest.controller.DatabaseHelper;

public class Tab1Medicamentos extends Fragment {
    BluetoothController bluetoothController = new BluetoothController(getActivity());
    Button btnAgendar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab1medicamentos, container, false);
        btnAgendar = rootView.findViewById(R.id.btnAgenda);
        btnAgendar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                vibrate();
            }
        });


        return rootView;
    }

    public void vibrate(){
       Constants.vibrate = true;
       bluetoothController.setValueVibration();

    }




}

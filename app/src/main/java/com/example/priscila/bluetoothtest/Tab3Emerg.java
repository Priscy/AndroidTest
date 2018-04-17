package com.example.priscila.bluetoothtest;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priscila.bluetoothtest.controller.DatabaseHelper;
import com.example.priscila.bluetoothtest.model.RegistroEventos;

import java.util.List;


public class Tab3Emerg extends Fragment {
    DatabaseHelper dbHelper;
    TextView listaAccidentes;
    String eventoStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DatabaseHelper(getActivity());
        View rootView = inflater.inflate(R.layout.tab3emerg, container, false);
        listaAccidentes= (TextView)rootView.findViewById(R.id.listaEmer);
        showEmergencias();
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        listaAccidentes.setText("");
        showEmergencias();

    }

    public void showEmergencias(){
        List<RegistroEventos> eventos= dbHelper.getEmergencias();
        for(RegistroEventos evento: eventos){
            eventoStr = new StringBuilder("Emergencia el ").append(evento.getFechaHora()).append(" ").append(evento.getStatus()).append("\n").toString();
            listaAccidentes.append(eventoStr);
        }
    }
}

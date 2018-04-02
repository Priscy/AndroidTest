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
    private static final String TAG = "---PRISCILA---";
    DatabaseHelper dbHelper;
    TextView listaAccidentes;
    String eventoStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DatabaseHelper(getActivity());
        View rootView = inflater.inflate(R.layout.tab3emerg, container, false);
        listaAccidentes= (TextView)rootView.findViewById(R.id.listaEmer);
        Log.d(TAG, "Emergencias: " +Constants.emerRegistradas);
        if(Constants.emerRegistradas != 0){
            showEmergencias();
        }
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        listaAccidentes.setText("");
        if (Constants.emerRegistradas != 0) {
            showEmergencias();
        }

    }

    public void showEmergencias(){
        List<RegistroEventos> eventos= dbHelper.getEmergencias();
        for(RegistroEventos evento: eventos){
            eventoStr = new StringBuilder("Emergencia").append(" de ").append(evento.getId_paciente()).append(" a las ").append(evento.getFechaHora()).append(evento.getId_accidente()).append("\n").toString();
            listaAccidentes.append(eventoStr);
        }
    }
}

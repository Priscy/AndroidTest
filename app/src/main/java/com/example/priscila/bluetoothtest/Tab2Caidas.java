package com.example.priscila.bluetoothtest;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priscila.bluetoothtest.controller.DatabaseHelper;
import com.example.priscila.bluetoothtest.model.RegistroEventos;

import java.util.List;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;




public class Tab2Caidas extends Fragment {
    DatabaseHelper dbHelper;
    TextView listaAccidentes;
    String eventoStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbHelper = new DatabaseHelper(getActivity());
        View rootView = inflater.inflate(R.layout.tab2caidas, container, false);
        listaAccidentes= (TextView)rootView.findViewById(R.id.listaAcc);
        showCaidas();
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        listaAccidentes.setText("");
        showCaidas();

    }

    public void showCaidas(){
        List<RegistroEventos> eventos= dbHelper.getCaidas();
        for(RegistroEventos evento: eventos){
            eventoStr = new StringBuilder("Caida").append(" de ").append(evento.getId_paciente()).append(" a las ").append(evento.getFechaHora()).append(evento.getId_accidente()).append("\n").toString();
            listaAccidentes.append(eventoStr);
        }
    }
}

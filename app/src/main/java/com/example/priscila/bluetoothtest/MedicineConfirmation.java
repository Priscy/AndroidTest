package com.example.priscila.bluetoothtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.priscila.bluetoothtest.controller.AgendarMedicamento;

public class MedicineConfirmation extends AppCompatActivity {
    Button btnEntendido;
    AgendarMedicamento agendarMedicamento = new AgendarMedicamento();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_confirmation);
        btnEntendido = (Button)findViewById(R.id.btnEtendido);

        btnEntendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agendarMedicamento.apagarAlarma();
            }
        });

    }
}

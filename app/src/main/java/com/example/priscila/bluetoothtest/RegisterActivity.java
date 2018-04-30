package com.example.priscila.bluetoothtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.priscila.bluetoothtest.controller.BluetoothController;

public class RegisterActivity extends AppCompatActivity {
    Button backLogin, register, nextBtn;
    LinearLayout layoutTipoUsuario,layoutPaciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backLogin = (Button)findViewById(R.id.back_login_btn);
        register = (Button)findViewById(R.id.register_btn);
        nextBtn = (Button)findViewById(R.id.nextBtn);
        layoutTipoUsuario = (LinearLayout)findViewById(R.id.tipoUsuarioLayout);
        layoutPaciente=(LinearLayout)findViewById(R.id.layoutPaciente);

        layoutPaciente.setVisibility(LinearLayout.GONE);
        layoutTipoUsuario.setVisibility(LinearLayout.VISIBLE);

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginIntent = new Intent(RegisterActivity.this, BluetoothController.class);
                startActivity(loginIntent);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutTipoUsuario.setVisibility(LinearLayout.GONE);
                layoutPaciente.setVisibility(LinearLayout.VISIBLE);
            }
        });


    }
}

package com.example.priscila.bluetoothtest;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.priscila.bluetoothtest.controller.BluetoothController;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText fechaNac;
    Button btnBackLogin, btnRegister, btnNext, btnBack;
    LinearLayout layoutTipoUsuario,layoutBase, layoutPaciente, layoutFamiliar, layoutMedico;
    SimpleDateFormat simpleDateFormat;
    RadioButton rbPaciente, rbFamiliar, rbMedico;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBackLogin = (Button)findViewById(R.id.back_login_btn);
        btnRegister = (Button)findViewById(R.id.register_btn);
        btnNext = (Button)findViewById(R.id.nextBtn);
        btnBack = (Button)findViewById(R.id.back_btn);


        layoutTipoUsuario = (LinearLayout)findViewById(R.id.tipoUsuarioLayout);
        layoutBase=(LinearLayout)findViewById(R.id.layoutBase);
        layoutPaciente=(LinearLayout)findViewById(R.id.layoutPaciente);
        layoutMedico= (LinearLayout)findViewById(R.id.layoutMedico);
        layoutFamiliar=(LinearLayout)findViewById(R.id.layoutFamiliar);

        fechaNac = (EditText)findViewById(R.id.paciente_fechaNac);
        rbPaciente = (RadioButton)findViewById(R.id.radioPaciente);
        rbFamiliar= (RadioButton)findViewById(R.id.radioFamiliar);
        rbMedico = (RadioButton)findViewById(R.id.radioMedico);

        Locale spanish = new Locale("es", "ES");
        simpleDateFormat = new SimpleDateFormat("dd MM yyyy", spanish);

        layoutPaciente.setVisibility(LinearLayout.GONE);
        layoutFamiliar.setVisibility(LinearLayout.GONE);
        layoutMedico.setVisibility(LinearLayout.GONE);
        layoutBase.setVisibility(LinearLayout.GONE);
        layoutTipoUsuario.setVisibility(LinearLayout.VISIBLE);

        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginIntent = new Intent(RegisterActivity.this, BluetoothController.class);
                startActivity(loginIntent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutTipoUsuario.setVisibility(LinearLayout.GONE);
                layoutBase.setVisibility(LinearLayout.VISIBLE);

                if (rbPaciente.isChecked()) {
                    layoutMedico.setVisibility(LinearLayout.GONE);
                    layoutFamiliar.setVisibility(LinearLayout.GONE);
                    layoutPaciente.setVisibility(LinearLayout.VISIBLE);
                } else if (rbFamiliar.isChecked()){
                    layoutMedico.setVisibility(LinearLayout.GONE);
                    layoutPaciente.setVisibility(LinearLayout.GONE);
                    layoutFamiliar.setVisibility(LinearLayout.VISIBLE);
                }else if (rbMedico.isChecked()){
                    layoutPaciente.setVisibility(LinearLayout.GONE);
                    layoutFamiliar.setVisibility(LinearLayout.GONE);
                    layoutMedico.setVisibility(LinearLayout.VISIBLE);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutPaciente.setVisibility(LinearLayout.GONE);
                layoutFamiliar.setVisibility(LinearLayout.GONE);
                layoutMedico.setVisibility(LinearLayout.GONE);
                layoutBase.setVisibility(LinearLayout.GONE);
                layoutTipoUsuario.setVisibility(LinearLayout.VISIBLE);
            }
        });

        fechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate(1990, 0, 1, R.style.DatePickerSpinner);
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
       fechaNac.setText(simpleDateFormat.format(calendar.getTime()));
    }


    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(RegisterActivity.this).callback(RegisterActivity.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }


}


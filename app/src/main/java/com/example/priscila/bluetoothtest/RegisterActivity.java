package com.example.priscila.bluetoothtest;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.priscila.bluetoothtest.controller.BluetoothController;
import com.example.priscila.bluetoothtest.controller.Constants;
import com.example.priscila.bluetoothtest.controller.DatabaseHelper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.auth.AuthResult;

import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    DatabaseHelper dbHelper = new DatabaseHelper(this);


    EditText etPacienteNombre, etPacienteEmail, etPacientePass, etFechaNac;
    EditText etFamiliarNombre, etFamiliarEmail, etFamiliarPass;
    EditText etMedicoNombre, etMedicoEmail, etMedicoPass;
    Button btnBackLogin, btnRegister, btnNext, btnBack;
    LinearLayout layoutTipoUsuario,layoutBase, layoutPaciente, layoutFamiliar, layoutMedico;
    SimpleDateFormat simpleDateFormat;
    RadioButton rbPaciente, rbFamiliar, rbMedico;
    RadioButton rbHombre, rbMujer;
    ProgressBar mRegisterProgressBar;
    String tipoUsuarioActivo;
    String name = "";

    int contPat;
    public static String idPat;
    public static String nombreStr;

    //private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       // mStorage = FirebaseStorage.getInstance().getReference().child("images");
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        btnBackLogin = (Button)findViewById(R.id.back_login_btn);
        btnRegister = (Button)findViewById(R.id.register_btn);
        btnNext = (Button)findViewById(R.id.nextBtn);
        btnBack = (Button)findViewById(R.id.back_btn);

        etFechaNac= (EditText)findViewById(R.id.paciente_fechaNac);
        etPacienteNombre = (EditText)findViewById(R.id.paciente_name);
        etPacienteEmail = (EditText)findViewById(R.id.paciente_email);
        etPacientePass = (EditText)findViewById(R.id.paciente_password);
        etFamiliarNombre = (EditText)findViewById(R.id.familiar_name);
        etFamiliarEmail=(EditText)findViewById(R.id.familiar_email);
        etFamiliarPass=(EditText)findViewById(R.id.familiar_password);
        etMedicoNombre = (EditText)findViewById(R.id.medico_name);
        etMedicoEmail = (EditText)findViewById(R.id.medico_email);
        etMedicoPass = (EditText)findViewById(R.id.medico_password);


        layoutTipoUsuario = (LinearLayout)findViewById(R.id.tipoUsuarioLayout);
        layoutBase=(LinearLayout)findViewById(R.id.layoutBase);
        layoutPaciente=(LinearLayout)findViewById(R.id.layoutPaciente);
        layoutMedico= (LinearLayout)findViewById(R.id.layoutMedico);
        layoutFamiliar=(LinearLayout)findViewById(R.id.layoutFamiliar);

        rbPaciente = (RadioButton)findViewById(R.id.radioPaciente);
        rbFamiliar= (RadioButton)findViewById(R.id.radioFamiliar);
        rbMedico = (RadioButton)findViewById(R.id.radioMedico);
        rbHombre = (RadioButton)findViewById(R.id.paciente_hombre);
        rbMujer = (RadioButton)findViewById(R.id.paciente_mujer);

        mRegisterProgressBar = (ProgressBar)findViewById(R.id.registerProgressBar);
        Locale spanish = new Locale("es", "ES");
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", spanish);

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
                Log.d(Constants.TAG, tipoUsuarioActivo);

                mRegisterProgressBar.setVisibility(View.VISIBLE);

                String email = "";
                String password = "";
                if (tipoUsuarioActivo.equals("Paciente")){
                    name = etPacienteNombre.getText().toString();
                    email = etPacienteEmail.getText().toString();
                    password = etPacientePass.getText().toString();
                } else if (tipoUsuarioActivo.equals("Familiar")){
                    name = etFamiliarNombre.getText().toString();
                    email = etFamiliarEmail.getText().toString();
                    password = etFamiliarPass.getText().toString();
                } else if(tipoUsuarioActivo.equals("Médico")){
                    name = etMedicoNombre.getText().toString();
                    email = etMedicoEmail.getText().toString();
                    password= etMedicoPass.getText().toString();
                }

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                final String user_id = mAuth.getCurrentUser().getUid();
                                String token_id = FirebaseInstanceId.getInstance().getToken();
                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put("name", name);
                                userMap.put("token_id", token_id);

                                mFirestore.collection(tipoUsuarioActivo).document(user_id).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mRegisterProgressBar.setVisibility(View.INVISIBLE);
                                        Intent loginIntent = new Intent(RegisterActivity.this, BluetoothController.class);
                                        startActivity(loginIntent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        mRegisterProgressBar.setVisibility(View.INVISIBLE);
                                    }
                                });

                            } else {
                                Toast.makeText(RegisterActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                mRegisterProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_LONG).show();
                    mRegisterProgressBar.setVisibility(View.INVISIBLE);
                }
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
                    tipoUsuarioActivo = rbPaciente.getText().toString();
                } else if (rbFamiliar.isChecked()){
                    layoutMedico.setVisibility(LinearLayout.GONE);
                    layoutPaciente.setVisibility(LinearLayout.GONE);
                    layoutFamiliar.setVisibility(LinearLayout.VISIBLE);
                    tipoUsuarioActivo = rbFamiliar.getText().toString();
                }else if (rbMedico.isChecked()){
                    layoutPaciente.setVisibility(LinearLayout.GONE);
                    layoutFamiliar.setVisibility(LinearLayout.GONE);
                    layoutMedico.setVisibility(LinearLayout.VISIBLE);
                    tipoUsuarioActivo = rbMedico.getText().toString();
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

        etFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate(1990, 0, 1, R.style.DatePickerSpinner);
            }
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
       etFechaNac.setText(simpleDateFormat.format(calendar.getTime()));
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

    public void registrarPaciente(){
        Log.d(Constants.TAG, "Inside Registrar Paciente");
        generateIdPaciente();
        String dateStr = etFechaNac.getText().toString();
        String sexo="";

        if(rbHombre.isChecked()){
            sexo="H";
        }else if (rbMujer.isChecked()){
            sexo="M";
        }

        nombreStr = etPacienteNombre.getText().toString();
        String email = etPacienteEmail.getText().toString();
        String password = etPacientePass.getText().toString();


        dbHelper.createPaciente(idPat, nombreStr, email, password,dateStr,sexo);
    }

    public void registrarPacienteFirebase(){

    }

    public void generateIdPaciente(){
        String lastidPat = dbHelper.getLastPaciente();
        if(lastidPat == "null"){
            contPat = -1;
        } else {
            contPat = Integer.parseInt(lastidPat);
        }
        contPat ++;

        idPat="Paciente";
        idPat = new StringBuilder(idPat).append("_").append(Integer.toString(contPat)).toString();

    }


}


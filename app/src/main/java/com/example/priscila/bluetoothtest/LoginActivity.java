package com.example.priscila.bluetoothtest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priscila.bluetoothtest.controller.BluetoothController;
import com.example.priscila.bluetoothtest.controller.Constants;
import com.example.priscila.bluetoothtest.controller.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    Button btnLogin, btnCreatenewAccount;
    EditText etLoginEmail, etLoginPass;

    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    String current_id;
    String current_name;
    Map<String, Object> tokenMap;


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Intent loginIntent = new Intent(LoginActivity.this, Home.class);
            startActivity(loginIntent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.login_btn);
        btnCreatenewAccount = (Button)findViewById(R.id.login_register_btn);
        etLoginEmail = (EditText)findViewById(R.id.login_email);
        etLoginPass = (EditText)findViewById(R.id.login_password);

        mProgressBar = (ProgressBar) findViewById(R.id.loginProgress);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etLoginEmail.getText().toString();
                String password = etLoginPass.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                    mProgressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                String token_id = FirebaseInstanceId.getInstance().getToken();
                                current_id = mAuth.getCurrentUser().getUid();

                                 tokenMap= new HashMap<>();
                                tokenMap.put("token_id", token_id);

                                mFirestore.collection("Paciente").document(current_id).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent loginIntent = new Intent(LoginActivity.this, BluetoothController.class);
                                        startActivity(loginIntent);
                                        mProgressBar.setVisibility(View.INVISIBLE);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mFirestore.collection("Familiar").document(current_id).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent loginIntent = new Intent(LoginActivity.this, Home.class);
                                                startActivity(loginIntent);
                                                mProgressBar.setVisibility(View.INVISIBLE);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                mFirestore.collection("Médico").document(current_id).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Intent loginIntent = new Intent(LoginActivity.this, Home.class);
                                                        startActivity(loginIntent);
                                                        mProgressBar.setVisibility(View.INVISIBLE);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(LoginActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                                                        mProgressBar.setVisibility(View.INVISIBLE);
                                                    }
                                                });

                                            }
                                        });
                                    }
                                });

                            } else {
                                Toast.makeText(LoginActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                mProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_LONG).show();
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        });


        btnCreatenewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(loginIntent);
            }
        });
    }


}


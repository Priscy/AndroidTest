package com.example.priscila.bluetoothtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priscila.bluetoothtest.model.Mensaje;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class Confirmation extends AppCompatActivity {
    TextView tipoAccidente;

    FirebaseDatabase database;
    DatabaseReference myRef;

    String dataTitle, dataMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);


        // Handle possible data accompanying notification message.
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                if (key.equals("title")) {
                    dataTitle=(String)getIntent().getExtras().get(key);
                }
                if (key.equals("message")) {
                    dataMessage = (String)getIntent().getExtras().get(key);;
                }
            }
            showAlertDialog();
        }

        database = FirebaseDatabase.getInstance();
        Log.d(Constants.TAG, database.toString());
        myRef = database.getReference("Messages");
        subscribeToTopic();
        sendMessage();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Confirmation.this, Home.class));
            }
        }, 2000);


    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensaje");
        builder.setMessage("title: " + dataTitle + "\n" + "message: " + dataMessage);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    public void subscribeToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("notifications");
        Toast.makeText(this, "Subscribed to Topic: Notifications", Toast.LENGTH_SHORT).show();
    }

    public void sendMessage() {
        myRef.push().setValue(new Mensaje("Alerta", "Familiar en emergenciaa"));
        Toast.makeText(this, "Mensaje Sent", Toast.LENGTH_SHORT).show();
    }

}





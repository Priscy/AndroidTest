package com.example.priscila.bluetoothtest.controller;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * Created by Priscila on 17/04/2018.
 */

public class CustomAutoCompleteTextChangedListener implements TextWatcher {
    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;

    public CustomAutoCompleteTextChangedListener(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        // if you want to see in the logcat what the user types
        Log.e(Constants.TAG, "User input: " + userInput);

       AgendarMedicamento agendarMedicamento = ((AgendarMedicamento)context);
        // query the database based on the user input

        agendarMedicamento.item = agendarMedicamento.getMedFromDb(userInput.toString());

        // update the adapater
        agendarMedicamento.myAdapter.notifyDataSetChanged();
        agendarMedicamento.myAdapter = new ArrayAdapter<String>(agendarMedicamento, android.R.layout.simple_dropdown_item_1line, agendarMedicamento.item);
        agendarMedicamento.myAutoComplete.setAdapter(agendarMedicamento.myAdapter);

    }
}

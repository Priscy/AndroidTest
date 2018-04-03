package com.example.priscila.bluetoothtest.model;

/**
 * Created by Priscila on 03/04/2018.
 */

public class Mensaje {
    private String title, message;

    public Mensaje(){}

    public Mensaje(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}

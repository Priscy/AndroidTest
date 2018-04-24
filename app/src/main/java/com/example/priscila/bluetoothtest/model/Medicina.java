package com.example.priscila.bluetoothtest.model;

/**
 * Created by Priscila on 11/03/2018.
 */

public class Medicina {
    public static final String TABLE_MEDICINA = "Medicina";
    public static final String KEY_ID_MEDICINA = "id_medicina";
    public static final String KEY_NOMBRE = "nombre";

    String nombre;

    public static final String CREATE_TABLE_MEDICINA = "CREATE TABLE " + TABLE_MEDICINA +
            "(" + KEY_ID_MEDICINA + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NOMBRE + " TEXT" + ")";

    public Medicina(){

    }

    public Medicina(String nombre) {
        this.nombre = nombre;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

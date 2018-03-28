package com.example.priscila.bluetoothtest.model;

/**
 * Created by Priscila on 11/03/2018.
 */

public class Medicina {
    public static final String TABLE_MEDICINA = "Medicina";
    public static final String KEY_ID_MEDICINA = "id_medicina";
    private static final String KEY_DESCRIPCION = "descripcion";

    String id_medicina;
    String descripcion;

    public static final String CREATE_TABLE_MEDICINA = "CREATE TABLE " + TABLE_MEDICINA +
            "(" + KEY_ID_MEDICINA + " TEXT PRIMARY KEY," + KEY_DESCRIPCION + " TEXT" + ")";

    public Medicina(){

    }

    public Medicina(String id_medicina, String descripcion) {
        this.id_medicina = id_medicina;
        this.descripcion = descripcion;
    }

    public String getId_medicina() {
        return id_medicina;
    }

    public void setId_medicina(String id_medicina) {
        this.id_medicina = id_medicina;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

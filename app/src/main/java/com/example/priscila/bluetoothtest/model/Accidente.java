package com.example.priscila.bluetoothtest.model;

/**
 * Created by Priscila on 11/03/2018.
 */

public class Accidente {
    public static final String TABLE_ACCIDENTE = "Accidente";
    public static final String KEY_ID_ACCIDENTE = "id_accidente";
    public static final String KEY_DESCRIPCION = "descripcion";

    String id_accidente;
    String descripcion;

    public static final String CREATE_TABLE_ACCIDENTE = "CREATE TABLE " + TABLE_ACCIDENTE +
            "(" + KEY_ID_ACCIDENTE + " TEXT PRIMARY KEY," + KEY_DESCRIPCION + " TEXT" + ")";

    public Accidente(){

    }

    public Accidente(String id_accidente, String descripcion) {
        this.id_accidente = id_accidente;
        this.descripcion = descripcion;
    }

    public String getId_accidente() {
        return id_accidente;
    }

    public void setId_accidente(String id_accidente) {
        this.id_accidente = id_accidente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

package com.example.priscila.bluetoothtest.model;

/**
 * Created by Priscila on 11/03/2018.
 */

public class Medico {
    public static final String TABLE_MEDICO= "Medico";
    private static final String KEY_ID_MEDICO = "id_medico";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_TELEFONO = "telefono";
    private static final String KEY_HOSPITAL = "hospital";


    String id_medico;
    String nombre;
    String telefono;
    String hospital;

    public static final String CREATE_TABLE_MEDICO = "CREATE TABLE " + TABLE_MEDICO +
            "(" + KEY_ID_MEDICO + "TEXT PRIMARY KEY," + KEY_NOMBRE + "TEXT," + KEY_TELEFONO +
            "TEXT," + KEY_HOSPITAL + "TEXT" + ")";

    public Medico(){

    }

    public Medico(String id_medico, String nombre, String telefono, String hospital) {
        this.id_medico = id_medico;
        this.nombre = nombre;
        this.telefono = telefono;
        this.hospital = hospital;
    }

    public String getId_medico() {
        return id_medico;
    }

    public void setId_medico(String id_medico) {
        this.id_medico = id_medico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}

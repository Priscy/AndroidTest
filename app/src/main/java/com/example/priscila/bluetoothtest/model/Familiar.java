package com.example.priscila.bluetoothtest.model;

/**
 * Created by Priscila on 11/03/2018.
 */

public class Familiar {
    public static final String TABLE_FAMILIAR = "Familiar";
    private static final String KEY_ID_FAMILIAR = "id_familiar";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_TELEFONO = "telefono";
    private static final String KEY_SEXO = "sexo";
    private static final String KEY_DIRECCION = "direccion";

    public static final String CREATE_TABLE_FAMILIAR = "CREATE TABLE " + TABLE_FAMILIAR +
            "(" + KEY_ID_FAMILIAR + " TEXT PRIMARY KEY," + KEY_NOMBRE + " TEXT," + KEY_TELEFONO +
            " TEXT," + KEY_SEXO + " CHAR," + KEY_DIRECCION + " TEXT" + ")";


    String id_familiar;
    String nombre;
    String telefono;
    char sexo;
    String direccion;

    public Familiar(){

    }

    public Familiar(String id_familiar, String nombre, String telefono, char sexo, String direccion) {
        this.id_familiar = id_familiar;
        this.nombre = nombre;
        this.telefono = telefono;
        this.sexo = sexo;
        this.direccion = direccion;
    }

    public String getId_familiar() {
        return id_familiar;
    }

    public void setId_familiar(String id_familiar) {
        this.id_familiar = id_familiar;
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

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}

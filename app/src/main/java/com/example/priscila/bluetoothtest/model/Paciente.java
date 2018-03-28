package com.example.priscila.bluetoothtest.model;

import java.util.Date;

/**
 * Created by Priscila on 11/03/2018.
 */

public class Paciente {

    public static final String TABLE_PACIENTE = "Paciente";
    public static final String KEY_ID_PACIENTE = "id_paciente";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_FECHANACIMIENTO = "fechaNacimiento";
    public static final String KEY_SEXO = "sexo";
    public static final String KEY_DIRECCION = "direccion";

    public static final String CREATE_TABLE_PACIENTE = "CREATE TABLE " + TABLE_PACIENTE +
            "(" + KEY_ID_PACIENTE + " TEXT PRIMARY KEY," + KEY_NOMBRE + " TEXT," + KEY_FECHANACIMIENTO +
            " DATE," + KEY_SEXO + " TEXT," + KEY_DIRECCION + " TEXT" + ")";

    String id_paciente;
    String nombre;
    Date fechaNacimiento;
    String sexo;
    String direccion;

    public Paciente(){

    }

    public Paciente(String id_paciente, String nombre, Date fechaNacimiento, String sexo, String direccion){
        this.id_paciente = id_paciente;

        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.direccion = direccion;
    }

    public String getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(String id_paciente) {
        this.id_paciente = id_paciente;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}

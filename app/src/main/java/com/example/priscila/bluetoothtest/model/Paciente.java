package com.example.priscila.bluetoothtest.model;

import java.util.Date;

/**
 * Created by Priscila on 11/03/2018.
 */

public class Paciente {

    public static final String TABLE_PACIENTE = "Paciente";
    public static final String KEY_ID_PACIENTE = "id_paciente";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASS = "password";
    public static final String KEY_FECHANACIMIENTO = "fechaNacimiento";
    public static final String KEY_SEXO = "sexo";

    public static final String CREATE_TABLE_PACIENTE = "CREATE TABLE " + TABLE_PACIENTE +
            "(" + KEY_ID_PACIENTE + " TEXT PRIMARY KEY," + KEY_NOMBRE + " TEXT," + KEY_EMAIL + " TEXT," + KEY_PASS + " TEXT,"+ KEY_FECHANACIMIENTO +
            " DATE," + KEY_SEXO + " TEXT" + ")";

    private String id_paciente;
    private String nombre;
    private String email;
    private String password;
    private Date fechaNacimiento;
    private String sexo;


    public Paciente(){

    }

    public Paciente(String id_paciente, String nombre, String email, String password, Date fechaNacimiento, String sexo) {
        this.id_paciente = id_paciente;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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




}

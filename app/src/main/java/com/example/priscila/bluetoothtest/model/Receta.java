package com.example.priscila.bluetoothtest.model;

import com.example.priscila.bluetoothtest.controller.DatabaseHelper;

import java.net.PortUnreachableException;
import java.util.Date;

/**
 * Created by Priscila on 20/03/2018.
 */

public class Receta {
    public static final String TABLE_RECETA= "Receta";
    public static final String KEY_ID_RECETA="id_receta";
    private static final String KEY_ID_PACIENTE="id_paciente";
    private static final String KEY_ID_CONTACTOALTA="id_contactoAlta";
    private static final String KEY_FECHA = "fecha";

    public static final String CREATE_TABLE_RECETA = "CREATE TABLE " + TABLE_RECETA +
            "(" + KEY_ID_RECETA + " TEXT PRIMARY KEY," + KEY_FECHA + " DATE," +
            KEY_ID_PACIENTE + " TEXT," + KEY_ID_CONTACTOALTA + " TEXT," +
            " FOREIGN KEY (" + KEY_ID_PACIENTE + ") REFERENCES " + Paciente.TABLE_PACIENTE + "(" + Paciente.KEY_ID_PACIENTE+ ")," +
            " FOREIGN KEY (" + KEY_ID_CONTACTOALTA + ") REFERENCES " + DatabaseHelper.TABLE_CONTACTO_PACIENTE + "(" + DatabaseHelper.KEY_ID_CONTACTOPACIENTE + "))";


    String id_receta;
    String id_paciente;
    String id_contactoAlta;
    Date fecha;

    public Receta(){
    }


    public Receta(String id_receta, String id_paciente, String id_contactoAlta, Date fecha) {
        this.id_receta = id_receta;
        this.id_paciente = id_paciente;
        this.id_contactoAlta = id_contactoAlta;
        this.fecha = fecha;
    }


    public String getId_receta() {
        return id_receta;
    }

    public void setId_receta(String id_receta) {
        this.id_receta = id_receta;
    }

    public String getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(String id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getId_contactoAlta() {
        return id_contactoAlta;
    }

    public void setId_contactoAlta(String id_contactoAlta) {
        this.id_contactoAlta = id_contactoAlta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

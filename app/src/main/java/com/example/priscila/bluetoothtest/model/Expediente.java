package com.example.priscila.bluetoothtest.model;

import com.example.priscila.bluetoothtest.controller.DatabaseHelper;

import java.util.Date;

/**
 * Created by Priscila on 11/03/2018.
 */

public class Expediente {
    public static final String TABLE_EXPEDIENTE = "Expediente";
    private static final String KEY_ID_EXPEDIENTE = "id_expediente";
    private static final String KEY_ID_PACIENTE = "id_paciente";
    private static final String KEY_ID_RECETA = "id_receta";
    private static final String KEY_ID_REGISTROEVENTOS = "id_registroEventos";
    private static final String KEY_FECHA = "fecha";

    String id_expediente;
    String id_paciente;
    String id_receta;
    String id_registroEventos;
    Date fecha;

    public static final String CREATE_TABLE_EXPEDIENTE = "CREATE TABLE " + TABLE_EXPEDIENTE +
            "(" + KEY_ID_EXPEDIENTE + " TEXT PRIMARY KEY," + KEY_FECHA + " DATE," +
            KEY_ID_PACIENTE + " TEXT," + KEY_ID_RECETA + " TEXT," + KEY_ID_REGISTROEVENTOS + " TEXT," +
            " FOREIGN KEY (" + KEY_ID_PACIENTE + ") REFERENCES " + Paciente.TABLE_PACIENTE+ "(" + Paciente.KEY_ID_PACIENTE+ ")," +
            " FOREIGN KEY (" + KEY_ID_RECETA + ") REFERENCES " + Receta.TABLE_RECETA+ "(" + Receta.KEY_ID_RECETA+ ")," +
            " FOREIGN KEY (" + KEY_ID_REGISTROEVENTOS + ") REFERENCES " + DatabaseHelper.TABLE_REGISTROEVENTOS+ "(" + DatabaseHelper.KEY_ID_EVENTO+ "))";

    public Expediente(){

    }

    public Expediente(String id_expediente, String id_paciente, String id_receta, String id_registroEventos, Date fecha) {
        this.id_expediente = id_expediente;
        this.id_paciente = id_paciente;
        this.id_receta = id_receta;
        this.id_registroEventos = id_registroEventos;
        this.fecha = fecha;
    }

    public String getId_expediente() {
        return id_expediente;
    }

    public void setId_expediente(String id_expediente) {
        this.id_expediente = id_expediente;
    }

    public String getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(String id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getId_receta() {
        return id_receta;
    }

    public void setId_receta(String id_receta) {
        this.id_receta = id_receta;
    }

    public String getId_registroEventos() {
        return id_registroEventos;
    }

    public void setId_registroEventos(String id_registroEventos) {
        this.id_registroEventos = id_registroEventos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

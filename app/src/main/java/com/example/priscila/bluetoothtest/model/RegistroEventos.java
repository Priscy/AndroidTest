package com.example.priscila.bluetoothtest.model;

import java.util.Date;

/**
 * Created by Priscila on 30/03/2018.
 */

public class RegistroEventos {

    public static final String TABLE_REGISTROEVENTOS = "RegistroEventos";
    public static final String KEY_ID_EVENTO = "id_evento";
    public static final String KEY_ID_ACCIDENTE = "id_accidente";
    public static final String KEY_ID_PACIENTE = "id_paciente";
    public static final String KEY_FECHAHORA="fechaHora";
    public static final String KEY_STATUS = "status";

    String id_evento;
    String id_accidente;
    String id_paciente;
    String fechaHora;
    int status;

    public static final String CREATE_TABLE_REGISTROEVENTOS = "CREATE TABLE " + TABLE_REGISTROEVENTOS +
            "(" + KEY_ID_EVENTO + " TEXT PRIMARY KEY," + KEY_FECHAHORA + " DATETIME," + KEY_STATUS + " INTEGER," +
            KEY_ID_ACCIDENTE + " TEXT," +  KEY_ID_PACIENTE + " TEXT," +
            " FOREIGN KEY (" + KEY_ID_ACCIDENTE + ") REFERENCES " + Accidente.TABLE_ACCIDENTE+ "(" + Accidente.KEY_ID_ACCIDENTE+ ")," +
            " FOREIGN KEY (" + KEY_ID_PACIENTE + ") REFERENCES " + Paciente.TABLE_PACIENTE+ "(" + Paciente.KEY_ID_PACIENTE+ "))";

    public  RegistroEventos(){

    }

    public RegistroEventos(String id_evento, String id_accidente, String id_paciente, String fechaHora, int status) {
        this.id_evento = id_evento;
        this.id_accidente = id_accidente;
        this.id_paciente = id_paciente;
        this.fechaHora = fechaHora;
        this.status = status;
    }

    public String getId_evento() {
        return id_evento;
    }

    public void setId_evento(String id_evento) {
        this.id_evento = id_evento;
    }

    public String getId_accidente() {
        return id_accidente;
    }

    public void setId_accidente(String id_accidente) {
        this.id_accidente = id_accidente;
    }

    public String getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(String id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String  fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}

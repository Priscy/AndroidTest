package com.example.priscila.bluetoothtest.model;

/**
 * Created by Priscila on 11/03/2018.
 */

public class Dispositivo {
    public static final String TABLE_DISPOSITIVO = "Dispositivo";
    public static final String KEY_ID_DISPOSITIVO = "id_dispositivo";
    private static final String KEY_STATUS = "status";

    String id_dispositivo;
    String status;

    public static final String CREATE_TABLE_DISPOSITIVO = "CREATE TABLE " + TABLE_DISPOSITIVO +
            "(" + KEY_ID_DISPOSITIVO + "TEXT PRIMARY KEY," + KEY_STATUS + "TEXT" + ")";

    public Dispositivo(){

    }

    public Dispositivo(String id_dispositivo, String status) {
        this.id_dispositivo = id_dispositivo;
        this.status = status;
    }

    public String getId_dispositivo() {
        return id_dispositivo;
    }

    public void setId_dispositivo(String id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

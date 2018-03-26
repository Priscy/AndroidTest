package com.example.priscila.bluetoothtest.controller;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.example.priscila.bluetoothtest.model.Accidente;
import com.example.priscila.bluetoothtest.model.Dispositivo;
import com.example.priscila.bluetoothtest.model.Expediente;
import com.example.priscila.bluetoothtest.model.Familiar;
import com.example.priscila.bluetoothtest.model.Medicina;
import com.example.priscila.bluetoothtest.model.Medico;
import com.example.priscila.bluetoothtest.model.Paciente;
import com.example.priscila.bluetoothtest.model.Receta;

import java.util.Date;

/**
 * Created by Priscila on 11/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PixkyDb";


    public static final String TABLE_REGISTROEVENTOS = "RegistroEventos";
    public static final String TABLE_DISPOSITIVO_PACIENTE = "AsignarDsipositivoPaciente";
    public static final String TABLE_CONTACTO_PACIENTE = "AsignarContactoPaciente";
    public static final String TABLE_DETALLESRECETA = "DetallesReceta";

    //COMMON COLUMNS
    private static final String KEY_ID_PACIENTE = "id_paciente";

    //REGISTROEVENTOS-COLUMNS
    public static final String KEY_ID_EVENTO = "id_evento";
    private static final String KEY_FECHAHORA="fechaHora";
    private static final String KEY_ID_ACCIDENTE = "id_accidente";
    private static final String KEY_STATUS = "status";

    //DISPOSITIVO PACIENTE COLUMNS
    private static final String KEY_ID_DISPOSITIVO = "id_dispositivo";

    //CONTACTO-PACIENTE-COLUMNS
    private static final String KEY_ID_CONTACTO = "id_contacto";
    private static final String KEY_TIPOCONTACTO = "tipoContacto";
    public static final String KEY_ID_CONTACTOPACIENTE = "id_contactoPaciente";

    //DETALLES RECETA COLUMNS
    public static final String KEY_ID_RECETA = "id_receta";
    private static final String KEY_FRECUENCIA = "frecuencia";
    private static final String KEY_ID_MEDICINA = "id_medicina";
    private static final String KEY_HORAINICIO = "horaInicio";



    //REGISTRO EVENTOS TABLE
    private static final String CREATE_TABLE_REGISTROEVENTOS = "CREATE TABLE " + TABLE_REGISTROEVENTOS +
            "(" + KEY_ID_EVENTO + "TEXT PRIMARY KEY," + KEY_FECHAHORA + "DATETIME," + KEY_STATUS + "BOOLEAN," +
            KEY_ID_ACCIDENTE + "TEXT," + " FOREIGN KEY (" + KEY_ID_ACCIDENTE + ") REFERENCES " + Accidente.TABLE_ACCIDENTE+ "(" + Accidente.KEY_ID_ACCIDENTE+ ")," +
            KEY_ID_PACIENTE + "TEXT," + " FOREIGN KEY (" + KEY_ID_PACIENTE + ") REFERENCES " + Paciente.TABLE_PACIENTE+ "(" + Paciente.KEY_ID_PACIENTE+ ");";

    //DISPOSITIVO PACIENTE TABLE
    private static final  String CREATE_TABLE_DISPOSITIVOPACIENTE = "CREATE TABLE " + TABLE_DISPOSITIVO_PACIENTE +
            "(" + KEY_ID_PACIENTE + "TEXT," + " FOREIGN KEY (" + KEY_ID_PACIENTE + ") REFERENCES " +Paciente.TABLE_PACIENTE+ "(" + Paciente.KEY_ID_PACIENTE+ ")," +
            KEY_ID_DISPOSITIVO + "TEXT," + " FOREIGN KEY (" + KEY_ID_DISPOSITIVO + ") REFERENCES " + Dispositivo.TABLE_DISPOSITIVO+ "(" + Dispositivo.KEY_ID_DISPOSITIVO+ ");";

    //CONTACTO PACIENTE TABLE
    private static final String CREATE_TABLE_CONTACTOPACIENTE = "CREATE TABLE " + TABLE_CONTACTO_PACIENTE +
           "(" +  KEY_ID_CONTACTOPACIENTE + "TEXT PRIMARY KET," + KEY_ID_CONTACTO + "TEXT," + KEY_TIPOCONTACTO + "TEXT," +
            KEY_ID_PACIENTE + "TEXT," + " FOREIGN KEY (" + KEY_ID_PACIENTE + ") REFERENCES " + Paciente.TABLE_PACIENTE+ "(" + KEY_ID_PACIENTE + ");";

    //DETALLES RECETA TABLE
    private static final String CREATE_TABLE_DETALLESRECETA = "CREATE TABLE " + TABLE_DETALLESRECETA +
            "(" + KEY_FRECUENCIA + "INTEGER," + KEY_HORAINICIO + "STRFTIME," +
            KEY_ID_RECETA + "TEXT," + "FOREIGN KEY (" + KEY_ID_RECETA + ") REFERENCES " + Receta.TABLE_RECETA+ "(" + Receta.KEY_ID_RECETA+")," +
            KEY_ID_MEDICINA + "TEXT," + " FOREIGN KEY (" + KEY_ID_MEDICINA + ") REFERENCES " + Medicina.TABLE_MEDICINA+ "(" + Medicina.KEY_ID_MEDICINA+ ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Accidente.CREATE_TABLE_ACCIDENTE);
        db.execSQL(Dispositivo.CREATE_TABLE_DISPOSITIVO);
        db.execSQL(Expediente.CREATE_TABLE_EXPEDIENTE);
        db.execSQL(Familiar.CREATE_TABLE_FAMILIAR);
        db.execSQL(Medicina.CREATE_TABLE_MEDICINA);
        db.execSQL(Medico.CREATE_TABLE_MEDICO);
        db.execSQL(Paciente.CREATE_TABLE_PACIENTE);
        db.execSQL(Receta.CREATE_TABLE_RECETA);
        db.execSQL(CREATE_TABLE_REGISTROEVENTOS);
        db.execSQL(CREATE_TABLE_DISPOSITIVOPACIENTE);
        db.execSQL(CREATE_TABLE_CONTACTOPACIENTE);
        db.execSQL(CREATE_TABLE_DETALLESRECETA);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Accidente.TABLE_ACCIDENTE);
        db.execSQL("DROP TABLE IF EXISTS " + Dispositivo.TABLE_DISPOSITIVO);
        db.execSQL("DROP TABLE IF EXISTS " + Expediente.TABLE_EXPEDIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + Familiar.TABLE_FAMILIAR);
        db.execSQL("DROP TABLE IF EXISTS " + Medicina.TABLE_MEDICINA);
        db.execSQL("DROP TABLE IF EXISTS " + Medico.TABLE_MEDICO);
        db.execSQL("DROP TABLE IF EXISTS " + Paciente.TABLE_PACIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + Receta.TABLE_RECETA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTROEVENTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISPOSITIVO_PACIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTO_PACIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETALLESRECETA);

        onCreate(db);
    }

}

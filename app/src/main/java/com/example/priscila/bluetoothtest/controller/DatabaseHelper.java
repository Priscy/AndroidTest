package com.example.priscila.bluetoothtest.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.util.Log;

import com.example.priscila.bluetoothtest.model.Accidente;
import com.example.priscila.bluetoothtest.model.Dispositivo;
import com.example.priscila.bluetoothtest.model.Expediente;
import com.example.priscila.bluetoothtest.model.Familiar;
import com.example.priscila.bluetoothtest.model.Medicina;
import com.example.priscila.bluetoothtest.model.Medico;
import com.example.priscila.bluetoothtest.model.Paciente;
import com.example.priscila.bluetoothtest.model.Receta;
import com.example.priscila.bluetoothtest.model.RegistroEventos;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Priscila on 11/03/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase mDefaultWritableDatabase = null;
    private SQLiteDatabase mDefaultReadableDatabase= null;

    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PixkyDb";

    public static final String TABLE_DISPOSITIVO_PACIENTE = "AsignarDsipositivoPaciente";
    public static final String TABLE_CONTACTO_PACIENTE = "AsignarContactoPaciente";
    public static final String TABLE_DETALLESRECETA = "DetallesReceta";

    //COMMON COLUMNS
    private static final String KEY_ID_PACIENTE = "id_paciente";

    //REGISTROEVENTOS-COLUMNS


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


    //DISPOSITIVO PACIENTE TABLE
    private static final  String CREATE_TABLE_DISPOSITIVOPACIENTE = "CREATE TABLE " + TABLE_DISPOSITIVO_PACIENTE +
            "(" + KEY_ID_PACIENTE + " TEXT," + KEY_ID_DISPOSITIVO + " TEXT," +
            " FOREIGN KEY (" + KEY_ID_PACIENTE + ") REFERENCES " +Paciente.TABLE_PACIENTE+ "(" + Paciente.KEY_ID_PACIENTE+ ")," +
            " FOREIGN KEY (" + KEY_ID_DISPOSITIVO + ") REFERENCES " + Dispositivo.TABLE_DISPOSITIVO+ "(" + Dispositivo.KEY_ID_DISPOSITIVO+ "))";

    //CONTACTO PACIENTE TABLE
    private static final String CREATE_TABLE_CONTACTOPACIENTE = "CREATE TABLE " + TABLE_CONTACTO_PACIENTE +
           "(" + KEY_ID_CONTACTOPACIENTE + " TEXT PRIMARY KEY," + KEY_ID_CONTACTO + " TEXT," + KEY_TIPOCONTACTO + " TEXT," +
            KEY_ID_PACIENTE + " TEXT," + " FOREIGN KEY (" + KEY_ID_PACIENTE + ") REFERENCES " + Paciente.TABLE_PACIENTE+ "(" + Paciente.KEY_ID_PACIENTE+ "))";

    //DETALLES RECETA TABLE
    private static final String CREATE_TABLE_DETALLESRECETA = "CREATE TABLE " + TABLE_DETALLESRECETA +
            "(" + KEY_FRECUENCIA + " INTEGER," + KEY_HORAINICIO + " STRFTIME," +
            KEY_ID_RECETA + " INTEGER," + KEY_ID_MEDICINA + " TEXT," +
            " FOREIGN KEY (" + KEY_ID_RECETA + ") REFERENCES " + Receta.TABLE_RECETA+ "(" + Receta.KEY_ID_RECETA+")," +
            " FOREIGN KEY (" + KEY_ID_MEDICINA + ") REFERENCES " + Medicina.TABLE_MEDICINA+ "(" + Medicina.KEY_ID_MEDICINA+ "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.mDefaultWritableDatabase = db;
        this.mDefaultReadableDatabase = db;
        db.execSQL(Accidente.CREATE_TABLE_ACCIDENTE);
        db.execSQL(Dispositivo.CREATE_TABLE_DISPOSITIVO);
        db.execSQL(Expediente.CREATE_TABLE_EXPEDIENTE);
        db.execSQL(Familiar.CREATE_TABLE_FAMILIAR);
        db.execSQL(Medicina.CREATE_TABLE_MEDICINA);
        db.execSQL(Medico.CREATE_TABLE_MEDICO);
        db.execSQL(Paciente.CREATE_TABLE_PACIENTE);
        db.execSQL(Receta.CREATE_TABLE_RECETA);
        db.execSQL(RegistroEventos.CREATE_TABLE_REGISTROEVENTOS);
        db.execSQL(CREATE_TABLE_DISPOSITIVOPACIENTE);
        db.execSQL(CREATE_TABLE_CONTACTOPACIENTE);
        db.execSQL(CREATE_TABLE_DETALLESRECETA);

        createAccidente("Accidente_01", "Caida");
        createAccidente("Accidente_02", "Emergencia");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.mDefaultWritableDatabase = db;
        this.mDefaultReadableDatabase = db;
        db.execSQL("DROP TABLE IF EXISTS " + Accidente.TABLE_ACCIDENTE);
        db.execSQL("DROP TABLE IF EXISTS " + Dispositivo.TABLE_DISPOSITIVO);
        db.execSQL("DROP TABLE IF EXISTS " + Expediente.TABLE_EXPEDIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + Familiar.TABLE_FAMILIAR);
        db.execSQL("DROP TABLE IF EXISTS " + Medicina.TABLE_MEDICINA);
        db.execSQL("DROP TABLE IF EXISTS " + Medico.TABLE_MEDICO);
        db.execSQL("DROP TABLE IF EXISTS " + Paciente.TABLE_PACIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + Receta.TABLE_RECETA);
        db.execSQL("DROP TABLE IF EXISTS " + RegistroEventos.TABLE_REGISTROEVENTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISPOSITIVO_PACIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTO_PACIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETALLESRECETA);

        onCreate(db);
    }

    public long createPaciente(String id_paciente, String nombre, String email, String password, String fechaNacimiento, String sexo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Paciente.KEY_ID_PACIENTE, id_paciente);
        values.put(Paciente.KEY_NOMBRE, nombre);
        values.put(Paciente.KEY_EMAIL, email);
        values.put(Paciente.KEY_PASS, password);
        values.put(Paciente.KEY_FECHANACIMIENTO, fechaNacimiento);
        values.put(Paciente.KEY_SEXO, sexo);

        long paciente_id = db.insert(Paciente.TABLE_PACIENTE, null, values);
        Log.d(Constants.TAG, "Paciente "+ nombre + " creado");
        return paciente_id;
    }

    public String getLastPaciente(){
        String lastId = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String count = "SELECT count(*) FROM " + Paciente.TABLE_PACIENTE;;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){
            String selectQuery = "SELECT  * FROM " + Paciente.TABLE_PACIENTE;

            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.moveToLast()){
                lastId = cursor.getString(cursor.getColumnIndex(Paciente.KEY_ID_PACIENTE));
            }
            Log.d(Constants.TAG, lastId);
            lastId = lastId.substring(9);
            Log.d(Constants.TAG, lastId);
        }

        else{
            lastId="null";
        }

        return lastId;
    }

    public long createAccidente(String id_accidente, String descripcion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Accidente.KEY_ID_ACCIDENTE, id_accidente);
        values.put(Accidente.KEY_DESCRIPCION, descripcion);

        long accidente_id = db.insert(Accidente.TABLE_ACCIDENTE, null, values);
        Log.d(Constants.TAG, "Accidente: " + descripcion + "registrado");
        return accidente_id;

    }

    public List<Accidente> getAccidentes(){
        List<Accidente> accidentes = new ArrayList<Accidente>();
        String selectQuery = "SELECT  * FROM " + Accidente.TABLE_ACCIDENTE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Accidente acc = new Accidente();
                acc.setId_accidente(c.getString(c.getColumnIndex(Accidente.KEY_ID_ACCIDENTE)));
                acc.setDescripcion(c.getString(c.getColumnIndex(Accidente.KEY_DESCRIPCION)));
                accidentes.add(acc);
            } while (c.moveToNext());
        }
        return accidentes;
    }

    public long createRegistroEventos(String id_evento, String id_accidente, String id_paciente, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RegistroEventos.KEY_ID_EVENTO, id_evento);
        values.put(RegistroEventos.KEY_ID_ACCIDENTE, id_accidente);
        values.put(RegistroEventos.KEY_ID_PACIENTE, id_paciente);
        values.put(RegistroEventos.KEY_FECHAHORA, getDateTime().toString());
        values.put(RegistroEventos.KEY_STATUS, status);

        long registro_id = db.insert(RegistroEventos.TABLE_REGISTROEVENTOS, null, values);
        return registro_id;
    }

    public List<RegistroEventos> getCaidas(){
        List<RegistroEventos> caidas = new ArrayList<RegistroEventos>();
        SQLiteDatabase db = this.getReadableDatabase();

        String count = "SELECT count(*) FROM " + RegistroEventos.TABLE_REGISTROEVENTOS + " WHERE " + RegistroEventos.KEY_ID_ACCIDENTE + " = 'Accidente_01'";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0) {
            String selectQuery = "SELECT  * FROM " + RegistroEventos.TABLE_REGISTROEVENTOS + " WHERE " + RegistroEventos.KEY_ID_ACCIDENTE + " = 'Accidente_01'";

            Log.e(LOG, selectQuery);
            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    RegistroEventos event = new RegistroEventos();
                    event.setId_evento(c.getString(c.getColumnIndex(RegistroEventos.KEY_ID_EVENTO)));
                    event.setId_accidente(c.getString(c.getColumnIndex(RegistroEventos.KEY_ID_ACCIDENTE)));
                    event.setId_paciente(c.getString(c.getColumnIndex(RegistroEventos.KEY_ID_PACIENTE)));
                    event.setFechaHora(c.getString(c.getColumnIndex(RegistroEventos.KEY_FECHAHORA)));
                    event.setStatus(c.getInt(c.getColumnIndex(RegistroEventos.KEY_STATUS)));
                    caidas.add(event);
                } while (c.moveToNext());
            }
        }
        return caidas;
    }

    public List<RegistroEventos> getEmergencias(){
        List<RegistroEventos> emergencias = new ArrayList<RegistroEventos>();
        SQLiteDatabase db = this.getReadableDatabase();

        String count = "SELECT count(*) FROM " + RegistroEventos.TABLE_REGISTROEVENTOS + " WHERE " + RegistroEventos.KEY_ID_ACCIDENTE + " = 'Accidente_02'";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0) {
            String selectQuery = "SELECT  * FROM " + RegistroEventos.TABLE_REGISTROEVENTOS + " WHERE " + RegistroEventos.KEY_ID_ACCIDENTE + " = 'Accidente_02'";
            Log.e(LOG, selectQuery);

            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    RegistroEventos event = new RegistroEventos();
                    event.setId_evento(c.getString(c.getColumnIndex(RegistroEventos.KEY_ID_EVENTO)));
                    event.setId_accidente(c.getString(c.getColumnIndex(RegistroEventos.KEY_ID_ACCIDENTE)));
                    event.setId_paciente(c.getString(c.getColumnIndex(RegistroEventos.KEY_ID_PACIENTE)));
                    event.setFechaHora(c.getString(c.getColumnIndex(RegistroEventos.KEY_FECHAHORA)));
                    event.setStatus(c.getInt(c.getColumnIndex(RegistroEventos.KEY_STATUS)));
                    emergencias.add(event);
                } while (c.moveToNext());
            }

        }
        return emergencias;
    }

    public String getLastEvent(){
        String lastId = "";
        SQLiteDatabase db = this.getReadableDatabase();

        String count = "SELECT count(*) FROM " + RegistroEventos.TABLE_REGISTROEVENTOS;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){
            String selectQuery = "SELECT  * FROM " + RegistroEventos.TABLE_REGISTROEVENTOS;

            Cursor cursor = db.rawQuery(selectQuery, null);

            if(cursor.moveToLast()){
                lastId = cursor.getString(cursor.getColumnIndex(RegistroEventos.KEY_ID_EVENTO));
            }
            Log.d(Constants.TAG, lastId);
            lastId = lastId.substring(7);
            Log.d(Constants.TAG, lastId);
        } else {
            lastId = "null";
        }

        return lastId;

    }

    public void updateEvent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + RegistroEventos.TABLE_REGISTROEVENTOS + " SET " + RegistroEventos.KEY_STATUS  + "= '1' WHERE " + RegistroEventos.KEY_ID_EVENTO + "='Evento_" + id +"'");
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public long createMedicine(String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Medicina.KEY_NOMBRE, nombre);

        long medicina_id = db.insert(Medicina.TABLE_MEDICINA, null, values);
        Log.d(Constants.TAG, "Medicina: " + nombre+ " registrada");
        return medicina_id;
    }

    public List<Medicina> getMedicinas(String searchMedicina){
        Log.d(Constants.TAG, "Inside Get medicinas: " + searchMedicina);
        List<Medicina> medicinas= new ArrayList<Medicina>();
        SQLiteDatabase db = this.getReadableDatabase();

        String count = "SELECT count(*) FROM " + Medicina.TABLE_MEDICINA + " WHERE " + Medicina.KEY_NOMBRE + " LIKE '%" + searchMedicina + "%'";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount > 0) {
            String selectQuery = "SELECT * FROM " + Medicina.TABLE_MEDICINA + " WHERE " + Medicina.KEY_NOMBRE + " LIKE '%" + searchMedicina + "%'" + " ORDER BY " + Medicina.KEY_ID_MEDICINA + " DESC";
            Log.e(LOG, selectQuery);

            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Medicina medicina= new Medicina();
                    medicina.setNombre(c.getString(c.getColumnIndex(Medicina.KEY_NOMBRE)));
                    medicinas.add(medicina);
                } while (c.moveToNext());
            }
        }
        return medicinas;

    }

    public long createReceta(String id_paciente, String id_contactoAlta, String fecha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Receta.KEY_ID_PACIENTE, id_paciente);
        values.put(Receta.KEY_ID_CONTACTOALTA, id_contactoAlta);
        values.put(Receta.KEY_FECHA, fecha);

        long receta_id = db.insert(Receta.TABLE_RECETA, null, values);
        Log.d(Constants.TAG, "Receta registrada");
        return receta_id;
    }

    public long createDetallesReceta(String id_paciente, String id_contactoAlta, String fecha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Receta.KEY_ID_PACIENTE, id_paciente);
        values.put(Receta.KEY_ID_CONTACTOALTA, id_contactoAlta);
        values.put(Receta.KEY_FECHA, fecha);

        long receta_id = db.insert(Receta.TABLE_RECETA, null, values);
        Log.d(Constants.TAG, "Receta registrada");
        return receta_id;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        final SQLiteDatabase db;
        if(mDefaultWritableDatabase != null){
            db = mDefaultWritableDatabase;
        } else {
            db = super.getWritableDatabase();
        }
        return db;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        final SQLiteDatabase db;
        if(mDefaultReadableDatabase != null){
            db = mDefaultReadableDatabase;
        } else {
            db = super.getReadableDatabase();
        }
        return db;
    }


    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}

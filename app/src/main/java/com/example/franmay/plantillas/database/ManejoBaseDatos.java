package com.example.franmay.plantillas.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.franmay.plantillas.proveedor_contenido.Contrato;
import static android.content.ContentValues.TAG;


public class ManejoBaseDatos extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "plantillas.db";
    private static final int DATABASE_VERSION = 105;

    Context contexto;

    String sqlJugadorEquipo;


    public ManejoBaseDatos(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        contexto=context;
    }


    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);

        if (!db.isReadOnly())
        {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");

        }
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sqlLogin = "create table if not exists "
                          + Contrato.TABLA_LOGIN
                          //+ " (_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                          + "("
                          + Contrato.ID_LOGIN + " INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                          + Contrato.NOMBRE_LOGIN + " TEXT, "
                          + Contrato.PASSWORD_LOGIN + " TEXT, "
                          + Contrato.ADMINISTRADOR + " INTEGER)";


        String sqlJugador = "create table if not exists "
                          + Contrato.TABLA_JUGADOR
                          //+ " (_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                          + "("
                          + Contrato.ID_JUGADOR + " INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                          + Contrato.NOMBRE_JUGADOR + " TEXT , "
                          + Contrato.EQUIPO_JUGADOR + " TEXT, "
                          + Contrato.EDAD_JUGADOR + " INTEGER, "
                          + Contrato.PAIS_JUGADOR + " TEXT, "
                          + Contrato.POSICION_JUGADOR + " TEXT, "
                          + Contrato.INDICE_POSICION_JUGADOR + " INTEGER, "
                          + Contrato.FOTO_JUGADOR + " BLOB, "

                          + Contrato.JUGADOR_NACIONALIZADO + " INTEGER, "
                          + Contrato.JUGADOR_COMUNITARIO + " INTEGER, "
                          + Contrato.JUGADOR_EXTRACOMUNITARIO + " INTEGER, "

                          + Contrato.LIGA_NACIONAL + " INTEGER, "
                          + Contrato.LIGA_EXTRANJERA + " INTEGER, "
                          + Contrato.COPA_REY + " INTEGER, "
                          + Contrato.CHAMPIONS_LEAGUE+ " INTEGER, "
                          + Contrato.MUNDIAL + " INTEGER)";


        String sqlEquipo = "create table if not exists "
                         + Contrato.TABLA_EQUIPO
                         //+ " (_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                         + "("
                         + Contrato.ID_EQUIPO + " INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                         + Contrato.NOMBRE_EQUIPO + " TEXT , "
                         + Contrato.EQUIPO_PAIS + " TEXT, "
                         + Contrato.FOTO_EQUIPO + " BLOB)";


        String sqlJugadorEquipo = "create table if not exists "
                + Contrato.TABLA_JUGADOR_EQUIPO
                //+ " (_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                + "("
                + Contrato.RELACION_NOMBRE_JUGADOR + " INTEGER, "
                + Contrato.RELACION_NOMBRE_EQUIPO + " INTEGER, "
                + "PRIMARY KEY " + "(" + Contrato.RELACION_NOMBRE_JUGADOR + ", " + Contrato.RELACION_NOMBRE_EQUIPO + "), "
                + "FOREIGN KEY (`" +  Contrato.RELACION_NOMBRE_JUGADOR + "`) " +
                                 "REFERENCES `" + Contrato.TABLA_JUGADOR + "` (`" + Contrato.ID_JUGADOR + "`)"
                + " ON DELETE CASCADE"
                + " ON UPDATE CASCADE, "
                + "FOREIGN KEY (`" +  Contrato.RELACION_NOMBRE_EQUIPO + "`) " +
                                 "REFERENCES `" + Contrato.TABLA_EQUIPO + "` (`" + Contrato.ID_EQUIPO + "`)"
                + " ON DELETE CASCADE"
                + " ON UPDATE CASCADE)";


        String sqlCuerpoTecnico = "create table if not exists "
                                + Contrato.TABLA_CUERPO_TECNICO
                                //+ " (_id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                                + "("
                                + Contrato.ID_CUERPO_TECNICO + " INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                                + Contrato.CLAVE_AJENA_EQUIPO + " INTEGER, "
                                + Contrato.NOMBRE_CUERPO_TECNICO + " TEXT, "
                                + Contrato.EDAD_CUERPO_TECNICO + " INTEGER, "
                                + Contrato.PAIS_CUERPO_TECNICO + " TEXT, "
                                + Contrato.CARGO_CUERPO_TECNICO + " TEXT, "
                                + Contrato.INDICE_CARGO + " INTEGER, "
                                + Contrato.FOTO_CUERPO_TECNICO + " BLOB, "
                                + "FOREIGN KEY (`" +  Contrato.CLAVE_AJENA_EQUIPO + "`) " +
                                                 " REFERENCES `" + Contrato.TABLA_EQUIPO + "` (`" + Contrato.ID_EQUIPO + "`)"
                                + " ON DELETE CASCADE"
                                + " ON UPDATE CASCADE)";


        db.execSQL(sqlLogin);
        db.execSQL(sqlJugador);
        db.execSQL(sqlEquipo);
        db.execSQL(sqlJugadorEquipo);
        db.execSQL(sqlCuerpoTecnico);


        String login="insert into login (usuario, password, administrador) values ('franmay', 'queen', 1)";

        db.execSQL(login);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String mensaje="Actualizando la base de datos de la versión " + oldVersion  +  " a la versión " + newVersion;

        Log.d(TAG, mensaje);

        String sqlDeleteLogin = "DROP TABLE IF EXISTS " + Contrato.TABLA_LOGIN;
        String sqlDeleteJugador = "DROP TABLE IF EXISTS " + Contrato.TABLA_JUGADOR;
        String sqlDeleteEquipo = "DROP TABLE IF EXISTS " + Contrato.TABLA_EQUIPO;
        String sqlDeleteJugadorEquipo = "DROP TABLE IF EXISTS " + Contrato.TABLA_JUGADOR_EQUIPO;
        String sqlDeleteCuerpoTecnico = "DROP TABLE IF EXISTS " + Contrato.TABLA_CUERPO_TECNICO;

        db.execSQL(sqlDeleteLogin);
        db.execSQL(sqlDeleteJugador);
        db.execSQL(sqlDeleteEquipo);
        db.execSQL(sqlDeleteJugadorEquipo);
        db.execSQL(sqlDeleteCuerpoTecnico);

        onCreate(db);
    }
}

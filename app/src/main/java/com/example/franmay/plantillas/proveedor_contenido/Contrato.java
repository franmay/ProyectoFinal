package com.example.franmay.plantillas.proveedor_contenido;

import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.franmay.plantillas.variables_globales.VariablesGlobales.AUTHORITY;

public class Contrato implements BaseColumns {

    public static String TABLA_LOGIN = "login";
    public static String TABLA_JUGADOR = "jugador";
    public static String TABLA_CUERPO_TECNICO = "cuerpo_tecnico";
    public static String TABLA_EQUIPO = "equipo";
    public static String TABLA_JUGADOR_EQUIPO = "jugador_equipo";
    public static String TABLA_GASTO = "gasto";
    public static String TABLA_EMPLEADO = "empleado";
    public static String TABLA_TELEFONO = "telefono";


    public static final Uri CONTENT_URI_LOGIN = Uri.parse("content://" + AUTHORITY + "/" + TABLA_LOGIN);
    public static final Uri CONTENT_URI_JUGADOR = Uri.parse("content://" + AUTHORITY + "/" + TABLA_JUGADOR);
    public static final Uri CONTENT_URI_CUERPO_TECNICO = Uri.parse("content://" + AUTHORITY + "/" + TABLA_CUERPO_TECNICO);
    public static final Uri CONTENT_URI_EQUIPO = Uri.parse("content://" + AUTHORITY + "/" + TABLA_EQUIPO);
    public static final Uri CONTENT_URI_JUGADOR_EQUIPO = Uri.parse("content://" + AUTHORITY + "/" + TABLA_JUGADOR_EQUIPO);


    // Columnas tabla "login"
    public static final String ID_LOGIN = "id";
    public static final String NOMBRE_LOGIN = "usuario";
    public static final String PASSWORD_LOGIN = "password";
    public static final String ADMINISTRADOR = "administrador";


    // Columnas tabla "jugador"
    public static final String ID_JUGADOR = "id";
    public static final String NOMBRE_JUGADOR = "nombre";
    public static final String EQUIPO_JUGADOR = "equipo";
    public static final String EDAD_JUGADOR = "edad";
    public static final String PAIS_JUGADOR = "pais";
    public static final String POSICION_JUGADOR = "puesto";
    public static final String INDICE_POSICION_JUGADOR = "indice_posicion";
    public static final String FOTO_JUGADOR = "foto_jugador";

    public static final String JUGADOR_NACIONALIZADO = "nacionalizado";
    public static final String JUGADOR_COMUNITARIO = "comunitario";
    public static final String JUGADOR_EXTRACOMUNITARIO = "extracomunitario";

    public static final String LIGA_NACIONAL = "liga";
    public static final String LIGA_EXTRANJERA = "liga_extranjera";
    public static final String COPA_REY = "copa_rey";
    public static final String CHAMPIONS_LEAGUE = "champions_league";
    public static final String MUNDIAL = "mundial";


    // Columnas tabla "cuerpo_tecnico"
    public static final String ID_CUERPO_TECNICO = "id";
    public static final String CLAVE_AJENA_EQUIPO = "idEquipo";
    public static final String NOMBRE_CUERPO_TECNICO = "nombre";
    public static final String EDAD_CUERPO_TECNICO = "edad";
    public static final String PAIS_CUERPO_TECNICO = "pais";
    public static final String CARGO_CUERPO_TECNICO = "cargo";
    public static final String INDICE_CARGO = "indice_cargo";
    public static final String FOTO_CUERPO_TECNICO = "foto_cuerpo_tecnico";


    // Columnas tabla "equipo"
    public static final String ID_EQUIPO = "id";
    public static final String NOMBRE_EQUIPO = "nombre";
    public static final String EQUIPO_PAIS = "pais";
    public static final String FOTO_EQUIPO = "imagen";


    // Columnas tabla "jugador_equipo"
    //public static final String ID_JUGADOR_EQUIPO = "id";
    public static final String RELACION_NOMBRE_JUGADOR = "jugador";
    public static final String RELACION_NOMBRE_EQUIPO = "equipo";
}

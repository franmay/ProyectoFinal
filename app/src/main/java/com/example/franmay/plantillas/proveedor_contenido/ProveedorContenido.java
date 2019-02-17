package com.example.franmay.plantillas.proveedor_contenido;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.SparseArray;
import com.example.franmay.plantillas.database.ManejoBaseDatos;
import java.util.ArrayList;
import static com.example.franmay.plantillas.variables_globales.VariablesGlobales.AUTHORITY;


public class ProveedorContenido extends ContentProvider {

    public ManejoBaseDatos dbHelper;
    private SQLiteDatabase sqlDB;

    private static final int ONE_REG_PLAYER = 1;
    private static final int ALL_REGS_PLAYER = 2;

    private static final int ONE_REG_CUERPO_TECNICO = 3;
    private static final int ALL_REGS_CUERPO_TECNICO = 4;

    private static final int ONE_REG_EQUIPO = 5;
    private static final int ALL_REGS_EQUIPO = 6;

    private static final int ONE_REG_LOGIN = 7;
    private static final int ALL_REGS_LOGIN = 8;

    private static final int ONE_REG_JUGADOR_EQUIPO =9;
    private static final int ALL_REGS_JUGADOR_EQUIPO =10;

    private static final int ONE_REG_EMPLEADO = 11;

    private static UriMatcher uriMatcher;
    private static SparseArray<String> mimeTypes;


    String sqlJoin="equipo inner join jugador_equipo on equipo.id = jugador_equipo.equipo" +
                   " inner join jugador on jugador_equipo.jugador = jugador.id";


    static
    {
        uriMatcher = new UriMatcher(0);
        mimeTypes = new SparseArray<>();

        uriMatcher.addURI(AUTHORITY,
                Contrato.TABLA_JUGADOR + "/#",
                ONE_REG_PLAYER);

        uriMatcher.addURI(AUTHORITY,
                Contrato.TABLA_JUGADOR,
                ALL_REGS_PLAYER);

        uriMatcher.addURI(AUTHORITY,
                Contrato.TABLA_CUERPO_TECNICO + "/#",
                ONE_REG_CUERPO_TECNICO);

        uriMatcher.addURI(AUTHORITY,
                Contrato.TABLA_CUERPO_TECNICO,
                ALL_REGS_CUERPO_TECNICO);

        uriMatcher.addURI(AUTHORITY,
                Contrato.TABLA_EQUIPO + "/#",
                ONE_REG_EQUIPO);

        uriMatcher.addURI(AUTHORITY,
                Contrato.TABLA_EQUIPO,
                ALL_REGS_EQUIPO);

        uriMatcher.addURI(AUTHORITY,
                Contrato.TABLA_LOGIN + "/#",
                ONE_REG_LOGIN);

        uriMatcher.addURI(AUTHORITY,
                Contrato.TABLA_LOGIN,
                ALL_REGS_LOGIN);

        uriMatcher.addURI(AUTHORITY,
                Contrato.TABLA_JUGADOR_EQUIPO + "/#",
                ONE_REG_JUGADOR_EQUIPO);

        uriMatcher.addURI(AUTHORITY,
                Contrato.TABLA_JUGADOR_EQUIPO,
                ALL_REGS_JUGADOR_EQUIPO);

        uriMatcher.addURI(AUTHORITY,
                Contrato.TABLA_EMPLEADO + "/#",
                ONE_REG_EMPLEADO);


        mimeTypes.put(ONE_REG_PLAYER,
                "vnd.android.cursor.item/vnd." + AUTHORITY + "." + Contrato.TABLA_JUGADOR);

        mimeTypes.put(ALL_REGS_PLAYER,
                "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + Contrato.TABLA_JUGADOR);

        mimeTypes.put(ONE_REG_CUERPO_TECNICO,
                "vnd.android.cursor.item/vnd." + AUTHORITY + "." + Contrato.TABLA_CUERPO_TECNICO);

        mimeTypes.put(ALL_REGS_CUERPO_TECNICO,
                "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + Contrato.TABLA_CUERPO_TECNICO);

        mimeTypes.put(ONE_REG_EQUIPO,
                "vnd.android.cursor.item/vnd." + AUTHORITY + "." + Contrato.TABLA_EQUIPO);

        mimeTypes.put(ALL_REGS_EQUIPO,
                "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + Contrato.TABLA_EQUIPO);

        mimeTypes.put(ONE_REG_LOGIN,
                "vnd.android.cursor.item/vnd." + AUTHORITY + "." + Contrato.TABLA_LOGIN);

        mimeTypes.put(ALL_REGS_LOGIN,
                "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + Contrato.TABLA_LOGIN);

        mimeTypes.put(ONE_REG_JUGADOR_EQUIPO,
                "vnd.android.cursor.item/vnd." + AUTHORITY + "." + Contrato.TABLA_JUGADOR_EQUIPO);

        mimeTypes.put(ALL_REGS_JUGADOR_EQUIPO,
                "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + Contrato.TABLA_JUGADOR_EQUIPO);

        mimeTypes.put(ONE_REG_EMPLEADO,
                "vnd.android.cursor.item/vnd." + AUTHORITY + "." + Contrato.TABLA_EMPLEADO);
    }


    public ProveedorContenido()
    {

    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        sqlDB = dbHelper.getWritableDatabase();

        int numeroRegistro=0;
        String tabla="";
        String where=selection;
        String argumentos[]= selectionArgs;

        //boolean join=false;


        switch (uriMatcher.match(uri))
        {
            case ONE_REG_PLAYER:
                tabla=uri.getPathSegments().get(0);
                where = Contrato.ID_JUGADOR + "=?";
                argumentos=new String[] {uri.getLastPathSegment()};
            break;

            case ALL_REGS_PLAYER:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_CUERPO_TECNICO:
                tabla=uri.getPathSegments().get(0);
                where = Contrato.ID_CUERPO_TECNICO + "=?";
                argumentos=new String[] {uri.getLastPathSegment()};
            break;

            case ALL_REGS_CUERPO_TECNICO:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_EQUIPO:
                tabla=uri.getPathSegments().get(0);
                where = Contrato.ID_EQUIPO + "=?";
                argumentos=new String[] {uri.getLastPathSegment()};
            break;

            case ALL_REGS_EQUIPO:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_LOGIN:
                tabla=uri.getPathSegments().get(0);
                where = Contrato.ID_LOGIN + "=?";
                argumentos=new String[] {uri.getLastPathSegment()};
            break;

            case ALL_REGS_LOGIN:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_JUGADOR_EQUIPO:
                tabla=uri.getLastPathSegment();
                //join=true;
            break;

            case ALL_REGS_JUGADOR_EQUIPO:
                tabla=uri.getLastPathSegment();
                //join=true;
            break;

            default:
                String mensajeError=String.valueOf(uri);
                throw new IllegalArgumentException(mensajeError);
        }


        numeroRegistro=sqlDB.delete(tabla, where, argumentos);


        if (numeroRegistro > 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numeroRegistro;
    }


    @Override
    public String getType(Uri uri)
    {
        return mimeTypes.get(uriMatcher.match(uri));
    }


    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        sqlDB = dbHelper.getWritableDatabase();

        String tabla;

        switch (uriMatcher.match(uri))
        {
            case ONE_REG_PLAYER:
                tabla=uri.getPathSegments().get(0);
            break;

            case ALL_REGS_PLAYER:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_CUERPO_TECNICO:
                tabla=uri.getPathSegments().get(0);
            break;

            case ALL_REGS_CUERPO_TECNICO:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_EQUIPO:
                tabla=uri.getPathSegments().get(0);
            break;

            case ALL_REGS_EQUIPO:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_LOGIN:
                tabla=uri.getPathSegments().get(0);
            break;

            case ALL_REGS_LOGIN:
                tabla=uri.getLastPathSegment();
            break;

            case ALL_REGS_JUGADOR_EQUIPO:
                tabla=uri.getLastPathSegment();
            break;

            default:
                String mensajeError=String.valueOf(uri);
                throw new IllegalArgumentException(mensajeError);
        }


        try
        {
            long rowId = sqlDB.insertOrThrow(tabla, null, values);

            Uri rowUri = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        catch (SQLiteConstraintException e)
        {
            throw new SQLiteConstraintException(String.valueOf(e.getMessage()));
        }
        catch (SQLException e)
        {
            throw new SQLException(String.valueOf(e.getMessage()));
        }
    }


    @Override
    public boolean onCreate()
    {
        dbHelper=new ManejoBaseDatos(getContext());

        if (dbHelper!=null)
            return true;
        else
           return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        sqlDB = dbHelper.getReadableDatabase();

        String tabla="";
        String where=selection;
        String argumentos[] = selectionArgs;

        boolean join=false;


        switch (uriMatcher.match(uri))
        {
            case ONE_REG_PLAYER:
                tabla=uri.getPathSegments().get(0);
                where="id=" + uri.getLastPathSegment();
            break;

            case ALL_REGS_PLAYER:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_CUERPO_TECNICO:
                tabla=uri.getPathSegments().get(0);
                where="id=" + uri.getLastPathSegment();
            break;

            case ALL_REGS_CUERPO_TECNICO:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_EQUIPO:
                tabla=uri.getPathSegments().get(0);
                where="id=" + uri.getLastPathSegment();
            break;

            case ALL_REGS_EQUIPO:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_LOGIN:
                tabla=uri.getPathSegments().get(0);
                where=Contrato.NOMBRE_LOGIN + "=?";
             break;

            case ALL_REGS_LOGIN:
                tabla=uri.getLastPathSegment();
            break;

            case ALL_REGS_JUGADOR_EQUIPO:
                join=true;
            break;


            default:
                String mensajeError=String.valueOf(uri);
                throw new IllegalArgumentException(mensajeError);
        }


        Cursor cursor;

        try
        {
            if (!join)
            {
                cursor = sqlDB.query(tabla, projection, where, argumentos, null, null, sortOrder);
            }
            else
            {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(sqlJoin);

                cursor = builder.query(sqlDB, projection, where, argumentos,null, null, null, null);
            }
        }
        catch (IllegalArgumentException e)
        {
            String mensajeError=String.valueOf(uri);
            throw new SQLException(mensajeError);
        }

        return cursor;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) throws IllegalArgumentException {

        sqlDB = dbHelper.getWritableDatabase();

        int numeroRegistro=0;
        String tabla;
        String where=selection;
        String argumentos[] = selectionArgs;


        switch (uriMatcher.match(uri))
        {
            case ONE_REG_PLAYER:
                tabla=uri.getPathSegments().get(0);
                where = Contrato.ID_JUGADOR + "=?";
                argumentos=new String[] {uri.getLastPathSegment()};
            break;

            case ALL_REGS_PLAYER:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_CUERPO_TECNICO:
                tabla=uri.getPathSegments().get(0);
                where = Contrato.ID_CUERPO_TECNICO + "=?";
                argumentos=new String[] {uri.getLastPathSegment()};
            break;

            case ALL_REGS_CUERPO_TECNICO:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_EQUIPO:
                tabla=uri.getPathSegments().get(0);
                where = Contrato.ID_EQUIPO + "=?";
                argumentos=new String[] {uri.getLastPathSegment()};
            break;

            case ALL_REGS_EQUIPO:
                tabla=uri.getLastPathSegment();
            break;

            case ONE_REG_LOGIN:
                tabla=uri.getPathSegments().get(0);
                where = Contrato.NOMBRE_LOGIN + "=?";
                argumentos=new String[] {uri.getLastPathSegment()};
            break;

            case ALL_REGS_LOGIN:
                tabla=uri.getLastPathSegment();
            break;

            default:
                String mensajeError=String.valueOf(uri);
                throw new IllegalArgumentException(mensajeError);
        }


        numeroRegistro=sqlDB.update(tabla, values, where, argumentos);

        if (numeroRegistro > 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numeroRegistro;
    }


    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException
    {
        sqlDB = dbHelper.getWritableDatabase();

        int numeroOperaciones = operations.size();
        ContentProviderResult[] resultados = new ContentProviderResult[numeroOperaciones];

        try
        {
            sqlDB.beginTransaction();

            for (int i = 0; i < numeroOperaciones; i++)
            {
                resultados[i] = operations.get(i).apply(this, resultados, i);
            }

            sqlDB.setTransactionSuccessful();
        }
        catch (OperationApplicationException e)
        {
            throw new OperationApplicationException(e);
        }
        finally
        {
            sqlDB.endTransaction();
        }

        return resultados;
    }
}

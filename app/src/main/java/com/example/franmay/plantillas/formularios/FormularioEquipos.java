package com.example.franmay.plantillas.formularios;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.example.franmay.plantillas.R;
import com.example.franmay.plantillas.VentanaEmergente;
import com.example.franmay.plantillas.adaptadores.AdaptadorRecyclerViewEquipos;
import com.example.franmay.plantillas.pojos.Equipo;
import com.example.franmay.plantillas.pojos.Jugador;
import com.example.franmay.plantillas.proveedor_contenido.Contrato;
import java.util.ArrayList;

public class FormularioEquipos extends AppCompatActivity {

    int idJugador, idEquipo, idEquipoActual;
    String nombreEquipo;
    byte[] foto;

    String equipo;

    boolean insertar, administrador;

    Jugador jugadorActual = new Jugador();

    ArrayList<Equipo> lista;

    RecyclerView recyclerView;
    AdaptadorRecyclerViewEquipos adaptador;

    Context contexto;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_equipos);


        // habilitar botón de regreso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contexto=this;

        lista = new ArrayList<>();

        Bundle datos = getIntent().getExtras();

        insertar=datos.getBoolean("insertar");
        equipo=datos.getString("nombreEquipo");
        jugadorActual=datos.getParcelable("objeto");
        administrador=datos.getBoolean("administrador");
        idEquipo=datos.getInt("idEquipo");

        idJugador=jugadorActual.getId();
        foto=jugadorActual.getFoto();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerPaises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lista = leerEquipos();

        if (lista.size() > 0)
        {
            adaptador = new AdaptadorRecyclerViewEquipos(lista);
            recyclerView.setAdapter(adaptador);

            adaptador.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    idEquipoActual=lista.get(recyclerView.getChildAdapterPosition(v)).getId();
                    nombreEquipo=lista.get(recyclerView.getChildAdapterPosition(v)).getNombre();

                    ventanaConfirmar();
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent accionVolver = new Intent(this,FormularioEquiposJugador.class);
                accionVolver.putExtra("insertar", false);
                accionVolver.putExtra("nombreEquipo", equipo);
                accionVolver.putExtra("objeto", (Parcelable) jugadorActual);
                accionVolver.putExtra("administrador", administrador);
                accionVolver.putExtra("idEquipo", idEquipo);

                startActivity(accionVolver);
        }

        return true;
    }


    public ArrayList<Equipo> leerEquipos()
    {
        ArrayList<Equipo> lista = new ArrayList<>();

        Cursor cursor;

        String campos[] = { Contrato.ID_EQUIPO,
                            Contrato.NOMBRE_EQUIPO,
                            Contrato.FOTO_EQUIPO};

        String seleccion= null;
        String[] args = null;


        try
        {
            cursor=getApplicationContext().getContentResolver().query(Contrato.CONTENT_URI_EQUIPO, campos, seleccion, args, null);

            Equipo equipo;

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
            {
                equipo = new Equipo();

                equipo.setId(cursor.getInt(cursor.getColumnIndex(Contrato.ID_EQUIPO)));
                equipo.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.NOMBRE_EQUIPO)));
                equipo.setFoto(cursor.getBlob(cursor.getColumnIndex(Contrato.FOTO_EQUIPO)));

                lista.add(equipo);
            }
        }
        catch (IllegalArgumentException e)
        {
            VentanaEmergente vv = new VentanaEmergente("URI incorrecta.", e.getMessage(), this);
            vv.ventana();
        }
        catch (SQLException e)
        {
            String titulo="Consulta SQL incorreca en la tabla: " + Contrato.TABLA_LOGIN;

            VentanaEmergente vv = new VentanaEmergente(titulo, e.getMessage(), this);
            vv.ventana();
        }

        return lista;
    }


    public void ventanaConfirmar()
    {
        String mensaje="¿Quiere agregar este equipo?" + "\n--> " + nombreEquipo;

        AlertDialog.Builder ventana = new AlertDialog.Builder(this);

        ventana.setTitle("Mensaje.");
        ventana.setMessage(mensaje);

        ventana.setPositiveButton("Continuar", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
                grabarEquipo();
            }
        });


        ventana.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });


        AlertDialog alert = ventana.create();
        alert.show();
    }


    public void grabarEquipo()
    {
        ContentValues valores = new ContentValues();

        valores.put(Contrato.RELACION_NOMBRE_JUGADOR, idJugador);
        valores.put(Contrato.RELACION_NOMBRE_EQUIPO, idEquipoActual);


        try
        {
            Uri uri = getApplicationContext().getContentResolver().insert(Contrato.CONTENT_URI_JUGADOR_EQUIPO, valores);

            //int idDevuelto=Integer.parseInt(uri.getLastPathSegment());

            AlertDialog.Builder ventana = new AlertDialog.Builder(contexto);

            ventana.setTitle("Mensaje.");
            ventana.setMessage("Equipo grabado correctamente...");

            // solo mostramos el botón "Aceptar"
            ventana.setPositiveButton("Continuar", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();

                    Intent accion = new Intent(getApplicationContext(), FormularioEquiposJugador.class);
                    accion.putExtra("insertar", false);
                    accion.putExtra("nombreEquipo", equipo);
                    accion.putExtra("objeto", (Parcelable) jugadorActual);
                    accion.putExtra("administrador", administrador);
                    accion.putExtra("idEquipo", idEquipo);

                    startActivity(accion);
                }
            });

                AlertDialog alert = ventana.create();
                alert.show();
        }
        catch (SQLiteConstraintException e)
        {
            VentanaEmergente vv = new VentanaEmergente("Advertencia (existe el equipo).", e.getMessage(), this);
            vv.ventana();
        }
        catch (IllegalArgumentException e)
        {
            VentanaEmergente vv = new VentanaEmergente("Uri incorrecta.", e.getMessage(), this);
            vv.ventana();
        }
        catch (SQLException e)
        {
            VentanaEmergente vv = new VentanaEmergente("Consulta SQL incorrecta.", e.getMessage(), this);
            vv.ventana();
        }
    }
}

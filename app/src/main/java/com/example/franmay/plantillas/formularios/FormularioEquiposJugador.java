package com.example.franmay.plantillas.formularios;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import com.example.franmay.plantillas.Navigation_Drawer;
import com.example.franmay.plantillas.R;
import com.example.franmay.plantillas.VentanaEmergente;
import com.example.franmay.plantillas.adaptadores.AdaptadorEquipos;
import com.example.franmay.plantillas.pojos.Equipo;
import com.example.franmay.plantillas.pojos.Jugador;
import com.example.franmay.plantillas.proveedor_contenido.Contrato;
import java.util.ArrayList;


public class FormularioEquiposJugador extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ImageView imagen;

    int idJugador, idEquipo, idEquipoEliminar, indiceEquipo;
    String equipo;
    byte[] foto;

    boolean insertar, administrador;

    Jugador jugadorActual = new Jugador();

    ArrayList<Equipo> listaEquipos;

    AdaptadorEquipos adaptador;

    ListView listaImagenes;

    MenuItem menuInsertarEquipo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_equipos_jugador);

        // habilitar botón de regreso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagen = (ImageView) findViewById(R.id.imagenJugador);

        listaEquipos = new ArrayList<>();
        //nombreEquipos = new ArrayList<>();

        Bundle datos = getIntent().getExtras();

        insertar=datos.getBoolean("insertar");
        equipo=datos.getString("nombreEquipo");
        jugadorActual=datos.getParcelable("objeto");
        administrador=datos.getBoolean("administrador");
        idEquipo=datos.getInt("idEquipo");

        idJugador=jugadorActual.getId();
        foto=jugadorActual.getFoto();

        Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
        imagen.setImageBitmap(bitmap);

        leerEquipos();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_equipos, menu);

        menuInsertarEquipo = (MenuItem) menu.findItem(R.id.menuInsertarEquipo);

        return true;
    }


    // deshabilitar opciones para usuarios no administradores
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if (!administrador)
        {
            menuInsertarEquipo.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuEquipoHome:
                Intent accion = new Intent(this, Navigation_Drawer.class);

                accion.putExtra("administrador", administrador);
                startActivity(accion);
            break;

            case R.id.menuInsertarEquipo:
                Intent accionInsertar = new Intent(this, FormularioEquipos.class);

                accionInsertar.putExtra("insertar", false);
                accionInsertar.putExtra("nombreEquipo", equipo);
                accionInsertar.putExtra("objeto", (Parcelable) jugadorActual);
                accionInsertar.putExtra("administrador", administrador);
                accionInsertar.putExtra("idEquipo", idEquipo);

                startActivity(accionInsertar);
            break;

            case android.R.id.home:
                Intent accionVolver = new Intent(this,FormularioJugador.class);

                accionVolver.putExtra("insertar", false);
                accionVolver.putExtra("nombreEquipo", equipo);
                accionVolver.putExtra("objeto", (Parcelable) jugadorActual);
                accionVolver.putExtra("administrador", administrador);
                accionVolver.putExtra("idEquipo", idEquipo);

                startActivity(accionVolver);
        }

        return true;
    }


    public void leerEquipos()
    {
        String campos[] = { Contrato.TABLA_EQUIPO + "." + Contrato.ID_EQUIPO,
                            Contrato.TABLA_EQUIPO + "." + Contrato.NOMBRE_EQUIPO,
                            Contrato.FOTO_EQUIPO };

        String args[] = { String.valueOf(idJugador)};
        String seleccion = Contrato.TABLA_JUGADOR + "." + Contrato.ID_JUGADOR + "=?";


        try
        {
            Cursor cursor=getApplicationContext().getContentResolver().query(Contrato.CONTENT_URI_JUGADOR_EQUIPO, campos, seleccion, args, null);

            Equipo equipo;

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
            {
                equipo = new Equipo();

                equipo.setId(cursor.getInt(cursor.getColumnIndex(Contrato.ID_EQUIPO)));
                equipo.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.NOMBRE_EQUIPO)));
                equipo.setFoto(cursor.getBlob(cursor.getColumnIndex(Contrato.FOTO_EQUIPO)));

                listaEquipos.add(equipo);
            }

            listaImagenes = findViewById(R.id.listadoEquipos);
            adaptador = new AdaptadorEquipos(this, listaEquipos);
            listaImagenes.setAdapter(adaptador);
            listaImagenes.setOnItemClickListener(this);
        }
        catch (IllegalArgumentException e)
        {
            VentanaEmergente vv = new VentanaEmergente("URI incorrecta.", e.getMessage(), this);
            vv.ventana();
        }
        catch (SQLException e)
        {
            String titulo="Consulta SQL incorrecta en la tabla: " + Contrato.TABLA_JUGADOR_EQUIPO;

            VentanaEmergente vv = new VentanaEmergente(titulo, e.getMessage(), this);
            vv.ventana();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        indiceEquipo=position;
        idEquipoEliminar=listaEquipos.get(position).getId();
        String equipo=listaEquipos.get(position).getNombre();


        if (administrador)
        {
            String mensaje="¿Quiere eliminar este equipo?" + "\n--> " + equipo;

            AlertDialog.Builder ventana = new AlertDialog.Builder(this);

            ventana.setTitle("Advertencia.");
            ventana.setMessage(mensaje);

            ventana.setPositiveButton("Eliminar", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                    eliminarEquipo();
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
    }


    public void eliminarEquipo()
    {
        String where = String.format("%s=? AND %s=?",
                       Contrato.RELACION_NOMBRE_JUGADOR, Contrato.RELACION_NOMBRE_EQUIPO);

        String args []= new String[] {String.valueOf(idJugador), String.valueOf(idEquipoEliminar)};


        try
        {
            int numeroRegistros = getApplicationContext().getContentResolver().delete(Contrato.CONTENT_URI_JUGADOR_EQUIPO, where, args);

            if (numeroRegistros > 0)
            {
                listaEquipos.remove(indiceEquipo);
                adaptador.notifyDataSetChanged();

                VentanaEmergente alerta = new VentanaEmergente("Advertencia.",
                                                             "El equipo fue eliminado correctamente...",
                                                             this);
                alerta.ventana();
            }
            else
            {
                VentanaEmergente alerta = new VentanaEmergente("Advertencia.",
                                                             "El equipo no se pudo eliminar\n" +
                                                                      "o no se encuentra registrado...",
                                                             this);
                alerta.ventana();
            }
        }
        catch (IllegalArgumentException e)
        {
            VentanaEmergente vv = new VentanaEmergente("Uri incorrecta.\n", e.getMessage(), this);
            vv.ventana();
        }
        catch (SQLException e)
        {
            VentanaEmergente vv = new VentanaEmergente("Consulta SQL incorrecta.\n", e.getMessage(), this);
            vv.ventana();
        }
    }
}

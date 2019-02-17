package com.example.franmay.plantillas;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.franmay.plantillas.adaptadores.AdaptadorEquipos;
import com.example.franmay.plantillas.fragmentos.SeccionesPlantillas;
import com.example.franmay.plantillas.pojos.Equipo;
import com.example.franmay.plantillas.proveedor_contenido.Contrato;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import static com.example.franmay.plantillas.variables_globales.VariablesGlobales.AUTHORITY;

public class ActividadEquipos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Equipo> listaEquipos=new ArrayList<>();

    boolean administrador;

    Context contexto;

    private Bundle savedInstanceState;

    static final String TAG = Inicio.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_equipos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_escudos);
        setSupportActionBar(toolbar);

        // habilitar botón de regreso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // información recibida de la actividad anterior
        Bundle informacionRecibida = getIntent().getExtras();
        administrador=informacionRecibida.getBoolean("administrador");

        contexto=this;
        AdaptadorEquipos adaptador;


        listaEquipos=leerEquipos();

        ListView listaImagenes = (ListView) findViewById(R.id.listadoEscudos);

        adaptador = new AdaptadorEquipos(getApplicationContext(), listaEquipos);
        listaImagenes.setAdapter(adaptador);
        listaImagenes.setOnItemClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == android.R.id.home)
        {
            Intent accion = new Intent(this, Navigation_Drawer.class);
            accion.putExtra("administrador", administrador);
            startActivity(accion);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        int idEquipo=listaEquipos.get(position).getId();
        String nombreEquipo=listaEquipos.get(position).getNombre();


        Intent accion = new Intent(contexto, SeccionesPlantillas.class);
        accion.putExtra("administrador", administrador);
        accion.putExtra("nombreEquipo", nombreEquipo);
        accion.putExtra("idEquipo", idEquipo);
        startActivity(accion);
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
            String titulo="Consulta SQL incorreca en la tabla: " + Contrato.TABLA_EQUIPO;

            VentanaEmergente vv = new VentanaEmergente(titulo, e.getMessage(), this);
            vv.ventana();
        }


        if (lista.size()==0)
        {
            Log.i(TAG,"Cargando la tabla \"equipo\"...");

            lista=iniciarTablaEquipos();
        }


        return lista;
    }


    public ArrayList<Equipo> iniciarTablaEquipos()
    {
        ArrayList<Equipo> lista = new ArrayList<>();
        Equipo equipo;


        String equipos[] = { "Alavés", "At. Madrid", "Ath. Bilbao", "Barcelona", "Betis", "Celta", "Eibar", "Español",
                             "Getafe","Girona", "Huesca", "Leganes", "Levante", "Rayo Vallecano","Real Madrid",
                             "Real Sociedad","Sevilla", "Valencia", "Valladolid", "Villareal" };

        int imagenes[] = { R.drawable.alaves, R.drawable.at_madrid, R.drawable.bilbao, R.drawable.barcelona,
                           R.drawable.betis, R.drawable.celta, R.drawable.eibar, R.drawable.espanyol, R.drawable.getafe,
                           R.drawable.girona, R.drawable.huesca, R.drawable.leganes, R.drawable.levante,
                           R.drawable.rayo_vallecano,  R.drawable.real_madrid, R.drawable.real_sociedad, R.drawable.sevilla,
                           R.drawable.valencia,  R.drawable.valladolid,  R.drawable.villareal };


        ContentResolver r = contexto.getContentResolver();

        // Lista de operaciones
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        Drawable drawable;
        Bitmap bitmap;
        ByteArrayOutputStream stream;
        byte[] imagen;


        for (int i=0; i<equipos.length; i++)
        {
            drawable = contexto.getResources().getDrawable(imagenes[i]);
            bitmap = ((BitmapDrawable)drawable).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            imagen = stream.toByteArray();

            ops.add(ContentProviderOperation.newInsert(Contrato.CONTENT_URI_EQUIPO)
                    .withValue(Contrato.NOMBRE_EQUIPO, equipos[i])
                    .withValue(Contrato.EQUIPO_PAIS, "España")
                    .withValue(Contrato.FOTO_EQUIPO, imagen)
                    .build());

            equipo = new Equipo();

            equipo.setId(i+1);
            equipo.setNombre(equipos[i]);
            equipo.setPais("España");
            equipo.setFoto(imagen);

            lista.add(equipo);
        }


        try
        {
            r.applyBatch(AUTHORITY, ops);
        }
        catch (OperationApplicationException e)
        {
            VentanaEmergente vv = new VentanaEmergente("Error en la tabla Equipo.", e.getMessage(), contexto);
            vv.ventana();

            lista=null;
        }
        catch (RemoteException e)
        {
            VentanaEmergente vv = new VentanaEmergente("Error en la tabla Equipo.", e.getMessage(), contexto);
            vv.ventana();

            lista=null;
        }

        return lista;
    }
}

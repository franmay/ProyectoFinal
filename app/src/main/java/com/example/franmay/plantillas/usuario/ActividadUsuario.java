package com.example.franmay.plantillas.usuario;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.franmay.plantillas.Navigation_Drawer;
import com.example.franmay.plantillas.R;
import com.example.franmay.plantillas.VentanaEmergente;
import com.example.franmay.plantillas.adaptadores.AdaptadorUsuarios;
import com.example.franmay.plantillas.pojos.Login;
import com.example.franmay.plantillas.proveedor_contenido.Contrato;
import java.util.ArrayList;

public class ActividadUsuario extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Login> listaUsuarios = new ArrayList<>();

    ListView listaImagenes;
    AdaptadorUsuarios adaptador;

    int idUsuario;

    boolean administrador;

    Context contexto;

    static final String TAG = ActividadUsuario.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // habilitar botón de regreso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // información recibida de la actividad anterior
        Bundle informacionRecibida = getIntent().getExtras();
        administrador=informacionRecibida.getBoolean("administrador");

        contexto=this;


        listaImagenes = (ListView) findViewById(R.id.listadoUsuarios);
        adaptador = new AdaptadorUsuarios(contexto, leerUsuarios());
        listaImagenes.setAdapter(adaptador);
        listaImagenes.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_usuarios, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuInsertarUsuario:
                Intent accionInsertar = new Intent(this, RegistrarUsuario.class);

                accionInsertar.putExtra("administrador", administrador);
                startActivity(accionInsertar);
            break;

            case android.R.id.home:
                Intent accionVolver = new Intent(this, Navigation_Drawer.class);
                accionVolver.putExtra("administrador", administrador);
                startActivity(accionVolver);
        }

        return true;
    }


    public ArrayList<Login> leerUsuarios()
    {
        Cursor cursor;

        String campos[] = { Contrato.ID_LOGIN,
                            Contrato.NOMBRE_LOGIN,
                            Contrato.PASSWORD_LOGIN,
                            Contrato.ADMINISTRADOR};

        String seleccion= null;
        String[] args = null;
        /*String[] args = new String[] {equipo};
        String seleccion= Contrato.EQUIPO_CUERPO_TECNICO + "=?";*/


        try
        {
            cursor=getApplicationContext().getContentResolver().query(Contrato.CONTENT_URI_LOGIN, campos, seleccion, args, null);

            Login login;

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
            {
                login = new Login();

                login.setEstado(cursor.getInt(cursor.getColumnIndex(Contrato.ADMINISTRADOR)));

                if (login.getEstado() == 0)
                {
                    login.setId(cursor.getInt(cursor.getColumnIndex(Contrato.ID_LOGIN)));
                    login.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.NOMBRE_LOGIN)));
                    login.setClave(cursor.getString(cursor.getColumnIndex(Contrato.PASSWORD_LOGIN)));

                    listaUsuarios.add(login);
                }
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


        return listaUsuarios;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        final int posicion=position;
        idUsuario=listaUsuarios.get(position).getId();
        String usuarioEliminar=listaUsuarios.get(position).getNombre();

        String mensaje="¿Quiere eliminar este usuario?" + "\n--> " + usuarioEliminar;

        AlertDialog.Builder ventana = new AlertDialog.Builder(this);

        ventana.setTitle("Advertencia.");
        ventana.setMessage(mensaje);
        ventana.setPositiveButton("Eliminar", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
                eliminarUsuario(posicion);
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


    public void eliminarUsuario(int posicion)
    {
        Uri uri = Uri.parse(Contrato.CONTENT_URI_LOGIN + "/" + idUsuario);

        //String[] args = new String[] {usuario};
        //String seleccion= Contrato.NOMBRE_LOGIN + "=?";


        try
        {
            int numeroRegistros = getApplicationContext().getContentResolver().delete(uri, null, null);

            if (numeroRegistros > 0)
            {
                listaUsuarios.remove(posicion);
                adaptador.notifyDataSetChanged();

                VentanaEmergente alerta = new VentanaEmergente("Advertencia.",
                                                             "El usuario se eliminó correctamente...\n",
                                                             this);
                alerta.ventana();
            }
            else
            {
                VentanaEmergente alerta = new VentanaEmergente("Advertencia.",
                                                             "El equipo no se pudo eliminar\n",
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

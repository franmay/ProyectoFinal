package com.example.franmay.plantillas.usuario;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.franmay.plantillas.Navigation_Drawer;
import com.example.franmay.plantillas.R;
import com.example.franmay.plantillas.VentanaEmergente;
import com.example.franmay.plantillas.proveedor_contenido.Contrato;

public class RegistrarUsuario extends AppCompatActivity implements View.OnClickListener {

    TextView nombreUsuario, passwordUsuario;
    Button botonCrear, botonCancelar;
    ImageView imagen;

    boolean administrador;

    Context contexto;

    static final String TAG = RegistrarUsuario.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // habilitar botón de regreso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // información recibida de la actividad anterior
        Bundle informacionRecibida = getIntent().getExtras();
        administrador=informacionRecibida.getBoolean("administrador");

        contexto=this;


        nombreUsuario = (TextView) findViewById(R.id.editTextUser);
        passwordUsuario = (TextView) findViewById(R.id.editTextPassword);

        imagen = (ImageView) findViewById(R.id.iconoLogin);

        botonCrear = (Button) findViewById(R.id.buttonCreate);
        botonCancelar = (Button) findViewById(R.id.buttonCancel);

        botonCrear.setOnClickListener(this);
        botonCancelar.setOnClickListener(this);

        Bitmap icono = BitmapFactory.decodeResource(contexto.getResources(), R.drawable.fondo);
        imagen.setImageBitmap(icono);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_registrar_usuarios, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuRegistrarHome:
                Intent accion = new Intent(this, Navigation_Drawer.class);
                accion.putExtra("administrador", administrador);
                startActivity(accion);
            break;


            case android.R.id.home:
                Intent accionVolver = new Intent(this, ActividadUsuario.class);
                accionVolver.putExtra("administrador", administrador);
                startActivity(accionVolver);
        }

        return true;
    }


    public void validar()
    {
        String nombre, clave;
        String mensaje;

        nombre = nombreUsuario.getText().toString();
        clave = passwordUsuario.getText().toString();


        // validamos los datos de entrada
        if (!nombre.equals(""))
        {
            if (!clave.equals(""))
            {
                buscarUsuario(nombre, clave);
            }
            else
            {
                mensaje="Introduzca la contraseña del usuario...";

                VentanaEmergente mostrar = new VentanaEmergente("Advertencia", mensaje, this);
                mostrar.ventana();
            }
        }
        else
        {
            mensaje="Introduzca el nombre del usuario...";

            VentanaEmergente mostrar = new VentanaEmergente("Advertencia", mensaje, this);
            mostrar.ventana();
        }
    }

    public void limpiarCampos()
    {
        nombreUsuario.setText("");
        passwordUsuario.setText("");
    }


    public void buscarUsuario(String nombre, String clave)
    {
        String campos[] = { Contrato.NOMBRE_LOGIN,
                            Contrato.PASSWORD_LOGIN,
                            Contrato.ADMINISTRADOR};

        int estado=0;
        String mensaje;

        String[] args = new String[] {nombre};
        String seleccion= Contrato.NOMBRE_LOGIN + "=?";


        try
        {
            Cursor cursor=getApplicationContext().getContentResolver().query(Contrato.CONTENT_URI_LOGIN, campos, seleccion, args, null);

            // si nos devuelve información
            if (cursor.moveToFirst())
            {
                Log.i(TAG, " El usuario " + nombre + " ya existe...");

                mensaje="El usuario ya está registrado...";

                VentanaEmergente mostrar = new VentanaEmergente("Advertencia.", mensaje, this);
                mostrar.ventana();
            }
            else
            {
                grabarUsuario(nombre, clave, estado);
            }
        }
        catch (IllegalArgumentException e)
        {
            VentanaEmergente vv = new VentanaEmergente("URI incorrecta.", e.getMessage(), this);
            vv.ventana();
        }
        catch (SQLException e)
        {
            String titulo="Consulta SQL incorrecta en la tabla: " + Contrato.TABLA_LOGIN;

            VentanaEmergente vv = new VentanaEmergente(titulo, e.getMessage(), this);
            vv.ventana();
        }
    }


    public void grabarUsuario(String nombre, String clave, int estado)
    {
        ContentValues valores = new ContentValues();

        valores.put(Contrato.NOMBRE_LOGIN, nombre);
        valores.put(Contrato.PASSWORD_LOGIN, clave);
        valores.put(Contrato.ADMINISTRADOR, estado);


        try
        {
            Uri uri = getApplicationContext().getContentResolver().insert(Contrato.CONTENT_URI_LOGIN, valores);

            // ventana para continuar
            AlertDialog.Builder ventana = new AlertDialog.Builder(this);

            ventana.setTitle("Mensaje.");
            ventana.setMessage("Usuario registrado correctamente...");

            ventana.setPositiveButton("Continuar", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();

                    Intent accion = new Intent(contexto, ActividadUsuario.class);
                    accion.putExtra("administrador", administrador);
                    startActivity(accion);
                }
            });

            AlertDialog alert = ventana.create();
            alert.show();
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


    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        switch (id)
        {
            case R.id.buttonCreate: validar();
            break;

            case R.id.buttonCancel: limpiarCampos();
            break;
        }
    }
}

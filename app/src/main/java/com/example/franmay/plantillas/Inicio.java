package com.example.franmay.plantillas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.franmay.plantillas.pojos.Login;
import com.example.franmay.plantillas.proveedor_contenido.Contrato;

public class Inicio extends AppCompatActivity implements View.OnClickListener {

    TextView nombreUsuario, passwordUsuario;
    Button botonLogin, botonCancelar;
    ImageView imagen;

    Context context;

    static final String TAG = Inicio.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*Intent a = new Intent(this, ActividadEquipos.class);
        a.putExtra("administrador", true);
        startActivity(a);*/

        nombreUsuario = (TextView) findViewById(R.id.editTextUser);
        passwordUsuario = (TextView) findViewById(R.id.editTextPassword);

        imagen = (ImageView) findViewById(R.id.iconoLogin);

        botonLogin = (Button) findViewById(R.id.buttonLogin);
        botonCancelar = (Button) findViewById(R.id.buttonCancel);

        botonLogin.setOnClickListener(this);
        botonCancelar.setOnClickListener(this);

        context=this;

        Bitmap icono = BitmapFactory.decodeResource(context.getResources(), R.drawable.login);
        imagen.setImageBitmap(icono);
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

        String mensaje;

        boolean estado;

        String[] args = new String[] {nombre};
        String seleccion= Contrato.NOMBRE_LOGIN + "=?";


        try
        {
            Cursor cursor=getApplicationContext().getContentResolver().query(Contrato.CONTENT_URI_LOGIN, campos, seleccion, args, null);

            // si nos devuelve información
            if (cursor.moveToFirst())
            {
                Login login = new Login();

                login.setClave(cursor.getString(cursor.getColumnIndex(Contrato.PASSWORD_LOGIN)));
                login.setEstado(cursor.getInt(cursor.getColumnIndex(Contrato.ADMINISTRADOR)));

                if (login.getClave().equals(clave))
                {
                    Log.i(TAG, " El usuario " + nombre + " se ha logueado correctamente...");

                    if (login.getEstado()==1)
                        estado=true;
                    else
                        estado=false;

                    Intent accion = new Intent(this, Navigation_Drawer.class);
                    accion.putExtra("administrador", estado);
                    startActivity(accion);
                }
                else
                {
                    Log.i(TAG, "Contraseña incorrecta...");

                    mensaje="Contraseña incorrecta...";

                    VentanaEmergente mostrar = new VentanaEmergente("Advertencia.", mensaje, this);
                    mostrar.ventana();
                }
            }
            else
            {
                Log.i(TAG, "Usuario incorrecto...");

                mensaje="Usuario incorrecto...";

                VentanaEmergente mostrar = new VentanaEmergente("Advertencia.", mensaje, this);
                mostrar.ventana();
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


    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        switch (id)
        {
            case R.id.buttonLogin: validar();
                break;

            case R.id.buttonCancel: limpiarCampos();
                break;
        }
    }
}

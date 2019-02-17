package com.example.franmay.plantillas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


// esta clase contiene el método para mostrar un cuadro de diálogo
public class VentanaEmergente {

    String titulo;
    String mensaje;
    Context contexto;


    public VentanaEmergente()
    {

    }


    public VentanaEmergente(String titulo, String mensaje, Context contexto)
    {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.contexto = contexto;
    }


    public void ventana()
    {
        AlertDialog.Builder ventana = new AlertDialog.Builder(contexto);

        ventana.setTitle(titulo);
        ventana.setMessage(mensaje);


        // solo mostramos el botón "Aceptar"
        ventana.setPositiveButton("Continuar", new DialogInterface.OnClickListener()
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

package com.example.franmay.plantillas.permisos;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.franmay.plantillas.Navigation_Drawer;
import com.example.franmay.plantillas.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ObtenerPermisosCamara extends AppCompatActivity {

    boolean administrador;

    final int maximoFotos=15;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;

    Context contexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener_permisos_camara);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contexto = this;


        // información recibida de la actividad anterior
        Bundle informacionRecibida = getIntent().getExtras();
        administrador=informacionRecibida.getBoolean("administrador");


        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {

                }
                else
                {
                    ActivityCompat.requestPermissions(this,
                                                              new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                              MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
            }
            else
                procesando_imagenes();
        }
        else
            procesando_imagenes();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.e("Permiso", "Permiso concedido para grabar en la carpeta Pictures...");
                    procesando_imagenes();
                }
                else
                {
                    Log.e("Permiso", "Permiso denegado para grabar en la carpeta Pictures...");

                    Intent accion = new Intent (this, Navigation_Drawer.class);

                    accion.putExtra("administrador", administrador);
                    startActivity(accion);
                }

                return;
            }
        }
    }


    public void procesando_imagenes()
    {
        String mensaje;

        int imagenes[] = new int[3];

        imagenes = cargarImagenes();


        // Album cargado con anterioridad
        if (imagenes[1] == maximoFotos)
        {
            mensaje = "Las imágenes fueron cargadas previamente...";
        }
        else
        // Album nuevo
        if ((imagenes[0] > 0) && imagenes[1] == 0)
        {
            if (imagenes[0] == maximoFotos)
            {
                mensaje = "Se cargaron " + imagenes[0] + " de " + maximoFotos + " imágenes";
            }
            else
            {
                int diferencia = maximoFotos - imagenes[0];
                mensaje = "Se cargaron " + diferencia + " de " + maximoFotos + " imágenes";
            }
        }
        else
        // Añadir imágenes al álbum
        if ((imagenes[0] > 0) && imagenes[1] > 0)
        {
            mensaje = "Se añadieron al álbum " + imagenes[0] + " imágenes";
        }
        else
        {
            mensaje = "No se pudo cargar ninguna imagen...";
        }


        AlertDialog.Builder ventana = new AlertDialog.Builder(contexto);

        ventana.setTitle("Mensaje.");
        ventana.setMessage(mensaje);


        // solo mostramos el botón "Aceptar"
        ventana.setPositiveButton("Continuar", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();

                Intent accion = new Intent (contexto, Navigation_Drawer.class);

                accion.putExtra("administrador", administrador);
                startActivity(accion);
            }
        });

        AlertDialog alert = ventana.create();
        alert.show();
    }


    public int[] cargarImagenes()
    {
        int id, fotosAñadidas=0, fotosYaAñadidas=0, fotosNoAñadidas=0;
        int valores[] = new int[3];

        File fichero;
        Drawable drawable;
        Bitmap bitmap;
        String archivo, pathArchivo, imagenDrawable;


        int indice=1;

        while (indice<=maximoFotos)
        {
            archivo="imagen" + indice + ".png";
            pathArchivo=Environment.getExternalStorageDirectory().getAbsolutePath()
                       + File.separator + "Pictures" + File.separator + archivo;

            fichero = new File(pathArchivo);
            if (!fichero.exists())
            {
                imagenDrawable = "imagen" + indice;
                id = getResources().getIdentifier(imagenDrawable, "drawable", getPackageName());
                drawable = getResources().getDrawable(id);
                bitmap = ((BitmapDrawable)drawable).getBitmap();

                String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                            + File.separator + "Pictures" + File.separator + archivo;

                File file = new File(path);


                FileOutputStream fOut = null;

                try
                {
                    fOut = new FileOutputStream(file);
                    BitmapFactory.decodeFile(path);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, fOut);
                    fOut.flush();
                    fOut.close();
                    MakeSureFileWasCreatedThenMakeAvabile(file);

                    fotosAñadidas++;
                    Log.i("Creación de Imagen","Imagen creada");
                }
                catch (FileNotFoundException e)
                {
                    fotosNoAñadidas++;
                    Log.e("Creación de Imagen","Imagen no creada");
                }
                catch (IOException e)
                {
                    fotosNoAñadidas++;
                    Log.e("Creación de Imagen","Imagen no creada");
                }
            }
            else
            {
                fotosYaAñadidas++;
            }

            indice++;
        }

        valores[0]=fotosAñadidas;
        valores[1]=fotosYaAñadidas;
        valores[2]=fotosNoAñadidas;


        return valores;
    }


    private void MakeSureFileWasCreatedThenMakeAvabile(File file)
    {
        MediaScannerConnection.scanFile(this,
                                                new String[]{file.toString()}, null,
                                                new MediaScannerConnection.OnScanCompletedListener()
       {
           public void onScanCompleted(String path, Uri uri)
           {

           }
       });
    }
}

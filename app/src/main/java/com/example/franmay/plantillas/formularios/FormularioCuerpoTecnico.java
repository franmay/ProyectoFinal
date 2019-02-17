package com.example.franmay.plantillas.formularios;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.franmay.plantillas.Navigation_Drawer;
import com.example.franmay.plantillas.R;
import com.example.franmay.plantillas.VentanaEmergente;
import com.example.franmay.plantillas.fragmentos.SeccionesPlantillas;
import com.example.franmay.plantillas.pojos.CuerpoTecnico;
import com.example.franmay.plantillas.proveedor_contenido.Contrato;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FormularioCuerpoTecnico extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerPosicion;

    String equipo;

    TextView titulo;

    boolean administrador;

    EditText editarNombre, editarEdad, editarPais;

    ImageView imagen;

    CuerpoTecnico cuerpoTecnicoActual = new CuerpoTecnico();
    CuerpoTecnico cuerpoTecnico = new CuerpoTecnico();

    ArrayList<CuerpoTecnico> listaCuerpoTecnico;

    int idEquipo, lugarPosicion=0;

    boolean insertar;

    Context contexto;

    final int CODIGO_IMAGEN=1;
    byte[] fotoGuardada;

    boolean imagenCargada=false, imagenModificada=false;

    MenuItem grabarCuerpoTecnico;
    MenuItem borrarCuerpoTecnico;
    MenuItem resetearCuerpoTecnico;
    MenuItem tomarFotoCuerpoTecnico;
    MenuItem menuInsetarEquipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cuerpo_tecnico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        contexto=this;

        titulo = (TextView) findViewById(R.id.titulo);
        editarNombre = (EditText) findViewById(R.id.editTextName);
        editarEdad = (EditText) findViewById(R.id.editTextAge);
        editarPais = (EditText) findViewById(R.id.editTextCountry);

        imagen = (ImageView) findViewById(R.id.imgenCuerpoTecnico);

        spinnerPosicion = (Spinner) findViewById(R.id.spinnerCargo);

        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(
                this, R.array.cargo, R.layout.spinner_item);

        spinnerPosicion.setAdapter(adaptador);
        spinnerPosicion.setOnItemSelectedListener(this);


        // habilitar botón de regreso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listaCuerpoTecnico = new ArrayList<>();


        Bundle informacionRecibida = getIntent().getExtras();

        administrador=informacionRecibida.getBoolean("administrador");
        equipo=informacionRecibida.getString("nombreEquipo");
        insertar=informacionRecibida.getBoolean("insertar");
        idEquipo = informacionRecibida.getInt("idEquipo");

        titulo.setText(equipo);

        imagen.setImageResource(R.drawable.fondo);


        if (!administrador)
        {
            editarNombre.setEnabled(false);
            editarEdad.setEnabled(false);
            editarPais.setEnabled(false);

            spinnerPosicion.setEnabled(false);

            editarNombre.setTextSize(20);
            editarNombre.setTextColor(Color.BLUE);

            editarPais.setTextSize(20);
            editarPais.setTextColor(Color.BLUE);

            editarEdad.setTextSize(20);
            editarEdad.setTextColor(Color.BLUE);
        }


        if (!insertar)
        {
            cuerpoTecnicoActual=informacionRecibida.getParcelable("objeto");

            byte[] blob = cuerpoTecnicoActual.getFoto();
            Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
            imagen.setImageBitmap(bitmap);

            editarNombre.setText(cuerpoTecnicoActual.getNombre());
            editarEdad.setText(String.valueOf(cuerpoTecnicoActual.getEdad()));
            editarPais.setText(cuerpoTecnicoActual.getPais());

            spinnerPosicion.setSelection(cuerpoTecnicoActual.getIndiceCargo());

            cuerpoTecnico.setIdEquipo(cuerpoTecnicoActual.getIdEquipo());
            cuerpoTecnico.setNombre(cuerpoTecnicoActual.getNombre());
            cuerpoTecnico.setEdad(cuerpoTecnicoActual.getEdad());
            cuerpoTecnico.setPais(cuerpoTecnicoActual.getPais());
            cuerpoTecnico.setCargo(cuerpoTecnicoActual.getCargo());
            cuerpoTecnico.setIndiceCargo(cuerpoTecnicoActual.getIndiceCargo());
            cuerpoTecnico.setFoto(cuerpoTecnicoActual.getFoto());

            imagenCargada=true;
        }
        else
        {
            Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byte[] byteArray = stream.toByteArray();

            cuerpoTecnico.setFoto(byteArray);

            cuerpoTecnicoActual = new CuerpoTecnico(cuerpoTecnico.getId(),
                                                    cuerpoTecnico.getIdEquipo(),
                                                    cuerpoTecnico.getNombre(),
                                                    //cuerpoTecnico.getEquipo(),
                                                    cuerpoTecnico.getPais(),
                                                    cuerpoTecnico.getCargo(),
                                                    cuerpoTecnico.getIndiceCargo(),
                                                    cuerpoTecnico.getEdad(),
                                                    cuerpoTecnico.getFoto());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cuerpo_tecnico, menu);


        grabarCuerpoTecnico = (MenuItem) menu.findItem(R.id.grabarCuerpoTecnico);
        borrarCuerpoTecnico = (MenuItem) menu.findItem(R.id.borrarCuerpoTecnico);
        resetearCuerpoTecnico = (MenuItem) menu.findItem(R.id.resetearCuerpoTecnico);
        tomarFotoCuerpoTecnico = (MenuItem) menu.findItem(R.id.tomarFotoCuerpoTecnico);

        return true;
    }


    // deshabilitar opciones para usuarios no administradores
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if (!administrador)
        {
            grabarCuerpoTecnico.setVisible(false);
            borrarCuerpoTecnico.setVisible(false);
            resetearCuerpoTecnico.setVisible(false);
            tomarFotoCuerpoTecnico.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuCuerpoTecnicoHome:
                Intent accion = new Intent(this, Navigation_Drawer.class);

                accion.putExtra("administrador", administrador);
                startActivity(accion);
            break;

            case R.id.tomarFotoCuerpoTecnico:
                buscarFotoCuerpoTecnico();
            break;


            case R.id.grabarCuerpoTecnico:
                if (insertar)
                    validarCampos();
                else
                    modificarCuerpoTecnico();
                break;

            case R.id.resetearCuerpoTecnico:
                ActualizarFormulario();
            break;

            case R.id.borrarCuerpoTecnico:
                eliminarCuerpoTecnico();
            break;

            case android.R.id.home:
                Intent accion2 = new Intent(this, SeccionesPlantillas.class);

                accion2.putExtra("administrador", administrador);
                accion2.putExtra("nombreEquipo", equipo);
                accion2.putExtra("idEquipo", idEquipo);
                accion2.putExtra("idEquipo", idEquipo);
                startActivity(accion2);
        }

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK)
        {
            Uri path = data.getData();
            imagen.setImageURI(path);


            Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            fotoGuardada = stream.toByteArray();
            cuerpoTecnico.setFoto(fotoGuardada);

            if (!imagenCargada)
                imagenCargada=true;
            else
            if (!imagenModificada)
                imagenModificada=true;
        }
    }


    public void buscarFotoCuerpoTecnico()
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),CODIGO_IMAGEN);
    }


    public void validarCampos()
    {
        //int idEquipo;

        String nombre = editarNombre.getText().toString();
        String pais = editarPais.getText().toString();
        String edad = editarEdad.getText().toString();


        editarNombre.setError(null);
        editarEdad.setError(null);
        editarPais.setError(null);


        if (!imagenCargada)
        {
            VentanaEmergente cuadroDialogo = new VentanaEmergente("Advertencia",
                                                                "No se ha cargado la foto...",
                                                                         contexto);
            cuadroDialogo.ventana();

            return;
        }


        if (!validarString(nombre, editarNombre))
            return;

        if (!validarString(pais, editarPais))
            return;

        if (!validarInt(edad, editarEdad))
            return;


        if (lugarPosicion==0)
        {
            VentanaEmergente cuadroDialogo = new VentanaEmergente("Advertencia",
                                                                "No se ha seleccionado ningún cargo...",
                                                                         contexto);
            cuadroDialogo.ventana();

            return;
        }


        cuerpoTecnico.setNombre(editarNombre.getText().toString());
        cuerpoTecnico.setEdad(Integer.parseInt(editarEdad.getText().toString()));
        cuerpoTecnico.setPais(editarPais.getText().toString());


        ContentValues valores = new ContentValues();

        valores.put(Contrato.CLAVE_AJENA_EQUIPO, idEquipo);
        valores.put(Contrato.NOMBRE_CUERPO_TECNICO, cuerpoTecnico.getNombre());
        valores.put(Contrato.EDAD_CUERPO_TECNICO, cuerpoTecnico.getEdad());
        valores.put(Contrato.PAIS_CUERPO_TECNICO, cuerpoTecnico.getPais());
        valores.put(Contrato.CARGO_CUERPO_TECNICO, cuerpoTecnico.getCargo());
        valores.put(Contrato.INDICE_CARGO, cuerpoTecnico.getIndiceCargo());
        valores.put(Contrato.FOTO_CUERPO_TECNICO, cuerpoTecnico.getFoto());


        try
        {
            Uri uri = getApplicationContext().getContentResolver().insert(Contrato.CONTENT_URI_CUERPO_TECNICO, valores);

            int idDevuelto=Integer.parseInt(uri.getLastPathSegment());


            // ventana para continuar
            AlertDialog.Builder ventana = new AlertDialog.Builder(this);

            ventana.setTitle("Mensaje.");
            ventana.setMessage("Registro grabado correctamente...");

            ventana.setPositiveButton("Continuar", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();

                    Intent accion = new Intent(contexto, SeccionesPlantillas.class);

                    accion.putExtra("nombreEquipo", equipo);
                    accion.putExtra("administrador", administrador);
                    accion.putExtra("idEquipo", idEquipo);

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


    public boolean validarString(String cadena, EditText error)
    {
        if (!cadena.trim().isEmpty())
            return true;
        else
        {
            error.setError(getString(R.string.errorCampo));
            error.requestFocus();
            return false;
        }
    }


    public boolean validarInt(String cadena, EditText error)
    {
        int numero;

        try
        {
            numero = Integer.parseInt(cadena);

            if (numero<=0)
            {
                error.setError(getString(R.string.errorCampoNumerico));
                error.requestFocus();
                return false;
            }
        }
        catch (NumberFormatException e)
        {
            error.setError(getString(R.string.errorCampoNoNumerico));
            error.requestFocus();
            return false;
        }

        return true;
    }


    public void ActualizarFormulario()
    {
        editarNombre.setText(cuerpoTecnicoActual.getNombre());

        int edad = cuerpoTecnicoActual.getEdad();

        if (edad > 0)
            editarEdad.setText(String.valueOf(edad));
        else
            editarEdad.setText("");

        editarPais.setText(cuerpoTecnicoActual.getPais());

        spinnerPosicion.setSelection(cuerpoTecnicoActual.getIndiceCargo());

        byte[] blob = cuerpoTecnicoActual.getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        imagen.setImageBitmap(bitmap);


        imagenModificada=false;
    }


    public void modificarCuerpoTecnico()
    {
        boolean modificar=false;

        ContentValues valores = new ContentValues();

        int idActual=cuerpoTecnicoActual.getId();

        //String equipoActual=cuerpoTecnicoActual.getEquipo();
        int idEquipo=cuerpoTecnicoActual.getIdEquipo();


        String nombre;

        if (!editarNombre.getText().toString().equals(cuerpoTecnicoActual.getNombre()))
        {
            valores.put(Contrato.NOMBRE_CUERPO_TECNICO, editarNombre.getText().toString());
            nombre=editarNombre.getText().toString();
            modificar=true;
        }
        else
            nombre=cuerpoTecnicoActual.getNombre();


        int edad=Integer.parseInt(editarEdad.getText().toString());

        if (edad != cuerpoTecnicoActual.getEdad())
        {
            valores.put(Contrato.EDAD_CUERPO_TECNICO, edad);

            if (!modificar)
                modificar=true;
        }
        else
            edad=cuerpoTecnicoActual.getEdad();


        String pais;

        if (!editarPais.getText().toString().equals(cuerpoTecnicoActual.getPais()))
        {
            valores.put(Contrato.PAIS_CUERPO_TECNICO, editarPais.getText().toString());
            pais=editarPais.getText().toString();

            if (!modificar)
                modificar=true;
        }
        else
            pais=cuerpoTecnicoActual.getPais();


        String posicion;
        int posicionCuerpoTecnico;

        if (!cuerpoTecnico.getCargo().equals(cuerpoTecnicoActual.getCargo()))
        {
            valores.put(Contrato.CARGO_CUERPO_TECNICO, cuerpoTecnico.getCargo());
            valores.put(Contrato.INDICE_CARGO, cuerpoTecnico.getIndiceCargo());

            posicion=cuerpoTecnico.getCargo();
            posicionCuerpoTecnico=cuerpoTecnico.getIndiceCargo();

            if (!modificar)
                modificar=true;
        }
        else
        {
            posicion=cuerpoTecnicoActual.getCargo();
            posicionCuerpoTecnico=cuerpoTecnicoActual.getIndiceCargo();
        }


        byte[] imagenCuerpoTecnico;

        if (imagenModificada)
        {
            Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            cuerpoTecnico.setFoto(stream.toByteArray());

            valores.put(Contrato.FOTO_CUERPO_TECNICO, cuerpoTecnico.getFoto());
            imagenCuerpoTecnico=cuerpoTecnico.getFoto();

            if (!modificar)
                modificar=true;
        }
        else
            imagenCuerpoTecnico=cuerpoTecnicoActual.getFoto();


        if (modificar)
        {
            try
            {
                Uri uri = Uri.parse(Contrato.CONTENT_URI_CUERPO_TECNICO + "/" + cuerpoTecnicoActual.getId());

                int numeroRegistros=getApplicationContext().getContentResolver().update(uri, valores, null, null);

                if (numeroRegistros>0)
                {
                    imagenModificada=false;

                    cuerpoTecnicoActual = new CuerpoTecnico(idActual,
                                                            idEquipo,
                                                            nombre,
                                                            pais,
                                                            posicion,
                                                            posicionCuerpoTecnico,
                                                            edad,
                                                            imagenCuerpoTecnico);

                    VentanaEmergente alerta = new VentanaEmergente("Mensaje.",
                                                                 "El registro fue modificado...",
                                                                 this);
                    alerta.ventana();
                }
                else
                {
                    VentanaEmergente alerta = new VentanaEmergente("Advertencia.",
                                                                 "El registro no se pudo modificar\n" +
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
        else
        {
            VentanaEmergente alerta = new VentanaEmergente("Advertencia.",
                                                         "El registro no fue modificado...",
                                                         this);
            alerta.ventana();
        }
    }


    public void eliminarCuerpoTecnico()
    {
        Uri uri = Uri.parse(Contrato.CONTENT_URI_CUERPO_TECNICO + "/" + cuerpoTecnicoActual.getId());


        try
        {
            int numeroRegistros = getApplicationContext().getContentResolver().delete(uri, null, null);

            if (numeroRegistros > 0)
            {
                AlertDialog.Builder ventana = new AlertDialog.Builder(contexto);

                ventana.setTitle("Mensaje.");
                ventana.setMessage("El miembro del Cuerpo Técnico fue eliminado...");

                // solo mostramos el botón "Aceptar"
                ventana.setPositiveButton("Continuar", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();

                        Intent accion = new Intent(contexto, SeccionesPlantillas.class);

                        accion.putExtra("administrador", administrador);
                        accion.putExtra("nombreEquipo", equipo);
                        accion.putExtra("idEquipo", idEquipo);

                        startActivity(accion);
                    }
                });

                AlertDialog alert = ventana.create();
                alert.show();
            }
            else
            {
                String mensaje = "El miembro del Cuerpo Técnico\n";
                mensaje += "no se pudo eliminar o no se encuentra registrado...";

                VentanaEmergente alerta = new VentanaEmergente("Advertencia.",
                                                                      mensaje,
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (parent.getId()==spinnerPosicion.getId())
        {
            lugarPosicion=position;

            cuerpoTecnico.setCargo(String.valueOf(parent.getItemAtPosition(position)));
            cuerpoTecnico.setIndiceCargo(position);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}

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
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.franmay.plantillas.pojos.Jugador;
import com.example.franmay.plantillas.proveedor_contenido.Contrato;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class FormularioJugador extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = FormularioJugador.class.getSimpleName();

    Spinner spinnerPosicion;

    String equipo;

    TextView titulo;

    boolean administrador;

    EditText editarNombre, editarEdad, editarPais;

    ImageView imagen;

    Jugador jugadorActual = new Jugador();
    Jugador jugador = new Jugador();

    ArrayList<Jugador> listaJugadores;
    ArrayList<CuerpoTecnico> listaCuerpoTecnico;

    int idEquipo, lugarPosicion=0;


    int nacionalizado=0, comunitario=0, extraComunitario=0;
    int auxNacionalizado=0, auxComunitario=0, auxExtracomunitario=0;
    int modificarNacionalizado=0, modificarComunitario=0, modificarExtraComunitario=0;

    int cambiosRadioButton[] = {0, 0, 0};
    int modificarRadioButton[] = {0, 0 , 0};


    int liga=0, ligaExtranjera=0, copa=0, champions=0, mundial=0;
    int auxLiga=0, auxLigaExtranjera=0, auxCopa=0, auxChampions=0, auxMundial=0;
    int modificarLiga=0, modificarLigaExtranjera=0, modificarCopa=0, modificarChampions=0, modificarMundial=0;

    int cambiosCheckBox[] = {0, 0, 0 , 0, 0};
    int modificarCheckBox[] = {0, 0, 0 , 0, 0};


    boolean insertar;

    Context contexto;

    final int CODIGO_IMAGEN=1;
    byte[] fotoGuardada;

    boolean imagenCargada=false, imagenModificada=false;


    MenuItem grabarJugador;
    MenuItem borrarJugador;
    MenuItem resetearJugador;
    MenuItem tomarFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_jugador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        contexto=this;

        titulo = (TextView) findViewById(R.id.titulo);
        editarNombre = (EditText) findViewById(R.id.editTextName);
        editarEdad = (EditText) findViewById(R.id.editTextAge);
        editarPais = (EditText) findViewById(R.id.editTextCountry);

        imagen = (ImageView) findViewById(R.id.imagenJugador);

        spinnerPosicion = (Spinner) findViewById(R.id.spinnerPlace);

        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(
                this, R.array.opciones, R.layout.spinner_item);

        spinnerPosicion.setAdapter(adaptador);
        spinnerPosicion.setOnItemSelectedListener(this);


        // habilitar botón de regreso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagen.setImageResource(R.drawable.fondo);

        listaJugadores = new ArrayList<>();
        listaCuerpoTecnico = new ArrayList<>();


        // información recibida de la actividad anterior
        Bundle informacionRecibida = getIntent().getExtras();
        equipo=informacionRecibida.getString("nombreEquipo");
        administrador=informacionRecibida.getBoolean("administrador");
        insertar=informacionRecibida.getBoolean("insertar");
        idEquipo = informacionRecibida.getInt("idEquipo");

        titulo.setText(equipo);


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
            jugadorActual=informacionRecibida.getParcelable("objeto");

            byte[] blob = jugadorActual.getFoto();
            Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
            imagen.setImageBitmap(bitmap);

            editarNombre.setText(jugadorActual.getNombre());
            editarEdad.setText(String.valueOf(jugadorActual.getEdad()));
            editarPais.setText(jugadorActual.getPais());

            spinnerPosicion.setSelection(jugadorActual.getIndicePosicion());

            jugador.setId(jugadorActual.getId());
            jugador.setNombre(jugadorActual.getNombre());
            jugador.setEquipo(jugadorActual.getEquipo());
            jugador.setEdad(jugadorActual.getEdad());
            jugador.setPais(jugadorActual.getPais());
            jugador.setPosicion(jugadorActual.getPosicion());
            jugador.setIndicePosicion(jugadorActual.getIndicePosicion());
            jugador.setFoto(jugadorActual.getFoto());


            jugador.setNacionalizado(jugadorActual.getNacionalizado());
            jugador.setComunitario(jugadorActual.getComunitario());
            jugador.setExtracomunitario(jugadorActual.getExtracomunitario());

            jugador.setLiga(jugadorActual.getLiga());
            jugador.setLiga_extranjera(jugadorActual.getLiga_extranjera());
            jugador.setCopa_rey(jugadorActual.getCopa_rey());
            jugador.setChampions_league(jugadorActual.getChampions_league());
            jugador.setMundial(jugadorActual.getMundial());


            nacionalizado=jugadorActual.getNacionalizado();
            comunitario=jugadorActual.getComunitario();
            extraComunitario=jugadorActual.getExtracomunitario();

            cambiosRadioButton[0] = nacionalizado;
            cambiosRadioButton[1] = comunitario;
            cambiosRadioButton[2] = extraComunitario;

            modificarRadioButton[0] = nacionalizado;
            modificarRadioButton[1] = comunitario;
            modificarRadioButton[2] = extraComunitario;


            liga=jugadorActual.getLiga();
            ligaExtranjera=jugadorActual.getLiga_extranjera();
            copa=jugadorActual.getCopa_rey();
            champions=jugadorActual.getChampions_league();
            mundial=jugadorActual.getMundial();

            cambiosCheckBox[0]=liga;
            cambiosCheckBox[1]=ligaExtranjera;
            cambiosCheckBox[2]=copa;
            cambiosCheckBox[3]=champions;
            cambiosCheckBox[4]=mundial;

            modificarCheckBox[0]=liga;
            modificarCheckBox[1]=ligaExtranjera;
            modificarCheckBox[2]=copa;
            modificarCheckBox[3]=champions;
            modificarCheckBox[4]=mundial;


            imagenCargada=true;
        }
        else
        {
            jugador.setEquipo(equipo);

            Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byte[] byteArray = stream.toByteArray();

            jugador.setFoto(byteArray);

            jugadorActual = new Jugador(jugador.getId(),
                                        jugador.getNombre(),
                                        jugador.getEquipo(),
                                        jugador.getPais(),
                                        jugador.getPosicion(),
                                        jugador.getIndicePosicion(),
                                        jugador.getEdad(),
                                        jugador.getFoto(),

                                        jugador.getNacionalizado(),
                                        jugador.getComunitario(),
                                        jugador.getExtracomunitario(),

                                        jugador.getLiga(),
                                        jugador.getLiga_extranjera(),
                                        jugador.getCopa_rey(),
                                        jugador.getChampions_league(),
                                        jugador.getMundial());


            nacionalizado=jugador.getNacionalizado();
            comunitario=jugador.getComunitario();
            extraComunitario=jugador.getExtracomunitario();

            liga=jugadorActual.getLiga();
            ligaExtranjera=jugador.getLiga_extranjera();
            copa=jugador.getCopa_rey();
            champions=jugador.getChampions_league();
            mundial=jugador.getMundial();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_jugadores, menu);

        grabarJugador = (MenuItem) menu.findItem(R.id.grabarJugador);
        borrarJugador = (MenuItem) menu.findItem(R.id.borrarJugador);
        resetearJugador = (MenuItem) menu.findItem(R.id.resetearJugador);
        tomarFoto = (MenuItem) menu.findItem(R.id.tomarFotoJugador);

        return true;
    }


    // deshabilitar opciones para usuarios no administradores
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if (!administrador)
        {
            grabarJugador.setVisible(false);
            borrarJugador.setVisible(false);
            resetearJugador.setVisible(false);
            tomarFoto.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuJugadorHome:
                Intent accion = new Intent(this, Navigation_Drawer.class);
                accion.putExtra("administrador", administrador);
                accion.putExtra("idEquipo", idEquipo);

                startActivity(accion);
            break;

            case R.id.menuEquiposJugador:
                if (!insertar)
                {
                    Intent accionEquipos = new Intent(this, FormularioEquiposJugador.class);

                    accionEquipos.putExtra("nombreEquipo", equipo);
                    accionEquipos.putExtra("objeto", (Parcelable) jugadorActual);
                    accionEquipos.putExtra("administrador", administrador);
                    accionEquipos.putExtra("idEquipo", idEquipo);

                    startActivity(accionEquipos);
                }
                else
                {
                    String mensaje="Tienes que registrar el jugador para poder continuar...";

                    VentanaEmergente ventanaEmergente = new VentanaEmergente("Advertencia.", mensaje, this);
                    ventanaEmergente.ventana();
                }
            break;

            case R.id.tomarFotoJugador:
                buscarFotoJugador();
            break;

            case R.id.grabarJugador:
                if (insertar)
                    validarCampos();
                else
                   modificarJugador();
            break;

            case R.id.borrarJugador:
                eliminarJugador();
            break;

            case R.id.resetearJugador:
                ActualizarFormulario();
            break;

            case R.id.informacionNacionalidad:
                cuadroDialogoNacionalidad();
            break;

            case R.id.informacionCampeonatos:
                cuadroDialogoCampeonatos();
            break;

            case android.R.id.home:
                Intent accion2 = new Intent(this, SeccionesPlantillas.class);

                accion2.putExtra("administrador", administrador);
                accion2.putExtra("nombreEquipo", equipo);
                accion2.putExtra("idEquipo", idEquipo);


                Log.i("INFO", "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE " + idEquipo);
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
            jugador.setFoto(fotoGuardada);

            if (!imagenCargada)
                imagenCargada=true;
            else
            if (!imagenModificada)
                imagenModificada=true;
        }
    }


    public void buscarFotoJugador()
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"), CODIGO_IMAGEN);
    }


    public void validarCampos()
    {
        String nombre = editarNombre.getText().toString();
        String pais = editarPais.getText().toString();
        String edad = editarEdad.getText().toString();


        editarNombre.setError(null);
        editarEdad.setError(null);
        editarPais.setError(null);


        if (!imagenCargada)
        {
            VentanaEmergente cuadroDialogo = new VentanaEmergente("Advertencia",
                                                                "No se ha seleccionado ninguna imagen...",
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
                                                                "No se ha seleccionado ninguna posición...",
                                                                         contexto);
            cuadroDialogo.ventana();

            return;
        }


        jugador.setNombre(editarNombre.getText().toString());
        jugador.setEdad(Integer.parseInt(editarEdad.getText().toString()));
        jugador.setPais(editarPais.getText().toString());


        ContentValues valores = new ContentValues();

        valores.put(Contrato.NOMBRE_JUGADOR, jugador.getNombre());
        valores.put(Contrato.EQUIPO_JUGADOR, jugador.getEquipo());
        valores.put(Contrato.EDAD_JUGADOR, jugador.getEdad());
        valores.put(Contrato.PAIS_JUGADOR, jugador.getPais());
        valores.put(Contrato.POSICION_JUGADOR, jugador.getPosicion());
        valores.put(Contrato.INDICE_POSICION_JUGADOR, jugador.getIndicePosicion());
        valores.put(Contrato.FOTO_JUGADOR, jugador.getFoto());


        if (nacionalizado==1)
            valores.put(Contrato.JUGADOR_NACIONALIZADO, nacionalizado);
        else
           valores.put(Contrato.JUGADOR_NACIONALIZADO, jugador.getNacionalizado());

        if (comunitario==1)
            valores.put(Contrato.JUGADOR_COMUNITARIO, comunitario);
        else
           valores.put(Contrato.JUGADOR_COMUNITARIO, jugador.getComunitario());

        if (extraComunitario==1)
            valores.put(Contrato.JUGADOR_EXTRACOMUNITARIO, extraComunitario);
        else
           valores.put(Contrato.JUGADOR_EXTRACOMUNITARIO, jugador.getExtracomunitario());


        if (liga==1)
            valores.put(Contrato.LIGA_NACIONAL, liga);
        else
           valores.put(Contrato.LIGA_NACIONAL, jugador.getLiga());

        if (ligaExtranjera==1)
            valores.put(Contrato.LIGA_EXTRANJERA, ligaExtranjera);
        else
           valores.put(Contrato.LIGA_EXTRANJERA, jugador.getLiga_extranjera());

        if (copa==1)
            valores.put(Contrato.COPA_REY, copa);
        else
           valores.put(Contrato.COPA_REY, jugador.getCopa_rey());

        if (champions==1)
            valores.put(Contrato.CHAMPIONS_LEAGUE, champions);
        else
           valores.put(Contrato.CHAMPIONS_LEAGUE, jugador.getChampions_league());

        if (mundial==1)
            valores.put(Contrato.MUNDIAL, mundial);
        else
           valores.put(Contrato.MUNDIAL, jugador.getMundial());


        try
        {
            Uri uri = getApplicationContext().getContentResolver().insert(Contrato.CONTENT_URI_JUGADOR, valores);

            int idDevuelto=Integer.parseInt(uri.getLastPathSegment());


            // ventana para continuar
            AlertDialog.Builder ventana = new AlertDialog.Builder(this);

            ventana.setTitle("Mensaje.");
            ventana.setMessage("Jugador registrado correctamente...");

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
        editarNombre.setText(jugadorActual.getNombre());

        int edad = jugadorActual.getEdad();

        if (edad > 0)
            editarEdad.setText(String.valueOf(edad));
        else
            editarEdad.setText("");

        editarPais.setText(jugadorActual.getPais());

        spinnerPosicion.setSelection(jugadorActual.getIndicePosicion());

        byte[] blob = jugadorActual.getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        imagen.setImageBitmap(bitmap);


        imagenModificada=false;
    }


    public void modificarJugador()
    {
        boolean modificar=false;

        ContentValues valores = new ContentValues();

        int idActual=jugadorActual.getId();

        String equipoActual=jugadorActual.getEquipo();


        String nombre;

        if (!editarNombre.getText().toString().equals(jugadorActual.getNombre()))
        {
            valores.put(Contrato.NOMBRE_JUGADOR, editarNombre.getText().toString());
            nombre=editarNombre.getText().toString();
            modificar=true;
        }
        else
           nombre=jugadorActual.getNombre();


        int edad=Integer.parseInt(editarEdad.getText().toString());

        if (edad != jugadorActual.getEdad())
        {
            valores.put(Contrato.EDAD_JUGADOR, edad);

            if (!modificar)
                modificar=true;
        }
        else
           edad=jugadorActual.getEdad();


        String pais;

        if (!editarPais.getText().toString().equals(jugadorActual.getPais()))
        {
            valores.put(Contrato.PAIS_JUGADOR, editarPais.getText().toString());
            pais=editarPais.getText().toString();

            if (!modificar)
                modificar=true;
        }
        else
           pais=jugadorActual.getPais();


        String posicion;
        int posicionJugador;

        if (!jugador.getPosicion().equals(jugadorActual.getPosicion()))
        {
            valores.put(Contrato.POSICION_JUGADOR, jugador.getPosicion());
            valores.put(Contrato.INDICE_POSICION_JUGADOR, jugador.getIndicePosicion());

            posicion=jugador.getPosicion();
            posicionJugador=jugador.getIndicePosicion();

            if (!modificar)
                modificar=true;
        }
        else
        {
            posicion=jugadorActual.getPosicion();
            posicionJugador=jugadorActual.getIndicePosicion();
        }


        byte[] imagenJugador;

        if (imagenModificada)
        {
            Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            jugador.setFoto(stream.toByteArray());

            valores.put(Contrato.FOTO_JUGADOR, jugador.getFoto());
            imagenJugador=jugador.getFoto();

            if (!modificar)
                modificar=true;
        }
        else
           imagenJugador=jugadorActual.getFoto();


        int indice=0;

        while (indice<=2)
        {
            switch (indice)
            {
                case 0:
                    if (cambiosRadioButton[0]!=modificarRadioButton[0])
                    {
                        modificarNacionalizado=modificarRadioButton[0];

                        if (!modificar)
                            modificar=true;

                        valores.put(Contrato.JUGADOR_NACIONALIZADO, modificarRadioButton[0]);
                    }
                    else
                        modificarNacionalizado=cambiosRadioButton[0];
                break;

                case 1:
                    if (cambiosRadioButton[1]!=modificarRadioButton[1])
                    {
                        modificarComunitario=modificarRadioButton[1];

                        if (!modificar)
                            modificar=true;

                        valores.put(Contrato.JUGADOR_COMUNITARIO, modificarRadioButton[1]);
                    }
                    else
                        modificarComunitario=cambiosRadioButton[1];
                break;

                case 2:
                    if (cambiosRadioButton[2]!=modificarRadioButton[2])
                    {
                        modificarExtraComunitario=modificarRadioButton[2];

                        if (!modificar)
                            modificar=true;

                        valores.put(Contrato.JUGADOR_EXTRACOMUNITARIO, modificarRadioButton[2]);
                    }
                    else
                        modificarExtraComunitario=cambiosRadioButton[2];
                break;
            }

            indice++;
        }


        indice=0;

        while (indice<=4)
        {
            switch (indice)
            {
                case 0:
                    if (cambiosCheckBox[0]!=modificarCheckBox[0])
                    {
                        modificarLiga=modificarCheckBox[0];

                        if (!modificar)
                            modificar=true;

                        valores.put(Contrato.LIGA_NACIONAL, modificarCheckBox[0]);
                    }
                    else
                        modificarLiga=cambiosCheckBox[0];
                break;


                case 1:
                    if (cambiosCheckBox[1]!=modificarCheckBox[1])
                    {
                        modificarLigaExtranjera=modificarCheckBox[1];

                        if (!modificar)
                            modificar=true;

                        valores.put(Contrato.LIGA_EXTRANJERA, modificarCheckBox[1]);
                    }
                    else
                        modificarLigaExtranjera=cambiosCheckBox[1];
                break;


                case 2:
                    if (cambiosCheckBox[2]!=modificarCheckBox[2])
                    {
                        modificarCopa=modificarCheckBox[2];

                        if (!modificar)
                            modificar=true;

                        valores.put(Contrato.COPA_REY, modificarCheckBox[2]);
                    }
                    else
                        modificarCopa=cambiosCheckBox[2];
                break;


                case 3:
                    if (cambiosCheckBox[3]!=modificarCheckBox[3])
                    {
                        modificarChampions=modificarCheckBox[3];

                        if (!modificar)
                            modificar=true;

                        valores.put(Contrato.CHAMPIONS_LEAGUE, modificarCheckBox[3]);
                    }
                    else
                        modificarChampions=cambiosCheckBox[3];
                break;


                case 4:
                    if (cambiosCheckBox[4]!=modificarCheckBox[4])
                    {
                        modificarMundial=modificarCheckBox[4];

                        if (!modificar)
                            modificar=true;

                        valores.put(Contrato.MUNDIAL, modificarCheckBox[4]);
                    }
                    else
                        modificarMundial=cambiosCheckBox[4];
                break;
            }

            indice++;
        }


        if (modificar)
        {
            try
            {
                Uri uriJurador = Uri.parse(Contrato.CONTENT_URI_JUGADOR + "/" + jugadorActual.getId());


                int numeroRegistros=getApplicationContext().getContentResolver().update(uriJurador, valores, null, null);

                if (numeroRegistros>0)
                {
                    imagenModificada=false;

                    jugadorActual = new Jugador(idActual,
                                                nombre,
                                                equipoActual,
                                                pais,
                                                posicion,
                                                posicionJugador,
                                                edad,
                                                imagenJugador,
                                                modificarNacionalizado,
                                                modificarComunitario,
                                                modificarExtraComunitario,
                                                modificarLiga,
                                                modificarLigaExtranjera,
                                                modificarCopa,
                                                modificarChampions,
                                                modificarMundial);


                    if (cambiosRadioButton[0]!=modificarRadioButton[0])
                        cambiosRadioButton[0]=modificarRadioButton[0];

                    if (cambiosRadioButton[1]!=modificarRadioButton[1])
                        cambiosRadioButton[1]=modificarRadioButton[1];

                    if (cambiosRadioButton[2]!=modificarRadioButton[2])
                        cambiosRadioButton[2]=modificarRadioButton[2];


                    if (cambiosCheckBox[0]!=modificarCheckBox[0])
                        cambiosCheckBox[0]=modificarCheckBox[0];

                    if (cambiosCheckBox[1]!=modificarCheckBox[1])
                        cambiosCheckBox[1]=modificarCheckBox[1];

                    if (cambiosCheckBox[2]!=modificarCheckBox[2])
                        cambiosCheckBox[2]=modificarCheckBox[2];

                    if (cambiosCheckBox[3]!=modificarCheckBox[3])
                        cambiosCheckBox[3]=modificarCheckBox[3];

                    if (cambiosCheckBox[4]!=modificarCheckBox[4])
                        cambiosCheckBox[4]=modificarCheckBox[4];


                    VentanaEmergente alerta = new VentanaEmergente("Mensaje.",
                                                                 "El jugador fue modificado...",
                                                                 this);

                        alerta.ventana();
                }
                else
                {
                    VentanaEmergente alerta = new VentanaEmergente("Advertencia.",
                                                                 "El jugador no se pudo modificar\n" +
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
                                                         "El jugador no fue modificado...",
                                                         this);
            alerta.ventana();
        }
    }


    public void eliminarJugador()
    {
        Uri uri = Uri.parse(Contrato.CONTENT_URI_JUGADOR + "/" + jugadorActual.getId());


        try
        {
            int numeroRegistros = getApplicationContext().getContentResolver().delete(uri, null, null);

            if (numeroRegistros > 0)
            {
                AlertDialog.Builder ventana = new AlertDialog.Builder(contexto);

                ventana.setTitle("Mensaje: ");
                ventana.setMessage("Jugador eliminado correctamente...");

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
                VentanaEmergente alerta = new VentanaEmergente("Advertencia.",
                                                             "El jugador no se pudo eliminar\n" +
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


    public void cuadroDialogoNacionalidad()
    {
        int opcion;

        auxNacionalizado=0;
        auxComunitario=0;
        auxExtracomunitario=0;


        if (nacionalizado==1)
            opcion=0;
        else
        if (comunitario==1)
            opcion=1;
        else
        if (extraComunitario==1)
            opcion=2;
        else
            opcion=-1;

        mostrarVentanaNacionalidad(opcion);
    }


    public void mostrarVentanaNacionalidad(int valor)
    {
        if (administrador)
            manipularNacionalidad(valor);
        else
            verNacionalidad(valor);
    }


    public void manipularNacionalidad(int valor)
    {
        final CharSequence[] items = new CharSequence[3];

        items[0] = "Nacionalizado";
        items[1] = "Comunitario";
        items[2] = "Extracomunitario";


        AlertDialog.Builder ventana = new AlertDialog.Builder(this);

        ventana.setTitle("Nacionalidad.");

        ventana.setSingleChoiceItems(items, valor, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int opcion)
            {
                if (opcion==0)
                {
                    auxNacionalizado=1;
                    auxComunitario=0;
                    auxExtracomunitario=0;
                }
                else
                if (opcion==1)
                {
                    auxNacionalizado=0;
                    auxComunitario=1;
                    auxExtracomunitario=0;
                }
                else
                {
                    auxNacionalizado=0;
                    auxComunitario=0;
                    auxExtracomunitario=1;
                }
            }
        });


        ventana.setPositiveButton("Validar", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                grabarNacionalidad();
                dialog.cancel();
            }
        });

        ventana.setNegativeButton("Salir", new DialogInterface.OnClickListener()
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


    public void verNacionalidad(int valor)
    {
        String mensaje="";


        if (nacionalizado==1)
            mensaje="Nacionalizado";
        else
        if (comunitario==1)
            mensaje="Comunitario";
        else
        if (extraComunitario==1)
            mensaje="ExtraComunitario";
        else
           mensaje="Nacional";


        AlertDialog.Builder ventana = new AlertDialog.Builder(this);

        ventana.setTitle("Nacionalidad.");

        ventana.setMessage(mensaje);


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


    public void grabarNacionalidad()
    {
        if (auxNacionalizado==1)
        {
            nacionalizado=1;
            comunitario=0;
            extraComunitario=0;

            modificarRadioButton[0]=nacionalizado;
            modificarRadioButton[1]=0;
            modificarRadioButton[2]=0;

        }
        else
        if (auxComunitario==1)
        {
            nacionalizado=0;
            comunitario=1;
            extraComunitario=0;

            modificarRadioButton[0]=0;
            modificarRadioButton[1]=comunitario;
            modificarRadioButton[2]=0;
        }
        else
        if (auxExtracomunitario==1)
        {
            nacionalizado=0;
            comunitario=0;
            extraComunitario=1;

            modificarRadioButton[0]=0;
            modificarRadioButton[1]=0;
            modificarRadioButton[2]=extraComunitario;
        }
    }


    public void cuadroDialogoCampeonatos()
    {
        boolean estado[] = new boolean[5];
        Arrays.fill(estado, false);

        auxLiga=0;
        auxLigaExtranjera=0;
        auxCopa=0;
        auxChampions=0;
        auxMundial=0;


        if (liga==1)
        {
            estado[0]=true;
            auxLiga=1;
        }
        else
        {
            estado[0]=false;
            auxLiga=0;
        }


        if (ligaExtranjera==1)
        {
            estado[1]=true;
            auxLigaExtranjera=1;
        }
        else
        {
            estado[1]=false;
            auxLigaExtranjera=0;
        }


        if (copa==1)
        {
            estado[2]=true;
            auxCopa=1;
        }
        else
        {
            estado[2]=false;
            auxCopa=0;
        }


        if (champions==1)
        {
            estado[3]=true;
            auxChampions=1;
        }
        else
        {
            estado[3]=false;
            auxChampions=0;
        }

        if (mundial==1)
        {
            estado[4]=true;
            auxMundial=1;
        }
        else
        {
            estado[4]=false;
            auxMundial=0;
        }

        mostrarVentanaCampeonatos(estado);
    }


    public void mostrarVentanaCampeonatos(boolean estado[])
    {
        if (administrador)
            manipularCampeonatos(estado);
        else
           verCampeonatos(estado);
    }


    public void manipularCampeonatos(boolean estado[])
    {
        final CharSequence[] items = new CharSequence[5];

        items[0] = "Liga Española";
        items[1] = "Ligas Extranjeras";
        items[2] = "Copa del Rey";
        items[3] = "Champions League";
        items[4] = "Mundial";


        AlertDialog.Builder ventana = new AlertDialog.Builder(this);

        ventana.setTitle("Campeonatos ganados.");

        ventana.setMultiChoiceItems(items, estado, new DialogInterface.OnMultiChoiceClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int opcion, boolean isChecked)
            {
                if (isChecked)
                {
                    if (opcion==0)
                        auxLiga=1;
                    else
                    if (opcion==1)
                        auxLigaExtranjera=1;
                    else
                    if (opcion==2)
                        auxCopa=1;
                    else
                    if (opcion==3)
                        auxChampions=1;
                    else
                    if (opcion==4)
                        auxMundial=1;
                }
                else
                {
                    if (opcion==0)
                        auxLiga=0;
                    else
                    if (opcion==1)
                        auxLigaExtranjera=0;
                    else
                    if (opcion==2)
                        auxCopa=0;
                    else
                    if (opcion==3)
                        auxChampions=0;
                    else
                    if (opcion==4)
                        auxMundial=0;
                }
            }
        });


        ventana.setPositiveButton("Validar", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                grabarLigas();
                dialog.cancel();
            }
        });

        ventana.setNegativeButton("Salir", new DialogInterface.OnClickListener()
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


    public void verCampeonatos(boolean estado[])
    {
        int indice=0;

        String mensaje="";

        if (auxLiga==1)
        {
            mensaje+="Liga Española";
            indice++;
        }

        if (auxLigaExtranjera==1)
        {
            if (indice==0)
                mensaje+="Liga Extranjera";
            else
               mensaje+="\nLiga Extranjera";

            indice++;
        }

        if (auxCopa==1)
        {
            if (indice==0)
                mensaje+="Copa del Rey";
            else
               mensaje+="\nCopa del Rey";

            indice++;
        }

        if (auxChampions==1)
        {
            if (indice==0)
                mensaje+="Champions League";
            else
               mensaje+="\nChampions League";

            indice++;
        }

        if (auxMundial==1)
        {
            if (indice==0)
                mensaje="Mundial";
            else
               mensaje+="\nMundial";

            indice++;
        }


        AlertDialog.Builder ventana = new AlertDialog.Builder(this);

        ventana.setTitle("Campeonatos ganados.");

        if (indice>0)
            ventana.setMessage(mensaje);
        else
        {
            mensaje="No ha seleccionado ninguna opción...";
            ventana.setMessage(mensaje);
        }


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


    public void grabarLigas()
    {
        if ((liga==0)   &&   (auxLiga==1))
            liga=1;
        else
        if ((liga==1)   &&   (auxLiga==0))
            liga=0;


        if ((ligaExtranjera==0)   &&   (auxLigaExtranjera==1))
            ligaExtranjera=1;
        else
        if ((ligaExtranjera==1)   &&   (auxLigaExtranjera==0))
            ligaExtranjera=0;


        if ((copa==0)   &&   (auxCopa==1))
            copa=1;
        else
        if ((copa==1)   &&   (auxCopa==0))
            copa=0;


        if ((champions==0)   &&   (auxChampions==1))
            champions=1;
        else
        if ((champions==1)   &&   (auxChampions==0))
            champions=0;


        if ((mundial==0)   &&   (auxMundial==1))
            mundial=1;
        else
        if ((mundial==1)   &&   (auxMundial==0))
            mundial=0;


        modificarCheckBox[0]=liga;
        modificarCheckBox[1]=ligaExtranjera;
        modificarCheckBox[2]=copa;
        modificarCheckBox[3]=champions;
        modificarCheckBox[4]=mundial;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if (parent.getId()==spinnerPosicion.getId())
        {
            lugarPosicion=position;

            jugador.setPosicion(String.valueOf(parent.getItemAtPosition(position)));
            jugador.setIndicePosicion(position);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}

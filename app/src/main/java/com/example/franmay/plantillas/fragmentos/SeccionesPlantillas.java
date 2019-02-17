package com.example.franmay.plantillas.fragmentos;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.franmay.plantillas.ActividadEquipos;
import com.example.franmay.plantillas.Navigation_Drawer;
import com.example.franmay.plantillas.R;
import com.example.franmay.plantillas.VentanaEmergente;
import com.example.franmay.plantillas.adaptadores.AdaptadorCuerpoTecnico;
import com.example.franmay.plantillas.adaptadores.AdaptadorJugadores;
import com.example.franmay.plantillas.formularios.FormularioCuerpoTecnico;
import com.example.franmay.plantillas.formularios.FormularioJugador;
import com.example.franmay.plantillas.pojos.CuerpoTecnico;
import com.example.franmay.plantillas.pojos.Equipo;
import com.example.franmay.plantillas.pojos.Jugador;
import com.example.franmay.plantillas.proveedor_contenido.Contrato;

import java.util.ArrayList;

public class SeccionesPlantillas extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private Bundle savedInstanceState;

    Context contexto;

    static String equipo;
    static int idEquipo, numberTab=0;
    static boolean administrador;

    static ArrayList<Jugador> listaJugadores;
    static ArrayList<CuerpoTecnico> listaCuerpoTecnico;

    MenuItem menuInsertarJugador;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secciones_plantillas);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsSeccionesPlantillas);
        tabLayout.setupWithViewPager(mViewPager);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                numberTab = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });


        // habilitar botón de regreso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle datos = getIntent().getExtras();

        equipo = datos.getString("nombreEquipo");
        administrador = datos.getBoolean("administrador");
        idEquipo = datos.getInt("idEquipo");

        this.setTitle(equipo);

        listaJugadores = new ArrayList<>();
        listaCuerpoTecnico = new ArrayList<>();

        contexto = this;

        numberTab=0;

        leerJugadores(equipo);
        leerCuerpoTecnico(equipo);
    }


    public void leerJugadores(String equipo)
    {
        Cursor cursor;

        String campos[] = { Contrato.ID_JUGADOR,
                            Contrato.NOMBRE_JUGADOR,
                            Contrato.EQUIPO_JUGADOR,
                            Contrato.EDAD_JUGADOR,
                            Contrato.PAIS_JUGADOR,
                            Contrato.POSICION_JUGADOR,
                            Contrato.INDICE_POSICION_JUGADOR,
                            Contrato.FOTO_JUGADOR,

                            Contrato.JUGADOR_NACIONALIZADO,
                            Contrato.JUGADOR_COMUNITARIO,
                            Contrato.JUGADOR_EXTRACOMUNITARIO,

                            Contrato.LIGA_NACIONAL,
                            Contrato.LIGA_EXTRANJERA,
                            Contrato.COPA_REY,
                            Contrato.CHAMPIONS_LEAGUE,
                            Contrato.MUNDIAL};


        String[] args = new String[] {equipo};
        String seleccion= Contrato.EQUIPO_JUGADOR + "=?";


        try
        {
            cursor=getApplicationContext().getContentResolver().query(Contrato.CONTENT_URI_JUGADOR, campos, seleccion, args, null);

            Jugador jugador;

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
            {
                jugador = new Jugador();

                jugador.setId(cursor.getInt(cursor.getColumnIndex(Contrato.ID_JUGADOR)));
                jugador.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.NOMBRE_JUGADOR)));
                jugador.setEquipo(cursor.getString(cursor.getColumnIndex(Contrato.EQUIPO_JUGADOR)));
                jugador.setEdad(cursor.getInt(cursor.getColumnIndex(Contrato.EDAD_JUGADOR)));
                jugador.setPais(cursor.getString(cursor.getColumnIndex(Contrato.PAIS_JUGADOR)));
                jugador.setPosicion(cursor.getString(cursor.getColumnIndex(Contrato.POSICION_JUGADOR)));
                jugador.setIndicePosicion(cursor.getInt(cursor.getColumnIndex(Contrato.INDICE_POSICION_JUGADOR)));
                jugador.setFoto(cursor.getBlob(cursor.getColumnIndex(Contrato.FOTO_JUGADOR)));

                jugador.setNacionalizado(cursor.getInt(cursor.getColumnIndex(Contrato.JUGADOR_NACIONALIZADO)));
                jugador.setComunitario(cursor.getInt(cursor.getColumnIndex(Contrato.JUGADOR_COMUNITARIO)));
                jugador.setExtracomunitario(cursor.getInt(cursor.getColumnIndex(Contrato.JUGADOR_EXTRACOMUNITARIO)));

                jugador.setLiga(cursor.getInt(cursor.getColumnIndex(Contrato.LIGA_NACIONAL)));
                jugador.setLiga_extranjera(cursor.getInt(cursor.getColumnIndex(Contrato.LIGA_EXTRANJERA)));
                jugador.setCopa_rey(cursor.getInt(cursor.getColumnIndex(Contrato.COPA_REY)));
                jugador.setChampions_league(cursor.getInt(cursor.getColumnIndex(Contrato.CHAMPIONS_LEAGUE)));
                jugador.setMundial(cursor.getInt(cursor.getColumnIndex(Contrato.MUNDIAL)));

                listaJugadores.add(jugador);
            }
        }
        catch (IllegalArgumentException e)
        {
            VentanaEmergente vv = new VentanaEmergente("URI incorrecta.", e.getMessage(), this);
            vv.ventana();
        }
        catch (SQLException e)
        {
            String titulo="Consulta SQL incorreca en la tabla: " + Contrato.TABLA_JUGADOR;

            VentanaEmergente vv = new VentanaEmergente(titulo, e.getMessage(), this);
            vv.ventana();
        }
    }


    public void leerCuerpoTecnico(String equipo)
    {
        Cursor cursor;

        String campos[] = { Contrato.ID_CUERPO_TECNICO,
                            Contrato.CLAVE_AJENA_EQUIPO,
                            Contrato.NOMBRE_CUERPO_TECNICO,
                            Contrato.EDAD_CUERPO_TECNICO,
                            Contrato.PAIS_CUERPO_TECNICO,
                            Contrato.CARGO_CUERPO_TECNICO,
                            Contrato.INDICE_CARGO,
                            Contrato.FOTO_CUERPO_TECNICO};


        String args[] = new String[] {String.valueOf(idEquipo)};
        String seleccion = Contrato.CLAVE_AJENA_EQUIPO + "=?";


        try
        {
            cursor=getApplicationContext().getContentResolver().query(Contrato.CONTENT_URI_CUERPO_TECNICO, campos, seleccion, args, null);

            CuerpoTecnico cuerpo;

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
            {
                cuerpo = new CuerpoTecnico();

                cuerpo.setId(cursor.getInt(cursor.getColumnIndex(Contrato.ID_CUERPO_TECNICO)));
                cuerpo.setIdEquipo(cursor.getInt(cursor.getColumnIndex(Contrato.CLAVE_AJENA_EQUIPO)));
                cuerpo.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.NOMBRE_CUERPO_TECNICO)));
                cuerpo.setEdad(cursor.getInt(cursor.getColumnIndex(Contrato.EDAD_CUERPO_TECNICO)));
                cuerpo.setPais(cursor.getString(cursor.getColumnIndex(Contrato.PAIS_CUERPO_TECNICO)));
                cuerpo.setCargo(cursor.getString(cursor.getColumnIndex(Contrato.CARGO_CUERPO_TECNICO)));
                cuerpo.setIndiceCargo(cursor.getInt(cursor.getColumnIndex(Contrato.INDICE_CARGO)));
                cuerpo.setFoto(cursor.getBlob(cursor.getColumnIndex(Contrato.FOTO_CUERPO_TECNICO)));

                listaCuerpoTecnico.add(cuerpo);
            }
        }
        catch (IllegalArgumentException e)
        {
            VentanaEmergente vv = new VentanaEmergente("URI incorrecta.", e.getMessage(), this);
            vv.ventana();
        }
        catch (SQLException e)
        {
            String titulo="Consulta SQL incorreca en la tabla: " + Contrato.TABLA_CUERPO_TECNICO;

            VentanaEmergente vv = new VentanaEmergente(titulo, e.getMessage(), this);
            vv.ventana();
        }
    }


    public int leerEquipo()
    {
        int id=0;


        Cursor cursor;

        String campos[] = { Contrato.ID_EQUIPO };

        String args[] = new String[] {equipo};
        String where= Contrato.NOMBRE_EQUIPO + "=?";

        try
        {
            cursor=getApplicationContext().getContentResolver().query(Contrato.CONTENT_URI_EQUIPO, campos, where, args, null);

            if (cursor.moveToFirst())
            {
                id=cursor.getInt(cursor.getColumnIndex(Contrato.ID_EQUIPO));
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


        return id;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_secciones_plantillas, menu);

        menuInsertarJugador = (MenuItem) menu.findItem(R.id.insertarPlantilla);

        return true;
    }


    // deshabilitar opciones para usuarios no administradores
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if (!administrador)
            menuInsertarJugador.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.insertarPlantilla)
        {
            if (numberTab==0)
            {
                Intent accion = new Intent(this, FormularioCuerpoTecnico.class);
                accion.putExtra("insertar", true);
                accion.putExtra("nombreEquipo",equipo);
                accion.putExtra("administrador", administrador);
                accion.putExtra("idEquipo", idEquipo);

                startActivity(accion);
            }
            else
            if (numberTab==1)
            {
                Intent accion = new Intent(this, FormularioJugador.class);
                accion.putExtra("insertar", true);
                accion.putExtra("nombreEquipo",equipo);
                accion.putExtra("administrador", administrador);
                accion.putExtra("idEquipo", idEquipo);

                startActivity(accion);
            }
        }
        else
        if (id == R.id.home)
        {
            Intent accion = new Intent(this, Navigation_Drawer.class);
            accion.putExtra("administrador", administrador);
            startActivity(accion);
        }
        else
        if (id == android.R.id.home)
        {
            Intent accion2 = new Intent(this, ActividadEquipos.class);
            accion2.putExtra("administrador", administrador);
            startActivity(accion2);
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber)
        {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View vista = inflater.inflate(R.layout.fragment_secciones_plantillas, container, false);

            int fragmento=getArguments().getInt(ARG_SECTION_NUMBER);


            switch (fragmento)
            {
                case 1:
                    ListView listaImagenes;
                    listaImagenes = (ListView) vista.findViewById(R.id.listaFinal);

                    AdaptadorCuerpoTecnico adaptador1;
                    adaptador1 = new AdaptadorCuerpoTecnico(getActivity(), listaCuerpoTecnico);
                    listaImagenes.setAdapter(adaptador1);
                    listaImagenes.setOnItemClickListener(this);
                break;


                case 2:
                    ListView listaImagenes2;
                    listaImagenes2 = (ListView) vista.findViewById(R.id.listaFinal);

                    AdaptadorJugadores adaptador2;
                    adaptador2 = new AdaptadorJugadores(getActivity(), listaJugadores);
                    listaImagenes2.setAdapter(adaptador2);
                    listaImagenes2.setOnItemClickListener(this);
            }

            return vista;
        }


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            int fragmentoActual=getArguments().getInt(ARG_SECTION_NUMBER);


            if (fragmentoActual==1)
            {
                Intent accion = new Intent(getActivity(), FormularioCuerpoTecnico.class);

                CuerpoTecnico cuerpoTecnicoActual = new CuerpoTecnico();

                cuerpoTecnicoActual.setId(listaCuerpoTecnico.get(position).getId());
                cuerpoTecnicoActual.setIdEquipo(listaCuerpoTecnico.get(position).getIdEquipo());
                cuerpoTecnicoActual.setNombre(listaCuerpoTecnico.get(position).getNombre());
                cuerpoTecnicoActual.setEdad(listaCuerpoTecnico.get(position).getEdad());
                cuerpoTecnicoActual.setPais(listaCuerpoTecnico.get(position).getPais());
                cuerpoTecnicoActual.setCargo(listaCuerpoTecnico.get(position).getCargo());
                cuerpoTecnicoActual.setIndiceCargo(listaCuerpoTecnico.get(position).getIndiceCargo());
                cuerpoTecnicoActual.setFoto(listaCuerpoTecnico.get(position).getFoto());

                accion.putExtra("insertar", false);
                accion.putExtra("nombreEquipo", equipo);
                accion.putExtra("objeto", (Parcelable) cuerpoTecnicoActual);
                accion.putExtra("administrador", administrador);
                accion.putExtra("idEquipo", idEquipo);

                startActivity(accion);
            }
            else
            {
                Intent accion = new Intent(getActivity(), FormularioJugador.class);

                Jugador jugadorActual = new Jugador();

                jugadorActual.setId(listaJugadores.get(position).getId());
                jugadorActual.setNombre(listaJugadores.get(position).getNombre());
                jugadorActual.setEquipo(listaJugadores.get(position).getEquipo());
                jugadorActual.setEdad(listaJugadores.get(position).getEdad());
                jugadorActual.setPais(listaJugadores.get(position).getPais());
                jugadorActual.setPosicion(listaJugadores.get(position).getPosicion());
                jugadorActual.setIndicePosicion(listaJugadores.get(position).getIndicePosicion());
                jugadorActual.setFoto(listaJugadores.get(position).getFoto());

                jugadorActual.setNacionalizado(listaJugadores.get(position).getNacionalizado());
                jugadorActual.setComunitario(listaJugadores.get(position).getComunitario());
                jugadorActual.setExtracomunitario(listaJugadores.get(position).getExtracomunitario());

                jugadorActual.setLiga(listaJugadores.get(position).getLiga());
                jugadorActual.setLiga_extranjera(listaJugadores.get(position).getLiga_extranjera());
                jugadorActual.setCopa_rey(listaJugadores.get(position).getCopa_rey());
                jugadorActual.setChampions_league(listaJugadores.get(position).getChampions_league());
                jugadorActual.setMundial(listaJugadores.get(position).getMundial());

                accion.putExtra("insertar", false);
                accion.putExtra("nombreEquipo", equipo);
                accion.putExtra("objeto", (Parcelable) jugadorActual);
                accion.putExtra("administrador", administrador);
                accion.putExtra("idEquipo", idEquipo);

                startActivity(accion);
            }
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position + 1);
        }


        @Override
        public int getCount()
        {
            // Show 3 total pages.
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0: return "Cuerpo Técnico";

                case 1: return "Plantillas";
            }

            return null;
        }
    }
}

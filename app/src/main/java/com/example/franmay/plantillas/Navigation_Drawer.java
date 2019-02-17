package com.example.franmay.plantillas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.franmay.plantillas.permisos.ObtenerPermisosCamara;
import com.example.franmay.plantillas.usuario.ActividadUsuario;

public class Navigation_Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = Navigation_Drawer.class.getSimpleName();

    boolean administrador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // información recibida de la actividad anterior
        Bundle informacionRecibida = getIntent().getExtras();
        administrador=informacionRecibida.getBoolean("administrador");

        // si se logueó un usuario distinto al administrador deshabilitamos algunas opciones
        if (!administrador)
        {
            Menu menu = navigationView.getMenu();

            MenuItem menuItem1 = menu.findItem(R.id.nav_imagenes);
            menuItem1.setVisible(false);

            MenuItem menuItem2 = menu.findItem(R.id.nav_registrar_usuario);
            menuItem2.setVisible(false);
        }
    }


    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_plantillas)
        {
            Intent accion = new Intent(getApplicationContext(), ActividadEquipos.class);
            accion.putExtra("administrador", administrador);
            startActivity(accion);
        }
        else
        if (id == R.id.nav_imagenes)
        {
            Intent accion = new Intent(getApplicationContext(), ObtenerPermisosCamara.class);
            accion.putExtra("administrador", administrador);
            startActivity(accion);
        }
        else
        if (id == R.id.nav_registrar_usuario)
        {
            Intent accion = new Intent(getApplicationContext(), ActividadUsuario.class);
            accion.putExtra("administrador", administrador);
            startActivity(accion);
        }
        else
        if (id == R.id.nav_cerrar_sesion)
        {
            Intent accion = new Intent(getApplicationContext(), Inicio.class);
            startActivity(accion);
        }
        else
        if (id == R.id.nav_salir)
        {
            Intent accion = new Intent(Intent.ACTION_MAIN);
            accion.addCategory(Intent.CATEGORY_HOME);
            accion.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(accion);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}

package com.example.franmay.plantillas.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franmay.plantillas.R;
import com.example.franmay.plantillas.pojos.Jugador;

import java.util.ArrayList;

public class AdaptadorJugadores extends BaseAdapter {

    private Context contexto;
    private ArrayList<Jugador> lista;


    public AdaptadorJugadores(Context contexto, ArrayList<Jugador> lista)
    {
        this.contexto = contexto;
        this.lista = lista;
    }


    @Override
    public int getCount()
    {
        return lista.size();
    }


    @Override
    public Object getItem(int position)
    {
        return lista.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vista = convertView;
        Jugador itemLista = (Jugador) getItem(position);

        vista = LayoutInflater.from(contexto).inflate(R.layout.item_jugadores, null);

        ImageView imagenFoto = (ImageView) vista.findViewById(R.id.icono);

        byte[] blob = itemLista.getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        imagenFoto.setImageBitmap(bitmap);

        TextView nombreJugador = (TextView) vista.findViewById(R.id.nombreJugador);
        nombreJugador.setText(itemLista.getNombre());

        TextView posicionJugador = (TextView) vista.findViewById(R.id.posicionJugador);
        posicionJugador.setText(itemLista.getPosicion());

        return vista;
    }
}

package com.example.franmay.plantillas.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.franmay.plantillas.R;
import com.example.franmay.plantillas.pojos.Login;

import java.util.ArrayList;

public class AdaptadorUsuarios extends BaseAdapter {

    private Context contexto;
    private ArrayList<Login> lista;


    public AdaptadorUsuarios(Context contexto, ArrayList<Login> lista)
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
        Login itemLista = (Login) getItem(position);

        vista = LayoutInflater.from(contexto).inflate(R.layout.item_usuarios, null);

        ImageView imagenFoto = (ImageView) vista.findViewById(R.id.icono);

        /*byte[] blob = itemLista.getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        imagenFoto.setImageBitmap(bitmap);*/
        imagenFoto.setImageResource(R.drawable.fondo);

        TextView nombreJugador = (TextView) vista.findViewById(R.id.usuarioRegistrado);
        nombreJugador.setText(itemLista.getNombre());

        return vista;
    }
}

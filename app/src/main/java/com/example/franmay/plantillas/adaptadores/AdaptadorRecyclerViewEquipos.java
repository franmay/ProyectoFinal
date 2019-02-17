package com.example.franmay.plantillas.adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.franmay.plantillas.R;
import com.example.franmay.plantillas.pojos.Equipo;

import java.util.ArrayList;

public class AdaptadorRecyclerViewEquipos extends RecyclerView.Adapter<AdaptadorRecyclerViewEquipos.ViewHolderDatos>
             implements View.OnClickListener {

    ArrayList<Equipo> lista;
    private View.OnClickListener listener;


    public AdaptadorRecyclerViewEquipos(ArrayList<Equipo> lista)
    {
        this.lista = lista;
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder
    {
        TextView equipo;
        ImageView foto;


        public ViewHolderDatos(@NonNull View itemView)
        {
            super(itemView);

            equipo=(TextView) itemView.findViewById(R.id.nombreEquipo);
            foto=(ImageView) itemView.findViewById(R.id.iconoEquipo);
        }
    }


    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_equipos, viewGroup, false);

        view.setOnClickListener(this);

        return new ViewHolderDatos(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i)
    {
        viewHolderDatos.equipo.setText(lista.get(i).getNombre());

        byte imagen[] = lista.get(i).getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagen,0, imagen.length);
        viewHolderDatos.foto.setImageBitmap(bitmap);
    }


    @Override
    public int getItemCount()
    {
        return lista.size();
    }


    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener=listener;
    }


    @Override
    public void onClick(View v)
    {
        if (listener!=null)
            listener.onClick(v);
    }
}

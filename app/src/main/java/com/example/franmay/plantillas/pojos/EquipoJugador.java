package com.example.franmay.plantillas.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class EquipoJugador implements Parcelable {

    int id;
    int id_jugador;
    String nombre;
    String pais;
    byte[] foto;


    public EquipoJugador()
    {
        id = 0;
        id_jugador = 0;
        nombre = "";
        pais = "";
        foto = null;
    }


    public EquipoJugador(int id, int id_jugador, String nombre, String pais, byte[] foto)
    {
        this.id = id;
        this.id_jugador = id_jugador;
        this.nombre = nombre;
        this.pais = pais;
        this.foto = foto;
    }


    public int getId()
    {
        return id;
    }

    public int getId_jugador()
    {
        return id_jugador;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getPais()
    {
        return pais;
    }

    public byte[] getFoto()
    {
        return foto;
    }


    public void setId(int id)
    {
        this.id = id;
    }

    public void setId_jugador(int id_jugador)
    {
        this.id_jugador = id_jugador;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setPais(String pais)
    {
        this.pais = pais;
    }

    public void setFoto(byte[] foto)
    {
        this.foto = foto;
    }


    protected EquipoJugador(Parcel in)
    {
        id = in.readInt();
        id_jugador = in.readInt();
        nombre = in.readString();
        pais = in.readString();
        foto = in.createByteArray();
    }


    public static final Creator<EquipoJugador> CREATOR = new Creator<EquipoJugador>()
    {
        @Override
        public EquipoJugador createFromParcel(Parcel in)
        {
            return new EquipoJugador(in);
        }


        @Override
        public EquipoJugador[] newArray(int size)
        {
            return new EquipoJugador[size];
        }
    };


    @Override
    public int describeContents()
    {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeInt(id_jugador);
        dest.writeString(nombre);
        dest.writeString(pais);
        dest.writeByteArray(foto);
    }
}

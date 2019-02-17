package com.example.franmay.plantillas.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class Equipo implements Parcelable {

    int id;
    String nombre;
    String pais;
    byte foto[];


    public Equipo()
    {
        id = 0;
        nombre = "";
        pais = "";
        foto = null;
    }


    public Equipo(int id, String nombre, String pais, byte[] foto)
    {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.foto = foto;
    }


    protected Equipo(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        pais = in.readString();
        foto = in.createByteArray();
    }

    public static final Creator<Equipo> CREATOR = new Creator<Equipo>() {
        @Override
        public Equipo createFromParcel(Parcel in) {
            return new Equipo(in);
        }

        @Override
        public Equipo[] newArray(int size) {
            return new Equipo[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public byte[] getFoto() {
        return foto;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(pais);
        dest.writeByteArray(foto);
    }
}

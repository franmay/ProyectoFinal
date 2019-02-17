package com.example.franmay.plantillas.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class CuerpoTecnico implements Parcelable {

    int id;
    int idEquipo;
    String nombre;
    //String equipo;
    String pais;
    String cargo;
    int indiceCargo;
    int edad;
    byte[] foto;


    public CuerpoTecnico()
    {
        id=0;
        idEquipo=0;
        nombre = "";
        //equipo = "";
        pais = "";
        cargo = "";
        indiceCargo=0;
        edad = 0;
        foto = null;
    }


    public CuerpoTecnico(int id, int idEquipo, String nombre, String pais, String cargo, int indiceCargo, int edad, byte[] foto)
    {
        this.id=id;
        this.idEquipo=idEquipo;
        this.nombre = nombre;
        //this.equipo = equipo;
        this.pais = pais;
        this.cargo = cargo;
        this.indiceCargo = indiceCargo;
        this.edad = edad;
        this.foto = foto;
    }


    protected CuerpoTecnico(Parcel in)
    {
        id = in.readInt();
        idEquipo =in.readInt();
        nombre = in.readString();
        //equipo = in.readString();
        pais = in.readString();
        cargo = in.readString();
        indiceCargo = in.readInt();
        edad = in.readInt();
        foto = in.createByteArray();
    }


    public static final Creator<CuerpoTecnico> CREATOR = new Creator<CuerpoTecnico>()
    {
        @Override
        public CuerpoTecnico createFromParcel(Parcel in)
        {
            return new CuerpoTecnico(in);
        }

        @Override
        public CuerpoTecnico[] newArray(int size)
        {
            return new CuerpoTecnico[size];
        }
    };


    public int getId() {
        return id;
    }

    public int getIdEquipo()
    {
        return idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    /*public String getEquipo() {
        return equipo;
    }*/

    public String getPais() {
        return pais;
    }

    public String getCargo() {
        return cargo;
    }

    public int getIndiceCargo() {
        return indiceCargo;
    }

    public int getEdad() {
        return edad;
    }

    public byte[] getFoto() {
        return foto;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setIdEquipo(int idEquipo)
    {
        this.idEquipo = idEquipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /*public void setEquipo(String equipo) {
        this.equipo = equipo;
    }*/

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setIndiceCargo(int indiceCargo) {
        this.indiceCargo = indiceCargo;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeInt(idEquipo);
        dest.writeString(nombre);
        //dest.writeString(equipo);
        dest.writeString(pais);
        dest.writeString(cargo);
        dest.writeInt(indiceCargo);
        dest.writeInt(edad);
        dest.writeByteArray(foto);
    }
}

package com.example.franmay.plantillas.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class Jugador implements Parcelable {

    int id;
    String nombre;
    String equipo;
    String pais;
    String posicion;
    int indicePosicion;
    int edad;
    byte[] foto;

    int nacionalizado;
    int comunitario;
    int extracomunitario;

    int liga;
    int liga_extranjera;
    int copa_rey;
    int champions_league;
    int mundial;



    public Jugador()
    {
        id=0;
        nombre = "";
        equipo = "";
        pais="";
        posicion = "";
        indicePosicion = 0;
        edad = 0;
        foto = null;

        nacionalizado = 0;
        comunitario = 0;
        extracomunitario = 0;

        liga = 0;
        liga_extranjera = 0;
        copa_rey = 0;
        champions_league = 0;
        mundial = 0;
    }


    public Jugador(int id, String nombre, String equipo, String pais, String posicion, int indicePosicion, int edad, byte[] foto, int nacionalizado, int comunitario, int extracomunitario, int liga, int liga_extranjera, int copa_rey, int champions_league, int mundial)
    {
        this.id = id;
        this.nombre = nombre;
        this.equipo = equipo;
        this.pais = pais;
        this.posicion = posicion;
        this.indicePosicion = indicePosicion;
        this.edad = edad;
        this.foto = foto;
        this.nacionalizado = nacionalizado;
        this.comunitario = comunitario;
        this.extracomunitario = extracomunitario;
        this.liga = liga;
        this.liga_extranjera = liga_extranjera;
        this.copa_rey = copa_rey;
        this.champions_league = champions_league;
        this.mundial = mundial;
    }


    protected Jugador(Parcel in)
    {
        id = in.readInt();
        nombre = in.readString();
        equipo = in.readString();
        pais = in.readString();
        posicion = in.readString();
        indicePosicion = in.readInt();
        edad = in.readInt();
        foto = in.createByteArray();
        nacionalizado = in.readInt();
        comunitario = in.readInt();
        extracomunitario = in.readInt();
        liga = in.readInt();
        liga_extranjera = in.readInt();
        copa_rey = in.readInt();
        champions_league = in.readInt();
        mundial = in.readInt();
    }


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEquipo() {
        return equipo;
    }

    public String getPais() {
        return pais;
    }

    public String getPosicion() {
        return posicion;
    }

    public int getIndicePosicion() {
        return indicePosicion;
    }

    public int getEdad() {
        return edad;
    }

    public byte[] getFoto() {
        return foto;
    }

    public int getNacionalizado() {
        return nacionalizado;
    }

    public int getComunitario() {
        return comunitario;
    }

    public int getExtracomunitario() {
        return extracomunitario;
    }

    public int getLiga() {
        return liga;
    }

    public int getLiga_extranjera() {
        return liga_extranjera;
    }

    public int getCopa_rey() {
        return copa_rey;
    }

    public int getChampions_league() {
        return champions_league;
    }

    public int getMundial() {
        return mundial;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public void setIndicePosicion(int indicePosicion) {
        this.indicePosicion = indicePosicion;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public void setNacionalizado(int nacionalizado) {
        this.nacionalizado = nacionalizado;
    }

    public void setComunitario(int comunitario) {
        this.comunitario = comunitario;
    }

    public void setExtracomunitario(int extracomunitario) {
        this.extracomunitario = extracomunitario;
    }

    public void setLiga(int liga) {
        this.liga = liga;
    }

    public void setLiga_extranjera(int liga_extranjera) {
        this.liga_extranjera = liga_extranjera;
    }

    public void setCopa_rey(int copa_rey) {
        this.copa_rey = copa_rey;
    }

    public void setChampions_league(int champions_league) {
        this.champions_league = champions_league;
    }

    public void setMundial(int mundial) {
        this.mundial = mundial;
    }


    public static final Creator<Jugador> CREATOR = new Creator<Jugador>()
    {
        @Override
        public Jugador createFromParcel(Parcel in)
        {
            return new Jugador(in);
        }

        @Override
        public Jugador[] newArray(int size)
        {
            return new Jugador[size];
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
        dest.writeString(nombre);
        dest.writeString(equipo);
        dest.writeString(pais);
        dest.writeString(posicion);
        dest.writeInt(indicePosicion);
        dest.writeInt(edad);
        dest.writeByteArray(foto);
        dest.writeInt(nacionalizado);
        dest.writeInt(comunitario);
        dest.writeInt(extracomunitario);
        dest.writeInt(liga);
        dest.writeInt(liga_extranjera);
        dest.writeInt(copa_rey);
        dest.writeInt(champions_league);
        dest.writeInt(mundial);
    }
}

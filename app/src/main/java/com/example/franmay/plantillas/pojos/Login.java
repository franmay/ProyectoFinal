package com.example.franmay.plantillas.pojos;

public class Login {

    int id;
    String nombre;
    String clave;
    int estado;


    public Login()
    {
        id=0;
        nombre="";
        clave="";
        estado=0;
    }


    public Login(int id, String nombre, String clave, int estado)
    {
        this.id=id;
        this.nombre = nombre;
        this.clave = clave;
        this.estado = estado;
    }


    public int getId()
    {
        return id;
    }

    public String getNombre()
    {
        return nombre;
    }


    public String getClave()
    {
        return clave;
    }


    public int getEstado()
    {
        return estado;
    }


    public void setId(int id)
    {
        this.id=id;
    }


    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }


    public void setClave(String clave)
    {
        this.clave = clave;
    }


    public void setEstado(int estado)
    {
        this.estado = estado;
    }
}

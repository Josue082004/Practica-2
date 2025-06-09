package com.unl.music.base.models;

import java.util.Date;
import java.util.HashMap;

public class Album {
    private Integer id;
    private String nombre;
    private Date fecha;
    private Integer id_banda;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getId_banda() {
        return this.id_banda;
    }

    public void setId_banda(Integer id_banda) {
        this.id_banda = id_banda;
    }

    public Album copy (Album obj){
        Album aux = new Album();
        aux.setId(obj.getId());
        aux.setNombre(obj.getNombre());
        aux.setFecha(obj.getFecha());
        aux.setId_banda(obj.getId_banda());
        return aux;
    }

    public HashMap<String, String> toMap() {
        HashMap<String, String> diccionario = new HashMap<>();
        diccionario.put("id", this.id.toString());
        diccionario.put("nombre", this.nombre);
        diccionario.put("fecha", this.fecha.toString());
        diccionario.put("id_banda", this.id_banda.toString());
        return diccionario;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", id_banda=" + id_banda +
                '}';
    }





}

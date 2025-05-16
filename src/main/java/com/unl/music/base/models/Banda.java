package com.unl.music.base.models;

import java.util.Date;
import java.util.HashMap;

public class Banda {
    private Integer id;
    private String nombre;
    private Date fecha;

    public Banda() {
    }
    public Banda(Integer id, String nombre, Date fecha) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
    }

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

    public Banda copy (Banda obj){
        Banda aux = new Banda();
        aux.setId(obj.getId());
        aux.setNombre(obj.getNombre());
        aux.setFecha(obj.getFecha());
        return aux;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> diccionario = new HashMap<>();
        diccionario.put("id", this.getId());
        diccionario.put("nombre", this.getNombre());
        diccionario.put("fecha", this.getFecha());
        return diccionario;
    }

    
}

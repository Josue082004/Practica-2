package com.unl.music.base.models;

import java.util.HashMap;

public class Cancion {
    private Integer id;
    private String nombre;
    private Integer id_genero;
    private Integer duracion;
    private String url;
    private TipoArchivoEnum tipo;
    private Integer id_album;

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

    public Integer getId_genero() {
        return this.id_genero;
    }

    public void setId_genero(Integer id_genero) {
        this.id_genero = id_genero;
    }

    public Integer getDuracion() {
        return this.duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TipoArchivoEnum getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoArchivoEnum tipo) {
        this.tipo = tipo;
    }

    public Integer getId_album() {
        return this.id_album;
    }

    public void setId_album(Integer id_album) {
        this.id_album = id_album;
    }

    public Cancion copy(Cancion obj) {
        Cancion aux = new Cancion();
        aux.setId(obj.getId());
        aux.setNombre(obj.getNombre());
        aux.setId_genero(obj.getId_genero());
        aux.setDuracion(obj.getDuracion());
        aux.setUrl(obj.getUrl());
        aux.setTipo(obj.getTipo());
        aux.setId_album(obj.getId_album());
        return aux;
    }

    public HashMap<String, String> toMap() {
        HashMap<String, String> diccionario = new HashMap<>();
        diccionario.put("id", this.id.toString());
        diccionario.put("nombre", this.nombre);
        diccionario.put("id_genero", this.id_genero.toString());
        diccionario.put("duracion", this.duracion.toString());
        diccionario.put("url", this.url);
        diccionario.put("tipo", this.tipo.toString());
        diccionario.put("id_album", this.id_album.toString());
        return diccionario;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", id_genero=" + id_genero +
                ", duracion=" + duracion +
                ", url='" + url + '\'' +
                ", tipo=" + tipo +
                ", id_album=" + id_album +
                '}';
    }


}

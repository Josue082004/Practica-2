package com.unl.music.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.unl.music.base.models.Album;
import com.unl.music.base.models.Banda;
import com.unl.music.base.models.Cancion;
import com.unl.music.base.models.TipoArchivoEnum;
import com.unl.music.base.controller.dao.dao_models.DaoAlbum;
import com.unl.music.base.controller.dao.dao_models.DaoBanda;
import com.unl.music.base.controller.dao.dao_models.DaoCancion;
import com.unl.music.base.controller.dao.dao_models.DaoGenero;
import com.unl.music.base.controller.data_struct.list.LinkedList;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.mappedtypes.Pageable;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

@BrowserCallable
@AnonymousAllowed
public class CancionService {
    private DaoCancion db;

    public CancionService() {
        db = new DaoCancion();
    }

    public void createCancion(@NotEmpty String nombre, Integer id_genero, @PositiveOrZero @NonNull Integer duracion,
            @NonNull String url, @NonNull TipoArchivoEnum tipo, Integer id_album) throws Exception {
        db.getObj().setNombre(nombre);
        db.getObj().setId_genero(id_genero);
        db.getObj().setDuracion(duracion);
        db.getObj().setUrl(url);
        db.getObj().setTipo(tipo);
        db.getObj().setId_album(id_album);
        if (!db.save())
            throw new Exception("No se pudo guardar los datos de la Cancion");

    }

    public void updateCancion(Integer id, @NotEmpty String nombre, Integer id_genero, @NonNull Integer duracion,
            @NonNull String url, @NonNull TipoArchivoEnum tipo, Integer id_album) throws Exception {
        Cancion aux = new Cancion();
        aux.setId(id);
        aux.setNombre(nombre);
        aux.setId_genero(id_genero);
        aux.setDuracion(duracion);
        aux.setUrl(url);
        aux.setTipo(tipo);
        aux.setId_album(id_album);
        db.update_by_id(aux, id);
    }

    public void deleteCancion(Integer id) throws Exception {
        if (id != null && id > 0) {
            Cancion aux = new Cancion();
            aux.setId(id);
            db.delete_by_id(id);

        }
    }

    public List<Cancion> list(Pageable pageable) {
        return Arrays.asList(db.listAll().toArray());
    }

    public List<Cancion> listAll() {
        return (List<Cancion>) Arrays.asList(db.listAll().toArray());
    }

    public List<String> listGenero() {
        List<String> lista = new ArrayList<>();
        List<Cancion> canciones = (List<Cancion>) db.listAll();
        for (Cancion cancion : canciones) {
            lista.add(new DaoGenero().listAll().get(cancion.getId_genero() - 1).getNombre());
        }
        return lista;
    }

    public List<String> listAlbum() {
        List<String> lista = new ArrayList<>();
        List<Cancion> canciones = (List<Cancion>) db.listAll();
        for (Cancion cancion : canciones) {
            lista.add(new DaoAlbum().listAll().get(cancion.getId_album() - 1).getNombre());
        }
        return lista;
    }

    // public List<Cancion> order(String atributo, Integer type) {
    // System.out.println("Ordenando por " + atributo + " tipo: " + type);
    // if (atributo.equalsIgnoreCase("nombre"))
    // return (List<Cancion>) Arrays.asList(db.orderQ(type).toArray());
    // else if (atributo.equalsIgnoreCase("duracion")) {
    // return (List<Cancion>) Arrays.asList(db.orderDuracion(type).toArray());
    // } else if (atributo.equalsIgnoreCase("id_genero")) {
    // return (List<Cancion>) Arrays.asList(db.orderGenero(type).toArray());
    // } else if (atributo.equalsIgnoreCase("id_album")) {
    // return (List<Cancion>) Arrays.asList(db.orderAlbum(type).toArray());
    // } else {
    // return (List<Cancion>) Arrays.asList(db.listAll().toArray());
    // }
    // }

    public List<Cancion> order(String atributo, Integer type) {
        return Arrays.asList(db.orderByAttribute(atributo, type).toArray());
    }

    public List<Cancion> shellSort(String atributo, Integer type) {
        return Arrays.asList(db.shellSortByAttribute(atributo, type).toArray());
    }

    public List<HashMap> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = db.search(attribute, text, type);
        if (!lista.isEmpty()) {
            return Arrays.asList(lista.toArray());
        } else {
            return new ArrayList<>();
        }
    }

}

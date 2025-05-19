package com.unl.music.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.unl.music.base.models.Cancion;
import com.unl.music.base.models.TipoArchivoEnum;
import com.unl.music.base.controller.dao.dao_models.DaoAlbum;
import com.unl.music.base.controller.dao.dao_models.DaoCancion;
import com.unl.music.base.controller.dao.dao_models.DaoGenero;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

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

    public void createCancion(@NotEmpty String nombre, Integer id_genero, @PositiveOrZero @NonNull Integer duracion,@NonNull String url, @NonNull TipoArchivoEnum tipo, Integer id_album) throws Exception {
        if (nombre.trim().length() > 0 && id_genero != null && duracion != null && url.trim().length() > 0 && tipo != null && id_album != null) {
            db.getObj().setNombre(nombre);
            db.getObj().setId_genero(id_genero);
            db.getObj().setDuracion(duracion);
            db.getObj().setUrl(url);
            db.getObj().setTipo(tipo);
            db.getObj().setId_album(id_album);
            if (!db.save())
                throw new Exception("No se pudo guardar los datos de la Cancion");
        }
    }

    public void updateCancion(Integer id, @NotEmpty String nombre, Integer id_genero, @NonNull Integer duracion, @NonNull String url, @NonNull TipoArchivoEnum tipo, Integer id_album) throws Exception{
        if (id != null && id > 0 && nombre.trim().length() > 0 && id_genero != null && duracion != null && url.trim().length() > 0 && tipo != null) {
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
    }

    public void deleteCancion(Integer id) throws Exception {
        if (id != null && id > 0 ) {
            Cancion aux = new Cancion();
            aux.setId(id);
            db.delete_by_id(id);
            
        }
    }

    public List<Cancion> listAllCancion() {
        return Arrays.asList(db.listAll().toArray());
    }

    public List<HashMap> listAll() {
        List<HashMap> lista = new ArrayList<>();
        if(!db.listAll().isEmpty()){
            Cancion[] arreglo = db.listAll().toArray();
            DaoGenero da = new DaoGenero();
            DaoAlbum db = new DaoAlbum();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", String.valueOf(arreglo[i].getId()));
                aux.put("nombre", arreglo[i].getNombre());
                aux.put("id_genero", da.listAll().get(arreglo[i].getId_genero() - 1).getNombre());
                aux.put("duracion", String.valueOf(arreglo[i].getDuracion()));
                aux.put("url", arreglo[i].getUrl());
                aux.put("tipo", arreglo[i].getTipo().toString());
                aux.put("id_album",db.listAll().get(arreglo[i].getId_album() - 1).getNombre());
                lista.add(aux);
            }

        }
        return lista;
    }
}


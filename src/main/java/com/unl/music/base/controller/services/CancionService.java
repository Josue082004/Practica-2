package com.unl.music.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.unl.music.base.models.Album;
import com.unl.music.base.models.Banda;
import com.unl.music.base.models.Cancion;
import com.unl.music.base.models.TipoArchivoEnum;
import com.github.javaparser.quality.NotNull;
import com.unl.music.base.controller.dao.dao_models.DaoAlbum;
import com.unl.music.base.controller.dao.dao_models.DaoBanda;
import com.unl.music.base.controller.dao.dao_models.DaoCancion;
import com.unl.music.base.controller.dao.dao_models.DaoGenero;
import com.unl.music.base.models.Genero;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class CancionService {
    private DaoCancion db;

    public CancionService() {
        db = new DaoCancion();
    }

    public void createCancion(@NotEmpty String nombre, Integer id_genero, @NonNull Integer duracion,@NonNull String url, @NonNull TipoArchivoEnum tipo, Integer id_album) throws Exception {
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

    public void updateCancion(Integer id, @NotEmpty String nombre, Integer id_genero, @NonNull Integer duracion,@NonNull String url, @NonNull TipoArchivoEnum tipo, Integer id_album)
            throws Exception {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("ID Genero: " + id_genero);
        System.out.println("Duración: " + duracion);
        System.out.println("URL: " + url);
        System.out.println("Tipo: " + tipo);
        System.out.println("ID Album: " + id_album);

        if (id != null && id > 0 && nombre.trim().length() > 0 && id_genero != null && duracion != null
                && url.trim().length() > 0 && tipo != null) {
            Cancion aux = new Cancion();
            aux.setId(id);
            aux.setNombre(nombre);
            aux.setId_genero(id_genero);
            aux.setDuracion(duracion);
            aux.setUrl(url);
            aux.setTipo(tipo);
            aux.setId_album(id_album);

            // Usar el método update_by_id para actualizar el álbum
            db.update_by_id(aux, id);
        }
    }

    public List<String> getNombresGeneros() {
        List<String> nombres = new ArrayList<>();
        DaoGenero dao = new DaoGenero();
        Genero[] arreglo = dao.listAll().toArray();

        for (Genero genero : arreglo) {
            nombres.add(genero.getNombre());
        }

        return nombres;
    }

    public List<String> getNombresAlbums() {
        List<String> nombres = new ArrayList<>();
        DaoAlbum dao = new DaoAlbum();
        Album[] arreglo = dao.listAll().toArray();

        for (Album album : arreglo) {
            nombres.add(album.getNombre());
        }

        return nombres;
    }

    public List<Cancion> listAllCancion() {
        return Arrays.asList(db.listAll().toArray());
    }

    public List<HashMap> listAll() {
        List<HashMap> lista = new ArrayList<>();
        Cancion[] arreglo = db.listAll().toArray();
        DaoGenero da = new DaoGenero();
        DaoBanda db = new DaoBanda();
        for (int i = 0; i < arreglo.length; i++) {
            HashMap<String, String> aux = new HashMap<>();
            aux.put("id", String.valueOf(arreglo[i].getId()));
            aux.put("nombre", arreglo[i].getNombre());
            int indexGenero = arreglo[i].getId_genero() - 1;
            String nombreGenero = indexGenero >= 0 && indexGenero < da.listAll().getLength()
                    ? da.listAll().get(indexGenero).getNombre()
                    : "Desconocido";
            aux.put("id_genero", nombreGenero);
            aux.put("duracion", String.valueOf(arreglo[i].getDuracion()));
            aux.put("url", arreglo[i].getUrl());
            aux.put("tipo", arreglo[i].getTipo().toString());
            int indexAlbum = arreglo[i].getId_album() - 1;
            String nombreAlbum = indexAlbum >= 0 && indexAlbum < db.listAll().getLength()
                    ? db.listAll().get(indexAlbum).getNombre()
                    : "Desconocido";
            aux.put("id_album", nombreAlbum);
            lista.add(aux);

        }
        return lista;
    }
}

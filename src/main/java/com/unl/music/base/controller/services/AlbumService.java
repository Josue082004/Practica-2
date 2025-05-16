package com.unl.music.base.controller.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import com.unl.music.base.models.Album;
import com.github.javaparser.quality.NotNull;
import com.unl.music.base.controller.dao.dao_models.DaoAlbum;
import com.unl.music.base.controller.dao.dao_models.DaoBanda;
import com.unl.music.base.models.Banda;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class AlbumService {
    private DaoAlbum db;

    public AlbumService() {
        db = new DaoAlbum();
    }

    public void createAlbum(@NotEmpty String nombre, @NotNull Date fecha, Integer id_banda) throws Exception {
        if (nombre.trim().length() > 0 && fecha.toString().length() > 0 && id_banda != null) {
            db.getObj().setNombre(nombre);
            db.getObj().setFecha(fecha);
            db.getObj().setId_banda(id_banda);
            if (!db.save())
                throw new Exception("No se pudo guardar los datos de la banda");
        }
    }

    public void updateAlbum(Integer id, @NotEmpty String nombre, @NotNull Date fecha, Integer id_banda)
            throws Exception {
        if (id != null && id > 0 && nombre.trim().length() > 0 && fecha.toString().length() > 0 && id_banda != null) {
            // Crear un nuevo objeto Album con los datos actualizados
            Album albumToUpdate = new Album();
            albumToUpdate.setId(id);
            albumToUpdate.setNombre(nombre);
            albumToUpdate.setFecha(fecha);
            albumToUpdate.setId_banda(id_banda);

            // Usar el método update_by_id para actualizar el álbum
            db.update_by_id(albumToUpdate, id);
        }
    }

    public List<String> getNombresBandas() {
        List<String> nombres = new ArrayList<>();
        DaoBanda dao = new DaoBanda();
        Banda[] arreglo = dao.listAll().toArray(); // conviértelo a arreglo

        for (Banda banda : arreglo) {
            nombres.add(banda.getNombre());
        }

        return nombres;
    }

    public List<Album> listAllAlbum() {
        return Arrays.asList(db.listAll().toArray());
    }

    public List<HashMap> listAll() {
        List<HashMap> lista = new ArrayList<>();
        Album[] arreglo = db.listAll().toArray();
        DaoBanda da = new DaoBanda();

        for (int i = 0; i < arreglo.length; i++) {
            HashMap<String, String> aux = new HashMap<>();
            aux.put("id", String.valueOf(arreglo[i].getId()));
            aux.put("nombre", arreglo[i].getNombre());
            aux.put("fecha", arreglo[i].getFecha().toString());

            int indexBanda = arreglo[i].getId_banda() - 1;
            String nombreBanda = indexBanda >= 0 && indexBanda < da.listAll().getLength()
                    ? da.listAll().get(indexBanda).getNombre()
                    : "Desconocido";

            aux.put("id_banda", nombreBanda);
            lista.add(aux);
        }
        return lista;
    }

}

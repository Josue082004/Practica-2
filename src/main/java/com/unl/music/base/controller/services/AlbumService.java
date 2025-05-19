package com.unl.music.base.controller.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import com.unl.music.base.models.Album;
import com.unl.music.base.controller.dao.dao_models.DaoAlbum;
import com.unl.music.base.controller.dao.dao_models.DaoBanda;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import java.text.SimpleDateFormat;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;


@BrowserCallable
@AnonymousAllowed
public class AlbumService {
    private DaoAlbum db;

    public AlbumService() {
        db = new DaoAlbum();
    }

    public void createAlbum(@NotEmpty String nombre, @NonNull Date fecha, Integer id_banda) throws Exception {
        if (nombre.trim().length() > 0 && fecha.toString().length() > 0 && id_banda != null) {
            db.getObj().setNombre(nombre);
            db.getObj().setFecha(fecha);
            db.getObj().setId_banda(id_banda);
            if (!db.save())
                throw new Exception("No se pudo guardar los datos de la banda");
        }
    }

    public void updateAlbum(Integer id, @NotEmpty String nombre, @NonNull Date fecha, Integer id_banda)
            throws Exception {
        if (id != null && id > 0 && nombre.trim().length() > 0 && fecha.toString().length() > 0 && id_banda != null) {
            Album albumToUpdate = new Album();
            albumToUpdate.setId(id);
            albumToUpdate.setNombre(nombre);
            albumToUpdate.setFecha(fecha);
            albumToUpdate.setId_banda(id_banda);
            db.update_by_id(albumToUpdate, id);
        }
    }

    public List<Album> listAllAlbum() {
        return Arrays.asList(db.listAll().toArray());
    }

    public List<HashMap> listAll() {
        List<HashMap> lista = new ArrayList<>();
        if(!db.listAll().isEmpty()){
            Album[] arreglo = db.listAll().toArray();
            DaoBanda da = new DaoBanda();
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", String.valueOf(arreglo[i].getId()));
                aux.put("nombre", arreglo[i].getNombre());
                Date fecha = arreglo[i].getFecha();
                aux.put("fecha", fecha != null ? isoFormat.format(fecha) : "");
                aux.put("id_banda", da.listAll().get(arreglo[i].getId_banda() - 1).getNombre());

                lista.add(aux);
            }

        }
        return lista;

}

}

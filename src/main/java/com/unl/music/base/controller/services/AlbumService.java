package com.unl.music.base.controller.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import com.unl.music.base.models.Album;
import com.unl.music.base.models.Banda;
import com.unl.music.base.controller.dao.dao_models.DaoAlbum;
import com.unl.music.base.controller.dao.dao_models.DaoBanda;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.mappedtypes.Pageable;
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
            db.getObj().setNombre(nombre);
            db.getObj().setFecha(fecha);
            db.getObj().setId_banda(id_banda);
            if (!db.save())
                throw new Exception("No se pudo guardar los datos de la banda");
    }


    public void updateAlbum(Integer id, @NotEmpty String nombre, @NonNull Date fecha, Integer id_banda)throws Exception {
            Album albumToUpdate = new Album();
            albumToUpdate.setId(id);
            albumToUpdate.setNombre(nombre);
            albumToUpdate.setFecha(fecha);
            albumToUpdate.setId_banda(id_banda);
            db.update_by_id(albumToUpdate, id);
    }

    public List<Album> list (Pageable pageable){
        return Arrays.asList(db.listAll().toArray());
    }

    public List<Album> listAll(){
        return (List<Album>) Arrays.asList(db.listAll().toArray());
    }

    public List<String>listBanda(){
        List<String> lista = new ArrayList<>();
        List<Banda> bandas = (List<Banda>) new DaoBanda().listAll();
        for (Banda banda : bandas) {
            lista.add(banda.getNombre());
        }
        return lista;
    }

    public List<Album>order(String atributo , Integer type){
        System.out.println("Ordenando por " + atributo + " tipo: " + type);
        if(atributo.equalsIgnoreCase("nombre"))
            return (List<Album>) Arrays.asList(db.orderQ(type).toArray());
        else if(atributo.equalsIgnoreCase("fecha")){
            return (List<Album>) Arrays.asList(db.orderFecha(type).toArray());
        }else if(atributo.equalsIgnoreCase("id_banda")){
            return (List<Album>) Arrays.asList(db.orderBanda(type).toArray());
        }else {
            return (List<Album>) Arrays.asList(db.listAll().toArray());
        }
}
}
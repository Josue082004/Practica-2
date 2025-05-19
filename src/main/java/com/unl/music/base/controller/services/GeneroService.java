package com.unl.music.base.controller.services;

import java.util.Arrays;
import java.util.List;
import com.unl.music.base.models.Genero;
import com.unl.music.base.controller.dao.dao_models.DaoGenero;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class GeneroService {
    private DaoGenero db;

    public GeneroService() {
        db = new DaoGenero();
    }

    public void createGenero(@NotEmpty String nombre) throws Exception {
        if (nombre.trim().length() > 0) {
            db.getObj().setNombre(nombre);
            if (!db.save())
                throw new Exception("No se pudo guardar los datos de la banda");
        }
    }

    public void updateGenero(Integer id, @NotEmpty String nombre) throws Exception {
        if(id != null && id > 0 && nombre.trim().length() > 0 ) {
            db.setObj(db.listAll().get(id - 1));
            db.getObj().setNombre(nombre);

            if(!db.update(id - 1))
                throw new  Exception("No se pudo modificar los datos de la banda");
        }
    }

    public List<Genero> listAllGenero() {
        return Arrays.asList(db.listAll().toArray());
    }

}

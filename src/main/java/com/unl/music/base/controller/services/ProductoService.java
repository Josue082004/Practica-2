package com.unl.music.base.controller.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import com.unl.music.base.models.Producto;
import com.unl.music.base.controller.dao.dao_models.DaoProducto;
import com.unl.music.base.controller.dao.dao_models.DaoMarca;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import java.text.SimpleDateFormat;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;


@BrowserCallable
@AnonymousAllowed
public class ProductoService {
    private DaoProducto db;

    public ProductoService() {
        db = new DaoProducto();
    }

    public void createProducto(@NotEmpty String nombre, @NotEmpty String descripcion ,Integer id_marca) throws Exception {
        if (nombre.trim().length() > 0 && descripcion.trim().length() > 0 && id_marca != null) {
            db.getObj().setNombre(nombre);
            db.getObj().setDescripcion(descripcion);
            db.getObj().setId_marca(id_marca);
            if (!db.save())
                throw new Exception("No se pudo guardar los datos de la banda");
        }
    }

    public void updateProducto(Integer id, @NotEmpty String nombre, @NotEmpty String descripcion, Integer id_marca)
            throws Exception {
        if (id != null && id > 0 && nombre.trim().length() > 0 && descripcion.trim().length() > 0 && id_marca != null) {
            Producto producto = new Producto();
            producto.setId(id);
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setId_marca(id_marca);
            this.db.update_by_id(producto, id);
        }
    }

    public List<Producto> listAllProducto() {
        return Arrays.asList(db.listAll().toArray());
    }

    public List<HashMap> listAll() {
        List<HashMap> lista = new ArrayList<>();
        if(!db.listAll().isEmpty()){
            Producto[] arreglo = db.listAll().toArray();
            DaoMarca da = new DaoMarca();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", String.valueOf(arreglo[i].getId()));
                aux.put("nombre", arreglo[i].getNombre());
                aux.put("descripcion", arreglo[i].getDescripcion());
                aux.put("id_marca", da.listAll().get(arreglo[i].getId_marca() - 1).getNombre());
                lista.add(aux);
            }

        }
        return lista;

}

}
package com.unl.music.base.controller.dao.dao_models;

import com.unl.music.base.models.Producto;
import com.unl.music.base.controller.dao.AdapterDao;
import com.unl.music.base.controller.data_struct.list.LinkedList;

public class DaoProducto extends AdapterDao<Producto>{
    private Producto obj;
    
    public DaoProducto() {
        super(Producto.class);
    }
    
    public Producto getObj() {
        if (obj == null) 
            this.obj = new Producto();
        return obj;
    }

    public void setObj(Producto obj) {
        this.obj = obj;
    }

    public Boolean save() {
    try {
        LinkedList<Producto> lista = listAll();
        int maxId = 0;
        for (int i = 0; i < lista.getLength(); i++) {
            int idActual = lista.get(i).getId();
            if (idActual > maxId) {
                maxId = idActual;
            }
        }
        obj.setId(maxId + 1);
        this.persist(obj);
        return true;
    } catch (Exception e) {
        //TODO
        return false;
    }
}

    public Boolean update(int i) {
        try {
            this.update(obj, obj.getId());
            return true;
        } catch (Exception e) {
            //Log de errores
            e.printStackTrace();
            System.out.println(e);
            return false;
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        DaoProducto da = new DaoProducto();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Fanta");
        da.getObj().setDescripcion("Bebida gaseosa sabor naranja");
        da.getObj().setId_marca(1);
        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");
        }
    }
}
    


package com.unl.music.base.controller.dao.dao_models;

import com.unl.music.base.models.Marca;
import com.unl.music.base.controller.dao.AdapterDao;
import com.unl.music.base.controller.data_struct.list.LinkedList;

public class DaoMarca extends AdapterDao<Marca>{
    private Marca obj;
    
    public DaoMarca() {
        super(Marca.class);
    }
    
    public Marca getObj() {
        if (obj == null) 
            this.obj = new Marca();
        return obj;
    }

    public void setObj(Marca obj) {
        this.obj = obj;
    }

    public Boolean save() {
    try {
        LinkedList<Marca> lista = listAll();
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
        DaoMarca da = new DaoMarca();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Coca-Cola");
        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");
        }
    }
}
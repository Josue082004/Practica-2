package com.unl.music.base.controller.dao.dao_models;

import com.unl.music.base.models.Genero;
import com.unl.music.base.controller.dao.AdapterDao;

public class DaoGenero extends AdapterDao<Genero>{
    private Genero obj;
    
    public DaoGenero() {
        super(Genero.class);
    }
    
    public Genero getObj() {
        if (obj == null) 
            this.obj = new Genero();
        return obj;
    }

    public void setObj(Genero obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            // TODO
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            // TODO
            return false;
            // TODO: handle exception
        }
    }   
    
    


    public static void main(String[] args) {
        DaoGenero da = new DaoGenero();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Bachata");
        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");     
 }
}
}
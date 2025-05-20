package com.unl.music.base.controller.dao.dao_models;

import com.unl.music.base.models.Album;
import com.unl.music.base.controller.dao.AdapterDao;

public class DaoAlbum extends AdapterDao<Album>{
    private Album obj;
    
    public DaoAlbum() {
        super(Album.class);
    }
    
    public Album getObj() {
        if (obj == null) 
            this.obj = new Album();
        return obj;
    }

    public void setObj(Album obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength()+1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
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
        DaoAlbum da = new DaoAlbum();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Juan Carlos");
        da.getObj().setFecha(new java.util.Date());
        da.getObj().setId_banda(5);
        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");     
 }
}
}
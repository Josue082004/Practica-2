package com.unl.music.base.controller.dao.dao_models;

import com.unl.music.base.models.Artista;
import com.unl.music.base.controller.dao.AdapterDao;

public class DaoArtista extends AdapterDao<Artista>{
    private Artista obj;
    
    public DaoArtista() {
        super(Artista.class);
    }
    
    public Artista getObj() {
        if (obj == null) 
            this.obj = new Artista();
        return obj;
    }

    public void setObj(Artista obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //Log de errores
            e.printStackTrace();
            System.out.println(e);
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update() {
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
        DaoArtista da = new DaoArtista();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombres("Tomala");
        da.getObj().setNacionalidad("Peruana");
        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar"); 
    }    }    
        
}
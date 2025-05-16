package com.unl.music.base.controller.dao.dao_models;

import com.unl.music.base.models.Cancion;
import com.unl.music.base.controller.dao.AdapterDao;

public class DaoCancion extends AdapterDao<Cancion>{
    private Cancion obj;
    
    public DaoCancion() {
        super(Cancion.class);
    }
    
    public Cancion getObj() {
        if (obj == null) 
            this.obj = new Cancion();
        return obj;
    }

    public void setObj(Cancion obj) {
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

    public Boolean delete(int i) {
        try {
            this.delete(obj, obj.getId());
            return true;
        } catch (Exception e) {
            //Log de errores
            e.printStackTrace();
            System.out.println(e);
            return false;
        //TODO: handle exception
        }
    }


    
    public static void main(String[] args) {
        DaoCancion da = new DaoCancion();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Juan Carlos");
        da.getObj().setId_genero(1);
        da.getObj().setDuracion(120);
        da.getObj().setUrl("http://www.unl.edu.ar/");
        da.getObj().setTipo(com.unl.music.base.models.TipoArchivoEnum.FISICO);
        da.getObj().setId_album(1);
        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");
        }
    }
}
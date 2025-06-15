package com.unl.music.base.controller.dao.dao_models;

import com.unl.music.base.models.Album;
import com.unl.music.base.models.Cancion;

import java.util.HashMap;

import com.unl.music.base.controller.Utiles;
import com.unl.music.base.controller.dao.AdapterDao;
import com.unl.music.base.controller.data_struct.list.LinkedList;

public class DaoCancion extends AdapterDao<Cancion> {
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
            LinkedList<Cancion> lista = listAll();
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
            // TODO
            return false;
        }
    }

    public Boolean update(int i) {
        try {
            this.update(obj, obj.getId());
            return true;
        } catch (Exception e) {
            // Log de errores
            e.printStackTrace();
            System.out.println(e);
            return false;
            // TODO: handle exception
        }
    }

    public LinkedList<HashMap<String, String>> all() throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!this.listAll().isEmpty()) {
            Cancion[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i]));
            }
        }
        return lista;
    }

    private HashMap<String, String> toDict(Cancion arreglo) throws Exception {
        DaoGenero da = new DaoGenero();
        DaoAlbum db = new DaoAlbum();
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("nombre", arreglo.getNombre());
        aux.put("id_genero", da.get(arreglo.getId_genero()).getNombre());
        aux.put("duracion", arreglo.getDuracion().toString());
        aux.put("url", arreglo.getUrl());
        aux.put("tipo", arreglo.getTipo().toString());
        aux.put("id_album", db.get(arreglo.getId_album()).getNombre());
        return aux;
    }

    //Metodos de Ordenacion
    public LinkedList<Cancion> orderQuickSort(String attribute, Integer type) {
        LinkedList<Cancion> lista = listAll();
        lista.quickSort(attribute, type);
        return lista;
    }

    public LinkedList<Cancion> orderShellSort(String attribute, Integer type) throws Exception {
        LinkedList<Cancion> lista = listAll();
        lista.shellSort(attribute, type);
        return lista;
    }

    //Metodos de Busqueda
    public LinkedList<Cancion> busquedaLineal(String attribute, String text, Integer type) throws Exception {
        LinkedList<Cancion> lista = listAll();
        return lista.busquedaLineal(attribute, text, type);
    }

    public LinkedList<Cancion> busquedaBinaria(String attribute, String text, Integer type) throws Exception {
        LinkedList<Cancion> lista = listAll();
        return lista.busquedaBinaria(attribute, text, type);
    }

    public LinkedList<Cancion> busquedaLinealBinaria (String attribute, String text, Integer type) throws Exception {
        LinkedList<Cancion> lista = listAll();
        return lista.busquedaLinealBinaria(attribute, text, type);
    }


}
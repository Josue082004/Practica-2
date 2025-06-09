package com.unl.music.base.controller.dao.dao_models;

import com.unl.music.base.models.Album;
import com.unl.music.base.controller.Utiles;
import com.unl.music.base.controller.dao.AdapterDao;
import com.unl.music.base.controller.data_struct.list.LinkedList;

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
    
    public LinkedList<Album> orderLastName (Integer type){
        LinkedList<Album> lista = new LinkedList<>();
        if(!listAll().isEmpty()) {
            Integer cont = 0;
            long startTime = System.currentTimeMillis();
            Album arr[] = listAll().toArray();
            int n = arr.length;
            if(type == Utiles.ASCEDENTE){
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].getNombre().toLowerCase().compareTo(arr[min_idx].getNombre().toLowerCase()) < 0) {
                            min_idx = j;
                            cont++;
                        }
                    }
                    Album temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }else {
                for (int i = 0; i < n - 1; i++) {
                    int max_idx = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].getNombre().toLowerCase().compareTo(arr[max_idx].getNombre().toLowerCase()) > 0) {
                            max_idx = j;
                            cont++;
                        }
                    }
                    Album temp = arr[max_idx];
                    arr[max_idx] = arr[i];
                    arr[i] = temp;
                }
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + " ms");
            lista.toList(arr);
    }
    return lista;
    }

    public LinkedList<Album> orderFecha (Integer type){
        LinkedList<Album> lista = new LinkedList<>();
        if(!listAll().isEmpty()) {
            Integer cont = 0;
            long startTime = System.currentTimeMillis();
            Album arr[] = listAll().toArray();
            int n = arr.length;
            if(type == Utiles.ASCEDENTE){
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].getFecha().compareTo(arr[min_idx].getFecha()) < 0) {
                            min_idx = j;
                            cont++;
                        }
                    }
                    Album temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }else {
                for (int i = 0; i < n - 1; i++) {
                    int max_idx = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].getFecha().compareTo(arr[max_idx].getFecha()) > 0) {
                            max_idx = j;
                            cont++;
                        }
                    }
                    Album temp = arr[max_idx];
                    arr[max_idx] = arr[i];
                    arr[i] = temp;
                }
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + " ms");
            lista.toList(arr);
    }
        return lista;
    }
    
    public LinkedList<Album> orderBanda(Integer type){
        LinkedList<Album> lista = new LinkedList<>();
        if(!listAll().isEmpty()) {
            Integer cont = 0;
            long startTime = System.currentTimeMillis();
            Album arr[] = listAll().toArray();
            int n = arr.length;
            if(type == Utiles.ASCEDENTE){
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].getId_banda() < arr[min_idx].getId_banda()) {
                            min_idx = j;
                            cont++;
                        }
                    }
                    Album temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }else {
                for (int i = 0; i < n - 1; i++) {
                    int max_idx = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].getId_banda() > arr[max_idx].getId_banda()) {
                            max_idx = j;
                            cont++;
                        }
                    }
                    Album temp = arr[max_idx];
                    arr[max_idx] = arr[i];
                    arr[i] = temp;
                }
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + " ms");
            lista.toList(arr);
    }
        return lista;
    }

    private int partition(Album arr[], int begin , int end , Integer type){
        Album pivot = arr[end];
        int i = (begin - 1);
        if(type == Utiles.ASCEDENTE){
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) < 0) {
                    i++;
                    Album swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }else {
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) > 0) {
                    i++;
                    Album swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        Album swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;
        return i + 1;
    }

    private void quickSort(Album arr[], int begin, int end, Integer type) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type);
            quickSort(arr, begin, partitionIndex - 1, type);
            quickSort(arr, partitionIndex + 1, end, type);
        }
    }

    public LinkedList<Album> orderQ(Integer type){
        LinkedList<Album> lista = new LinkedList<>();
        if(!listAll().isEmpty()) {
            Album arr[] = listAll().toArray();
            quickSort(arr, 0, arr.length - 1, type);
            lista.toList(arr);
        }
        return lista;
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
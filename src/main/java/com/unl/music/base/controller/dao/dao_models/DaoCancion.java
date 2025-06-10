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

    // public LinkedList<Album> orderLastName (Integer type){
    // LinkedList<Album> lista = new LinkedList<>();
    // if(!listAll().isEmpty()) {
    // Integer cont = 0;
    // long startTime = System.currentTimeMillis();
    // Cancion arr[] = listAll().toArray();
    // int n = arr.length;
    // if(type == Utiles.ASCEDENTE){
    // for (int i = 0; i < n - 1; i++) {
    // int min_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if
    // (arr[j].getNombre().toLowerCase().compareTo(arr[min_idx].getNombre().toLowerCase())
    // < 0) {
    // min_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[min_idx];
    // arr[min_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }else {
    // for (int i = 0; i < n - 1; i++) {
    // int max_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if
    // (arr[j].getNombre().toLowerCase().compareTo(arr[max_idx].getNombre().toLowerCase())
    // > 0) {
    // max_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[max_idx];
    // arr[max_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }

    // long endTime = System.currentTimeMillis();
    // System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + "
    // ms");
    // // Convert Cancion[] to Album[] before calling toList
    // Album[] albumArr = new Album[arr.length];
    // for (int i = 0; i < arr.length; i++) {
    // // Create a new Album from Cancion fields (adjust as needed)
    // Cancion cancion = arr[i];
    // Album album = new Album();
    // album.setId(cancion.getId_album());
    // album.setNombre(cancion.getNombre());
    // // Set other Album fields as needed from Cancion
    // albumArr[i] = album;
    // }
    // lista.toList(albumArr);
    // }
    // return lista;
    // }

    // public LinkedList<Cancion> orderGenero (Integer type){
    // LinkedList<Cancion> lista = new LinkedList<>();
    // if(!listAll().isEmpty()) {
    // Integer cont = 0;
    // long startTime = System.currentTimeMillis();
    // Cancion arr[] = listAll().toArray();
    // int n = arr.length;
    // if(type == Utiles.ASCEDENTE){
    // for (int i = 0; i < n - 1; i++) {
    // int min_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if (arr[j].getId_genero() < arr[min_idx].getId_genero()) {
    // min_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[min_idx];
    // arr[min_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }else {
    // for (int i = 0; i < n - 1; i++) {
    // int max_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if (arr[j].getId_genero() > arr[max_idx].getId_genero()) {
    // max_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[max_idx];
    // arr[max_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }

    // long endTime = System.currentTimeMillis();
    // System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + "
    // ms");
    // lista.toList(arr);
    // }
    // return lista;
    // }

    // public LinkedList<Cancion> orderDuracion (Integer type){
    // LinkedList<Cancion> lista = new LinkedList<>();
    // if(!listAll().isEmpty()) {
    // Integer cont = 0;
    // long startTime = System.currentTimeMillis();
    // Cancion arr[] = listAll().toArray();
    // int n = arr.length;
    // if(type == Utiles.ASCEDENTE){
    // for (int i = 0; i < n - 1; i++) {
    // int min_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if (arr[j].getDuracion() < arr[min_idx].getDuracion()) {
    // min_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[min_idx];
    // arr[min_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }else {
    // for (int i = 0; i < n - 1; i++) {
    // int max_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if (arr[j].getDuracion() > arr[max_idx].getDuracion()) {
    // max_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[max_idx];
    // arr[max_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }

    // long endTime = System.currentTimeMillis();
    // System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + "
    // ms");
    // lista.toList(arr);
    // }
    // return lista;
    // }

    // public LinkedList<Cancion> orderUrl (Integer type){
    // LinkedList<Cancion> lista = new LinkedList<>();
    // if(!listAll().isEmpty()) {
    // Integer cont = 0;
    // long startTime = System.currentTimeMillis();
    // Cancion arr[] = listAll().toArray();
    // int n = arr.length;
    // if(type == Utiles.ASCEDENTE){
    // for (int i = 0; i < n - 1; i++) {
    // int min_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if (arr[j].getUrl().compareTo(arr[min_idx].getUrl()) < 0) {
    // min_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[min_idx];
    // arr[min_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }else {
    // for (int i = 0; i < n - 1; i++) {
    // int max_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if (arr[j].getUrl().compareTo(arr[max_idx].getUrl()) > 0) {
    // max_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[max_idx];
    // arr[max_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }

    // long endTime = System.currentTimeMillis();
    // System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + "
    // ms");
    // lista.toList(arr);
    // }
    // return lista;
    // }

    // public LinkedList<Cancion> orderTipo (Integer type){
    // LinkedList<Cancion> lista = new LinkedList<>();
    // if(!listAll().isEmpty()) {
    // Integer cont = 0;
    // long startTime = System.currentTimeMillis();
    // Cancion arr[] = listAll().toArray();
    // int n = arr.length;
    // if(type == Utiles.ASCEDENTE){
    // for (int i = 0; i < n - 1; i++) {
    // int min_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if (arr[j].getTipo().ordinal() < arr[min_idx].getTipo().ordinal()) {
    // min_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[min_idx];
    // arr[min_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }else {
    // for (int i = 0; i < n - 1; i++) {
    // int max_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if (arr[j].getTipo().ordinal() > arr[max_idx].getTipo().ordinal()) {
    // max_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[max_idx];
    // arr[max_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }

    // long endTime = System.currentTimeMillis();
    // System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + "
    // ms");
    // lista.toList(arr);
    // }
    // return lista;
    // }

    // public LinkedList<Cancion> orderAlbum (Integer type){
    // LinkedList<Cancion> lista = new LinkedList<>();
    // if(!listAll().isEmpty()) {
    // Integer cont = 0;
    // long startTime = System.currentTimeMillis();
    // Cancion arr[] = listAll().toArray();
    // int n = arr.length;
    // if(type == Utiles.ASCEDENTE){
    // for (int i = 0; i < n - 1; i++) {
    // int min_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if (arr[j].getId_album() < arr[min_idx].getId_album()) {
    // min_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[min_idx];
    // arr[min_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }else {
    // for (int i = 0; i < n - 1; i++) {
    // int max_idx = i;
    // for (int j = i + 1; j < n; j++) {
    // if (arr[j].getId_album() > arr[max_idx].getId_album()) {
    // max_idx = j;
    // cont++;
    // }
    // }
    // Cancion temp = arr[max_idx];
    // arr[max_idx] = arr[i];
    // arr[i] = temp;
    // }
    // }

    // long endTime = System.currentTimeMillis();
    // System.out.println("Tiempo de ordenamiento: " + (endTime - startTime) + "
    // ms");
    // lista.toList(arr);
    // }
    // return lista;
    // }

    // public LinkedList<Cancion> orderByAttribute(String attribute, Integer type) {
    // LinkedList<Cancion> lista = new LinkedList<>();
    // if (!listAll().isEmpty()) {
    // Cancion arr[] = listAll().toArray();
    // int n = arr.length;
    // for (int i = 0; i < n - 1; i++) {
    // int idx = i;
    // for (int j = i + 1; j < n; j++) {
    // try {
    // Object valJ = Cancion.class.getMethod("get" +
    // capitalize(attribute)).invoke(arr[j]);
    // Object valIdx = Cancion.class.getMethod("get" +
    // capitalize(attribute)).invoke(arr[idx]);
    // int cmp;
    // if (valJ instanceof Comparable && valIdx instanceof Comparable) {
    // cmp = ((Comparable) valJ).compareTo(valIdx);
    // } else {
    // cmp = valJ.toString().compareTo(valIdx.toString());
    // }
    // if ((type == Utiles.ASCEDENTE && cmp < 0) || (type == Utiles.DESCENDENTE &&
    // cmp > 0)) {
    // idx = j;
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    // Cancion temp = arr[idx];
    // arr[idx] = arr[i];
    // arr[i] = temp;
    // }
    // lista.toList(arr);
    // }
    // return lista;
    // }

    // // Método auxiliar para capitalizar el atributo (por ejemplo: "nombre" ->
    // // "Nombre")
    // private String capitalize(String str) {
    // if (str == null || str.isEmpty())
    // return str;
    // return str.substring(0, 1).toUpperCase() + str.substring(1);
    // }

    // private int partition(Cancion arr[], int begin, int end, Integer type) {
    // Cancion pivot = arr[end];
    // int i = (begin - 1);
    // if (type == Utiles.ASCEDENTE) {
    // for (int j = begin; j < end; j++) {
    // if
    // (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase())
    // < 0) {
    // i++;
    // Cancion swapTemp = arr[i];
    // arr[i] = arr[j];
    // arr[j] = swapTemp;
    // }
    // }
    // } else {
    // for (int j = begin; j < end; j++) {
    // if
    // (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase())
    // > 0) {
    // i++;
    // Cancion swapTemp = arr[i];
    // arr[i] = arr[j];
    // arr[j] = swapTemp;
    // }
    // }
    // }
    // Cancion swapTemp = arr[i + 1];
    // arr[i + 1] = arr[end];
    // arr[end] = swapTemp;
    // return i + 1;
    // }

    // private void quickSort(Cancion arr[], int begin, int end, Integer type) {
    // if (begin < end) {
    // int partitionIndex = partition(arr, begin, end, type);
    // quickSort(arr, begin, partitionIndex - 1, type);
    // quickSort(arr, partitionIndex + 1, end, type);
    // }
    // }

    // public LinkedList<Cancion> orderQ(Integer type) {
    // LinkedList<Cancion> lista = new LinkedList<>();
    // if (!listAll().isEmpty()) {
    // Cancion arr[] = listAll().toArray();
    // quickSort(arr, 0, arr.length - 1, type);
    // lista.toList(arr);
    // }
    // return lista;
    // }

    // public LinkedList<Cancion> orderQuickSortPorNombre() {
    // LinkedList<Cancion> lista = listAll();
    // lista.quickSort(Utiles.ASCEDENTE); // Si Cancion implementa Comparable por
    // nombre
    // return lista;
    // }

    // Quick Sort

    public LinkedList<Cancion> orderQuickSort(String attribute, Integer type) {
        LinkedList<Cancion> lista = listAll();
        lista.quickSort(attribute, type);
        return lista;
    }

    // Shell Sort

    public LinkedList<Cancion> orderShellSort(String attribute, Integer type) throws Exception {
        LinkedList<Cancion> lista = listAll();
        lista.shellSort(attribute, type);
        return lista;
    }

    public LinkedList<Cancion> busquedaLineal(String attribute, String text, Integer type) throws Exception {
        LinkedList<Cancion> lista = listAll();
        return lista.busquedaLineal(attribute, text, type);
    }

    // public Integer buquedaLinealBinario(String attribute, String text, Integer type) throws Exception {
    //     LinkedList<Cancion> lista = listAll();
    //     return lista.LinealBinario(attribute, text, type);
    // }


 
    
    public LinkedList<HashMap<String, String>> busquedaLinealBinaria(String attribute, String text, Integer type)
        throws Exception {
    LinkedList<HashMap<String, String>> lista = all();
    lista.quickSort(attribute, Utiles.ASCEDENTE);
    return lista.busquedaLinealBinaria(attribute, text, type);
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
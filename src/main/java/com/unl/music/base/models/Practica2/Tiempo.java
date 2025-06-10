package com.unl.music.base.models.Practica2;

import com.unl.music.base.controller.data_struct.list.LinkedList;

public class Tiempo {
    public static void main(String[] args) throws Exception {
        String path = "src/main/java/com/unl/music/base/models/parte1/data.txt";

        // Cargar datos en lista enlazada
        LinkedList<Integer> datosListaQ = Lista.cargarData(path);
        LinkedList<Integer> datosListaS = Lista.cargarData(path);

        // Medir tiempo QuickSort en LinkedList
        long inicioQuick = System.nanoTime();
        datosListaQ.quickSort(null, 1); // null porque Integer no tiene atributo, 1 para ascendente
        long finQuick = System.nanoTime();

        // Medir tiempo ShellSort en LinkedList
        long inicioShell = System.nanoTime();
        datosListaS.shellSort(null, 1); // null porque Integer no tiene atributo, 1 para ascendente
        long finShell = System.nanoTime();

        System.out.println("QuickSort LinkedList: " + (finQuick - inicioQuick) + " ns");
        System.out.println("ShellSort LinkedList: " + (finShell - inicioShell) + " ns");
    }
}
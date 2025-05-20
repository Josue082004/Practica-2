package com.unl.music.base.models.Practica2;

import com.unl.music.base.controller.data_struct.list.LinkedList;

public class Tiempo {
    public static void main(String[] args) throws Exception {
        String path = "src/main/java/com/unl/music/base/models/Practica2/data.txt";

        long inicioArreglo = System.nanoTime();
        Integer[] datosArreglo = Arreglo.cargarData(path);
        int repetidosArreglo = Arreglo.contarRepetidos(datosArreglo);
        long finArreglo = System.nanoTime();

        long inicioLista = System.nanoTime();
        LinkedList<Integer> datosLista = Lista.cargarData(path);
        int repetidosLista = Lista.contarRepetidos(datosLista);
        long finLista = System.nanoTime();

        System.out.printf("Numero repetidos en el Arreglo       : %d%n", repetidosArreglo);
        System.out.printf("Numero repetidos en la Lista enlazada: %d%n", repetidosLista);
        System.out.println("\nTIEMPOS:\n");
        System.out.printf("Arreglo       : %d ns%n", (finArreglo - inicioArreglo));
        System.out.printf("Lista Enlazada: %d ns%n", (finLista - inicioLista));
    }
}



package com.unl.music.base.models.Practica2;

import com.unl.music.base.controller.data_struct.list.LinkedList;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Tiempo {
    public static void main(String[] args) throws Exception {
        String path = "src/main/java/com/unl/music/base/models/Practica2/datos.txt";

        // long inicioArreglo = System.nanoTime();
        // Integer[] datosArreglo = Arreglo.cargarData(path);
        // int repetidosArreglo = Arreglo.contarRepetidos(datosArreglo);
        // long finArreglo = System.nanoTime();

        // long inicioLista = System.nanoTime();
        // LinkedList<Integer> datosLista = Lista.cargarData(path);
        // int repetidosLista = Lista.contarRepetidos(datosLista);
        // long finLista = System.nanoTime();

        // System.out.printf("Numero repetidos en el Arreglo : %d%n", repetidosArreglo);
        // System.out.printf("Numero repetidos en la Lista enlazada: %d%n",
        // repetidosLista);
        // System.out.println("\nTIEMPOS:\n");
        // System.out.printf("Arreglo : %d ns%n", (finArreglo - inicioArreglo));
        // System.out.printf("Lista Enlazada: %d ns%n", (finLista - inicioLista));
        // }

        LinkedList<Integer> datosLista = Lista.cargarData(path);

        LinkedList<Integer> listaQuick = new LinkedList<>();
        LinkedList<Integer> listaShell = new LinkedList<>();
        Integer[] datos = datosLista.toArray();
        listaQuick.toList(datos);
        listaShell.toList(datos);

        long inicioQuick = System.nanoTime();
        listaQuick.quickSort("", 1);
        long finQuick = System.nanoTime();
        long tiempoQuick = finQuick - inicioQuick;

        long inicioShell = System.nanoTime();
        listaShell.shellSort("", 1);
        long finShell = System.nanoTime();
        long tiempoShell = finShell - inicioShell;

        System.out.println("QuickSort (NS)\tShellSort (NS)");
        System.out.printf("%d\t%d%n", tiempoQuick, tiempoShell);

        // Guardar lista ordenada por QuickSort
        try (PrintWriter pw = new PrintWriter(
                new FileWriter("src/main/java/com/unl/music/base/models/Practica2/quicksort_ordenado.txt"))) {
            Integer[] quickArr = listaQuick.toArray();
            for (Integer n : quickArr) {
                pw.println(n);
            }
        }

        // Guardar lista ordenada por ShellSort
        try (PrintWriter pw = new PrintWriter(
                new FileWriter("src/main/java/com/unl/music/base/models/Practica2/shellsort_ordenado.txt"))) {
            Integer[] shellArr = listaShell.toArray();
            for (Integer n : shellArr) {
                pw.println(n);
            }
        }
    }
}

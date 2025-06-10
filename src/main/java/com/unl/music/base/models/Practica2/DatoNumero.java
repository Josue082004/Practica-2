package com.unl.music.base.models.Practica2;

import java.util.List;

public class DatoNumero {
    private Integer valor;

    public DatoNumero(Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public static void main(String[] args) {
        List<DatoNumero> datosListaQ = List.of(new DatoNumero(1), new DatoNumero(2));
        List<DatoNumero> datosListaS = List.of(new DatoNumero(3), new DatoNumero(4));

        DatoNumero[] arrQ = datosListaQ.toArray(DatoNumero[]::new);
        DatoNumero[] arrS = datosListaS.toArray(DatoNumero[]::new);

        for (DatoNumero dato : arrQ) {
            System.out.println("arrQ valor: " + dato.getValor());
        }

        for (DatoNumero dato : arrS) {
            System.out.println("arrS valor: " + dato.getValor());
        }
    }

    public static void quickSort(DatoNumero[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(DatoNumero[] arr, int low, int high) {
        int pivot = arr[high].getValor();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j].getValor() < pivot) {
                i++;
                DatoNumero temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        DatoNumero temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}

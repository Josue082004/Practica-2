// package com.unl.music.base.models.Practica2;

// import com.unl.music.base.models.Practica2.DatoNumero;
// import com.unl.music.base.models.Practica2.Lista;
// import com.unl.music.base.models.Practica2.Ordenamiento;
// import com.unl.music.base.controller.data_struct.list.LinkedList;
// public class Tiempo {
//     public static void main(String[] args) throws Exception {
//         String path = "src/main/java/com/unl/music/base/models/parte1/data.txt";

//         LinkedList<DatoNumero> datosListaQ = Lista.cargarData(path);
//         LinkedList<DatoNumero> datosListaS = Lista.cargarData(path);

//         DatoNumero[] arrQ = datosListaQ.toArray();
//         DatoNumero[] arrS = datosListaS.toArray();

//         long inicioQuick = System.nanoTime();
//         quickSort(arrQ, 0, arrQ.length - 1);
//         long finQuick = System.nanoTime();

//         long inicioShell = System.nanoTime();
//         shellSort(arrS);
//         long finShell = System.nanoTime();

//         System.out.println("QuickSort: " + (finQuick - inicioQuick) + " ns");
//         System.out.println("ShellSort: " + (finShell - inicioShell) + " ns");
//     }

//     // Métodos quickSort y shellSort para DatoNumero[] aquí

//     public static void quickSort(DatoNumero[] arr, int low, int high) {
//         if (low < high) {
//             int pi = partition(arr, low, high);

//             quickSort(arr, low, pi - 1);
//             quickSort(arr, pi + 1, high);
//         }
//     }

//     private static int partition(DatoNumero[] arr, int low, int high) {
//         DatoNumero pivot = arr[high];
//         int i = (low - 1);
//         for (int j = low; j < high; j++) {
//             if (arr[j].compareTo(pivot) <= 0) {
//                 i++;
//                 DatoNumero temp = arr[i];
//                 arr[i] = arr[j];
//                 arr[j] = temp;
//             }
//         }
//         DatoNumero temp = arr[i + 1];
//         arr[i + 1] = arr[high];
//         arr[high] = temp;
//         return i + 1;
//     }

//     public static void shellSort(DatoNumero[] arr) {
//         int n = arr.length;
//         for (int gap = n / 2; gap > 0; gap /= 2) {
//             for (int i = gap; i < n; i++) {
//                 DatoNumero temp = arr[i];
//                 int j;
//                 for (j = i; j >= gap && arr[j - gap].compareTo(temp) > 0; j -= gap) {
//                     arr[j] = arr[j - gap];
//                 }
//                 arr[j] = temp;
//             }
//         }
//     }
// }


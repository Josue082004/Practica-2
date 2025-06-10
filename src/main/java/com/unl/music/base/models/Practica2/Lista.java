package com.unl.music.base.models.Practica2;

import com.unl.music.base.controller.data_struct.list.LinkedList;
import com.unl.music.base.models.Practica2.DatoNumero;
import java.io.BufferedReader;
import java.io.FileReader;



public class Lista {
    public static LinkedList<DatoNumero> cargarData(String path) throws Exception {
        LinkedList<DatoNumero> lista = new LinkedList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
            String linea;
            while ((linea = br.readLine()) != null) {
            if (!linea.trim().isEmpty()) {
                lista.add(new DatoNumero(Integer.parseInt(linea.trim())));
            }
        }
        br.close();
        return lista;
    }


    public static int contarRepetidos(LinkedList<Integer> lista) {
        Integer[] datos = lista.toArray();
        int repetidos = 0;
        boolean[] yaContado = new boolean[datos.length];
        for (int i = 0; i < datos.length; i++) {
            if (yaContado[i]) continue;
            int contador = 1;
            for (int j = i + 1; j < datos.length; j++) {
                if (yaContado[j]) continue;
                if (datos[i].intValue() == datos[j].intValue()) {
                    contador++;
                    yaContado[j] = true;
                }
            }
            if (contador > 1) {
                repetidos++;
            }
        }
        return repetidos;
    }
}
    


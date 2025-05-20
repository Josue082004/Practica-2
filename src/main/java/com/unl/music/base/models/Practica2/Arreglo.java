package com.unl.music.base.models.Practica2;

import com.unl.music.base.controller.data_struct.stack.Stack;

import java.io.BufferedReader;
import java.io.FileReader;

public class Arreglo {

    public static Integer[] cargarData(String path) throws Exception{
        Stack<Integer> stack = new Stack<>(15000);
        int cantidad = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                stack.push(Integer.parseInt(linea.trim()));
                cantidad++;
            }
        }
        Integer[] datos = new Integer[cantidad];
        for (int i = cantidad - 1; i >= 0; i--) {
            datos[i] = stack.pop();
        }
        return datos;
    }

    public static int contarRepetidos(Integer[] datos) {
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


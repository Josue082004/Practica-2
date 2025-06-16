package com.unl.music.base.models.Practica2;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

public class crearDatos {
    public static void main(String[] args) {
        String path = "src/main/java/com/unl/music/base/models/Practica2/datos.txt";
        int cantidad = 25000;
        Random random = new Random();

        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (int i = 0; i < cantidad; i++) {
                int numero = random.nextInt(10000); // Números entre 0 y 9999 (ajusta si quieres otro rango)
                pw.println(numero);
            }
            System.out.println("Archivo creado con " + cantidad + " datos en: " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

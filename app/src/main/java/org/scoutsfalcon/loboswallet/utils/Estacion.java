package org.scoutsfalcon.loboswallet.utils;

import java.util.ArrayList;

/**
 * Created by jamp on 17/09/15.
 */
public class Estacion {
    private static String nombre;
    private static Integer maximo;
    private static Boolean tipo;

    public Estacion(String nombre, int maximo, Boolean tipo){
        this.nombre = nombre;
        this.maximo = maximo;
        this.tipo = tipo;
    }

    public static String getNombre() {
        return nombre;
    }

    public static Integer getMaximo() {
        return maximo;
    }

    public static Boolean getTipo() {
        return tipo;
    }
}

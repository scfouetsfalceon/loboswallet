package org.scoutsfalcon.loboswallet.utils;

import java.util.ArrayList;

/**
 * Created by jamp on 17/09/15.
 */
public class Estacion {
    private Integer id;
    private String nombre;
    private Integer maximo;
    private Boolean tipo;

    public Estacion(int id, String nombre, int maximo, Boolean tipo){
        this.id = id;
        this.nombre = nombre;
        this.maximo = maximo;
        this.tipo = tipo;
    }

    public String getId() {
        return Integer.toString(id);
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public Boolean getTipo() {
        return tipo;
    }
}

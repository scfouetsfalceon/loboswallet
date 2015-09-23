package org.scoutsfalcon.loboswallet.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LobosEstacion extends ArrayList<Joven> {
    private static LobosEstacion mInstance = null;
    private Joven inicial = new Joven("");
    private Boolean vacio = true;
    private Integer maxLobos = 0;

    public LobosEstacion() {
        add(inicial);
    }

    public static LobosEstacion getInstance(){
        if(mInstance == null)
        {
            mInstance = new LobosEstacion();
        }
        return mInstance;
    }

    public void setMaximo(Integer maximo) { maxLobos = maximo; }

    public int getTamano() {
        return size();
    }

    public ArrayList<String> getIds() {
        ArrayList<String> ids = new ArrayList<String>();

        for (Joven item: this) {
            ids.add(item.getCode());
        }

        return ids;
    }

    public boolean agregar(Joven joven) {
        boolean salida = false;
        if (vacio) {
            clear();
            vacio = false;
        }
        if (size() != maxLobos && !existe(joven)) {
            add(joven);
            salida = true;
        }

        return salida;
    }

    public Boolean existe(Joven joven) {
        for (Joven item: this) {
            if (item.getCode().equals(joven.getCode())) {
                return true;
            }
        }
        return false;
    }

    public void vaciar() {
        clear();
        add(inicial);
    }

    public boolean isFull() {
        boolean salida = false;
        if (size() < maxLobos) {
            salida = true;
        }
        return !salida;
    }

}

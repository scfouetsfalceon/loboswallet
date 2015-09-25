package org.scoutsfalcon.loboswallet;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.scoutsfalcon.loboswallet.utils.Comunicacion;
import org.scoutsfalcon.loboswallet.utils.Estacion;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);

        ArrayList<Estacion> resultado = Comunicacion.ConfigurarAplicacion();
        assertEquals(2, resultado.size());

    }
}
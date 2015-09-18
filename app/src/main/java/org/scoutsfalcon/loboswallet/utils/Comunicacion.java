package org.scoutsfalcon.loboswallet.utils;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;

public class Comunicacion {
    public static final String DOMAIN = "http://10.227.97.199:3000/";
    public static final String TAG = "Comunicacion";

    public static ArrayList<Estacion> appConfiguration() {

        ArrayList<Estacion> estaciones = new ArrayList<Estacion>();
        HttpURLConnection httpConection = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;

        try {
            URL url = new URL(DOMAIN+"estaciones/");
            httpConection = (HttpURLConnection) url.openConnection();
            httpConection.setRequestMethod("GET");
            httpConection.connect();

            bufferedReader = new BufferedReader(new InputStreamReader(httpConection.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }

            JSONObject jsonResponse = new JSONObject(response.toString());

            JSONArray jsonArray = jsonResponse.getJSONArray("estaciones");
            JSONObject jsonObject;

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = (JSONObject) jsonArray.get(i);

                Estacion estacion = new Estacion();
                Estacion.nombre = jsonObject.getString("nombre");
                Estacion.maximo = jsonObject.getInt("maximo");
                Estacion.tipo = jsonObject.getBoolean("tipo");

                estaciones.add(estacion);
            }

        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if(httpConection != null) {
                httpConection.disconnect();
            }
        }

        return estaciones;
    }
}

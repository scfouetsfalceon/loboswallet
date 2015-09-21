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
import java.util.ArrayList;

public class Comunicacion {
    public static final String DOMAIN = "http://192.168.100.16:3000/";
    public static final String TAG = "Comunicacion";

    public static ArrayList<Estacion> appConfiguration() {

        ArrayList<Estacion> estaciones = null;
        HttpURLConnection httpConection = null;
        //OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;

        try {
            URL url = new URL(DOMAIN+"estaciones/?salida=json");
            httpConection = (HttpURLConnection) url.openConnection();
            httpConection.setRequestMethod("GET");
            //httpConection.setDoOutput(true);
            //httpConection.setRequestProperty("Accept", "application/json");
            httpConection.connect();
            
            bufferedReader = new BufferedReader(new InputStreamReader(httpConection.getInputStream()));

            response = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }

            JSONArray jsonArray = new JSONArray(response.toString());
            JSONObject jsonObject;
            estaciones = new ArrayList<Estacion>();

            Estacion estacion = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = (JSONObject) jsonArray.get(i);
                Log.i("HOOO", jsonObject.toString());

                String nombre = jsonObject.getString("nombre");
                int maximo = jsonObject.getInt("maximo");
                Boolean tipo = jsonObject.getBoolean("tipo");

                estacion = new Estacion(nombre, maximo, tipo);
                Log.i("HOOO", estacion.getNombre());
                estaciones.add(estacion);
            }

            for (Estacion item : estaciones) {
                Log.i("HOOO", item.getNombre());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } finally {
            if(httpConection != null) {
                httpConection.disconnect();
            }
        }

        return estaciones;
    }
}

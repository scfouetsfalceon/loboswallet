package org.scoutsfalcon.loboswallet.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scoutsfalcon.loboswallet.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Comunicacion {
    public static final String DOMAIN = "http://192.168.100.16:3000/";
    public static final String TAG = "Comunicacion";

    public static ArrayList<Estacion> ConfigurarAplicacion() {

        ArrayList<Estacion> estaciones = null;
        HttpURLConnection httpConection = null;
        //OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;

        try {
            URL url = new URL(DOMAIN+"estaciones/?salida=json");
            httpConection = (HttpURLConnection) url.openConnection();
            httpConection.setRequestMethod("GET");
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

                int id = jsonObject.getInt("id");
                String nombre = jsonObject.getString("nombre");
                int maximo = jsonObject.getInt("maximo");
                Boolean tipo = jsonObject.getBoolean("tipo");

                estacion = new Estacion(id, nombre, maximo, tipo);
                estaciones.add(estacion);
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

    public static Joven DatosJovenes(String id, String estacion) {

        Joven datos = null;
        HttpURLConnection httpConection = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;

        try {
            String location = String.format("%s%s/%s/?estacion=%s&salida=json", DOMAIN, "jovenes", id, estacion);
            URL url = new URL(location);
            httpConection = (HttpURLConnection) url.openConnection();
            httpConection.setRequestMethod("GET");
            httpConection.connect();

            bufferedReader = new BufferedReader(new InputStreamReader(httpConection.getInputStream()));

            response = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }

            JSONObject jsonObject = new JSONObject(response.toString());

            String codigo = jsonObject.getString("id");
            String nombres = jsonObject.getString("nombres");
            String apellidos = jsonObject.getString("apellidos");
            Boolean sexo = jsonObject.getBoolean("sexo");
            String region = jsonObject.getString("region");
            String distrito = jsonObject.getString("distrito");
            String grupo = jsonObject.getString("grupo");
            int saldo = jsonObject.getInt("saldo");

            datos = new Joven(codigo, nombres, apellidos, sexo, region, distrito, grupo, saldo);

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

        return datos;
    }

    public static Boolean OperacionesBancarias(String operacion, ArrayList<String> ids, String estacion) {

        Boolean resultado = false;
        HttpURLConnection httpConection = null;
        DataOutputStream dataOutputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;

        String data = "";
        for(String id : ids){
            data += id + ',';
        }

        try {
            String location = String.format("%sbanco/%s", DOMAIN, operacion.toLowerCase());
            String parameters = String.format("personas=%s&estacion=%s", URLEncoder.encode(data, "UTF-8"), URLEncoder.encode(estacion, "UTF-8"));

            URL url = new URL(location);
            httpConection = (HttpURLConnection) url.openConnection();
            httpConection.setRequestMethod("POST");
            httpConection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConection.setRequestProperty("Content-Length", "" + Integer.toString(parameters.getBytes().length));

            httpConection.setUseCaches(false);
            httpConection.setDoInput(true);
            httpConection.setDoOutput(true);

            dataOutputStream =  new DataOutputStream(httpConection.getOutputStream ());
            dataOutputStream.writeBytes(parameters);
            dataOutputStream.flush();
            dataOutputStream.close();

            bufferedReader = new BufferedReader(new InputStreamReader(httpConection.getInputStream()));
            response = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }

            Log.i(TAG, response.toString());
            JSONObject jsonObject = new JSONObject(response.toString());

            resultado = jsonObject.getBoolean("resultado");
            Log.i(TAG, resultado.toString());

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

        return resultado;
    }
}

package org.scoutsfalcon.loboswallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.scoutsfalcon.loboswallet.utils.Comunicacion;
import org.scoutsfalcon.loboswallet.utils.CustomSpinnerAdapter;
import org.scoutsfalcon.loboswallet.utils.Estacion;

import java.util.ArrayList;


public class ConfigurationActivity extends ActionBarActivity {
    private TextView txtId;
    private TextView txtMaximo;
    private Spinner spinStation;
    private TextView txtTipo;
    private ArrayList<Estacion> estaciones;
    private int item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        estaciones = new ArrayList<Estacion>();
        ArrayAdapter<Estacion> adapter = new CustomSpinnerAdapter(getApplicationContext(), R.layout.spinner_row, estaciones);

        txtId = (TextView)findViewById(R.id.txt_id);
        txtMaximo = (TextView)findViewById(R.id.txt_maximo);
        spinStation = (Spinner)findViewById(R.id.spn_station);
        txtTipo = (TextView)findViewById(R.id.txt_type);

        Button btnGuardar = (Button)findViewById(R.id.btn_submit);

        spinStation.setAdapter(adapter);

        spinStation.setOnItemSelectedListener(new ItemSeleccionado());
        btnGuardar.setOnClickListener(new GuardarListener());

        ConsultaDatos datos = new ConsultaDatos();
        datos.adapter = adapter;
        datos.nombres = estaciones;
        datos.activity = this;

        datos.execute();
    }

    public class ConsultaDatos extends AsyncTask<Void, Void, Boolean> {
        public ArrayAdapter<Estacion> adapter;
        public ArrayList<Estacion> nombres;
        public ConfigurationActivity activity;
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getResources().getString(R.string.loading_stations));
            pDialog.setIndeterminate(false);

            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean result = true;
            try {
                ArrayList<Estacion> estaciones = Comunicacion.ConfigurarAplicacion();
                for (Estacion item : estaciones) {
                    nombres.add(item);
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (!result) {
                Toast.makeText(activity, getResources().getString(R.string.error_comunication), Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
    }

    public void setItem(int i) {
        item = i;
    }

    public void setEstacion(){
        Estacion estacion = estaciones.get(item);
        String id = estacion.getId();
        String maximo =  String.format("%d Personas", estacion.getMaximo());
        String tipo = getResources().getString(R.string.action_buy);
        if (estacion.getTipo()) {
            tipo = getResources().getString(R.string.action_pay);
        }
        txtId.setText(id);
        txtMaximo.setText(maximo);
        txtTipo.setText(tipo);
    }

    class ItemSeleccionado implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            setItem(i);
            setEstacion();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            // Nada
        }
    }


    class GuardarListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Estacion estacion = estaciones.get(item);

            SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.pref_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString(getResources().getString(R.string.pref_station_id), estacion.getId());
            editor.putString(getResources().getString(R.string.pref_station), estacion.getNombre());
            editor.putInt(getResources().getString(R.string.pref_max), estacion.getMaximo());
            editor.putBoolean(getResources().getString(R.string.pref_type), estacion.getTipo());
            editor.commit();

            finish();
        }
    }
}

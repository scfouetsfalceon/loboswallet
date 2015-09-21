package org.scoutsfalcon.loboswallet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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
    private TextView txtMaximo;
    private Spinner spinStation;
    private TextView txtTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        ArrayList<Estacion> estaciones = new ArrayList<Estacion>();
        ArrayAdapter<Estacion> adapter = new CustomSpinnerAdapter(getApplicationContext(), R.layout.spinner_row, estaciones);

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

    public class ConsultaDatos extends AsyncTask<Void, Void, Void> {
        public ArrayAdapter<Estacion> adapter;
        public ArrayList<Estacion> nombres;
        public ConfigurationActivity activity;
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Cargando Estaciones");
            pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);

            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.e("HOOO", "Background");
            try {
                ArrayList<Estacion> estaciones = Comunicacion.appConfiguration();
                for (Estacion item : estaciones) {
                    nombres.add(item);
                }
            } catch (Exception e) {
                Toast.makeText(activity, "Error de comunicación con el servidor", Toast.LENGTH_LONG).show();
                Log.e("HOOO", e.getMessage());
            } finally {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            pDialog.dismiss();
        }
    }

    class ItemSeleccionado extends Activity implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Object item = adapterView.getItemAtPosition(i);
            Log.i("HOOO", String.format("Select %i", i));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Log.i("HOOO", "Nothing Select");
        }
    }


    class GuardarListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String tipo = txtTipo.toString();
            Boolean btipo = false;

            if (tipo.equals("Pagar")) {
                btipo = true;
            }

            SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.pref_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(getResources().getString(R.string.pref_station), spinStation.getSelectedItem().toString());
            editor.putInt(getResources().getString(R.string.pref_max), Integer.parseInt(txtMaximo.getText().toString()));
            editor.putBoolean(getResources().getString(R.string.pref_type), btipo);
            editor.commit();
        }
    }
}

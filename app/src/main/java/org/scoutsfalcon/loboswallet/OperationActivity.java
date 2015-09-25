package org.scoutsfalcon.loboswallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.scoutsfalcon.loboswallet.utils.Comunicacion;
import org.scoutsfalcon.loboswallet.utils.Joven;
import org.scoutsfalcon.loboswallet.utils.LobosEstacion;


public class OperationActivity extends ActionBarActivity {
    private Joven joven;
    private String estacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        IntentIntegrator.initiateScan(this);

        SharedPreferences pref = getSharedPreferences("WalletPreferences", Context.MODE_PRIVATE);
        estacion = pref.getString(getResources().getString(R.string.pref_station_id), "");

        Button btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new ButtonClickListener());
    }

    private void setData(Joven datos) {
        TextView txtName = (TextView) findViewById(R.id.txt_name);
        TextView txtLastname = (TextView) findViewById(R.id.txt_lastname);
        TextView txtRegion = (TextView) findViewById(R.id.txt_region);
        TextView txtDistrict = (TextView) findViewById(R.id.txt_district);
        TextView txtGroup = (TextView) findViewById(R.id.txt_group);
        TextView txtCount = (TextView) findViewById(R.id.txt_count);

        joven = datos;

        txtName.setText(datos.getName());
        txtLastname.setText(datos.getLastName());
        txtRegion.setText(datos.getRegion());
        txtDistrict.setText(datos.getDistrict());
        txtGroup.setText(datos.getDistrict());
        txtCount.setText(String.format("%d £", datos.getAccount()));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            String scanId = scanningResult.getContents();

            if (scanId != null) {
                DatosJoven data = new DatosJoven();
                data.activity = this;
                data.id = scanId;
                data.estacion = estacion;

                data.execute();

            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_no_scan), Toast.LENGTH_LONG).show();
                finish();
            }

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_no_scan), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public class DatosJoven extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog pDialog;
        public OperationActivity activity;
        public String id;
        public String estacion;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getResources().getString(R.string.loading_data));
            pDialog.setIndeterminate(false);

            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean resultado = false;
            try {
                Joven datos = Comunicacion.DatosJovenes(id, estacion);
                setData(datos);
                resultado = true;
            } catch (Exception e) {
                resultado = false;
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            super.onPostExecute(resultado);
            if (!resultado) {
                Toast.makeText(activity, getResources().getString(R.string.error_comunication), Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
    }

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            LobosEstacion lobos = LobosEstacion.getInstance();
            if ( !lobos.agregar(joven) ) {
                Toast.makeText(getApplicationContext(), R.string.msg_no_person, Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }

}

package org.scoutsfalcon.loboswallet;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.scoutsfalcon.loboswallet.utils.Joven;
import org.scoutsfalcon.loboswallet.utils.LobosEstacion;


public class OperationActivity extends ActionBarActivity {
    private Joven joven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        IntentIntegrator.initiateScan(this);
        setData("123");

        Button btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new ButtonClickListener());
    }

    private void setData(String id) {
        TextView txtName = (TextView) findViewById(R.id.txt_name);
        TextView txtLastname = (TextView) findViewById(R.id.txt_lastname);
        TextView txtRegion = (TextView) findViewById(R.id.txt_region);
        TextView txtDistrict = (TextView) findViewById(R.id.txt_district);
        TextView txtGroup = (TextView) findViewById(R.id.txt_group);
        TextView txtCount = (TextView) findViewById(R.id.txt_count);

        String sample = "123";

        // AsyncTask para solicitar datos
        if (sample.equals(id)) {

            joven = new Joven(sample, "Jaro", "Marval", "Falcón", "Paraguaná", "Nazaret", 20, true);

            txtName.setText("Jaro");
            txtLastname.setText("Marval");
            txtRegion.setText("Falcón");
            txtDistrict.setText("Paraguaná");
            txtGroup.setText("Nazaret");
            txtCount.setText("20 £");
        } else {

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();

            if (scanContent != null) {
                //Toast toast = Toast.makeText(getApplicationContext(), scanContent, Toast.LENGTH_LONG);
                //toast.show();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_no_scan), Toast.LENGTH_LONG).show();
                finish();
            }

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_no_scan), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    //public class ConsultaDatos extends AsyncTask<Params, Progress, Result>{
    public class ConsultaDatos extends AsyncTask<String, Object, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
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

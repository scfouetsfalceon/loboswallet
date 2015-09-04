package org.scoutsfalcon.loboswallet;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.scoutsfalcon.loboswallet.utils.Joven;


public class CashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        Bundle action = getIntent().getExtras();
        String operation = action.getString("Action");

        IntentIntegrator.initiateScan(this);

        TextView txtCount = (TextView) findViewById(R.id.txt_count);
        Button btnSubmit = (Button) findViewById(R.id.btn_submit);

        this.setData("123");

        if (operation.equals(getResources().getString(R.string.action_buy))) {
            txtCount.setText("30 £");
        } else {
            txtCount.setText("20 £");
        }

        btnSubmit.setText(operation);
    }

    private void setData(String id) {
        TextView txtName = (TextView) findViewById(R.id.txt_name);
        TextView txtLastname = (TextView) findViewById(R.id.txt_lastname);
        TextView txtRegion = (TextView) findViewById(R.id.txt_region);
        TextView txtDistrict = (TextView) findViewById(R.id.txt_district);
        TextView txtGroup = (TextView) findViewById(R.id.txt_group);

        String sample = "123";

        if (sample.equals(id)) {
            txtName.setText("Jaro");
            txtLastname.setText("Marval");
            txtRegion.setText("Falcón");
            txtDistrict.setText("Paraguaná");
            txtGroup.setText("Nazaret");
        } else {
            // AsyncTask para solicitar datos
            Joven joven = new Joven();
            txtName.setText(joven.getName());
            txtLastname.setText(joven.getLastName());
            txtRegion.setText(joven.getRegion());
            txtDistrict.setText(joven.getDistrict());
            txtGroup.setText(joven.getGroup());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();

            if (scanContent != null) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        scanContent, Toast.LENGTH_LONG);
                toast.show();
            } else {
                finish();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No se pudo escanear nada!", Toast.LENGTH_LONG);
                toast.show();
            }

        } else {
            finish();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se pudo escanear nada!", Toast.LENGTH_LONG);
            toast.show();
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

}

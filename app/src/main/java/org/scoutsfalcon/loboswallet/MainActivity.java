package org.scoutsfalcon.loboswallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.scoutsfalcon.loboswallet.utils.Comunicacion;
import org.scoutsfalcon.loboswallet.utils.CustomListAdapter;
import org.scoutsfalcon.loboswallet.utils.LobosEstacion;


public class MainActivity extends ActionBarActivity {
    private LobosEstacion lobos = LobosEstacion.getInstance();
    private CustomListAdapter adapter;

    private MenuItem itemCobrar = null;
    private MenuItem itemPagar;

    private String estation;
    private Integer maximo;
    private Boolean tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnAgregar = (ImageButton)findViewById(R.id.button_add);

        btnAgregar.setOnClickListener(new ClickButtonListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences("WalletPreferences", Context.MODE_PRIVATE);
        estation = pref.getString(getResources().getString(R.string.pref_station), getResources().getString(R.string.msg_nostation));
        maximo = pref.getInt(getResources().getString(R.string.pref_max), 0);
        tipo = pref.getBoolean(getResources().getString(R.string.pref_type), false);

        TextView lblStation = (TextView)findViewById(R.id.txt_station);
        lblStation.setText(estation);

        lobos.setMaximo(maximo);

        if (itemCobrar != null) {
            if (tipo) {
                itemPagar.setVisible(true);
                itemCobrar.setVisible(false);
            } else {
                itemPagar.setVisible(false);
                itemCobrar.setVisible(true);
            }
        }

        final ListView lstView = (ListView)findViewById(R.id.lstView);
        adapter = new CustomListAdapter(getApplicationContext(), lobos);
        lstView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        itemCobrar = menu.findItem(R.id.action_buy);
        itemPagar = menu.findItem(R.id.action_pay);
        if (tipo) {
            itemCobrar.setVisible(false);
        } else {
            itemPagar.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent config = new Intent(getApplicationContext(), ConfigurationActivity.class);
            startActivity(config);
            return true;
        } else {
            OperationBank operationBank = new OperationBank();
            operationBank.activity = this;
            operationBank.adapter = adapter;
            operationBank.station = estation;
            operationBank.data = lobos;
            if (id == R.id.action_buy) {
                operationBank.operation = getResources().getString(R.string.action_buy);
                operationBank.execute();
                return true;
            } else if (id == R.id.action_pay) {
                operationBank.operation = getResources().getString(R.string.action_pay);
                operationBank.execute();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public class OperationBank extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog pDialog;
        public MainActivity activity;
        public CustomListAdapter adapter;
        public String operation;
        public LobosEstacion data;
        public String station;

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
            Boolean result;
            try {
                result = Comunicacion.OperacionesBancarias(operation, data.getIds(), station);
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
            } else {
                Toast.makeText(activity, getResources().getString(R.string.msg_account_ok), Toast.LENGTH_LONG).show();
                data.vaciar();
                adapter.notifyDataSetChanged();
            }
            pDialog.dismiss();
        }
    }

    class ClickButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (lobos.isFull()) {
                Toast.makeText(getApplicationContext(), R.string.msg_full, Toast.LENGTH_LONG).show();
                return;
            }
            Intent action = new Intent(getApplicationContext(), OperationActivity.class);
            startActivity(action);
        }
    }

}

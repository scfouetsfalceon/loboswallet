package org.scoutsfalcon.loboswallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.scoutsfalcon.loboswallet.utils.Comunicacion;
import org.scoutsfalcon.loboswallet.utils.CustomListAdapter;
import org.scoutsfalcon.loboswallet.utils.Joven;
import org.scoutsfalcon.loboswallet.utils.LobosEstacion;

import java.util.ArrayList;


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
        } else if (id == R.id.action_buy) {
            ArrayList<String> array = lobos.getIds();
            for (String ids : array) {
                Log.i("HOO", ids);
            }
            return true;
        } else if(id == R.id.action_pay) {
            ArrayList<String> array = lobos.getIds();
            for (String ids : array) {
                Log.i("HOO", ids);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
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

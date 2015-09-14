package org.scoutsfalcon.loboswallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class ConfigurationActivity extends ActionBarActivity {
    private TextView txtMaximo;
    private Spinner SpinStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item);

        txtMaximo = (TextView)findViewById(R.id.txt_maximo);
        SpinStation = (Spinner)findViewById(R.id.spn_station);
        Button btnGuardar = (Button)findViewById(R.id.btn_submit);

        SpinStation.setAdapter(adapter);

        btnGuardar.setOnClickListener(new GuardarListener());
    }

    class GuardarListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.pref_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(getResources().getString(R.string.pref_station), SpinStation.getSelectedItem().toString());
            editor.putInt(getResources().getString(R.string.pref_max), Integer.parseInt(txtMaximo.getText().toString()));
            editor.commit();
        }
    }
}

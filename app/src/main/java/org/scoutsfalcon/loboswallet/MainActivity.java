package org.scoutsfalcon.loboswallet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button CobrarBtn =  (Button)findViewById(R.id.btn_buy);
        Button PagarBtn = (Button)findViewById(R.id.btn_pay);
        CobrarBtn.setOnClickListener(new ClickButtonListener());
        PagarBtn.setOnClickListener(new ClickButtonListener());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    class ClickButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent action = new Intent(getApplicationContext(), CashActivity.class);
            String command = ((Button) view).getText().toString();
            action.putExtra("Action", command);
            startActivity(action);
        }
    }
}
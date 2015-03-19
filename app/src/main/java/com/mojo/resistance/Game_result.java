package com.mojo.resistance;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Game_result extends ActionBarActivity {
    String winteam, spies_text;
    String[] output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        Intent result = getIntent();
        output = result.getExtras().getStringArray("gr");
        winteam = output[0];
        spies_text = output[1];

        TextView showit = (TextView) findViewById(R.id.gr_wteam);
        TextView spies = (TextView) findViewById(R.id.gr_identity);

        if (winteam.equals("Blue")) {
            showit.setTextColor(getResources().getColor(R.color.cadetblue));
        }
        else {
            showit.setTextColor(getResources().getColor(R.color.crimson));
        }
        showit.setText(winteam + " Team!!");
        String spies_identity = "Imperial Spies are: ";
        spies_identity += spies_text;
        spies.setText(spies_identity);

        TextView top = (TextView) findViewById(R.id.gr_text);
        Button btn = (Button) findViewById(R.id.back_tohome);

        top.setVisibility(View.VISIBLE);
        showit.setVisibility(View.VISIBLE);
        spies.setVisibility(View.VISIBLE);
        btn.setVisibility(View.VISIBLE);

    }

    public void backToHome(View view) {
        Intent home = new Intent(this, Home_Page.class);
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_result, menu);
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
}

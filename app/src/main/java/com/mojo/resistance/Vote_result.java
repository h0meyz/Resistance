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


public class Vote_result extends ActionBarActivity {
    Mission output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_result);

        Intent da_result = getIntent();
        Parcelable temp = da_result.getExtras().getParcelable("vr_mission");
        output = (Mission) temp;

        TextView success = (TextView) findViewById(R.id.vr_s);
        TextView fail = (TextView) findViewById(R.id.vr_f);
        TextView wteam = (TextView) findViewById(R.id.vr_t);
        success.setText(String.valueOf(output.get_success_vote()));
        fail.setText(String.valueOf(output.get_fail_vote()));
        wteam.setText(output.get_winteam());

        success.setVisibility(View.VISIBLE);
        fail.setVisibility(View.VISIBLE);
        Button gb = (Button) findViewById(R.id.vr_bot);
        gb.setVisibility(View.VISIBLE);


    }

    public void goBack(View view) {
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vote_result, menu);
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

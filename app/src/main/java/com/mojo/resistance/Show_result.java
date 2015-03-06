package com.mojo.resistance;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class Show_result extends ActionBarActivity {
    int success_count = 3, fail_count = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        /*Intent intent = getIntent();
        int[] temp = intent.getIntArrayExtra("myResult");
        System.out.println(temp.length);
        success_count = temp[0];
        fail_count = temp[1];*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_result, menu);
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
    public void showit(View view) {
        String f_count = Integer.toString(fail_count);
        String s_count = Integer.toString(success_count);
        char[] myArray1 = f_count.toCharArray();
        char[] myArray2 = s_count.toCharArray();
        char[] myArray3;

        TextView temp1 = (TextView) findViewById(R.id.sr_f);
        TextView temp2 = (TextView) findViewById(R.id.sr_s);
        TextView temp3 = (TextView) findViewById(R.id.sr_t);
        if (success_count > fail_count) {
            String winteam = "Blue";
            myArray3 = winteam.toCharArray();
        }
        else {
            String winteam = "Red";
            myArray3 = winteam.toCharArray();
        }

        temp1.setText(myArray1,0,myArray1.length);
        temp2.setText(myArray2,0,myArray2.length);
        temp3.setText(myArray3,0,myArray3.length);


    }
}

package com.mojo.resistance;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;


public class NumberOfPlayerActivity extends ActionBarActivity {
    public static String selectedValue = "test";
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_of_player);
        NumberPicker numPicker = (NumberPicker)findViewById(R.id.numberPicker);
        final TextView numberSelected = (TextView)findViewById(R.id.numberConfirmation);
        numPicker.setWrapSelectorWheel(true);
        numPicker.setMinValue(5);
        numPicker.setMaxValue(10);
        numPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldValue, int newValue){
                numberSelected.setText("Selected number is " + newValue);
                value = newValue;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_number_of_player, menu);
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
    public void nextToSetup(View view){
        Intent intent = new Intent(this, PlayerSetupActivity.class);
        System.out.println(selectedValue);
        intent.putExtra(selectedValue, value);
        startActivity(intent);
    }
}

package com.mojo.resistance;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.widget.TableRow.LayoutParams;

import java.util.ArrayList;
import java.util.List;


public class PlayerSetupActivity extends ActionBarActivity {
    TableLayout table_layout;
    int test = 0;
    int result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);

        Intent intent = getIntent();
        result = intent.getIntExtra(NumberOfPlayerActivity.selectedValue, test);
        //TextView textView = (TextView)findViewById(R.id.numberOfPlayer);
        //textView.setText("Number of player selected: " + result);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        BuildTable(result);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_setup, menu);
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
    /*private EditText createEditText(int numberOfPlayers)
    {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT); // Width , height
        final EditText edittext = new EditText(this);
        edittext.setHint("Player " + numberOfPlayers+ " name");
        edittext.setLayoutParams(lparams);
        return edittext;
    }*/

    public void nextToGamePlay(View view){
        //Get text input values from edittext and store it in Player object
        Player[] player_list = new Player[result];
        for(int i=1; i<=result;i++){
            //store it into player object
            EditText pname = (EditText)findViewById(100+i);
            EditText pnum = (EditText)findViewById(200+i);
            player_list[(i-1)] = new Player(pname.getText().toString(),pnum.getText().toString());
        }
        Intent intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra("array", player_list);
        startActivity(intent);
    }
    private void BuildTable(int rows) {

        // outer for loop
        for (int i = 1; i <= rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            EditText pname = new EditText(this);
            pname.setHint("Player " + i + " name");
            pname.setId(100 + i);
            //System.out.println("ID is " + pname.getId());
            pname.setLayoutParams(new LayoutParams(300,
                    200));
            row.addView(pname);

            EditText pnumber = new EditText(this);
            pnumber.setHint("Phone Number");
            pnumber.setId(200+i);
            pnumber.setLayoutParams(new LayoutParams(300,
                    200));
            row.addView(pnumber);

            table_layout.addView(row);

        }
    }

}

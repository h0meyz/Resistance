package com.mojo.resistance;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

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
        result = intent.getIntExtra("selectedValue", test);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        BuildTable(result);

    }

    boolean isEmpty() {
        boolean is_no_input = false;
        boolean notDone = true;
        for(int i=1; i<=result && notDone;i++){
            EditText pname = (EditText)findViewById(100+i);
            if (pname.getText().toString().matches("")) {
                notDone = false;
                is_no_input = true;
            }
        }
        return is_no_input; // true = empty, false = not empty
    }

    public void nextToGamePlay(View view){
        if (isEmpty()) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Missing Player Name");
            builder1.setMessage("Please type in all player names");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else { // if not empty go
            //Get text input values from edittext and store it in Player object
            Player[] player_list = new Player[result];
            for (int i = 1; i <= result; i++) {
                //store it into player object
                EditText pname = (EditText) findViewById(100 + i);
                player_list[(i-1)] = new Player(pname.getText().toString(), "NA");
            }
            Intent intent = new Intent(this, Identity.class);
            intent.putExtra("array", player_list);
            startActivity(intent);
        }
    }

    private void BuildTable(int max) {
        int i = 0;
        int r = max -i;
        // outer for loop
        while (i <= max) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            if (r >= 3) {
                i++;
                EditText pname1 = new EditText(this);
                pname1.setHint("Player " + i);
                pname1.setId(100 + i);
                pname1.setLayoutParams(new LayoutParams(220, 130));
                row.addView(pname1);
                setListener(pname1);

                i++;
                EditText pname2 = new EditText(this);
                pname2.setHint("Player " + i);
                pname2.setId(100 + i);
                pname2.setLayoutParams(new LayoutParams(220, 130));
                row.addView(pname2);
                setListener(pname2);

                i++;
                EditText pname3 = new EditText(this);
                pname3.setHint("Player " + i);
                pname3.setId(100 + i);
                pname3.setLayoutParams(new LayoutParams(220, 130));
                row.addView(pname3);
                setListener(pname3);

                row.setGravity(Gravity.CENTER);
                table_layout.addView(row);
            }

            else if (r == 2) {
                i++;
                EditText pname1 = new EditText(this);
                pname1.setHint("Player " + i);
                pname1.setId(100 + i);
                pname1.setLayoutParams(new LayoutParams(220, 130));
                row.addView(pname1);
                setListener(pname1);

                i++;
                EditText pname2 = new EditText(this);
                pname2.setHint("Player " + i);
                pname2.setId(100 + i);
                pname2.setLayoutParams(new LayoutParams(220, 130));
                row.addView(pname2);
                setListener(pname2);

                table_layout.addView(row);
            }
            else if (r == 1) {
                i++;
                EditText pname1 = new EditText(this);
                pname1.setHint("Player " + i);
                pname1.setId(100 + i);
                pname1.setLayoutParams(new LayoutParams(220, 130));
                row.addView(pname1);
                setListener(pname1);

                row.setGravity(Gravity.CENTER);
                table_layout.addView(row);
            }
            else {// r = 0
                i++;
            }

            r = max - i;
        }
    }

    public void setListener(EditText edittext) {
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void sendSMS(View view) {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, PlayerSetupActivity.class), 0);
        SmsManager sms = SmsManager.getDefault();

        String message = "Yo idiot. I'm testing my sms sender code";

        EditText pnum1 = (EditText)findViewById(200+1);

        EditText pnum2 = (EditText)findViewById(200+2);

        sms.sendTextMessage(pnum1.getText().toString(), null, message, pi, null);
        sms.sendTextMessage(pnum2.getText().toString(), null, message, pi, null);
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

}

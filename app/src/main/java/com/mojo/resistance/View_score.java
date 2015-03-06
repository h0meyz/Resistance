package com.mojo.resistance;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;


public class View_score extends ActionBarActivity {
    Score[] score_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);
        /*Intent intent = getIntent();
        Parcelable[] allParcelables = intent.getExtras().getParcelableArray("array1");
        score_list = new score[allParcelables.length];
        for (int i = 0 ; i < allParcelables.length; i++) {
            score_list[i] = (score)allParcelables[i];
        }*/
        score_list = new Score[5];
        for (int i =0; i< 5; i++) {
            score_list[i] = new Score("moip","red");
        }

        set_my_text();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_score, menu);
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
    void set_my_text() {
        // set set for leader
        String leader1 = score_list[0].getLeader();
        String leader2 = score_list[1].getLeader();
        String leader3 = score_list[2].getLeader();
        String leader4 = score_list[3].getLeader();
        String leader5 = score_list[4].getLeader();
        char[] charArray1 = leader1.toCharArray();
        char[] charArray2 = leader2.toCharArray();
        char[] charArray3 = leader3.toCharArray();
        char[] charArray4 = leader4.toCharArray();
        char[] charArray5 = leader5.toCharArray();

        TextView temp1 = (TextView) findViewById(R.id.l1);
        TextView temp2 = (TextView) findViewById(R.id.l2);
        TextView temp3 = (TextView) findViewById(R.id.l3);
        TextView temp4 = (TextView) findViewById(R.id.l4);
        TextView temp5 = (TextView) findViewById(R.id.l5);

        temp1.setText(charArray1,0,charArray1.length);
        temp2.setText(charArray2,0,charArray2.length);
        temp3.setText(charArray3,0,charArray3.length);
        temp4.setText(charArray4,0,charArray4.length);
        temp5.setText(charArray5,0,charArray5.length);

        // set text for win team
        String wt1 = score_list[0].getWinteam();
        String wt2 = score_list[1].getWinteam();
        String wt3 = score_list[2].getWinteam();
        String wt4 = score_list[3].getWinteam();
        String wt5 = score_list[4].getWinteam();
        char[] charArray6 = wt1.toCharArray();
        char[] charArray7 = wt2.toCharArray();
        char[] charArray8 = wt3.toCharArray();
        char[] charArray9 = wt4.toCharArray();
        char[] charArray10 = wt5.toCharArray();

        temp1 = (TextView) findViewById(R.id.t1);
        temp2 = (TextView) findViewById(R.id.t2);
        temp3 = (TextView) findViewById(R.id.t3);
        temp4 = (TextView) findViewById(R.id.t4);
        temp5 = (TextView) findViewById(R.id.t5);

        temp1.setText(charArray6,0,charArray6.length);
        temp2.setText(charArray7,0,charArray7.length);
        temp3.setText(charArray8,0,charArray8.length);
        temp4.setText(charArray9,0,charArray9.length);
        temp5.setText(charArray10,0,charArray10.length);
    }
}

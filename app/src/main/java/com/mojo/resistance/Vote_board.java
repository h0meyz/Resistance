package com.mojo.resistance;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Vote_board extends ActionBarActivity {
    boolean success = false;
    boolean fail = false;
    int number_voters =3;
    int voter_count = 0;
    int success_count= 0;
    int fail_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_board);
    }

    @Override
    public void onResume(){
        super.onResume();
        voter_count = 0;
    }
    /* @Override
    public void onResume() {
        super.onResume();

        *//*while (voter_count <= number_voters) {
            System.out.println("in while loop");
        }*//*
        int[] temp = new int[2];
        temp[0] = success_count;
        temp[1] = fail_count;
        String myResult = "myResult";
        //Intent intent = new Intent(this, view_score.class);
        //intent.putExtra(myResult, temp);
        //startActivity(intent);

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vote_board, menu);
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

    public void set_success(View view){
        success = false;
        fail = false;
        success = true;

    }
    public void set_fail(View view){
        success = false;
        fail = false;
        fail = true;

    }
    public void confirm(View view){
        voter_count += 1;
        if (success) {
            success_count += 1;
        }
        else {
            fail_count += 1;
        }
        Intent intent;
        if (voter_count == number_voters) {
            intent = new Intent(this,View_score.class);
            startActivity(intent);
        }


    }

}

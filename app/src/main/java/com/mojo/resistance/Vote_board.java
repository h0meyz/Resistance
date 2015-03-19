package com.mojo.resistance;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Vote_board extends ActionBarActivity {
    Mission current_mission;
    boolean success;
    boolean fail;
    boolean returnFromVoteResult;

    String da_result;
    int voter_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_board);

        // extract extras from intent and cast it to Mission
        Intent fromGP = getIntent();
        Parcelable temp = fromGP.getExtras().getParcelable("da_mission");
        current_mission = (Mission) temp;

        initial_setup();

        LinearLayout vb1 = (LinearLayout) findViewById(R.id.vb1);
        LinearLayout vb2 = (LinearLayout) findViewById(R.id.vb2);
        LinearLayout vb3 = (LinearLayout) findViewById(R.id.vb3);

        vb1.setVisibility(View.VISIBLE);
        vb2.setVisibility(View.VISIBLE);
        vb3.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume(){
        super.onResume();
        if (returnFromVoteResult) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("da_winning_team",da_result);
            setResult(RESULT_OK,returnIntent);
            finish();
        }
    }

    void initial_setup() {
        success = false;
        fail = false;
        voter_count = 0;
        setText(voter_count);
        returnFromVoteResult = false;
    }

    void setText(int i) {
        TextView da_text = (TextView) findViewById(R.id.player_text);
        da_text.setText("Player " + (i+1));
    }

    void reset_Button() {
        success = false;
        fail = false;

        Button suc_btn = (Button) findViewById(R.id.vb1_y);
        Button fai_btn = (Button) findViewById(R.id.vb1_n);

        suc_btn.setBackgroundColor(getResources().getColor(R.color.btn_nonpressed));
        suc_btn.invalidate();
        fai_btn.setBackgroundColor(getResources().getColor(R.color.btn_nonpressed));
        fai_btn.invalidate();
    }

    public void set_success(View view){
        // reset success and fail
        reset_Button();
        // then update
        success = true;
        view.setBackgroundColor(getResources().getColor(R.color.btn_pressed));
        view.invalidate();
    }
    public void set_fail(View view){
        // reset success and fail
        reset_Button();
        // then update
        fail = true;
        view.setBackgroundColor(getResources().getColor(R.color.btn_pressed));
        view.invalidate();
    }
    public void onConfirm(View view){
        voter_count += 1;
        if (success) {
            current_mission.count_success();
        }
        else {
            current_mission.count_fail();
        }

        LinearLayout vb1 = (LinearLayout) findViewById(R.id.vb1);
        LinearLayout vb2 = (LinearLayout) findViewById(R.id.vb2);
        LinearLayout vb3 = (LinearLayout) findViewById(R.id.vb3);

        vb1.setVisibility(View.INVISIBLE);
        vb2.setVisibility(View.INVISIBLE);
        vb3.setVisibility(View.INVISIBLE);
        reset_Button();

        if (voter_count == current_mission.get_number_of_people()) {
            if (current_mission.isBlue_win()) {
                da_result = "Blue";
            }
            else {
                da_result = "Red";
            }
            current_mission.set_winteam(da_result);

            returnFromVoteResult = true;
            Intent vote_result = new Intent(this, Vote_result.class);
            vote_result.putExtra("vr_mission",current_mission);
            // put some extras to this intent later
            startActivity(vote_result);
        }
        else {
            setText(voter_count);
            vb1.setVisibility(View.VISIBLE);
            vb2.setVisibility(View.VISIBLE);
            vb3.setVisibility(View.VISIBLE);
        }
    }

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

}

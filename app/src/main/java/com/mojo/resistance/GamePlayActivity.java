package com.mojo.resistance;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GamePlayActivity extends ActionBarActivity {
    Mission[] the_Mission;
    Player[] Player_list;
    String spies_text = "";

    int lead_index;
    int lead_fail_count;

    int[] spy_position = null; // spy_position[i] = index of the Player assigned as spy
    int number_of_spies = 0, number_of_Player = 0;
    int blue_winpoint, red_winpoint;
    int round;
    boolean firstrun;
    boolean return_from_vscore;
    boolean return_from_vote_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        return_from_vscore = false;

        Intent intent = getIntent();
        // get player input received from setupactivity
        Parcelable[] temp = intent.getExtras().getParcelableArray("from_iden");
        Player_list = new Player[temp.length];
        for (int i = 0; i < temp.length; i++) {
            Player_list[i] = (Player) temp[i];
            Player_list[i].identfy(); // restore variable Good based on Good_int
        }
        // Player List should have full attributes by now

        initial_setup(); // set number of players, spies, initialize mission, and set up roles
        return_from_vscore = false;
        return_from_vote_board = false;
        nextRound(); // 1st round in this case
    }


    @Override
    public void onResume() {
        super.onResume();
        if (return_from_vscore){
            return_from_vscore = false;
        }
        else {
            if (return_from_vote_board) {
                return_from_vote_board = false;
                if (blue_winpoint == 3 || red_winpoint == 3) {
                    toEndGame();
                }
                else {
                    nextRound();
                }
            }
        }
    }

    public void startRound(View view) {
        // hide the button first
        Button start_button = (Button) findViewById(R.id.start_bt);
        start_button.setVisibility(View.INVISIBLE);

        lead_fail_count = 0;

        // get lead index and retrieve lead name to set it in layout
        String selected_lead;
        setLead_index();
        selected_lead = Player_list[lead_index].getName();
        TextView lead_text = (TextView) findViewById(R.id.gp_mid_leader);
        lead_text.setText(selected_lead);

        // display the mid layout
        RelativeLayout mid = (RelativeLayout) findViewById(R.id.gp_mid);
        mid.setVisibility(View.VISIBLE);

    }

    void nextRound() {
        // next round
        round += 1;
        TextView current_round = (TextView) findViewById(R.id.round_txt);
        current_round.setText("ROUND " + (round + 1));
        // show start button
        Button start_button = (Button) findViewById(R.id.start_bt);
        start_button.setVisibility(View.VISIBLE);
    }
    void toEndGame() {
        // then show the game result
        Button result = (Button) findViewById(R.id.gp_bot_result);
        result.setVisibility(View.VISIBLE);
    }

    public void onNo(View view) {
        lead_fail_count += 1;

        if (lead_fail_count == 5) {
            // skip round - red wins 1 point
            red_winpoint += 1;
            RelativeLayout mid = (RelativeLayout) findViewById(R.id.gp_mid);
            mid.setVisibility(View.INVISIBLE);
            the_Mission[round].set_winteam("Red");

            if (red_winpoint == 3) {
                // then show the game result
                toEndGame();
            }
            else { // next round
                nextRound();
            }

        }
        else {
            // else get another lead and display his/her name
            String selected_lead;
            setLead_index();
            selected_lead = Player_list[lead_index].getName();
            TextView lead_text = (TextView) findViewById(R.id.gp_mid_leader);
            lead_text.setText(selected_lead);
        }
    }
    public void onYes(View view) {
        return_from_vote_board = true;
        // hide mid layout first
        RelativeLayout mid = (RelativeLayout) findViewById(R.id.gp_mid);
        mid.setVisibility(View.INVISIBLE);

        Mission current_mission = the_Mission[round];
        current_mission.set_lead(Player_list[lead_index].getName()); // update leader for this mission

        Intent goVote = new Intent(this,Vote_board.class);
        goVote.putExtra("da_mission",current_mission);
        startActivityForResult(goVote, 1); // 1 = requestCode
    }

    public void onResult(View view) {
        String winteam;
        String[] gr_array = new String[2];

        if (blue_winpoint == 3){
            winteam = "Blue";
        }
        else if (red_winpoint == 3){
            winteam = "Red";
        }
        else {
            winteam = " Error";
        }

        gr_array[0] = winteam;
        gr_array[1] = spies_text;

        Intent endgame = new Intent(this, Game_result.class);
        endgame.putExtra("gr",gr_array);
        startActivity(endgame);
    }

    public void onViewScore(View view) {
        return_from_vscore = true;
        Intent go_viewscore = new Intent(this,View_score.class);
        go_viewscore.putExtra("array_score",the_Mission);
        startActivity(go_viewscore);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String winning_team;
                winning_team = data.getExtras().getString("da_winning_team");
                System.out.println("Returned is " + winning_team);
                if (winning_team.equals("Red")) {
                    red_winpoint += 1;
                    the_Mission[round].set_winteam("Red");
                } else if (winning_team.equals("Blue")) {
                    blue_winpoint += 1;
                    the_Mission[round].set_winteam("Blue");
                } else {
                    System.out.println("Error returning from vb");
                }
                if (resultCode == RESULT_CANCELED) {
                    System.out.println("Why no result?");
                }
            } else {
                System.out.println("The error what?");
            }
        } // end outtest if
    }//onActivityResult

    void initial_setup() {
        blue_winpoint = 0;
        red_winpoint = 0;
        round = -1;
        firstrun = true;

        // set number of Players
        number_of_Player = Player_list.length;

        // set number of spies
        if (number_of_Player == 5 || number_of_Player == 6) {
            spy_position = new int[2];
            number_of_spies = 2;
        }
        else if (number_of_Player == 7 || number_of_Player == 8 || number_of_Player == 9) {
            spy_position = new int[3];
            number_of_spies = 3;
        }
        else if (number_of_Player == 10) {
            spy_position = new int[4];
            number_of_spies = 4;
        }

        // find spy position among player list and initialize spy_position array
        identify_spies();
        // now produce list of spies and put to spies_text
        set_spies_text();

        // create and initialize array of Mission
        initialize_Mission();

        // Send results
    }

    void identify_spies() {
        int i = 0;
        int spy_index = 0;

        while (spy_index < number_of_spies) {
            boolean notFound = true;
            while (notFound) {
                if (!Player_list[i].isGood()) {
                    spy_position[spy_index] = i;
                    notFound = false;
                }
                i += 1;
            }
            spy_index += 1;
        }
    }

    void set_spies_text() {
        for (int i=0; i<number_of_spies; i++) {
            if (i == (number_of_spies - 2)) { // second to last
                spies_text += Player_list[spy_position[i]].getName();
                spies_text += " and ";
            }
            else if (i == (number_of_spies - 1)) { // last
                spies_text += Player_list[spy_position[i]].getName();
            }
            else {
                spies_text += Player_list[spy_position[i]].getName();
                spies_text += ", ";
            }
        }
    }

    void initialize_Mission() {
        the_Mission = new Mission[5];
        if (number_of_Player == 5) {
            the_Mission[0] = new Mission(1,2,1);
            the_Mission[1] = new Mission(2,3,1);
            the_Mission[2] = new Mission(3,2,1);
            the_Mission[3] = new Mission(4,3,1);
            the_Mission[4] = new Mission(5,3,1);
        }
        else if (number_of_Player == 6) {
            the_Mission[0] = new Mission(1,2,1);
            the_Mission[1] = new Mission(2,3,1);
            the_Mission[2] = new Mission(3,3,1);
            the_Mission[3] = new Mission(4,3,1);
            the_Mission[4] = new Mission(5,4,1);
        }
        else if (number_of_Player == 7) {
            the_Mission[0] = new Mission(1,2,1);
            the_Mission[1] = new Mission(2,3,1);
            the_Mission[2] = new Mission(3,3,1);
            the_Mission[3] = new Mission(4,4,2);
            the_Mission[4] = new Mission(5,4,1);
        }
        else if (number_of_Player == 8) {
            the_Mission[0] = new Mission(1,3,1);
            the_Mission[1] = new Mission(2,4,1);
            the_Mission[2] = new Mission(3,4,1);
            the_Mission[3] = new Mission(4,5,2);
            the_Mission[4] = new Mission(5,5,1);
        }
        else if (number_of_Player == 9) {
            the_Mission[0] = new Mission(1,3,1);
            the_Mission[1] = new Mission(2,4,1);
            the_Mission[2] = new Mission(3,4,1);
            the_Mission[3] = new Mission(4,5,2);
            the_Mission[4] = new Mission(5,5,1);
        }
        else if (number_of_Player == 10) {
            the_Mission[0] = new Mission(1,3,1);
            the_Mission[1] = new Mission(2,4,1);
            the_Mission[2] = new Mission(3,4,1);
            the_Mission[3] = new Mission(4,5,2);
            the_Mission[4] = new Mission(5,5,1);
        }
    } // end initialize Mission

    int getRandom() { // generate random number between 0 to (number_of_player -1)
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i < number_of_Player; i++) {
            list.add(new Integer(i));
        }

        Collections.shuffle(list);
        return list.get(0);
    }

    void setLead_index() {
        if (firstrun) {
            firstrun = false;
            lead_index = getRandom();
        }
        else {
            lead_index = ((lead_index + 1) % number_of_Player);
        }
    } // end get lead index




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_play, menu);
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

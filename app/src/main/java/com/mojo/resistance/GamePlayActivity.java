package com.mojo.resistance;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GamePlayActivity extends ActionBarActivity {
    Mission[] the_Mission;
    Player[] Player_list;
    // MailService mailService = new MailService();
    int[] spy_position = null; // spy_position[i] = index of the Player assigned as spy
    int number_of_spies = 0, number_of_Player = 0;
    int blue_winpoint, red_winpoint;
    int round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        Intent intent = getIntent();

        // get player input received from setupactivity
        Parcelable[] temp = intent.getExtras().getParcelableArray("array");
        Player_list = new Player[temp.length];
        for (int i = 0; i < temp.length; i++) {
            Player_list[i] = (Player) temp[i];
            //System.out.println("Name: " + Player_list[i].getName() + " and phone number: " + Player_list[i].getNum());
        }

        initial_setup(); // set number of players, spies, initialize mission, and set up roles
    }

    @Override
    // come back to this later!
    public void onStart() {
        super.onStart();
        boolean notDone = true;

        while (notDone) {
            int selected_leader_index = 0;
            boolean firstrun = true;

            // 1. Round 1 starts - game end whenever any team reachs 3 points
            while ( (blue_winpoint != 3) & (red_winpoint != 3) ) {
                int lead_fail_count = 0;
                boolean moveon = false, skip = false;
                round += 1; // update round
                Mission temp;

                // load the corresponding Mission (round number - Mission number/index)
                temp = the_Mission[round];

                // repeat following process until a leader is confirmed or 5 leaders are rejected
                while ((!moveon) && (lead_fail_count != 5)) {
                    boolean confirmed_leader = false;

                    // a. select leader = person to the left of previous leader
                    if (round == 1 && firstrun) {
                        selected_leader_index = getRandom();
                        firstrun = false;
                    }
                    else {
                        selected_leader_index = nextLeader(selected_leader_index);
                    }

                    // b. leader by mouth assign people to Mission


                    // c. people publicly and simultaneously vote for the Mission team
                    // !! Active listen choice from user - And record it !!
                    if (confirmed_leader) {
                        moveon = true;
                    }
                    else {
                        lead_fail_count += 1;
                        if (lead_fail_count == 5) { //	after 5 rejected Mission in a row, red team automatically wins 1 point and skip round
                            skip = true;
                        }
                    }
                } // end select leader

                // vote process - after Mission group is endorsed by majority
                if (!skip) { // if not skip, process as normal. otherwise skip and team red wins  1 point
                    // d. People vote - vote choice - transition to next screen
                    //
                    // e. leader show results - app should hide whos voting what. just show number of yes and number of no
                    // f. app updates win point for each team
                }
                else {
                    // Display that red team wins 1 point and move on to round 2
                    red_winpoint += 1;
                }
            } // end while - a team wins

            // 4. Display whos winning - using if/else and winpoint of each team

            // 5. display menu (return to main menu or create new game)

            notDone = false;

        } // end while
    } //end onStart()


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

    void initial_setup() {
        blue_winpoint = 0;
        red_winpoint = 0;
        round = 0;

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

        // by default all players are good. now set spies and record index of them in Player_list
        randomize_roles();

        // create and initialize array of Mission
        initialize_Mission();

        // Send results
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
    void randomize_roles() { // set position of spies (on array of Players)

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i < number_of_Player; i++) {
            list.add(new Integer(i));
        }

        Collections.shuffle(list);

        for (int i=0; i < number_of_spies; i++) {
            spy_position[i] = list.get(i);
            Player_list[spy_position[i]].setSpy();
        }
    } // end random role

    int getRandom() { // generate random number between 0 to (number_of_player -1)
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i < number_of_Player; i++) {
            list.add(new Integer(i));
        }

        Collections.shuffle(list);
        return list.get(0);
    }
    int nextLeader(int index) { // give index of next leader
        int return_me;
        return_me = (index + 1) % number_of_Player;
        return return_me;
    }
    

}

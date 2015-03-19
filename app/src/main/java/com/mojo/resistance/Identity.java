package com.mojo.resistance;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class Identity extends ActionBarActivity {
    Player[] Player_list;
    int[] spy_position;
    int number_of_spies, number_of_Player;
    int current_player;
    String spies_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);

        Intent intent = getIntent();
        Parcelable[] temp = intent.getExtras().getParcelableArray("array");
        Player_list = new Player[temp.length];
        for (int i = 0; i < temp.length; i++) {
            Player_list[i] = (Player) temp[i];
            Player_list[i].identfy();
        }

        initial_setup();
        next_Player();

    }

    void next_Player() {
        current_player += 1;

        TextView player_name = (TextView) findViewById(R.id.identity_player_text);
        ImageView the_image = (ImageView) findViewById(R.id.identity_image);
        Button show_button = (Button) findViewById(R.id.iden_btn);
        TextView result = (TextView) findViewById(R.id.iden_result);
        Button next_btn = (Button) findViewById(R.id.iden_next_btn);

        player_name.setText("Player: " + Player_list[current_player].getName());

        player_name.setVisibility(View.INVISIBLE);
        the_image.setVisibility(View.INVISIBLE);
        show_button.setVisibility(View.INVISIBLE);
        result.setVisibility(View.INVISIBLE);
        next_btn.setVisibility(View.INVISIBLE);

        player_name.setVisibility(View.VISIBLE);
        the_image.setVisibility(View.VISIBLE);
        show_button.setVisibility(View.VISIBLE);
    }

    public void on_show_identity(View view) {
        String result_text = "";
        view.setVisibility(View.INVISIBLE);

        TextView result = (TextView) findViewById(R.id.iden_result);
        Button next_btn = (Button) findViewById(R.id.iden_next_btn);

        if (Player_list[current_player].isGood()) {
            result_text += "You are a member of the resistance allies";
        }
        else {
            result_text += "You are a member of the Imperial Spies\n";
            result_text += "Players in your team are: ";
            result_text += spies_text;
        }

        if (current_player == (number_of_Player -1)) { // last
            result_text += "\n\nPlease press the next button to start the game";
        }
        else {
            result_text += "\n\nPlease press the next button before passing on to player ";
            result_text += Player_list[current_player + 1].getName();
        }

        result.setText(result_text);
        result.setVisibility(View.VISIBLE);
        next_btn.setVisibility(View.VISIBLE);
    }

    public void onNext(View view) {
        if (current_player == (number_of_Player-1)) { // last player --> start game
            Intent intent = new Intent(this, GamePlayActivity.class);
            intent.putExtra("from_iden", Player_list);
            startActivity(intent);
        }
        else { // if not last player
            next_Player();
        }
    }

    void initial_setup() {
        current_player = -1;
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

        randomize_roles();
    }

    void randomize_roles() { // set position of spies and set spies

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i < number_of_Player; i++) {
            list.add(new Integer(i));
        }

        Collections.shuffle(list);

        for (int i=0; i < number_of_spies; i++) {
            spy_position[i] = list.get(i);
            Player_list[spy_position[i]].setSpy();
            System.out.println("Spy position: " + spy_position[i]);
            System.out.println("Player index at this point is: " + i + ". Player: " + Player_list[spy_position[i]].getName());
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
    } // end random role


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_identity, menu);
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

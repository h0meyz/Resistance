package com.mojo.resistance;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Victor on 3/1/2015.
 */
public class Mission implements Parcelable{
    int mission_number;
    int number_of_people;
    int failvote_required;

    int success_count;
    int fail_count;

    String leader;
    String win_team;

    public static final Parcelable.Creator<Mission> CREATOR
            = new Parcelable.Creator<Mission>() {
        public Mission createFromParcel(Parcel in) {
            return new Mission(in);
        }

        public Mission[] newArray(int size) {
            return new Mission[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    // ^ done touch the above part

    // below is my code
    //
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mission_number);
        out.writeInt(number_of_people);
        out.writeInt(failvote_required);
        out.writeString(leader);
        out.writeString(win_team);
        out.writeInt(success_count);
        out.writeInt(fail_count);
    }
    private Mission(Parcel in) {
        mission_number = in.readInt();
        number_of_people = in.readInt();
        failvote_required = in.readInt();
        leader = in.readString();
        win_team = in.readString();
        success_count = in.readInt();
        fail_count = in.readInt();
    }
    //

    Mission(int m_number, int people_number, int failvotes) {
        mission_number = m_number;
        number_of_people = people_number;
        failvote_required = failvotes;

        success_count = 0;
        fail_count = 0;

        leader = "NA";
        win_team = "NA";

    }

    void count_success() {
        success_count += 1;
    } // increase success count by 1
    void count_fail() {
        fail_count += 1;
    }     // increase fail count by 1

    boolean isBlue_win() {
        if (fail_count >= failvote_required)
            return false;
        else {
            return true;
        }

    }     // count votes and report result - true = blue wins; false = red wins

    int get_fail_vote() {
        return fail_count;
    }

    int get_success_vote() {
        return success_count;
    }

    int get_number_of_people() {
        return number_of_people;
    }

    int get_mission_number() {
        return mission_number;
    }

    int get_failvote_required() {
        return failvote_required;
    }

    void reset_counts() {
        success_count = 0;
        fail_count = 0;
    }

    void set_lead(String dLead) {
        leader = dLead;
    }
    String getLead() {
        return leader;
    }
    void set_winteam(String wteam){
        win_team = wteam;
    }
    String get_winteam() {
        return win_team;
    }

} // end mission class

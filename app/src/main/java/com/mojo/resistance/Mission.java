package com.mojo.resistance;

/**
 * Created by Victor on 3/1/2015.
 */
public class Mission {
    int mission_number;
    int number_of_people;
    int failvote_required;

    int success_count;
    int fail_count;

    Mission(int m_number, int people_number, int failvotes) {
        mission_number = m_number;
        number_of_people = people_number;
        failvote_required = failvotes;

        success_count = 0;
        fail_count = 0;

    }

    void count_success() {
        success_count +=1;
    } // increase success count by 1
    void count_fail() {
        fail_count +=1;
    }     // increase fail count by 1

    boolean isBlue_win() {
        if (fail_count == failvote_required)
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

} // end mission class

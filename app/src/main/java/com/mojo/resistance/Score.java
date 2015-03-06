package com.mojo.resistance;

/**
 * Created by Victor on 3/1/2015.
 */
import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable{
    String leader;
    String winteam;

    Score(String l, String wt){
        leader = l;
        winteam = wt;
    }

    private Score(Parcel in) {
        leader = in.readString();
        winteam = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(leader);
        out.writeString(winteam);
    }

    public static final Parcelable.Creator<Score> CREATOR
            = new Parcelable.Creator<Score>() {
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    String getLeader(){
        return leader;
    }
    String getWinteam(){
        return winteam;
    }
}


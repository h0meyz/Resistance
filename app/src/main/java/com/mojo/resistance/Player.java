package com.mojo.resistance;

/**
 * Created by Victor on 3/1/2015.
 */
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Victor on 2/28/2015.
 */
public class Player implements Parcelable {
    private String name;
    private String phone_number;
    private boolean Good;   // true = good, false = spies

    public static final Parcelable.Creator<Player> CREATOR
            = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    // ^ = do not touch part ...

    // below is my code

    Player(String dname, String phone_num) {
        Good = true;
        name = dname;
        phone_number = phone_num;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(phone_number);
    }
    private Player(Parcel in) {
        name = in.readString();
        phone_number = in.readString();
    }

    void setSpy() {
        Good = false;
    }
    boolean isGood() {
        return Good;
    }
    String getName() {
        return name;
    }
    String getNum() {
        return phone_number;
    }

    void setName(String dname) {
        name = dname;
    }
    void setNumber(String dnum) {
        phone_number = dnum;
    }

} // end class


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
    int Good_int; // 0 ~ good = false, 1 ~ good = true

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
    //
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(phone_number);
        out.writeInt(Good_int);
    }
    private Player(Parcel in) {
        name = in.readString();
        phone_number = in.readString();
        Good_int = in.readInt();
    }
    //
    // constructor
    Player(String dname, String phone_num) {
        name = dname;
        phone_number = phone_num;
        Good = true;
        Good_int = 1;
    }

    void setSpy() {
        Good = false;
        Good_int = 0;
    }
    void identfy() {
        if (Good_int == 0) {
            Good = false;
        }
        else {
            Good = true;
        }
    }
    boolean isGood() {
        return Good;
    }

    String getName() {
        return name;
    }
    void setName(String dname) {
        name = dname;
    }

    String getNum() {
        return phone_number;
    }
    void setNumber(String dnum) {
        phone_number = dnum;
    }

} // end class


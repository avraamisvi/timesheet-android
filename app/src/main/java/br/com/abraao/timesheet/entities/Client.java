package br.com.abraao.timesheet.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abraao on 10/20/15.
 */
public class Client extends AbstractEntity {

    public String name;
    public String code;

    public Client(long id, String name, String code) {

        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(code);
    }

    public static final Parcelable.Creator<Client> CREATOR
            = new Parcelable.Creator<Client>() {
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    private Client(Parcel in) {
        super(in);
        name = in.readString();
        code = in.readString();
    }

}

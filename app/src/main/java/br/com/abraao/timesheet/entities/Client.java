package br.com.abraao.timesheet.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by abraao on 10/20/15.
 */
@Table(name = "client")
public class Client extends Model {// implements Parcelable{//extends AbstractEntity {

    @Column(name = "Name")
    public String name;

    @Column(name = "Code")
    public String code;

    public Client() {}

    public Client(String name, String code) {
        super();
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(getId());
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
        setId(in.readLong());
        name = in.readString();
        code = in.readString();
    }*/

}

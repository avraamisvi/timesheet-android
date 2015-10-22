package br.com.abraao.timesheet.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abraao on 10/22/15.
 */
public abstract class AbstractEntity implements Parcelable {

    public long rowId;

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public long getRowId() {
        return rowId;
    }

    public AbstractEntity(long id) {
        this.rowId = id;
    }

    public AbstractEntity(Parcel in) {
        this.rowId = in.readLong();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(rowId);
    }
}

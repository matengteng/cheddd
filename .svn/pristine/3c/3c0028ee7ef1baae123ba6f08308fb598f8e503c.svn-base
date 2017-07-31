package com.cheddd.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 通话记录
 *
 * @author zhan
 */
public class CallLogInfo implements Parcelable {
    private String number;
    private long date;
    private int type;
    private String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.number);
        dest.writeLong(this.date);
        dest.writeInt(this.type);
        dest.writeString(this.name);
    }

    public CallLogInfo() {
    }

    protected CallLogInfo(Parcel in) {
        this.number = in.readString();
        this.date = in.readLong();
        this.type = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<CallLogInfo> CREATOR = new Creator<CallLogInfo>() {
        @Override
        public CallLogInfo createFromParcel(Parcel source) {
            return new CallLogInfo(source);
        }

        @Override
        public CallLogInfo[] newArray(int size) {
            return new CallLogInfo[size];
        }
    };

    @Override
    public String toString() {
        return "CallLogInfo{" +
                "number='" + number + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
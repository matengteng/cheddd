package com.cheddd.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by MaTeng on 16/11/30.
 */

public class ContactGroup implements Parcelable {

    private String sort_key;

    private List<ContactBean> contact_childs;

    public String getSort_key() {
        return sort_key;
    }

    public void setSort_key(String sort_key) {
        this.sort_key = sort_key;
    }

    public List<ContactBean> getContact_childs() {
        return contact_childs;
    }

    public void setContact_childs(List<ContactBean> contact_childs) {
        this.contact_childs = contact_childs;
    }



    @Override
    public String toString() {
        return "ContactGroup{" +
                "sort_key='" + sort_key + '\'' +
                ", contact_childs=" + contact_childs +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sort_key);
        dest.writeTypedList(this.contact_childs);
    }

    public ContactGroup() {
    }

    protected ContactGroup(Parcel in) {
        this.sort_key = in.readString();
        this.contact_childs = in.createTypedArrayList(ContactBean.CREATOR);
    }

    public static final Creator<ContactGroup> CREATOR = new Creator<ContactGroup>() {
        @Override
        public ContactGroup createFromParcel(Parcel source) {
            return new ContactGroup(source);
        }

        @Override
        public ContactGroup[] newArray(int size) {
            return new ContactGroup[size];
        }
    };
}

package com.cheddd.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactBean implements Parcelable {

    private int contactId; //id  
    private String displayName;//姓名
    private String phoneNum; // 电话号码
    private String sortKey; // 排序用的
    private Long photoId; // 图片id
    private String lookUpKey;
    private String phoneBookLabel;

    public String getPhoneBookLabel() {
        return phoneBookLabel;
    }

    public void setPhoneBookLabel(String phoneBookLabel) {
        this.phoneBookLabel = phoneBookLabel;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getLookUpKey() {
        return lookUpKey;
    }

    public void setLookUpKey(String lookUpKey) {
        this.lookUpKey = lookUpKey;
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "contactId=" + contactId +
                ", displayName='" + displayName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", sortKey='" + sortKey + '\'' +
                ", photoId=" + photoId +
                ", lookUpKey='" + lookUpKey + '\'' +
                ", phoneBookLabel='" + phoneBookLabel + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.contactId);
        dest.writeString(this.displayName);
        dest.writeString(this.phoneNum);
        dest.writeString(this.sortKey);
        dest.writeValue(this.photoId);
        dest.writeString(this.lookUpKey);
        dest.writeString(this.phoneBookLabel);
    }

    public ContactBean() {
    }

    protected ContactBean(Parcel in) {
        this.contactId = in.readInt();
        this.displayName = in.readString();
        this.phoneNum = in.readString();
        this.sortKey = in.readString();
        this.photoId = (Long) in.readValue(Long.class.getClassLoader());
        this.lookUpKey = in.readString();
        this.phoneBookLabel = in.readString();
    }

    public static final Creator<ContactBean> CREATOR = new Creator<ContactBean>() {
        @Override
        public ContactBean createFromParcel(Parcel source) {
            return new ContactBean(source);
        }

        @Override
        public ContactBean[] newArray(int size) {
            return new ContactBean[size];
        }
    };
}

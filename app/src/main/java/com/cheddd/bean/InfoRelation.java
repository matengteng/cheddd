package com.cheddd.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/21 0021.
 * 联系人
 */

public class InfoRelation implements Parcelable {
    //客户端来源、用户令牌、直系亲属人编号、直系亲属姓名、直系亲属手机号、直系亲属关系、紧急联系人编号、紧急联系人姓名、紧急联系人
    private String clientType;
    private String token;
    private String id;
    private String contractName;
    private String telNo;
    private String relation;
    private String urgentId;
    private String urgentContractName;
    private String urgentTelNo;

    public InfoRelation() {
    }

    public InfoRelation(String clientType, String token, String id, String contractName, String telNo, String relation, String urgentId, String urgentContractName, String urgentTelNo) {
        this.clientType = clientType;
        this.token = token;
        this.id = id;
        this.contractName = contractName;
        this.telNo = telNo;
        this.relation = relation;
        this.urgentId = urgentId;
        this.urgentContractName = urgentContractName;
        this.urgentTelNo = urgentTelNo;
    }

    protected InfoRelation(Parcel in) {
        clientType = in.readString();
        token = in.readString();
        id = in.readString();
        contractName = in.readString();
        telNo = in.readString();
        relation = in.readString();
        urgentId = in.readString();
        urgentContractName = in.readString();
        urgentTelNo = in.readString();
    }

    public static final Creator<InfoRelation> CREATOR = new Creator<InfoRelation>() {
        @Override
        public InfoRelation createFromParcel(Parcel in) {
            return new InfoRelation(in);
        }

        @Override
        public InfoRelation[] newArray(int size) {
            return new InfoRelation[size];
        }
    };

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getUrgentTelNo() {
        return urgentTelNo;
    }

    public void setUrgentTelNo(String urgentTelNo) {
        this.urgentTelNo = urgentTelNo;
    }

    public String getUrgentContractName() {
        return urgentContractName;
    }

    public void setUrgentContractName(String urgentContractName) {
        this.urgentContractName = urgentContractName;
    }

    public String getUrgentId() {
        return urgentId;
    }

    public void setUrgentId(String urgentId) {
        this.urgentId = urgentId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "InfoRelation{" +
                "clientType='" + clientType + '\'' +
                ", token='" + token + '\'' +
                ", id='" + id + '\'' +
                ", contractName='" + contractName + '\'' +
                ", telNo='" + telNo + '\'' +
                ", relation='" + relation + '\'' +
                ", urgentId='" + urgentId + '\'' +
                ", urgentContractName='" + urgentContractName + '\'' +
                ", urgentTelNo='" + urgentTelNo + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clientType);
        dest.writeString(token);
        dest.writeString(id);
        dest.writeString(contractName);
        dest.writeString(telNo);
        dest.writeString(relation);
        dest.writeString(urgentId);
        dest.writeString(urgentContractName);
        dest.writeString(urgentTelNo);
    }
}

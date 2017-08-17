package com.cheddd.bean;

/**
 * Created by mateng on 2017/8/17.
 */

public class AddContactParams {

    private String clientType;
    private String token;
    private String id;
    private String contractName;
    private String telNo;
    private String relation;
    private String urgentId;
    private String urgentContractName;
    private String urgentTelNo;

    public AddContactParams(String clientType, String token, String id, String contractName, String telNo, String relation, String urgentId, String urgentContractName, String urgentTelNo) {
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

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
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

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getUrgentId() {
        return urgentId;
    }

    public void setUrgentId(String urgentId) {
        this.urgentId = urgentId;
    }

    public String getUrgentContractName() {
        return urgentContractName;
    }

    public void setUrgentContractName(String urgentContractName) {
        this.urgentContractName = urgentContractName;
    }

    public String getUrgentTelNo() {
        return urgentTelNo;
    }

    public void setUrgentTelNo(String urgentTelNo) {
        this.urgentTelNo = urgentTelNo;
    }
}

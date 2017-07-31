package com.cheddd.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class RelationsBean {


    /**
     * token : null
     * returnCode : 000000
     * returnMsg : 操作成功
     * entity : {"urgentId":"cc4801c293e54166b7e55b027ba902e3","urgentTelNo":"18998988989","urgentContractName":"Cdftyui"}
     * rows : [{"textOrSearch":null,"orderbyText":null,"page":null,"rows":null,"nodeStatus":null,"nodeId":null,"nodeOperName":null,"nodeDetail":null,"id":"b5507d683c0843e28dc370ef9ba51cbc","orderNo":"M000001","contractName":"12","relation":0,"addressCompany":"sdfsdf","telNo":"18923230909","telDetail":-1,"telNotime":1499299200000,"isKnow":-1,"remark":"","createTime":1499326739000,"updateTime":1499330824000,"isRemove":0}]
     * flag : true
     * personalAuth : 1
     * workInfoAuth : 1
     * phoneAuth : 1
     * houseInfoAuth : 1
     * contactsInfoAuth : 1
     * carInfoAuth : 1
     * bankInfoAuth : 1
     * personalInfoUpdateYN : 1
     * loanInitAud : 1
     */

    private String returnCode;
    private String returnMsg;
    private EntityBean entity;
    private String flag;
    private int personalAuth;
    private int workInfoAuth;
    private int phoneAuth;
    private int houseInfoAuth;
    private int contactsInfoAuth;
    private int carInfoAuth;
    private int bankInfoAuth;
    private int personalInfoUpdateYN;
    private int loanInitAud;
    private List<RowsBean> rows;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public EntityBean getEntity() {
        return entity;
    }

    public void setEntity(EntityBean entity) {
        this.entity = entity;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getPersonalAuth() {
        return personalAuth;
    }

    public void setPersonalAuth(int personalAuth) {
        this.personalAuth = personalAuth;
    }

    public int getWorkInfoAuth() {
        return workInfoAuth;
    }

    public void setWorkInfoAuth(int workInfoAuth) {
        this.workInfoAuth = workInfoAuth;
    }

    public int getPhoneAuth() {
        return phoneAuth;
    }

    public void setPhoneAuth(int phoneAuth) {
        this.phoneAuth = phoneAuth;
    }

    public int getHouseInfoAuth() {
        return houseInfoAuth;
    }

    public void setHouseInfoAuth(int houseInfoAuth) {
        this.houseInfoAuth = houseInfoAuth;
    }

    public int getContactsInfoAuth() {
        return contactsInfoAuth;
    }

    public void setContactsInfoAuth(int contactsInfoAuth) {
        this.contactsInfoAuth = contactsInfoAuth;
    }

    public int getCarInfoAuth() {
        return carInfoAuth;
    }

    public void setCarInfoAuth(int carInfoAuth) {
        this.carInfoAuth = carInfoAuth;
    }

    public int getBankInfoAuth() {
        return bankInfoAuth;
    }

    public void setBankInfoAuth(int bankInfoAuth) {
        this.bankInfoAuth = bankInfoAuth;
    }

    public int getPersonalInfoUpdateYN() {
        return personalInfoUpdateYN;
    }

    public void setPersonalInfoUpdateYN(int personalInfoUpdateYN) {
        this.personalInfoUpdateYN = personalInfoUpdateYN;
    }

    public int getLoanInitAud() {
        return loanInitAud;
    }

    public void setLoanInitAud(int loanInitAud) {
        this.loanInitAud = loanInitAud;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class EntityBean {
        /**
         * urgentId : cc4801c293e54166b7e55b027ba902e3
         * urgentTelNo : 18998988989
         * urgentContractName : Cdftyui
         */

        private String urgentId;
        private String urgentTelNo;
        private String urgentContractName;

        public String getUrgentId() {
            return urgentId;
        }

        public void setUrgentId(String urgentId) {
            this.urgentId = urgentId;
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
    }

    public static class RowsBean {
        /**
         * textOrSearch : null
         * orderbyText : null
         * page : null
         * rows : null
         * nodeStatus : null
         * nodeId : null
         * nodeOperName : null
         * nodeDetail : null
         * id : b5507d683c0843e28dc370ef9ba51cbc
         * orderNo : M000001
         * contractName : 12
         * relation : 0
         * addressCompany : sdfsdf
         * telNo : 18923230909
         * telDetail : -1
         * telNotime : 1499299200000
         * isKnow : -1
         * remark :
         * createTime : 1499326739000
         * updateTime : 1499330824000
         * isRemove : 0
         */

        private String id;
        private String orderNo;
        private String contractName;
        private int relation;
        private String addressCompany;
        private String telNo;
        private int telDetail;
        private long telNotime;
        private int isKnow;
        private String remark;
        private long createTime;
        private long updateTime;
        private int isRemove;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getContractName() {
            return contractName;
        }

        public void setContractName(String contractName) {
            this.contractName = contractName;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public String getAddressCompany() {
            return addressCompany;
        }

        public void setAddressCompany(String addressCompany) {
            this.addressCompany = addressCompany;
        }

        public String getTelNo() {
            return telNo;
        }

        public void setTelNo(String telNo) {
            this.telNo = telNo;
        }

        public int getTelDetail() {
            return telDetail;
        }

        public void setTelDetail(int telDetail) {
            this.telDetail = telDetail;
        }

        public long getTelNotime() {
            return telNotime;
        }

        public void setTelNotime(long telNotime) {
            this.telNotime = telNotime;
        }

        public int getIsKnow() {
            return isKnow;
        }

        public void setIsKnow(int isKnow) {
            this.isKnow = isKnow;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getIsRemove() {
            return isRemove;
        }

        public void setIsRemove(int isRemove) {
            this.isRemove = isRemove;
        }
    }
}

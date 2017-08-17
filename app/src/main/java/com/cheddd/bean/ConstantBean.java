package com.cheddd.bean;

import java.util.List;

/**
 * Created by mateng on 2017/8/17.
 */

public class ConstantBean {


    /**
     * bankInfoAuth : 3
     * carInfoAuth : 3
     * contactsInfoAuth : 3
     * entity : {"urgentId":"6ab31225b75343c982a7afe34f495359","urgentTelNo":"18109496612","urgentContractName":"啃老了了了"}
     * flag : true
     * houseInfoAuth : 3
     * loanInitAud : 1
     * personalAuth : 3
     * personalInfoUpdateYN : 1
     * phoneAuth : 3
     * returnCode : 000000
     * returnMsg : 操作成功
     * rows : [{"addressCompany":"","contractName":"贾林勇","createTime":1502956847000,"id":"2d85b6bef96441b49f8947b2c3eaa8f6","isKnow":-1,"isRemove":0,"nodeDetail":"","nodeId":"","nodeOperName":"","nodeStatus":null,"orderNo":"M000154","orderbyText":"","page":null,"relation":3,"remark":"","rows":null,"telDetail":-1,"telNo":"18109496612","telNotime":1502956847000,"textOrSearch":"","updateTime":1502957240000}]
     * token :
     * workInfoAuth : 3
     */

    private int bankInfoAuth;
    private int carInfoAuth;
    private int contactsInfoAuth;
    private EntityBean entity;
    private String flag;
    private int houseInfoAuth;
    private int loanInitAud;
    private int personalAuth;
    private int personalInfoUpdateYN;
    private int phoneAuth;
    private String returnCode;
    private String returnMsg;
    private String token;
    private int workInfoAuth;
    private List<RowsBean> rows;

    public int getBankInfoAuth() {
        return bankInfoAuth;
    }

    public void setBankInfoAuth(int bankInfoAuth) {
        this.bankInfoAuth = bankInfoAuth;
    }

    public int getCarInfoAuth() {
        return carInfoAuth;
    }

    public void setCarInfoAuth(int carInfoAuth) {
        this.carInfoAuth = carInfoAuth;
    }

    public int getContactsInfoAuth() {
        return contactsInfoAuth;
    }

    public void setContactsInfoAuth(int contactsInfoAuth) {
        this.contactsInfoAuth = contactsInfoAuth;
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

    public int getHouseInfoAuth() {
        return houseInfoAuth;
    }

    public void setHouseInfoAuth(int houseInfoAuth) {
        this.houseInfoAuth = houseInfoAuth;
    }

    public int getLoanInitAud() {
        return loanInitAud;
    }

    public void setLoanInitAud(int loanInitAud) {
        this.loanInitAud = loanInitAud;
    }

    public int getPersonalAuth() {
        return personalAuth;
    }

    public void setPersonalAuth(int personalAuth) {
        this.personalAuth = personalAuth;
    }

    public int getPersonalInfoUpdateYN() {
        return personalInfoUpdateYN;
    }

    public void setPersonalInfoUpdateYN(int personalInfoUpdateYN) {
        this.personalInfoUpdateYN = personalInfoUpdateYN;
    }

    public int getPhoneAuth() {
        return phoneAuth;
    }

    public void setPhoneAuth(int phoneAuth) {
        this.phoneAuth = phoneAuth;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getWorkInfoAuth() {
        return workInfoAuth;
    }

    public void setWorkInfoAuth(int workInfoAuth) {
        this.workInfoAuth = workInfoAuth;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class EntityBean {
        /**
         * urgentId : 6ab31225b75343c982a7afe34f495359
         * urgentTelNo : 18109496612
         * urgentContractName : 啃老了了了
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
         * addressCompany :
         * contractName : 贾林勇
         * createTime : 1502956847000
         * id : 2d85b6bef96441b49f8947b2c3eaa8f6
         * isKnow : -1
         * isRemove : 0
         * nodeDetail :
         * nodeId :
         * nodeOperName :
         * nodeStatus : null
         * orderNo : M000154
         * orderbyText :
         * page : null
         * relation : 3
         * remark :
         * rows : null
         * telDetail : -1
         * telNo : 18109496612
         * telNotime : 1502956847000
         * textOrSearch :
         * updateTime : 1502957240000
         */

        private String addressCompany;
        private String contractName;
        private long createTime;
        private String id;
        private int isKnow;
        private int isRemove;
        private String nodeDetail;
        private String nodeId;
        private String nodeOperName;
        private String orderNo;
        private String orderbyText;
        private int relation;
        private String remark;
        private int telDetail;
        private String telNo;
        private long telNotime;
        private String textOrSearch;
        private long updateTime;

        public String getAddressCompany() {
            return addressCompany;
        }

        public void setAddressCompany(String addressCompany) {
            this.addressCompany = addressCompany;
        }

        public String getContractName() {
            return contractName;
        }

        public void setContractName(String contractName) {
            this.contractName = contractName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsKnow() {
            return isKnow;
        }

        public void setIsKnow(int isKnow) {
            this.isKnow = isKnow;
        }

        public int getIsRemove() {
            return isRemove;
        }

        public void setIsRemove(int isRemove) {
            this.isRemove = isRemove;
        }

        public String getNodeDetail() {
            return nodeDetail;
        }

        public void setNodeDetail(String nodeDetail) {
            this.nodeDetail = nodeDetail;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getNodeOperName() {
            return nodeOperName;
        }

        public void setNodeOperName(String nodeOperName) {
            this.nodeOperName = nodeOperName;
        }


        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderbyText() {
            return orderbyText;
        }

        public void setOrderbyText(String orderbyText) {
            this.orderbyText = orderbyText;
        }


        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }


        public int getTelDetail() {
            return telDetail;
        }

        public void setTelDetail(int telDetail) {
            this.telDetail = telDetail;
        }

        public String getTelNo() {
            return telNo;
        }

        public void setTelNo(String telNo) {
            this.telNo = telNo;
        }

        public long getTelNotime() {
            return telNotime;
        }

        public void setTelNotime(long telNotime) {
            this.telNotime = telNotime;
        }

        public String getTextOrSearch() {
            return textOrSearch;
        }

        public void setTextOrSearch(String textOrSearch) {
            this.textOrSearch = textOrSearch;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}

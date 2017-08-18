package com.cheddd.config;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public interface NetConfig {
    //基地址 release版本
    //http://47.93.163.237:9080/
    //String BASE_URL = "https://bssapp.cheddd.com/app/";
    //String BASE_URL = "http://47.93.163.237:9080/app/";//测试
    String BASE_URL = "http://192.168.2.113:8080/cddd-app-server/app/";//科长
    String CLIENTTYPE = "?content={\"clientType\":2,}";
    String TYPE = "?";
    //消息滚动条
    String NOTICE = BASE_URL + "index/notice" + CLIENTTYPE;
    //手机动态登录验证码
    String lOGIN_AUTHCODE = BASE_URL + "oauth/sms" + TYPE;
    //手机动态登录
    String PHONE_TREND = BASE_URL + "oauth/login_sms" + TYPE;
    //注册时的验证码
    String REGISTER_AUTHCODE = BASE_URL + "oauth/register_sms" + TYPE;
    //注册
    String REGISTER = BASE_URL + "oauth/register" + TYPE;
    //忘记密码时的验证码
    String FORGETPASSWORD_AUTHCODE = BASE_URL + "oauth/password_sms" + TYPE;
    //忘记密码
    String FORGETPASSWORD = BASE_URL + "oauth/password_reset" + TYPE;
    //密码登录
    String LOGIN_PASSWORD = BASE_URL + "oauth/login_pw" + TYPE;
    //提交工作信息
    String INFO_WORK = BASE_URL + "user_info/work_submit" + TYPE;
    //获取工作信息
    String INFO_WORK_INFO = BASE_URL + "user_info/work_info" + TYPE;
    //提交居住信息
    String INFO_LIVE = BASE_URL + "user_info/address_submit" + TYPE;
    //获取居住信息
    String INFO_LIVE_INFO = BASE_URL + "user_info/address" + TYPE;
    //提交联系人
    String INFO_RELATION = BASE_URL + "user_info/linkman_submit" + TYPE;
    //获取联系人
    String INFO_RELATION_INFO = BASE_URL + "user_info/linkman" + TYPE;
    //提交个人信息
    String INFO = BASE_URL + "user_info/info_submit" + TYPE;
    //获取个人信息
    String INFO_INFO = BASE_URL + "user_info/all_info" + TYPE;
    //车辆信息
    String INFO_CAR = BASE_URL + "car/car_submit" + TYPE;
    //获取车辆信息
    String INFO_CAR_INFO = BASE_URL + "car/car_info" + TYPE;
    //获取认证进度
    String OAUTH_SETP = BASE_URL + "user_info/oauth_step" + TYPE;
    //手机认证提交
    String INFO_PHONE = BASE_URL + "user_info/oauth_mobile_submit" + TYPE;
    //手机认证的请求
    String INFO_PHONE_INFO = BASE_URL + "user_info/oauth_mobile" + TYPE;
    //获取银行列表
    String INFO_BANK_LIST = BASE_URL + "bank/bank_list" + TYPE;
    //提交银行认证
    String INFO_BANK = BASE_URL + "bank/bank_submit" + TYPE;
    //获取银行认证的信息
    String INFO_BANK_INFO = BASE_URL + "bank/bank_info" + TYPE;
    //极速抵押的接口
    //获取服务城市+门店列表+地址
    String INDEX_EXTREME_GAIN = BASE_URL + "loan/service_city" + TYPE;
    //提交抵押贷款借款申请
    String INDEX_EXTREME_SUBMIT = BASE_URL + "loan/loan_apply" + TYPE;
    //获取抵押贷款贷款详情
    String INDEX_PLEDGE_INFO = BASE_URL + "loan/loan_info" + TYPE;
    //小额借款  获取可借额度和总额度，还款试算
    String INDEX_PETTYLOAN_INFO = BASE_URL + "credit_loan/credit_loan_info" + TYPE;
    //获取小额借款月利率、日利率
    String INDEX_PETTYLOAN_INSTERT = BASE_URL + "credit_loan/loan_rate" + TYPE;
    //借贷试算
    String INDEX_PRTTYLOAN_TRIAL = BASE_URL + "credit_loan/loan_compute" + TYPE;
    //获取小额借贷试算结果
    String INDEX_PETTLYLOAN_RESULT = BASE_URL + "credit_loan/loan_compute_result" + TYPE;
    //借款银行卡
    String INDEX_PETTLYLOAN_BANK = BASE_URL + "bank/bank_info" + TYPE;
    //确认借钱
    String INDEX_PETTLYLOAN_lIST = BASE_URL + "credit_loan/loan_submit" + TYPE;
    //获取借款详情
    String INDEX_PETTLYLOAN_ORDER = BASE_URL + "account/loan_info" + TYPE;
    //提前还请借款
    String INDEX_PETTYLOAN_ADVANCE = BASE_URL + "loan/loan_prerepay_info" + TYPE;
    //获取还款详情信息
    String INDEX_PETTLYLOAN_DETALIS = BASE_URL + "loan/loan_repay_info" + TYPE;
    //查询待还金额
    String MINE_UNPAID = BASE_URL + "account/unpaid_amount" + TYPE;
    //判断支付密码是否设置
    String MINE_SETZHIFU_PASSWORD = BASE_URL + "pay/checkPayPasswordSet" + TYPE;
    //新增支付密码
    String MINE_PAY_PASSWORD = BASE_URL + "pay/password" + TYPE;
    //变更支付密码
    String MINE_PAY_AMENDPAYMEMT = BASE_URL + "pay/password_reset" + TYPE;
    //获取设置支付密码的验证码
    String MINE_PAY_FORGETPAYMENT = BASE_URL + "pay/password_sms" + TYPE;
    //找回支付密码
    String MINE_PAY_BANKPASSWORD = BASE_URL + "pay/back_password" + TYPE;
    //获取单个还款中列表
    String INDEX_PETTY_SINGLE = BASE_URL + "loan/repay_list" + TYPE;
    //单个订单单期还款提交
    String INDEX_PETTY_SINGLE_PAY = BASE_URL + "loan/loan_repay_submit" + TYPE;
    //提前还款提交
    String INDEX_PETTY_PREREPAY = BASE_URL + "loan/loan_prerepay_submit" + TYPE;
    //意见反馈cms/suggestion_submit
    String MINE_SUGGESTION_SUBMIT = BASE_URL + "cms/suggestion_submit" + TYPE;
    //全部订单的还款记录loan/all/repay_record
    String MINE_ALL_REPAYLIST = BASE_URL + "loan/all_repay_list" + TYPE;
    //借款记录account/loan_list
    String MINE_ACCOUNT_LIST = BASE_URL + "account/loan_list" + TYPE;
    //获取已还清列表/loan/repaied_list
    String MINE_REPAIED_LIST = BASE_URL + "loan/repaied_list" + TYPE;
    //全部订单的还款记录loan/all/repay_record
    String MINE_ALL_REPAY_RECORD = BASE_URL + "loan/all/repay_record" + TYPE;
    //登录密码修改account/password_update
    String ACCOUNT_PASSWORD = BASE_URL + "account/password_update" + TYPE;
    //获取单个订单的还款记录
    String MINE_REFUNDSINGLE_RECORD = BASE_URL + "loan/repay_record" + TYPE;
}

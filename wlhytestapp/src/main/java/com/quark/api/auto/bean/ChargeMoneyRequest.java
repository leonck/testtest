package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class ChargeMoneyRequest {
    public String url = "/app/CommonManage/chargeMoney";
    public String method = "get";
    private String token;//token
    private String user_type;//用户类型：1-注册用户，2-商家
    private String charge_money_constant_id;//
    private String orders_number;//订单编号
    private String pay_type;//1-支付宝，2-微信，3-银行转账，4-电子卡支付,5-商家寄付月结
    private String app_sign;//app的签名
    private String invoke;//Infer
    private String logistics_id;
    private String pay_account;

    public String getPay_account() {
        return pay_account;
    }

    public void setPay_account(String pay_account) {
        this.pay_account = pay_account;
    }

    public String getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(String logistics_id) {
        this.logistics_id = logistics_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getCharge_money_constant_id() {
        return charge_money_constant_id;
    }

    public void setCharge_money_constant_id(String charge_money_constant_id) {
        this.charge_money_constant_id = charge_money_constant_id;
    }

    public String getOrders_number() {
        return orders_number;
    }

    public void setOrders_number(String orders_number) {
        this.orders_number = orders_number;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getApp_sign() {
        return app_sign;
    }

    public void setApp_sign(String app_sign) {
        this.app_sign = app_sign;
    }

    public String getInvoke() {
        return invoke;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }
}

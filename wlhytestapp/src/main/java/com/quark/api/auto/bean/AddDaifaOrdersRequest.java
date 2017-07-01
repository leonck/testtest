package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-30 14:30:18
 */
public class AddDaifaOrdersRequest {
    public String url = "/app/DaifaOrdersCompanyManage/addDaifaOrders";
    public String method = "get";
    private String token;//token
    private String type;//1-新加波物流，2-国际中国物流
    private String daifa_orders_company_id;//代发订单ID
    private String money;//费用
    private String idcard_record_id;//身份证记录是否国际物流：0-国内，其他-国际（大于0）
    private String idcard_number;//身份证号码
    private String front_card;//身份证正面
    private String back_card;//身份证背面
    private String send_user_address_id;//寄送地址
    private String collect_user_address_id;//收地址
    private String logistics_company_id;//商家公司ID
    private String app_sign;//app的签名
    private String invoke;//Infer

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDaifa_orders_company_id() {
        return daifa_orders_company_id;
    }

    public void setDaifa_orders_company_id(String daifa_orders_company_id) {
        this.daifa_orders_company_id = daifa_orders_company_id;
    }

//    public String getMoney() {
//        return money;
//    }
//
//    public void setMoney(String money) {
//        this.money = money;
//    }

    public String getIdcard_record_id() {
        return idcard_record_id;
    }

    public void setIdcard_record_id(String idcard_record_id) {
        this.idcard_record_id = idcard_record_id;
    }

    public String getIdcard_number() {
        return idcard_number;
    }

    public void setIdcard_number(String idcard_number) {
        this.idcard_number = idcard_number;
    }

    public String getFront_card() {
        return front_card;
    }

    public void setFront_card(String front_card) {
        this.front_card = front_card;
    }

    public String getBack_card() {
        return back_card;
    }

    public void setBack_card(String back_card) {
        this.back_card = back_card;
    }

    public String getSend_user_address_id() {
        return send_user_address_id;
    }

    public void setSend_user_address_id(String send_user_address_id) {
        this.send_user_address_id = send_user_address_id;
    }

    public String getCollect_user_address_id() {
        return collect_user_address_id;
    }

    public void setCollect_user_address_id(String collect_user_address_id) {
        this.collect_user_address_id = collect_user_address_id;
    }

    public String getLogistics_company_id() {
        return logistics_company_id;
    }

    public void setLogistics_company_id(String logistics_company_id) {
        this.logistics_company_id = logistics_company_id;
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

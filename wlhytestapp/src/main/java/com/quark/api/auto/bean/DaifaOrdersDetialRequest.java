package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-23 11:59:33
 */
public class DaifaOrdersDetialRequest {
    public String url = "/app/InspectionDaifaManage/daifaOrdersDetial";
    public String method = "get";
    private String token;//token
    private String daifa_orders_company_id;//商家代发订单ID
    private String app_sign;//app的签名
    private String invoke;//Infer

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setDaifa_orders_company_id(String daifa_orders_company_id) {
        this.daifa_orders_company_id = daifa_orders_company_id;
    }

    public String getDaifa_orders_company_id() {
        return this.daifa_orders_company_id;
    }

    public void setApp_sign(String app_sign) {
        this.app_sign = app_sign;
    }

    public String getApp_sign() {
        return this.app_sign;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getInvoke() {
        return this.invoke;
    }

}

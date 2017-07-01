package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-01 14:58:28
 */
public class SetPayOrdersRequest {
    public String url = "/app/UserTransportLogManage/setPayOrders";
    public String method = "get";
    private String token;//token
    private String order_id;//用户寄件id为send_orders_id 商家代发id为daifa_orders_company_id
    private String set_type;//1-取消订单，2-删除订单
    private String order_type;//1-用户寄件，2-商家代发
    private String app_sign;//app的签名
    private String invoke;//Infer
    private String user_type;//Infer

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSet_type() {
        return set_type;
    }

    public void setSet_type(String set_type) {
        this.set_type = set_type;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
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

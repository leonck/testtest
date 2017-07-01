package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-30 18:14:00
 */
public class PaySendOrdersRequest {
    public String url = "/app/SendOrdersManage/paySendOrders";
    public String method = "get";
    private String token;//token
    private String user_type;//用户类型：1-注册用户，2-商家
    private String send_orders_id;//代发订单ID
    private String trade_password;//电子卡支付必须密码
    private String app_sign;//app的签名
    private String invoke;//Infer
    private String orders_money;

    public String getOrders_money() {
        return orders_money;
    }

    public void setOrders_money(String orders_money) {
        this.orders_money = orders_money;
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

    public String getSend_orders_id() {
        return send_orders_id;
    }

    public void setSend_orders_id(String send_orders_id) {
        this.send_orders_id = send_orders_id;
    }

    public String getTrade_password() {
        return trade_password;
    }

    public void setTrade_password(String trade_password) {
        this.trade_password = trade_password;
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

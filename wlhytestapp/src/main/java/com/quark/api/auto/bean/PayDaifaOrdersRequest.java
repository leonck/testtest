package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-21 17:03:23
 */
public class PayDaifaOrdersRequest {
    public String url = "/app/DaifaOrdersCompanyManage/payDaifaOrders";
    public String method = "get";
    private String token;//token
    private String daifa_orders_company_id;//代发订单ID
    private String pay_type;//支付方式：1-支付宝，2-微信，3-银行转账，4-电子卡支付
    private String trade_password;//电子卡支付必须密码
    private String app_sign;//app的签名
    private String invoke;//Infer
    private String pay_account;

    public String getPay_account() {
        return pay_account;
    }

    public void setPay_account(String pay_account) {
        this.pay_account = pay_account;
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

    public String getDaifa_orders_company_id() {
        return daifa_orders_company_id;
    }

    public void setDaifa_orders_company_id(String daifa_orders_company_id) {
        this.daifa_orders_company_id = daifa_orders_company_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
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

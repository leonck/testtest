package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-02-09 10:45:38
 */
public class DeleteDaifaOrderRequest {
    public String url = "/app/UserTransportLogManage/deleteDaifaOrder";
    public String method = "get";
    private String token;//token
    private String user_type;//1-注册用户，2-商家
    private String daifa_orders_company_id;//商家代发订单ID
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public void setDaifa_orders_company_id(String daifa_orders_company_id) {
        this.daifa_orders_company_id = daifa_orders_company_id;
    }

    public String getDaifa_orders_company_id() {
        return this.daifa_orders_company_id;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getInvoke() {
        return this.invoke;
    }

}

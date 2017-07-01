package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-03-14 16:56:57
 */
public class AddFollowLogisticsRequest {
    public String url = "/app/FollowLogisticsManage/addFollowLogistics";
    public String method = "get";
    private String token;//token
    private String user_type;//用户类型：1-注册用户，2-商家
    private String send_type;//寄送类型：1-上门取件，2-物流代发
    private String orders_package_id;//包裹快递ID
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

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_type() {
        return this.user_type;
    }

    public void setSend_type(String send_type) {
        this.send_type = send_type;
    }

    public String getSend_type() {
        return this.send_type;
    }

    public void setOrders_package_id(String orders_package_id) {
        this.orders_package_id = orders_package_id;
    }

    public String getOrders_package_id() {
        return this.orders_package_id;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getInvoke() {
        return this.invoke;
    }

}

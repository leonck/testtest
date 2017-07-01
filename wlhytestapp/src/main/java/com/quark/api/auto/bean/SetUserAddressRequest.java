package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:41
 */
public class SetUserAddressRequest {
    public String url = "/app/UserAddresss/setUserAddress";
    public String method = "get";
    private String token;//token
    private String user_address_id;//
    private String user_type;//用户类型：1-注册，2-商家
    private String set_type;//1-设置默认地址，2-删除地址
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

    public void setUser_address_id(String user_address_id) {
        this.user_address_id = user_address_id;
    }

    public String getUser_address_id() {
        return this.user_address_id;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_type() {
        return this.user_type;
    }

    public void setSet_type(String set_type) {
        this.set_type = set_type;
    }

    public String getSet_type() {
        return this.set_type;
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

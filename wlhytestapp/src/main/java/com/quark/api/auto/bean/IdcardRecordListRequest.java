package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-29 11:31:34
 */
public class IdcardRecordListRequest {
    public String url = "/app/CommonManage/idcardRecordList";
    public String method = "get";
    private String token;//token
    private String user_type;//用户类型：1-注册用户，2-商家
    private String app_sign;//app的签名
    private String invoke;//Infer

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
}

package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class FeedBackRequest {
    public String url = "/app/CommonManage/feedBack";
    public String method = "get";
    private String token;//token
    private String user_type;//1-用户，2-商家
    private String content;//意见
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

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_type() {
        return this.user_type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
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

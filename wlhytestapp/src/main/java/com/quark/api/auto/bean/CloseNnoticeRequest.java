package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-03-08 11:20:53
 */
public class CloseNnoticeRequest {
    public String url = "/app/CommonManage/closeNnotice";
    public String method = "get";
    private String token;//token
    private String notices_id;//
    private String user_type;//用户类型：1-用户，2-商家
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

    public void setNotices_id(String notices_id) {
        this.notices_id = notices_id;
    }

    public String getNotices_id() {
        return this.notices_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getInvoke() {
        return this.invoke;
    }

}

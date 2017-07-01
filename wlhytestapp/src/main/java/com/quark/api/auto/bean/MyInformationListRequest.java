package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-28 09:43:49
 */
public class MyInformationListRequest {
    public String url = "/app/Home/myInformationList";
    public String method = "get";
    private String token;//token
    private String user_type;//用户类型：1-注册用户，2-商家
    private int pn;//Infer
    private int page_size;//Infer
    private String invoke;//Infer

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

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public String getInvoke() {
        return invoke;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }
}

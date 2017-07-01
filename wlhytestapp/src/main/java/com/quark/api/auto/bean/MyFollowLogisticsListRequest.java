package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-03-14 17:15:43
 */
public class MyFollowLogisticsListRequest {
    public String url = "/app/FollowLogisticsManage/myFollowLogisticsList";
    public String method = "get";
    private String token;//token
    private String user_type;//用户类型：1-注册用户，2-商家
    private int pn;//Infer
    private int page_size;//Infer
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

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getInvoke() {
        return this.invoke;
    }

}

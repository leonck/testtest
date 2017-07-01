package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-03-15 09:10:31
 */
public class DeleteFollowLogisticsRequest {
    public String url = "/app/FollowLogisticsManage/deleteFollowLogistics";
    public String method = "get";
    private String token;//token
    private String user_type;//用户类型：1-注册用户，2-商家
    private String follow_logistics_id;//
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

    public void setFollow_logistics_id(String follow_logistics_id) {
        this.follow_logistics_id = follow_logistics_id;
    }

    public String getFollow_logistics_id() {
        return this.follow_logistics_id;
    }

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getInvoke() {
        return this.invoke;
    }

}

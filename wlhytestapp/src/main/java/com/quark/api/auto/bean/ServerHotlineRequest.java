package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class ServerHotlineRequest {
    public String url = "/app/CommonManage/serverHotline";
    public String method = "get";
    public String kw;
    private String invoke;//Infer
    private String city;
    private String area;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

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

    public void setInvoke(String invoke) {
        this.invoke = invoke;
    }

    public String getInvoke() {
        return this.invoke;
    }

}

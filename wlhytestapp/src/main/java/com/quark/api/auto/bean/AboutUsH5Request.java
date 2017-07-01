package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class AboutUsH5Request {
    public String url = "/app/CommonManage/aboutUsH5";
    public String method = "get";
    private String type;//1-关于我们，2-公司介绍

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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}

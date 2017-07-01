package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-29 17:38:53
 */
public class LoginDaifaAccountRequest {
    public String url = "/app/DaifaOrdersCompanyManage/loginDaifaAccount";
    public String method = "get";
    private String daifa_type;//1-商家代发，2-物流代发
    private String logistics_number;//ID号
    private String random_password;//密码
    private String app_sign;//app的签名
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

    public String getDaifa_type() {
        return daifa_type;
    }

    public void setDaifa_type(String daifa_type) {
        this.daifa_type = daifa_type;
    }

    public String getLogistics_number() {
        return logistics_number;
    }

    public void setLogistics_number(String logistics_number) {
        this.logistics_number = logistics_number;
    }

    public String getRandom_password() {
        return random_password;
    }

    public void setRandom_password(String random_password) {
        this.random_password = random_password;
    }

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
}

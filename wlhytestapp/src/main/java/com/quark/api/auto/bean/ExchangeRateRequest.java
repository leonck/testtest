package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-01-19 17:23:42
 */
public class ExchangeRateRequest {
    public String url = "/app/CommonManage/exchangeRate";
    public String method = "get";
    private String invoke;//Infer
    private String user_type; //用户类型：1-用户充值，2-商家代发，3-物流代发
    private String company_id;//对应类型ID：用户充值为0，商家代发时为商家id,，，

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
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

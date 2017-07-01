package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-25 18:01:28
 */
public class LogisticsSendGoodsListRequest {
    public String url = "/app/SendOrdersManage/logisticsSendGoodsList";
    public String method = "get";
    private String kw;//
    private String logistics_id;//物流企业id
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

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(String logistics_id) {
        this.logistics_id = logistics_id;
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

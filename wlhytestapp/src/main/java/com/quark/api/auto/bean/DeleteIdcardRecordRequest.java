package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-29 11:31:34
 */
public class DeleteIdcardRecordRequest {
    public String url = "/app/CommonManage/deleteIdcardRecord";
    public String method = "get";
    private String idcard_record_id;//
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

    public void setIdcard_record_id(String idcard_record_id) {
        this.idcard_record_id = idcard_record_id;
    }

    public String getIdcard_record_id() {
        return this.idcard_record_id;
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

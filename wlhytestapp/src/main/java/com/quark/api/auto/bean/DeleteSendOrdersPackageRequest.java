package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:41
 */
public class DeleteSendOrdersPackageRequest {
    public String url = "/app/SendOrdersManage/deleteSendOrdersPackage";
    public String method = "get";
    private String send_orders_package_id;//寄送包裹快递ID
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

    public void setSend_orders_package_id(String send_orders_package_id) {
        this.send_orders_package_id = send_orders_package_id;
    }

    public String getSend_orders_package_id() {
        return this.send_orders_package_id;
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

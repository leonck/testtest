package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-30 09:07:14
 */
public class FirstDaifaOrdersPremissRequest {
    public String url = "/app/DaifaOrdersCompanyManage/firstDaifaOrdersPremiss";
    public String method = "get";
    private String token;//token
    private String product_ids_nums;//购买多商品拼接{商品ID‘A’商品数量#商品ID‘A’商品数量}如：1A3#2A12{商品id=1购买了3个，商品id=2购买了12个}
    private String money;//费用
    private String send_type;//1-正常买卖，2-物流代发
    private String logistics_id;//正常购买时id=0，物流代发Id{只需一个值}
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProduct_ids_nums() {
        return product_ids_nums;
    }

    public void setProduct_ids_nums(String product_ids_nums) {
        this.product_ids_nums = product_ids_nums;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSend_type() {
        return send_type;
    }

    public void setSend_type(String send_type) {
        this.send_type = send_type;
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

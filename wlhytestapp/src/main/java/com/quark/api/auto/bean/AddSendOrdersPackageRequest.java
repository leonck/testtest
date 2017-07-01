package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-28 12:01:54
 */
public class AddSendOrdersPackageRequest {
    public String url = "/app/SendOrdersManage/addSendOrdersPackage";
    public String method = "get";
    private String token;//token
    private String send_orders_id;//寄送包裹快递ID
    private String send_orders_package_id;
    private String user_type;//用户类型：1-注册用户，2-商家
    private String type;//1-新加波物流，2-国际中国物流
    private String idcard_record_id;//身份证记录是否国际物流：0-国内，其他-国际（大于0）
    private String idcard_number;//身份证号码
    private String front_card;//身份证正面
    private String back_card;//身份证背面
    private String send_user_address_id;//寄送地址
    private String collect_user_address_id;//收地址
    private String home_start_time;//上门开始时间
    private String home_end_time;//上门结束时间
    private String logistics_id;//物流公司ID
    private String logistics_name;//物流公司名称
    private String waybill_number;//运单号/快递单号/物流单号
    private String remarker;//备注
    private String package_goods;//多物品拼接{物品名称‘A’数量#物品名称‘A’数量}如：哈哈A3#2A12{物品名称=哈哈-选择了哈哈，数量=3}
    private String declared_weight;//申报重量-kg
    private String goods_value;//物品货值-元
    private String app_sign;//app的签名
    private String invoke;//Infer

    public String getSend_orders_package_id() {
        return send_orders_package_id;
    }

    public void setSend_orders_package_id(String send_orders_package_id) {
        this.send_orders_package_id = send_orders_package_id;
    }

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

    public String getSend_orders_id() {
        return send_orders_id;
    }

    public void setSend_orders_id(String send_orders_id) {
        this.send_orders_id = send_orders_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdcard_record_id() {
        return idcard_record_id;
    }

    public void setIdcard_record_id(String idcard_record_id) {
        this.idcard_record_id = idcard_record_id;
    }

    public String getIdcard_number() {
        return idcard_number;
    }

    public void setIdcard_number(String idcard_number) {
        this.idcard_number = idcard_number;
    }

    public String getFront_card() {
        return front_card;
    }

    public void setFront_card(String front_card) {
        this.front_card = front_card;
    }

    public String getBack_card() {
        return back_card;
    }

    public void setBack_card(String back_card) {
        this.back_card = back_card;
    }

    public String getSend_user_address_id() {
        return send_user_address_id;
    }

    public void setSend_user_address_id(String send_user_address_id) {
        this.send_user_address_id = send_user_address_id;
    }

    public String getCollect_user_address_id() {
        return collect_user_address_id;
    }

    public void setCollect_user_address_id(String collect_user_address_id) {
        this.collect_user_address_id = collect_user_address_id;
    }

    public String getHome_start_time() {
        return home_start_time;
    }

    public void setHome_start_time(String home_start_time) {
        this.home_start_time = home_start_time;
    }

    public String getHome_end_time() {
        return home_end_time;
    }

    public void setHome_end_time(String home_end_time) {
        this.home_end_time = home_end_time;
    }

    public String getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(String logistics_id) {
        this.logistics_id = logistics_id;
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public String getWaybill_number() {
        return waybill_number;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public String getRemarker() {
        return remarker;
    }

    public void setRemarker(String remarker) {
        this.remarker = remarker;
    }

    public String getPackage_goods() {
        return package_goods;
    }

    public void setPackage_goods(String package_goods) {
        this.package_goods = package_goods;
    }

    public String getDeclared_weight() {
        return declared_weight;
    }

    public void setDeclared_weight(String declared_weight) {
        this.declared_weight = declared_weight;
    }

    public String getGoods_value() {
        return goods_value;
    }

    public void setGoods_value(String goods_value) {
        this.goods_value = goods_value;
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

package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:41
 */
public class AddUserAddressRequest {
    public String url = "/app/UserAddresss/addUserAddress";
    public String method = "get";
    private String token;//token
    private String name;//姓名
    private String telephone;//手机号码
    private String province;//省
    private String city;//市
    private String area;//区
    private String address;//详细地址
    private String latitude;//维度
    private String longitude;//经度
    private String address_type;//地址类型：1-国内，2-国际
    private String user_type;//用户类型：1-注册，2-商家
    private String app_sign;//app的签名
    private String invoke;//Infer
    private String user_address_id;
    private String oper_type;

    public String getUser_address_id() {
        return user_address_id;
    }

    public void setUser_address_id(String user_address_id) {
        this.user_address_id = user_address_id;
    }

    public String getOper_type() {
        return oper_type;
    }

    public void setOper_type(String oper_type) {
        this.oper_type = oper_type;
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

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return this.province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public String getAddress_type() {
        return this.address_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_type() {
        return this.user_type;
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

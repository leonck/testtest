package com.quark.api.auto.bean;

import java.io.Serializable;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:41
 */
public class ListUserAddress implements Serializable {
    //
    public int user_address_id;
    //姓名
    public String name;
    //手机号码
    public String telephone;
    //省
    public String province;
    //市
    public String city;
    //区
    public String area;
    //详细地址
    public String address;
    //地址类型：1-国内，2-国际
    public int address_type;
    //2-默认，1-显示，0-删除
    public int status;

    public void setUser_address_id(int user_address_id) {
        this.user_address_id = user_address_id;
    }

    public int getUser_address_id() {
        return this.user_address_id;
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

    public void setAddress_type(int address_type) {
        this.address_type = address_type;
    }

    public int getAddress_type() {
        return this.address_type;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
}
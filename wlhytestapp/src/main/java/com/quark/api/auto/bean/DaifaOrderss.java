package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-23 11:59:33
 */
public class DaifaOrderss {
    public String send_telephone;
    public String send_address;
    public String orders_number;
    public String collect_telephone;
    public int idcard_record_id;
    public int type;
    public String post_time;
    public String money;
    public String send_name;
    public String collect_name;
    public String collect_address;
    public int daifa_orders_company_id;
    public List<DaifaOrdersGoodsList> DaifaOrdersGoodsList;
    public List<DaifaOrdersPackageList> DaifaOrdersPackageList;

    public String getSend_telephone() {
        return send_telephone;
    }

    public void setSend_telephone(String send_telephone) {
        this.send_telephone = send_telephone;
    }

    public String getSend_address() {
        return send_address;
    }

    public void setSend_address(String send_address) {
        this.send_address = send_address;
    }

    public String getOrders_number() {
        return orders_number;
    }

    public void setOrders_number(String orders_number) {
        this.orders_number = orders_number;
    }

    public String getCollect_telephone() {
        return collect_telephone;
    }

    public void setCollect_telephone(String collect_telephone) {
        this.collect_telephone = collect_telephone;
    }

    public int getIdcard_record_id() {
        return idcard_record_id;
    }

    public void setIdcard_record_id(int idcard_record_id) {
        this.idcard_record_id = idcard_record_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getCollect_name() {
        return collect_name;
    }

    public void setCollect_name(String collect_name) {
        this.collect_name = collect_name;
    }

    public String getCollect_address() {
        return collect_address;
    }

    public void setCollect_address(String collect_address) {
        this.collect_address = collect_address;
    }

    public int getDaifa_orders_company_id() {
        return daifa_orders_company_id;
    }

    public void setDaifa_orders_company_id(int daifa_orders_company_id) {
        this.daifa_orders_company_id = daifa_orders_company_id;
    }

    public List<com.quark.api.auto.bean.DaifaOrdersGoodsList> getDaifaOrdersGoodsList() {
        return DaifaOrdersGoodsList;
    }

    public void setDaifaOrdersGoodsList(List<com.quark.api.auto.bean.DaifaOrdersGoodsList> daifaOrdersGoodsList) {
        DaifaOrdersGoodsList = daifaOrdersGoodsList;
    }

    public List<com.quark.api.auto.bean.DaifaOrdersPackageList> getDaifaOrdersPackageList() {
        return DaifaOrdersPackageList;
    }

    public void setDaifaOrdersPackageList(List<com.quark.api.auto.bean.DaifaOrdersPackageList> daifaOrdersPackageList) {
        DaifaOrdersPackageList = daifaOrdersPackageList;
    }
}
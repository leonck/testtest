package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-01-06 17:31:15
 */
public class ListPaySendOrdersList {
    //包裹数量
    public String package_number;
    //
    public String orders_id;
    //订单号
    public String orders_number;
    //状态消息
    public String status_message;
    //上门取件订单状态
    public String orders_status;
    //
    public String collect_name;
    //
    public String collect_telephone;
    //
    public String collect_address;
    //
    public String pay_type_comfity;
    //
    public String product_number;
    //订单类型：1-上门取件，2-商家代发，3-物流代发
    public String orders_type;
    public String money;
    public String company_id;

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setPackage_number(String package_number) {
        this.package_number = package_number;
    }

    public String getPackage_number() {
        return this.package_number;
    }

    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getOrders_id() {
        return this.orders_id;
    }

    public void setOrders_number(String orders_number) {
        this.orders_number = orders_number;
    }

    public String getOrders_number() {
        return this.orders_number;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public String getStatus_message() {
        return this.status_message;
    }

    public void setOrders_status(String orders_status) {
        this.orders_status = orders_status;
    }

    public String getOrders_status() {
        return this.orders_status;
    }

    public void setCollect_name(String collect_name) {
        this.collect_name = collect_name;
    }

    public String getCollect_name() {
        return this.collect_name;
    }

    public void setCollect_telephone(String collect_telephone) {
        this.collect_telephone = collect_telephone;
    }

    public String getCollect_telephone() {
        return this.collect_telephone;
    }

    public void setCollect_address(String collect_address) {
        this.collect_address = collect_address;
    }

    public String getCollect_address() {
        return this.collect_address;
    }

    public void setPay_type_comfity(String pay_type_comfity) {
        this.pay_type_comfity = pay_type_comfity;
    }

    public String getPay_type_comfity() {
        return this.pay_type_comfity;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public String getProduct_number() {
        return this.product_number;
    }

    public void setOrders_type(String orders_type) {
        this.orders_type = orders_type;
    }

    public String getOrders_type() {
        return this.orders_type;
    }
}
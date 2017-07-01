package com.quark.api.auto.bean;

import java.io.Serializable;

/**
 * Created by pan on 2016/11/28 0028.
 * >#
 * >#
 */
public class GoodsList implements Serializable {

    String goods_name;
    int goods_number;
//    int send_orders_package_goods_id;
    int limit_amount;
    int logistics_send_goods_id;

    public int getLogistics_send_goods_id() {
        return logistics_send_goods_id;
    }

    public void setLogistics_send_goods_id(int logistics_send_goods_id) {
        this.logistics_send_goods_id = logistics_send_goods_id;
    }

    public int getLimit_amount() {
        return limit_amount;
    }

    public void setLimit_amount(int limit_amount) {
        this.limit_amount = limit_amount;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }

//    public int getSend_orders_package_goods_id() {
//        return send_orders_package_goods_id;
//    }
//
//    public void setSend_orders_package_goods_id(int send_orders_package_goods_id) {
//        this.send_orders_package_goods_id = send_orders_package_goods_id;
//    }
}

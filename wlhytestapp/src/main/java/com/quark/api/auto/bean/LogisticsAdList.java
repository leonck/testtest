package com.quark.api.auto.bean;

import java.io.Serializable;

/**
 * Created by pan on 2016/11/28 0028.
 * >#
 * >#
 */
public class LogisticsAdList implements Serializable{

    String wuliu;//物流名称
    String logistics_id;//物流id

    String goods;//拼接物品
    String weight;//重量
    String waybill_number;//运单号
    String goodsNameShow;//显示名称
    double totalMoney;
    String send_orders_package_id;

    public LogisticsAdList(){}

    public LogisticsAdList(String wuliu, String logistics_id,String goods, String weight, String waybill_number,String goodsNameShow,double totalMoney,String send_orders_package_id) {
        this.wuliu = wuliu;
        this.logistics_id = logistics_id;
        this.goods = goods;
        this.weight = weight;
        this.waybill_number = waybill_number;
        this.goodsNameShow = goodsNameShow;
        this.totalMoney = totalMoney;
        this.send_orders_package_id = send_orders_package_id;
    }

    public String getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(String logistics_id) {
        this.logistics_id = logistics_id;
    }

    public String getSend_orders_package_id() {
        return send_orders_package_id;
    }

    public void setSend_orders_package_id(String send_orders_package_id) {
        this.send_orders_package_id = send_orders_package_id;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getGoodsNameShow() {
        return goodsNameShow;
    }

    public void setGoodsNameShow(String goodsNameShow) {
        this.goodsNameShow = goodsNameShow;
    }

    public String getWaybill_number() {
        return waybill_number;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public String getWuliu() {
        return wuliu;
    }

    public void setWuliu(String wuliu) {
        this.wuliu = wuliu;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}

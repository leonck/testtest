package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-23 11:59:33
 */
public class DaifaOrdersPackageGoods {
    //代发包裹名称
    public String goods_name;
    //代发包裹数量
    public int goods_number;
    //代发包裹物品ID
    public int daifa_orders_company_package_goods_id;


    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_name() {
        return this.goods_name;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }

    public int getGoods_number() {
        return this.goods_number;
    }

    public void setDaifa_orders_company_package_goods_id(int daifa_orders_company_package_goods_id) {
        this.daifa_orders_company_package_goods_id = daifa_orders_company_package_goods_id;
    }

    public int getDaifa_orders_company_package_goods_id() {
        return this.daifa_orders_company_package_goods_id;
    }
}
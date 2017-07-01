package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class ListDaifaOrdersCompany {
    //商家代发订单ID
    public int daifa_orders_company_id;
    //订单编号
    public String orders_number;
    //收-姓名
    public String collect_name;
    //收-电话
    public String collect_telephone;
    //1-拆包、2-物流代发---[只有一个、要么是物流代发、要么是拆包]
    public int send_type;

    public void setDaifa_orders_company_id(int daifa_orders_company_id) {
        this.daifa_orders_company_id = daifa_orders_company_id;
    }

    public int getDaifa_orders_company_id() {
        return this.daifa_orders_company_id;
    }

    public void setOrders_number(String orders_number) {
        this.orders_number = orders_number;
    }

    public String getOrders_number() {
        return this.orders_number;
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

    public void setSend_type(int send_type) {
        this.send_type = send_type;
    }

    public int getSend_type() {
        return this.send_type;
    }
}
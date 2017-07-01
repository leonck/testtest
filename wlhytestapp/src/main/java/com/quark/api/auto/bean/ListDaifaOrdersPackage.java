package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-23 11:59:33
 */
public class ListDaifaOrdersPackage {
    //物流公司名称
    public String logistics_name;
    //运单号/快递单号【运单号：没有的时候显示等待分配运单号】
    public String waybill_number;
    //申报重量-kg
    public String declared_weight;
    //代发快递包裹ID
    public int daifa_orders_company_package_id;
    //-1-无效，0-未出单，1-已出单，2-运送中，3-已运达
    public int status;

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public String getLogistics_name() {
        return this.logistics_name;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public String getWaybill_number() {
        return this.waybill_number;
    }

    public void setDeclared_weight(String declared_weight) {
        this.declared_weight = declared_weight;
    }

    public String getDeclared_weight() {
        return this.declared_weight;
    }

    public void setDaifa_orders_company_package_id(int daifa_orders_company_package_id) {
        this.daifa_orders_company_package_id = daifa_orders_company_package_id;
    }

    public int getDaifa_orders_company_package_id() {
        return this.daifa_orders_company_package_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
}
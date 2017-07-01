package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-25 18:01:28
 */
public class DaifaOrdersPackage {
    //代发快递包裹ID
    public int daifa_orders_package_id;
    //商家代发订单ID
    public int daifa_orders_company_id;
    //运单号/快递单号【运单号：没有的时候显示等待分配运单号】
    public String waybill_number;
    //日期
    public String post_time;
    //寄送-地区
    public DaifaOrdersCompany DaifaOrdersCompany;

    public int getDaifa_orders_package_id() {
        return daifa_orders_package_id;
    }

    public void setDaifa_orders_package_id(int daifa_orders_package_id) {
        this.daifa_orders_package_id = daifa_orders_package_id;
    }

    public int getDaifa_orders_company_id() {
        return daifa_orders_company_id;
    }

    public void setDaifa_orders_company_id(int daifa_orders_company_id) {
        this.daifa_orders_company_id = daifa_orders_company_id;
    }

    public String getWaybill_number() {
        return waybill_number;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public com.quark.api.auto.bean.DaifaOrdersCompany getDaifaOrdersCompany() {
        return DaifaOrdersCompany;
    }

    public void setDaifaOrdersCompany(com.quark.api.auto.bean.DaifaOrdersCompany daifaOrdersCompany) {
        DaifaOrdersCompany = daifaOrdersCompany;
    }
}
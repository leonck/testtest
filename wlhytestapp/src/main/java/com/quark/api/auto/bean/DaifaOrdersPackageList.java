package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-23 11:59:33
 *
 */
public class DaifaOrdersPackageList{
    public String logistics_name;
    public String waybill_number;
    public String declared_weight;
    public int daifa_orders_company_package_id;
    public int status;
    public List<ListDaifaOrdersPackageGoodsList> DaifaOrdersPackageGoodsList;

    public String getLogistics_name() {
        return logistics_name;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public String getWaybill_number() {
        return waybill_number;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public String getDeclared_weight() {
        return declared_weight;
    }

    public void setDeclared_weight(String declared_weight) {
        this.declared_weight = declared_weight;
    }

    public int getDaifa_orders_company_package_id() {
        return daifa_orders_company_package_id;
    }

    public void setDaifa_orders_company_package_id(int daifa_orders_company_package_id) {
        this.daifa_orders_company_package_id = daifa_orders_company_package_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ListDaifaOrdersPackageGoodsList> getDaifaOrdersPackageGoodsList() {
        return DaifaOrdersPackageGoodsList;
    }

    public void setDaifaOrdersPackageGoodsList(List<ListDaifaOrdersPackageGoodsList> daifaOrdersPackageGoodsList) {
        DaifaOrdersPackageGoodsList = daifaOrdersPackageGoodsList;
    }
}
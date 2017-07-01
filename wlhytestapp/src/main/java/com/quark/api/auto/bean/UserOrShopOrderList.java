package com.quark.api.auto.bean;

/**
 * Created by pan on 2016/12/1 0001.
 * >#
 * >#
 */
public class UserOrShopOrderList {
    /**
     * status_message :
     * package_number : 4
     * orders_number : 80327754232183
     * send_orders_id : 4
     */

    private String status_message;
    private int package_number;
    private String orders_number;
    private int send_orders_id;
    private String status;

    private String collect_name;
    private String collect_telephone;
    private String collect_address;
    private int daifa_orders_company_id;
    private int pay_type_comfity;
    private int product_number;

    private String order_type;  // 1.用户寄件 2.商家代发

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCollect_address() {
        return collect_address;
    }

    public void setCollect_address(String collect_address) {
        this.collect_address = collect_address;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public int getPackage_number() {
        return package_number;
    }

    public void setPackage_number(int package_number) {
        this.package_number = package_number;
    }

    public String getOrders_number() {
        return orders_number;
    }

    public void setOrders_number(String orders_number) {
        this.orders_number = orders_number;
    }

    public int getSend_orders_id() {
        return send_orders_id;
    }

    public void setSend_orders_id(int send_orders_id) {
        this.send_orders_id = send_orders_id;
    }

    public String getCollect_name() {
        return collect_name;
    }

    public void setCollect_name(String collect_name) {
        this.collect_name = collect_name;
    }

    public String getCollect_telephone() {
        return collect_telephone;
    }

    public void setCollect_telephone(String collect_telephone) {
        this.collect_telephone = collect_telephone;
    }

    public int getDaifa_orders_company_id() {
        return daifa_orders_company_id;
    }

    public void setDaifa_orders_company_id(int daifa_orders_company_id) {
        this.daifa_orders_company_id = daifa_orders_company_id;
    }

    public int getPay_type_comfity() {
        return pay_type_comfity;
    }

    public void setPay_type_comfity(int pay_type_comfity) {
        this.pay_type_comfity = pay_type_comfity;
    }

    public int getProduct_number() {
        return product_number;
    }

    public void setProduct_number(int product_number) {
        this.product_number = product_number;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
}

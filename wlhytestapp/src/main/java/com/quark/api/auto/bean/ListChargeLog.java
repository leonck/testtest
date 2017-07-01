package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class ListChargeLog {
    //交易记录ID
    public int charge_log_id;
    //订单编号
    public String orders_number;
    //订单总价
    public String money;
    //1-充值，2-快递单支付
    public int type;
    //0-删除，1-待管理员确认付款，2-管理员已确认付款
    public int status;
    //
    public String post_time;

    public void setCharge_log_id(int charge_log_id) {
        this.charge_log_id = charge_log_id;
    }

    public int getCharge_log_id() {
        return this.charge_log_id;
    }

    public void setOrders_number(String orders_number) {
        this.orders_number = orders_number;
    }

    public String getOrders_number() {
        return this.orders_number;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoney() {
        return this.money;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getPost_time() {
        return this.post_time;
    }
}
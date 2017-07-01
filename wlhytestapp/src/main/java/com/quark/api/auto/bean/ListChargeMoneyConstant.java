package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class ListChargeMoneyConstant {
    //
    public int charge_money_constant_id;
    //价格
    public int money;

    public void setCharge_money_constant_id(int charge_money_constant_id) {
        this.charge_money_constant_id = charge_money_constant_id;
    }

    public int getCharge_money_constant_id() {
        return this.charge_money_constant_id;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return this.money;
    }
}
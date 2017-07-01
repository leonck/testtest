package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class UserChargeLogListResult {
    //page number
    public ChargeLogList ChargeLogList;

    public com.quark.api.auto.bean.ChargeLogList getChargeLogList() {
        return ChargeLogList;
    }

    public void setChargeLogList(com.quark.api.auto.bean.ChargeLogList chargeLogList) {
        ChargeLogList = chargeLogList;
    }
}
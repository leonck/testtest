package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:42
 */
public class MySendOrdersListResult {
    //page number
    public MySendOrdersList MySendOrdersList;

    public com.quark.api.auto.bean.MySendOrdersList getMySendOrdersList() {
        return MySendOrdersList;
    }

    public void setMySendOrdersList(com.quark.api.auto.bean.MySendOrdersList mySendOrdersList) {
        MySendOrdersList = mySendOrdersList;
    }
}
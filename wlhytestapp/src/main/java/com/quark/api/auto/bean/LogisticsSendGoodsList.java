package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-25 18:01:28
 */
public class LogisticsSendGoodsList {
    //允许寄送的物品ID
    public int logistics_send_goods_id;
    //物品名称
    public String name;
    //限制数量：0-不限制，其他为限制数量【大于0】
    public int limit_amount;

    public void setLogistics_send_goods_id(int logistics_send_goods_id) {
        this.logistics_send_goods_id = logistics_send_goods_id;
    }

    public int getLogistics_send_goods_id() {
        return this.logistics_send_goods_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setLimit_amount(int limit_amount) {
        this.limit_amount = limit_amount;
    }

    public int getLimit_amount() {
        return this.limit_amount;
    }
}
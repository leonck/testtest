package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class ChargeMoneyListResponse {
    //
    public List<ChargeMoneyList> ChargeMoneyList;
    //
    public String orders_number;
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public ChargeMoneyListResponse() {
    }

    public ChargeMoneyListResponse(String json) {
        Map<String, ChargeMoneyListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, ChargeMoneyListResponse>>() {
        });
        this.ChargeMoneyList = map.get("ChargeMoneyListResponse").getChargeMoneyList();
        this.message = map.get("ChargeMoneyListResponse").getMessage();
        this.status = map.get("ChargeMoneyListResponse").getStatus();
        this.code = map.get("ChargeMoneyListResponse").getCode();
        this.orders_number = map.get("ChargeMoneyListResponse").getOrders_number();
    }

    public String getOrders_number() {
        return orders_number;
    }

    public void setOrders_number(String orders_number) {
        this.orders_number = orders_number;
    }

    public List<com.quark.api.auto.bean.ChargeMoneyList> getChargeMoneyList() {
        return ChargeMoneyList;
    }

    public void setChargeMoneyList(List<com.quark.api.auto.bean.ChargeMoneyList> chargeMoneyList) {
        ChargeMoneyList = chargeMoneyList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
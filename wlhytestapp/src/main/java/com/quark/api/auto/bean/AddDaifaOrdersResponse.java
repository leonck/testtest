package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-30 14:30:18
 */
public class AddDaifaOrdersResponse {
    //订单编号
    public String orders_number;
    //
    public String message;
    //1-操作成功，0-生成出错
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public AddDaifaOrdersResponse() {
    }

    public AddDaifaOrdersResponse(String json) {
        Map<String, AddDaifaOrdersResponse> map = JSON.parseObject(json, new TypeReference<Map<String, AddDaifaOrdersResponse>>() {
        });
        this.orders_number = map.get("AddDaifaOrdersResponse").getOrders_number();
        this.message = map.get("AddDaifaOrdersResponse").getMessage();
        this.status = map.get("AddDaifaOrdersResponse").getStatus();
        this.code = map.get("AddDaifaOrdersResponse").getCode();
    }

    public void setOrders_number(String orders_number) {
        this.orders_number = orders_number;
    }

    public String getOrders_number() {
        return this.orders_number;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-30 09:07:14
 */
public class FirstDaifaOrdersPremissResponse {
    //商家代发订单ID
    public String daifa_orders_company_id;
    //
    public String message;
    //1-操作成功，0-生成出错
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public FirstDaifaOrdersPremissResponse() {
    }

    public FirstDaifaOrdersPremissResponse(String json) {
        Map<String, FirstDaifaOrdersPremissResponse> map = JSON.parseObject(json, new TypeReference<Map<String, FirstDaifaOrdersPremissResponse>>() {
        });
        this.daifa_orders_company_id = map.get("FirstDaifaOrdersPremissResponse").getDaifa_orders_company_id();
        this.message = map.get("FirstDaifaOrdersPremissResponse").getMessage();
        this.status = map.get("FirstDaifaOrdersPremissResponse").getStatus();
        this.code = map.get("FirstDaifaOrdersPremissResponse").getCode();
    }

    public void setDaifa_orders_company_id(String daifa_orders_company_id) {
        this.daifa_orders_company_id = daifa_orders_company_id;
    }

    public String getDaifa_orders_company_id() {
        return this.daifa_orders_company_id;
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
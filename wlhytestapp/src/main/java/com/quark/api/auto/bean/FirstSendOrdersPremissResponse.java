package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:41
 */
public class FirstSendOrdersPremissResponse {
    //快递订单ID
    public String send_orders_id;
    //
    public String message;
    //1-操作成功，0-生成出错
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public FirstSendOrdersPremissResponse() {
    }

    public FirstSendOrdersPremissResponse(String json) {
        Map<String, FirstSendOrdersPremissResponse> map = JSON.parseObject(json, new TypeReference<Map<String, FirstSendOrdersPremissResponse>>() {
        });
        this.send_orders_id = map.get("FirstSendOrdersPremissResponse").getSend_orders_id();
        this.message = map.get("FirstSendOrdersPremissResponse").getMessage();
        this.status = map.get("FirstSendOrdersPremissResponse").getStatus();
        this.code = map.get("FirstSendOrdersPremissResponse").getCode();
    }

    public void setSend_orders_id(String send_orders_id) {
        this.send_orders_id = send_orders_id;
    }

    public String getSend_orders_id() {
        return this.send_orders_id;
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
package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-30 18:14:00
 */
public class PaySendOrdersResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public PaySendOrdersResponse() {
    }

    public PaySendOrdersResponse(String json) {
        Map<String, PaySendOrdersResponse> map = JSON.parseObject(json, new TypeReference<Map<String, PaySendOrdersResponse>>() {
        });
        this.message = map.get("PaySendOrdersResponse").getMessage();
        this.status = map.get("PaySendOrdersResponse").getStatus();
        this.code = map.get("PaySendOrdersResponse").getCode();
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
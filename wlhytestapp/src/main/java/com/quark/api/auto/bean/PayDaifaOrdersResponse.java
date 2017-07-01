package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-21 17:03:23
 */
public class PayDaifaOrdersResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public PayDaifaOrdersResponse() {
    }

    public PayDaifaOrdersResponse(String json) {
        Map<String, PayDaifaOrdersResponse> map = JSON.parseObject(json, new TypeReference<Map<String, PayDaifaOrdersResponse>>() {
        });
        this.message = map.get("PayDaifaOrdersResponse").getMessage();
        this.status = map.get("PayDaifaOrdersResponse").getStatus();
        this.code = map.get("PayDaifaOrdersResponse").getCode();
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
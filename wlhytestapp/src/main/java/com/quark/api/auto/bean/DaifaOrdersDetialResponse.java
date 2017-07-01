package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-23 11:59:33
 */
public class DaifaOrdersDetialResponse {
    public DaifaOrdersDetialResult daifaOrdersDetialResult;
    public String message;
    public int status;
    public int code;

    public DaifaOrdersDetialResponse() {
    }

    public DaifaOrdersDetialResponse(String json) {
        Map<String, DaifaOrdersDetialResponse> map = JSON.parseObject(json, new TypeReference<Map<String, DaifaOrdersDetialResponse>>() {
        });
        this.daifaOrdersDetialResult = map.get("DaifaOrdersDetialResponse").getDaifaOrdersDetialResult();
        this.message = map.get("DaifaOrdersDetialResponse").getMessage();
        this.status = map.get("DaifaOrdersDetialResponse").getStatus();
        this.code = map.get("DaifaOrdersDetialResponse").getCode();
    }

    public DaifaOrdersDetialResult getDaifaOrdersDetialResult() {
        return daifaOrdersDetialResult;
    }

    public void setDaifaOrdersDetialResult(DaifaOrdersDetialResult daifaOrdersDetialResult) {
        this.daifaOrdersDetialResult = daifaOrdersDetialResult;
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
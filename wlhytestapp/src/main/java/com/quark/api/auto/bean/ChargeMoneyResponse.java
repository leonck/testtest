package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class ChargeMoneyResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public ChargeMoneyResponse() {
    }

    public ChargeMoneyResponse(String json) {
        Map<String, ChargeMoneyResponse> map = JSON.parseObject(json, new TypeReference<Map<String, ChargeMoneyResponse>>() {
        });
        this.message = map.get("ChargeMoneyResponse").getMessage();
        this.status = map.get("ChargeMoneyResponse").getStatus();
        this.code = map.get("ChargeMoneyResponse").getCode();
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
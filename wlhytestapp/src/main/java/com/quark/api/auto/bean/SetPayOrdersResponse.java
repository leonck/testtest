package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-01 14:58:28
 */
public class SetPayOrdersResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public SetPayOrdersResponse() {
    }

    public SetPayOrdersResponse(String json) {
        Map<String, SetPayOrdersResponse> map = JSON.parseObject(json, new TypeReference<Map<String, SetPayOrdersResponse>>() {
        });
        this.message = map.get("SetPayOrdersResponse").getMessage();
        this.status = map.get("SetPayOrdersResponse").getStatus();
        this.code = map.get("SetPayOrdersResponse").getCode();
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
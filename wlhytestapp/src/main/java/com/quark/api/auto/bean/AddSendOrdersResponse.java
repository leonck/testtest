package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:41
 */
public class AddSendOrdersResponse {
    //
    public String message;
    //1-操作成功，0-生成出错
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public AddSendOrdersResponse() {
    }

    public AddSendOrdersResponse(String json) {
        Map<String, AddSendOrdersResponse> map = JSON.parseObject(json, new TypeReference<Map<String, AddSendOrdersResponse>>() {
        });
        this.message = map.get("AddSendOrdersResponse").getMessage();
        this.status = map.get("AddSendOrdersResponse").getStatus();
        this.code = map.get("AddSendOrdersResponse").getCode();
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
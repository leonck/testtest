package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class UpdateOtherResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public UpdateOtherResponse() {
    }

    public UpdateOtherResponse(String json) {
        Map<String, UpdateOtherResponse> map = JSON.parseObject(json, new TypeReference<Map<String, UpdateOtherResponse>>() {
        });
        this.message = map.get("UpdateOtherResponse").getMessage();
        this.status = map.get("UpdateOtherResponse").getStatus();
        this.code = map.get("UpdateOtherResponse").getCode();
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
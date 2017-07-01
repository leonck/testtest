package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class RegistTelResponse {
    //message
    public String message;
    //token
    public User user;
    //1-操作成功,2-错误信息提示
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public RegistTelResponse() {
    }

    public RegistTelResponse(String json) {
        Map<String, RegistTelResponse> map = JSON.parseObject(json, new TypeReference<Map<String, RegistTelResponse>>() {
        });
        this.message = map.get("RegistTelResponse").getMessage();
        this.user = map.get("RegistTelResponse").getUser();
        this.status = map.get("RegistTelResponse").getStatus();
        this.code = map.get("RegistTelResponse").getCode();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
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
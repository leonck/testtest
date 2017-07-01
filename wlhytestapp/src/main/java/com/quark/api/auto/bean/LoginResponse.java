package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class LoginResponse {
    //
    public User user;
    //服務電話
    public String server_telephone;
    //1-登陆成功,2-错误信息
    public int status;
    //
    public String message;
    //200-正常返回，405-重新登陆
    public int code;

    public LoginResponse() {
    }

    public LoginResponse(String json) {
        Map<String, LoginResponse> map = JSON.parseObject(json, new TypeReference<Map<String, LoginResponse>>() {
        });
        this.user = map.get("LoginResponse").getUser();
        this.server_telephone = map.get("LoginResponse").getServer_telephone();
        this.status = map.get("LoginResponse").getStatus();
        this.message = map.get("LoginResponse").getMessage();
        this.code = map.get("LoginResponse").getCode();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setServer_telephone(String server_telephone) {
        this.server_telephone = server_telephone;
    }

    public String getServer_telephone() {
        return this.server_telephone;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
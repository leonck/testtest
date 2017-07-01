package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-29 17:38:53
 */
public class LoginDaifaAccountResponse {
    //商家id
    public int logistics_company_id;
    //物流企业ID
    public int logistics_id;
    //
    public String message;
    //1-操作成功，0-生成出错
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public LoginDaifaAccountResponse() {
    }

    public LoginDaifaAccountResponse(String json) {
        Map<String, LoginDaifaAccountResponse> map = JSON.parseObject(json, new TypeReference<Map<String, LoginDaifaAccountResponse>>() {
        });
        this.logistics_company_id = map.get("LoginDaifaAccountResponse").getLogistics_company_id();
        this.logistics_id = map.get("LoginDaifaAccountResponse").getLogistics_id();
        this.message = map.get("LoginDaifaAccountResponse").getMessage();
        this.status = map.get("LoginDaifaAccountResponse").getStatus();
        this.code = map.get("LoginDaifaAccountResponse").getCode();
    }

    public void setLogistics_company_id(int logistics_company_id) {
        this.logistics_company_id = logistics_company_id;
    }

    public int getLogistics_company_id() {
        return this.logistics_company_id;
    }

    public void setLogistics_id(int logistics_id) {
        this.logistics_id = logistics_id;
    }

    public int getLogistics_id() {
        return this.logistics_id;
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
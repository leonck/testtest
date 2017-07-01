package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class GetSetCodeResponse {
    //
    public String message;
    //1-获取成功
    public int status;
    //验证码
    public String tel_code;
    //200-正常返回，405-重新登陆
    public String code;

    public GetSetCodeResponse() {
    }

    public GetSetCodeResponse(String json) {
        Map<String, GetSetCodeResponse> map = JSON.parseObject(json, new TypeReference<Map<String, GetSetCodeResponse>>() {
        });
        this.message = map.get("GetSetCodeResponse").getMessage();
        this.status = map.get("GetSetCodeResponse").getStatus();
        this.tel_code = map.get("GetSetCodeResponse").getTel_code();
        this.code = map.get("GetSetCodeResponse").getCode();
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

    public void setTel_code(String tel_code) {
        this.tel_code = tel_code;
    }

    public String getTel_code() {
        return this.tel_code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class GetForgetCodeResponse {
    //
    public String message;
    //1-获取成功(可进行下一步),2-用户不存在
    public int status;
    //200-正常返回，405-重新登陆
    public String code;
    //验证码
    public String tel_code;

    public GetForgetCodeResponse() {
    }

    public GetForgetCodeResponse(String json) {
        Map<String, GetForgetCodeResponse> map = JSON.parseObject(json, new TypeReference<Map<String, GetForgetCodeResponse>>() {
        });
        this.message = map.get("GetForgetCodeResponse").getMessage();
        this.status = map.get("GetForgetCodeResponse").getStatus();
        this.code = map.get("GetForgetCodeResponse").getCode();
        this.tel_code = map.get("GetForgetCodeResponse").getTel_code();
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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setTel_code(String tel_code) {
        this.tel_code = tel_code;
    }

    public String getTel_code() {
        return this.tel_code;
    }
}
package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class SetPasswordResponse {
    //
    public String message;
    //1-操作成功,2-验证码不正确,3-密码长度不合格,请输入不少于6位的数字、字母
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public SetPasswordResponse() {
    }

    public SetPasswordResponse(String json) {
        Map<String, SetPasswordResponse> map = JSON.parseObject(json, new TypeReference<Map<String, SetPasswordResponse>>() {
        });
        this.message = map.get("SetPasswordResponse").getMessage();
        this.status = map.get("SetPasswordResponse").getStatus();
        this.code = map.get("SetPasswordResponse").getCode();
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
package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-01 16:44:25
 */
public class SetTradePasswordResponse {
    //
    public String message;
    //1-操作成功,2-验证码不正确,3-密码长度不合格,请输入不少于6位的数字、字母
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public SetTradePasswordResponse() {
    }

    public SetTradePasswordResponse(String json) {
        Map<String, SetTradePasswordResponse> map = JSON.parseObject(json, new TypeReference<Map<String, SetTradePasswordResponse>>() {
        });
        this.message = map.get("SetTradePasswordResponse").getMessage();
        this.status = map.get("SetTradePasswordResponse").getStatus();
        this.code = map.get("SetTradePasswordResponse").getCode();
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
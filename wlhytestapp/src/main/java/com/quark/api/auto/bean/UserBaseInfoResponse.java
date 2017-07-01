package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class UserBaseInfoResponse {
    //
    public UserBaseInfoResult userBaseInfoResult;
    //
    public String message;
    //1-操作成功，0-失败
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public UserBaseInfoResponse() {
    }

    public UserBaseInfoResponse(String json) {
        Map<String, UserBaseInfoResponse> map = JSON.parseObject(json, new TypeReference<Map<String, UserBaseInfoResponse>>() {
        });
        this.userBaseInfoResult = map.get("UserBaseInfoResponse").getUserBaseInfoResult();
        this.message = map.get("UserBaseInfoResponse").getMessage();
        this.status = map.get("UserBaseInfoResponse").getStatus();
        this.code = map.get("UserBaseInfoResponse").getCode();
    }

    public UserBaseInfoResult getUserBaseInfoResult() {
        return userBaseInfoResult;
    }

    public void setUserBaseInfoResult(UserBaseInfoResult userBaseInfoResult) {
        this.userBaseInfoResult = userBaseInfoResult;
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
package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class UserChargeLogListResponse {
    //page number
    public UserChargeLogListResult userChargeLogListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public UserChargeLogListResponse() {
    }

    public UserChargeLogListResponse(String json) {
        Map<String, UserChargeLogListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, UserChargeLogListResponse>>() {
        });
        this.userChargeLogListResult = map.get("UserChargeLogListResponse").getUserChargeLogListResult();
        this.message = map.get("UserChargeLogListResponse").getMessage();
        this.status = map.get("UserChargeLogListResponse").getStatus();
        this.code = map.get("UserChargeLogListResponse").getCode();
    }

    public UserChargeLogListResult getUserChargeLogListResult() {
        return userChargeLogListResult;
    }

    public void setUserChargeLogListResult(UserChargeLogListResult userChargeLogListResult) {
        this.userChargeLogListResult = userChargeLogListResult;
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
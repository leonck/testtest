package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:42
 */
public class MySendOrdersListResponse {
    //page number
    public MySendOrdersListResult mySendOrdersListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public MySendOrdersListResponse() {
    }

    public MySendOrdersListResponse(String json) {
        Map<String, MySendOrdersListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, MySendOrdersListResponse>>() {
        });
        this.mySendOrdersListResult = map.get("MySendOrdersListResponse").getMySendOrdersListResult();
        this.message = map.get("MySendOrdersListResponse").getMessage();
        this.status = map.get("MySendOrdersListResponse").getStatus();
        this.code = map.get("MySendOrdersListResponse").getCode();
    }

    public MySendOrdersListResult getMySendOrdersListResult() {
        return mySendOrdersListResult;
    }

    public void setMySendOrdersListResult(MySendOrdersListResult mySendOrdersListResult) {
        this.mySendOrdersListResult = mySendOrdersListResult;
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
package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class UserdaifaOrdersListResponse {
    //page number
    public UserdaifaOrdersListResult userdaifaOrdersListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public UserdaifaOrdersListResponse() {
    }

    public UserdaifaOrdersListResponse(String json) {
        Map<String, UserdaifaOrdersListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, UserdaifaOrdersListResponse>>() {
        });
        this.userdaifaOrdersListResult = map.get("UserdaifaOrdersListResponse").getUserdaifaOrdersListResult();
        this.message = map.get("UserdaifaOrdersListResponse").getMessage();
        this.status = map.get("UserdaifaOrdersListResponse").getStatus();
        this.code = map.get("UserdaifaOrdersListResponse").getCode();
    }

    public UserdaifaOrdersListResult getUserdaifaOrdersListResult() {
        return userdaifaOrdersListResult;
    }

    public void setUserdaifaOrdersListResult(UserdaifaOrdersListResult userdaifaOrdersListResult) {
        this.userdaifaOrdersListResult = userdaifaOrdersListResult;
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
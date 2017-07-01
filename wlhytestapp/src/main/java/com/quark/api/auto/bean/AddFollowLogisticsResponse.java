package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-03-14 16:56:57
 */
public class AddFollowLogisticsResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public AddFollowLogisticsResponse() {
    }

    public AddFollowLogisticsResponse(String json) {
        Map<String, AddFollowLogisticsResponse> map = JSON.parseObject(json, new TypeReference<Map<String, AddFollowLogisticsResponse>>() {
        });
        this.message = map.get("AddFollowLogisticsResponse").getMessage();
        this.status = map.get("AddFollowLogisticsResponse").getStatus();
        this.code = map.get("AddFollowLogisticsResponse").getCode();
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
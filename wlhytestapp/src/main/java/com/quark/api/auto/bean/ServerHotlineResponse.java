package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class ServerHotlineResponse {
    //
    public List<LogisticsList> LogisticsList;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public ServerHotlineResponse() {
    }

    public ServerHotlineResponse(String json) {
        Map<String, ServerHotlineResponse> map = JSON.parseObject(json, new TypeReference<Map<String, ServerHotlineResponse>>() {
        });
        this.LogisticsList = map.get("ServerHotlineResponse").getLogisticsList();
        this.message = map.get("ServerHotlineResponse").getMessage();
        this.status = map.get("ServerHotlineResponse").getStatus();
        this.code = map.get("ServerHotlineResponse").getCode();
    }

    public List<com.quark.api.auto.bean.LogisticsList> getLogisticsList() {
        return LogisticsList;
    }

    public void setLogisticsList(List<com.quark.api.auto.bean.LogisticsList> logisticsList) {
        LogisticsList = logisticsList;
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
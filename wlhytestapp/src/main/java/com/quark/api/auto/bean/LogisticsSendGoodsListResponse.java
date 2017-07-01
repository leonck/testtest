package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-25 18:01:28
 */
public class LogisticsSendGoodsListResponse {
    //允许寄送的物品ID
    public List<LogisticsSendGoodsList> LogisticsSendGoodsList;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public LogisticsSendGoodsListResponse() {
    }

    public LogisticsSendGoodsListResponse(String json) {
        Map<String, LogisticsSendGoodsListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, LogisticsSendGoodsListResponse>>() {
        });
        this.LogisticsSendGoodsList = map.get("LogisticsSendGoodsListResponse").getLogisticsSendGoodsList();
        this.message = map.get("LogisticsSendGoodsListResponse").getMessage();
        this.status = map.get("LogisticsSendGoodsListResponse").getStatus();
        this.code = map.get("LogisticsSendGoodsListResponse").getCode();
    }

    public List<com.quark.api.auto.bean.LogisticsSendGoodsList> getLogisticsSendGoodsList() {
        return LogisticsSendGoodsList;
    }

    public void setLogisticsSendGoodsList(List<com.quark.api.auto.bean.LogisticsSendGoodsList> logisticsSendGoodsList) {
        LogisticsSendGoodsList = logisticsSendGoodsList;
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
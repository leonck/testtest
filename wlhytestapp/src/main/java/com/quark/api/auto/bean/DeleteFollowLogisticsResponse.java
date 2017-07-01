package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-03-15 09:10:31
 */
public class DeleteFollowLogisticsResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public DeleteFollowLogisticsResponse() {
    }

    public DeleteFollowLogisticsResponse(String json) {
        Map<String, DeleteFollowLogisticsResponse> map = JSON.parseObject(json, new TypeReference<Map<String, DeleteFollowLogisticsResponse>>() {
        });
        this.message = map.get("DeleteFollowLogisticsResponse").getMessage();
        this.status = map.get("DeleteFollowLogisticsResponse").getStatus();
        this.code = map.get("DeleteFollowLogisticsResponse").getCode();
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
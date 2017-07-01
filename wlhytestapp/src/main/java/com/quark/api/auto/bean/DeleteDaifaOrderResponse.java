package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-02-09 10:45:38
 */
public class DeleteDaifaOrderResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public DeleteDaifaOrderResponse() {
    }

    public DeleteDaifaOrderResponse(String json) {
        Map<String, DeleteDaifaOrderResponse> map = JSON.parseObject(json, new TypeReference<Map<String, DeleteDaifaOrderResponse>>() {
        });
        this.message = map.get("DeleteDaifaOrderResponse").getMessage();
        this.status = map.get("DeleteDaifaOrderResponse").getStatus();
        this.code = map.get("DeleteDaifaOrderResponse").getCode();
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
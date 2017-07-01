package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-29 11:31:34
 */
public class DeleteIdcardRecordResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public DeleteIdcardRecordResponse() {
    }

    public DeleteIdcardRecordResponse(String json) {
        Map<String, DeleteIdcardRecordResponse> map = JSON.parseObject(json, new TypeReference<Map<String, DeleteIdcardRecordResponse>>() {
        });
        this.message = map.get("DeleteIdcardRecordResponse").getMessage();
        this.status = map.get("DeleteIdcardRecordResponse").getStatus();
        this.code = map.get("DeleteIdcardRecordResponse").getCode();
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
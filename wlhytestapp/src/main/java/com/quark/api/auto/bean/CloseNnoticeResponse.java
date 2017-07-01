package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-03-08 11:20:53
 */
public class CloseNnoticeResponse {
    //1-未读，2-已读
    public String is_read;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public CloseNnoticeResponse() {
    }

    public CloseNnoticeResponse(String json) {
        Map<String, CloseNnoticeResponse> map = JSON.parseObject(json, new TypeReference<Map<String, CloseNnoticeResponse>>() {
        });
        this.is_read = map.get("CloseNnoticeResponse").getIs_read();
        this.message = map.get("CloseNnoticeResponse").getMessage();
        this.status = map.get("CloseNnoticeResponse").getStatus();
        this.code = map.get("CloseNnoticeResponse").getCode();
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getIs_read() {
        return this.is_read;
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
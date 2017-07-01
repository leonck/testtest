package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-29 11:31:34
 */
public class UpdateImageResponse {
    //
    public String fileName;
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public UpdateImageResponse() {
    }

    public UpdateImageResponse(String json) {
        Map<String, UpdateImageResponse> map = JSON.parseObject(json, new TypeReference<Map<String, UpdateImageResponse>>() {
        });
        this.fileName = map.get("UpdateImageResponse").getFileName();
        this.message = map.get("UpdateImageResponse").getMessage();
        this.status = map.get("UpdateImageResponse").getStatus();
        this.code = map.get("UpdateImageResponse").getCode();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
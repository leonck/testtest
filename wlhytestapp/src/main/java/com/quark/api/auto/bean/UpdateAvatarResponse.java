package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class UpdateAvatarResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public UpdateAvatarResponse() {
    }

    public UpdateAvatarResponse(String json) {
        Map<String, UpdateAvatarResponse> map = JSON.parseObject(json, new TypeReference<Map<String, UpdateAvatarResponse>>() {
        });
        this.message = map.get("UpdateAvatarResponse").getMessage();
        this.status = map.get("UpdateAvatarResponse").getStatus();
        this.code = map.get("UpdateAvatarResponse").getCode();
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
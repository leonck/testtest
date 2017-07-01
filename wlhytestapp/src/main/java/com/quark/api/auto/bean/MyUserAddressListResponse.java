package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:41
 */
public class MyUserAddressListResponse {
    //page number
    public MyUserAddressListResult myUserAddressListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public MyUserAddressListResponse() {
    }

    public MyUserAddressListResponse(String json) {
        Map<String, MyUserAddressListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, MyUserAddressListResponse>>() {
        });
        this.myUserAddressListResult = map.get("MyUserAddressListResponse").getMyUserAddressListResult();
        this.message = map.get("MyUserAddressListResponse").getMessage();
        this.status = map.get("MyUserAddressListResponse").getStatus();
        this.code = map.get("MyUserAddressListResponse").getCode();
    }

    public MyUserAddressListResult getMyUserAddressListResult() {
        return myUserAddressListResult;
    }

    public void setMyUserAddressListResult(MyUserAddressListResult myUserAddressListResult) {
        this.myUserAddressListResult = myUserAddressListResult;
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
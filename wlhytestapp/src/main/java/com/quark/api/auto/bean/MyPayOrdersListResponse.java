package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:42
 */
public class MyPayOrdersListResponse {
    //快递订单ID
    public MyPayOrdersListResult myPayOrdersListResult;
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public MyPayOrdersListResponse() {
    }

    public MyPayOrdersListResponse(String json) {
        Map<String, MyPayOrdersListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, MyPayOrdersListResponse>>() {
        });
        this.myPayOrdersListResult = map.get("MyPayOrdersListResponse").getMyPayOrdersListResult();
        this.message = map.get("MyPayOrdersListResponse").getMessage();
        this.status = map.get("MyPayOrdersListResponse").getStatus();
        this.code = map.get("MyPayOrdersListResponse").getCode();
    }

    public MyPayOrdersListResult getMyPayOrdersListResult() {
        return myPayOrdersListResult;
    }

    public void setMyPayOrdersListResult(MyPayOrdersListResult myPayOrdersListResult) {
        this.myPayOrdersListResult = myPayOrdersListResult;
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
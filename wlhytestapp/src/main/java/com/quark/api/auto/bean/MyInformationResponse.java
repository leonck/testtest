package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-28 09:43:49
 */
public class MyInformationResponse {
    //page number
    public MyInformationListResult myInformationListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public MyInformationResponse() {
    }

    public MyInformationResponse(String json) {
        Map<String, MyInformationResponse> map = JSON.parseObject(json, new TypeReference<Map<String, MyInformationResponse>>() {
        });
        this.myInformationListResult = map.get("MyInformationResponse").getMyInformationListResult();
        this.message = map.get("MyInformationResponse").getMessage();
        this.status = map.get("MyInformationResponse").getStatus();
        this.code = map.get("MyInformationResponse").getCode();
    }

    public MyInformationListResult getMyInformationListResult() {
        return myInformationListResult;
    }

    public void setMyInformationListResult(MyInformationListResult myInformationListResult) {
        this.myInformationListResult = myInformationListResult;
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
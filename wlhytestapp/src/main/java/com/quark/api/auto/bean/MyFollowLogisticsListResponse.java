package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-03-14 17:15:43
 */
public class MyFollowLogisticsListResponse {
    //page number
    public MyFollowLogisticsListResult myFollowLogisticsListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public MyFollowLogisticsListResponse() {
    }

    public MyFollowLogisticsListResponse(String json) {
        Map<String, MyFollowLogisticsListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, MyFollowLogisticsListResponse>>() {
        });
        this.myFollowLogisticsListResult = map.get("MyFollowLogisticsListResponse").getMyFollowLogisticsListResult();
        this.message = map.get("MyFollowLogisticsListResponse").getMessage();
        this.status = map.get("MyFollowLogisticsListResponse").getStatus();
        this.code = map.get("MyFollowLogisticsListResponse").getCode();
    }

    public MyFollowLogisticsListResult getMyFollowLogisticsListResult() {
        return myFollowLogisticsListResult;
    }

    public void setMyFollowLogisticsListResult(MyFollowLogisticsListResult myFollowLogisticsListResult) {
        this.myFollowLogisticsListResult = myFollowLogisticsListResult;
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
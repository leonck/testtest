package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-26 10:59:54
 */
public class HomeBannerResponse {
    //banner
    public HomeBannerResult homeBannerResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public HomeBannerResponse() {
    }

    public HomeBannerResponse(String json) {
        Map<String, HomeBannerResponse> map = JSON.parseObject(json, new TypeReference<Map<String, HomeBannerResponse>>() {
        });
        this.homeBannerResult = map.get("HomeBannerResponse").getHomeBannerResult();
        this.message = map.get("HomeBannerResponse").getMessage();
        this.status = map.get("HomeBannerResponse").getStatus();
        this.code = map.get("HomeBannerResponse").getCode();
    }

    public HomeBannerResult getHomeBannerResult() {
        return homeBannerResult;
    }

    public void setHomeBannerResult(HomeBannerResult homeBannerResult) {
        this.homeBannerResult = homeBannerResult;
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
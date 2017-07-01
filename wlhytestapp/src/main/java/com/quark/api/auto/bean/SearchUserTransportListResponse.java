package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-25 18:01:29
 */
public class SearchUserTransportListResponse {
    //寄送包裹快递ID
    public SearchUserTransportListResult searchUserTransportListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public SearchUserTransportListResponse() {
    }

    public SearchUserTransportListResponse(String json) {
        Map<String, SearchUserTransportListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, SearchUserTransportListResponse>>() {
        });
        this.searchUserTransportListResult = map.get("SearchUserTransportListResponse").getSearchUserTransportListResult();
        this.message = map.get("SearchUserTransportListResponse").getMessage();
        this.status = map.get("SearchUserTransportListResponse").getStatus();
        this.code = map.get("SearchUserTransportListResponse").getCode();
    }

    public SearchUserTransportListResult getSearchUserTransportListResult() {
        return searchUserTransportListResult;
    }

    public void setSearchUserTransportListResult(SearchUserTransportListResult searchUserTransportListResult) {
        this.searchUserTransportListResult = searchUserTransportListResult;
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
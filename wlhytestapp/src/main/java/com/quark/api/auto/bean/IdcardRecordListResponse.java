package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-29 11:31:34
 */
public class IdcardRecordListResponse {
    //
    public List<IdcardRecordList> IdcardRecordList;
    //
    public String message;
    //1-操作成功，0-生成出错
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public IdcardRecordListResponse() {
    }

    public IdcardRecordListResponse(String json) {
        Map<String, IdcardRecordListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, IdcardRecordListResponse>>() {
        });
        this.IdcardRecordList = map.get("IdcardRecordListResponse").getIdcardRecordList();
        this.message = map.get("IdcardRecordListResponse").getMessage();
        this.status = map.get("IdcardRecordListResponse").getStatus();
        this.code = map.get("IdcardRecordListResponse").getCode();
    }

    public List<com.quark.api.auto.bean.IdcardRecordList> getIdcardRecordList() {
        return IdcardRecordList;
    }

    public void setIdcardRecordList(List<com.quark.api.auto.bean.IdcardRecordList> idcardRecordList) {
        IdcardRecordList = idcardRecordList;
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
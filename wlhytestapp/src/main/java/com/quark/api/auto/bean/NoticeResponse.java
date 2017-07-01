package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class NoticeResponse {
    //
    public Notices Notices;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;
    String is_read;//. 	1-未读，2-已读

    public NoticeResponse() {
    }

    public NoticeResponse(String json) {
        Map<String, NoticeResponse> map = JSON.parseObject(json, new TypeReference<Map<String, NoticeResponse>>() {
        });
        this.Notices = map.get("NoticeResponse").getNotices();
        this.message = map.get("NoticeResponse").getMessage();
        this.status = map.get("NoticeResponse").getStatus();
        this.code = map.get("NoticeResponse").getCode();
        this.is_read = map.get("NoticeResponse").getIs_read();
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public com.quark.api.auto.bean.Notices getNotices() {
        return Notices;
    }

    public void setNotices(com.quark.api.auto.bean.Notices notices) {
        Notices = notices;
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
package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-01-05 13:54:50
 */
public class SendOrdersPackageInfoResponse {
    //物流公司名称
    public SendOrdersPackageEdit SendOrdersPackage;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public SendOrdersPackageInfoResponse() {
    }

    public SendOrdersPackageInfoResponse(String json) {
        Map<String, SendOrdersPackageInfoResponse> map = JSON.parseObject(json, new TypeReference<Map<String, SendOrdersPackageInfoResponse>>() {
        });
        this.SendOrdersPackage = map.get("SendOrdersPackageInfoResponse").getSendOrdersPackage();
        this.message = map.get("SendOrdersPackageInfoResponse").getMessage();
        this.status = map.get("SendOrdersPackageInfoResponse").getStatus();
        this.code = map.get("SendOrdersPackageInfoResponse").getCode();
    }

    public SendOrdersPackageEdit getSendOrdersPackage() {
        return SendOrdersPackage;
    }

    public void setSendOrdersPackage(SendOrdersPackageEdit sendOrdersPackage) {
        SendOrdersPackage = sendOrdersPackage;
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
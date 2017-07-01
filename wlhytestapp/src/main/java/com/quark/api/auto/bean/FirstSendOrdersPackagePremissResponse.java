package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:41
 */
public class FirstSendOrdersPackagePremissResponse {
    //寄送包裹快递ID
    public String send_orders_package_id;
    //
    public String message;
    //1-操作成功，0-生成出错
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public FirstSendOrdersPackagePremissResponse() {
    }

    public FirstSendOrdersPackagePremissResponse(String json) {
        Map<String, FirstSendOrdersPackagePremissResponse> map = JSON.parseObject(json, new TypeReference<Map<String, FirstSendOrdersPackagePremissResponse>>() {
        });
        this.send_orders_package_id = map.get("FirstSendOrdersPackagePremissResponse").getSend_orders_package_id();
        this.message = map.get("FirstSendOrdersPackagePremissResponse").getMessage();
        this.status = map.get("FirstSendOrdersPackagePremissResponse").getStatus();
        this.code = map.get("FirstSendOrdersPackagePremissResponse").getCode();
    }

    public void setSend_orders_package_id(String send_orders_package_id) {
        this.send_orders_package_id = send_orders_package_id;
    }

    public String getSend_orders_package_id() {
        return this.send_orders_package_id;
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
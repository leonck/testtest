package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-28 12:01:54
 */
public class AddSendOrdersPackageResponse {
    //
    public String message;
    //1-操作成功，2-其他错误信息
    public int status;
    //200-正常返回，405-重新登陆
    public int code;
    public String send_orders_package_id;
    public AddSendOrdersPackageResponse() {
    }

    public AddSendOrdersPackageResponse(String json) {
        Map<String, AddSendOrdersPackageResponse> map = JSON.parseObject(json, new TypeReference<Map<String, AddSendOrdersPackageResponse>>() {
        });
        this.message = map.get("AddSendOrdersPackageResponse").getMessage();
        this.status = map.get("AddSendOrdersPackageResponse").getStatus();
        this.code = map.get("AddSendOrdersPackageResponse").getCode();
        this.send_orders_package_id = map.get("AddSendOrdersPackageResponse").getSend_orders_package_id();
    }

    public String getSend_orders_package_id() {
        return send_orders_package_id;
    }

    public void setSend_orders_package_id(String send_orders_package_id) {
        this.send_orders_package_id = send_orders_package_id;
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
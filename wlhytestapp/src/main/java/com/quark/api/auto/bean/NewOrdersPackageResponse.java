package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-14 17:46:40
 */
public class NewOrdersPackageResponse {
    //寄送包裹快递ID
    public SendOrdersPackage SendOrdersPackage;
    //代发快递包裹ID
    public DaifaOrdersPackage DaifaOrdersPackage;
    //1-上门取件,2-商家代发
    public int is_send;
    //
    public String message;
    //1-操作成功,2-无快递
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public String waybill_number;

    public String getWaybill_number() {
        return waybill_number;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public NewOrdersPackageResponse() {
    }

    public NewOrdersPackageResponse(String json) {
        Map<String, NewOrdersPackageResponse> map = JSON.parseObject(json, new TypeReference<Map<String, NewOrdersPackageResponse>>() {
        });
        this.SendOrdersPackage = map.get("NewOrdersPackageResponse").getSendOrdersPackage();
        this.DaifaOrdersPackage = map.get("NewOrdersPackageResponse").getDaifaOrdersPackage();
        this.is_send = map.get("NewOrdersPackageResponse").getIs_send();
        this.message = map.get("NewOrdersPackageResponse").getMessage();
        this.status = map.get("NewOrdersPackageResponse").getStatus();
        this.code = map.get("NewOrdersPackageResponse").getCode();
    }

    public com.quark.api.auto.bean.SendOrdersPackage getSendOrdersPackage() {
        return SendOrdersPackage;
    }

    public void setSendOrdersPackage(com.quark.api.auto.bean.SendOrdersPackage sendOrdersPackage) {
        SendOrdersPackage = sendOrdersPackage;
    }

    public com.quark.api.auto.bean.DaifaOrdersPackage getDaifaOrdersPackage() {
        return DaifaOrdersPackage;
    }

    public void setDaifaOrdersPackage(com.quark.api.auto.bean.DaifaOrdersPackage daifaOrdersPackage) {
        DaifaOrdersPackage = daifaOrdersPackage;
    }

    public int getIs_send() {
        return is_send;
    }

    public void setIs_send(int is_send) {
        this.is_send = is_send;
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
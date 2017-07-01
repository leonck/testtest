package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-23 11:59:33
 */
public class SendOrdersInfoResponse {
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;
    SendOrdersPersonInfo sOrdersPackage;
    public String send_type;//2物流代发
    List<SendOrdersPackageInformation> SendOrdersPackageInformation;

    List<SendOrdersPackageInformation>  DaifaOrdersPackageInformation;

    public SendOrdersInfoResponse() {
    }

    public SendOrdersInfoResponse(String json) {
        Map<String, SendOrdersInfoResponse> map = JSON.parseObject(json, new TypeReference<Map<String, SendOrdersInfoResponse>>() {
        });
        this.message = map.get("PackageInfoResponse").getMessage();
        this.status = map.get("PackageInfoResponse").getStatus();
        this.code = map.get("PackageInfoResponse").getCode();
        this.SendOrdersPackageInformation = map.get("PackageInfoResponse").getSendOrdersPackageInformation();
        this.sOrdersPackage = map.get("PackageInfoResponse").getsOrdersPackage();
        this.DaifaOrdersPackageInformation = map.get("PackageInfoResponse").getDaifaOrdersPackageInformation();
        this.send_type = map.get("PackageInfoResponse").getSend_type();
    }

    public String getSend_type() {
        return send_type;
    }

    public void setSend_type(String send_type) {
        this.send_type = send_type;
    }

    public List<com.quark.api.auto.bean.SendOrdersPackageInformation> getDaifaOrdersPackageInformation() {
        return DaifaOrdersPackageInformation;
    }

    public void setDaifaOrdersPackageInformation(List<com.quark.api.auto.bean.SendOrdersPackageInformation> daifaOrdersPackageInformation) {
        DaifaOrdersPackageInformation = daifaOrdersPackageInformation;
    }

    public SendOrdersPersonInfo getsOrdersPackage() {
        return sOrdersPackage;
    }

    public void setsOrdersPackage(SendOrdersPersonInfo sOrdersPackage) {
        this.sOrdersPackage = sOrdersPackage;
    }

    public List<com.quark.api.auto.bean.SendOrdersPackageInformation> getSendOrdersPackageInformation() {
        return SendOrdersPackageInformation;
    }

    public void setSendOrdersPackageInformation(List<com.quark.api.auto.bean.SendOrdersPackageInformation> sendOrdersPackageInformation) {
        SendOrdersPackageInformation = sendOrdersPackageInformation;
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
package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-01-19 15:52:38
 *
 */
public class DaifaOrdersInfoResponse{
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;
    List<SendOrdersPackageInformation> DaifaOrdersPackageInformation;
    SendOrdersPersonInfo sOrdersPackage;
    public DaifaOrdersInfoResponse() {
    }

    public DaifaOrdersInfoResponse(String json) {
        Map<String, DaifaOrdersInfoResponse> map = JSON.parseObject(json, new TypeReference<Map<String, DaifaOrdersInfoResponse>>() {
        });
        this.message = map.get("DaifaOrdersInfoResponse").getMessage();
        this.status = map.get("DaifaOrdersInfoResponse").getStatus();
        this.code = map.get("DaifaOrdersInfoResponse").getCode();
        this.sOrdersPackage = map.get("DaifaOrdersInfoResponse").getsOrdersPackage();
        this.DaifaOrdersPackageInformation = map.get("DaifaOrdersInfoResponse").getDaifaOrdersPackageInformation();
    }

    public List<SendOrdersPackageInformation> getDaifaOrdersPackageInformation() {
        return DaifaOrdersPackageInformation;
    }

    public void setDaifaOrdersPackageInformation(List<SendOrdersPackageInformation> daifaOrdersPackageInformation) {
        DaifaOrdersPackageInformation = daifaOrdersPackageInformation;
    }

    public SendOrdersPersonInfo getsOrdersPackage() {
        return sOrdersPackage;
    }

    public void setsOrdersPackage(SendOrdersPersonInfo sOrdersPackage) {
        this.sOrdersPackage = sOrdersPackage;
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
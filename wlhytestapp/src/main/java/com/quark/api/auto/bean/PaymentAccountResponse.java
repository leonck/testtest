package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-01 16:44:25
 */
public class PaymentAccountResponse {
    //
    public List<PaymentAccountList> PaymentAccountList;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public PaymentAccountResponse() {
    }

    public PaymentAccountResponse(String json) {
        Map<String, PaymentAccountResponse> map = JSON.parseObject(json, new TypeReference<Map<String, PaymentAccountResponse>>() {
        });
        this.PaymentAccountList = map.get("PaymentAccountResponse").getPaymentAccountList();
        this.message = map.get("PaymentAccountResponse").getMessage();
        this.status = map.get("PaymentAccountResponse").getStatus();
        this.code = map.get("PaymentAccountResponse").getCode();
    }

    public List<com.quark.api.auto.bean.PaymentAccountList> getPaymentAccountList() {
        return PaymentAccountList;
    }

    public void setPaymentAccountList(List<com.quark.api.auto.bean.PaymentAccountList> paymentAccountList) {
        PaymentAccountList = paymentAccountList;
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
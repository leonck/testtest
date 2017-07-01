package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-01-03 16:12:25
 */
public class ExpressPriceResponse {
    //包裹单价
    public String unit_price;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;



    public ExpressPriceResponse() {
    }

    public ExpressPriceResponse(String json) {
        Map<String, ExpressPriceResponse> map = JSON.parseObject(json, new TypeReference<Map<String, ExpressPriceResponse>>() {
        });
        this.unit_price = map.get("ExpressPriceResponse").getUnit_price();
        this.message = map.get("ExpressPriceResponse").getMessage();
        this.status = map.get("ExpressPriceResponse").getStatus();
        this.code = map.get("ExpressPriceResponse").getCode();

    }


    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getUnit_price() {
        return this.unit_price;
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
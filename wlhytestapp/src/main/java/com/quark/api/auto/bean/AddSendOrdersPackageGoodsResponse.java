package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:41
 */
public class AddSendOrdersPackageGoodsResponse {
    //寄送包裹物品ID
    public String send_orders_package_goods_id;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public AddSendOrdersPackageGoodsResponse() {
    }

    public AddSendOrdersPackageGoodsResponse(String json) {
        Map<String, AddSendOrdersPackageGoodsResponse> map = JSON.parseObject(json, new TypeReference<Map<String, AddSendOrdersPackageGoodsResponse>>() {
        });
        this.send_orders_package_goods_id = map.get("AddSendOrdersPackageGoodsResponse").getSend_orders_package_goods_id();
        this.message = map.get("AddSendOrdersPackageGoodsResponse").getMessage();
        this.status = map.get("AddSendOrdersPackageGoodsResponse").getStatus();
        this.code = map.get("AddSendOrdersPackageGoodsResponse").getCode();
    }

    public void setSend_orders_package_goods_id(String send_orders_package_goods_id) {
        this.send_orders_package_goods_id = send_orders_package_goods_id;
    }

    public String getSend_orders_package_goods_id() {
        return this.send_orders_package_goods_id;
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
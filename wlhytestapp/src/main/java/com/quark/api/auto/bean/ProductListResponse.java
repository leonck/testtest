package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-30 09:07:14
 */
public class ProductListResponse {
    //page number
    public ProductListResult ProductListResult;
    //
    public String message;
    //1-操作成功
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public ProductListResponse() {
    }

    public ProductListResponse(String json) {
        Map<String, ProductListResponse> map = JSON.parseObject(json, new TypeReference<Map<String, ProductListResponse>>() {
        });
        this.ProductListResult = map.get("ProductListResponse").getProductListResult();
        this.message = map.get("ProductListResponse").getMessage();
        this.status = map.get("ProductListResponse").getStatus();
        this.code = map.get("ProductListResponse").getCode();
    }

    public com.quark.api.auto.bean.ProductListResult getProductListResult() {
        return ProductListResult;
    }

    public void setProductListResult(com.quark.api.auto.bean.ProductListResult productListResult) {
        ProductListResult = productListResult;
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
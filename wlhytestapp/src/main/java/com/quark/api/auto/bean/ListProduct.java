package com.quark.api.auto.bean;

import java.io.Serializable;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-30 09:07:14
 */
public class ListProduct implements Serializable {
    //物流代发Id
    public String logistics_id;
    //
    public int product_id;
    //发布者【物流企业或者商家】
    public int user_id;
    //
    public String images_01;
    //
    public String title;
    //商家价格
    public String company_price;
    //物流企业价格
    public String logistics_price;
    //库存量
    public int stock;
    //1-直售，2-物流代发
    public int send_type;
    public boolean check;

    public int goods_number;//物品个数

    public int getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void setLogistics_id(String logistics_id) {
        this.logistics_id = logistics_id;
    }

    public String getLogistics_id() {
        return this.logistics_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_id() {
        return this.product_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setImages_01(String images_01) {
        this.images_01 = images_01;
    }

    public String getImages_01() {
        return this.images_01;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setCompany_price(String company_price) {
        this.company_price = company_price;
    }

    public String getCompany_price() {
        return this.company_price;
    }

    public void setLogistics_price(String logistics_price) {
        this.logistics_price = logistics_price;
    }

    public String getLogistics_price() {
        return this.logistics_price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return this.stock;
    }

    public void setSend_type(int send_type) {
        this.send_type = send_type;
    }

    public int getSend_type() {
        return this.send_type;
    }

}
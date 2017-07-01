package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class ListLogistics {
    //
    public int logistics_id;
    //logo像
    public String images_01;
    //物流公司名称
    public String name;
    //服务电话#多号码连接
    public String service_telephone;

    public void setLogistics_id(int logistics_id) {
        this.logistics_id = logistics_id;
    }

    public int getLogistics_id() {
        return this.logistics_id;
    }

    public void setImages_01(String images_01) {
        this.images_01 = images_01;
    }

    public String getImages_01() {
        return this.images_01;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setService_telephone(String service_telephone) {
        this.service_telephone = service_telephone;
    }

    public String getService_telephone() {
        return this.service_telephone;
    }
}
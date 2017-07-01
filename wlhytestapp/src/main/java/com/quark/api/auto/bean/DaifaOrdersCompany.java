package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-25 18:01:28
 */
public class DaifaOrdersCompany {
    //寄送-地区
    public String send_area;
    //寄送-姓名
    public String send_name;
    //收-姓名
    public String collect_name;
    //收-地区
    public String collect_area;

    public void setSend_area(String send_area) {
        this.send_area = send_area;
    }

    public String getSend_area() {
        return this.send_area;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getSend_name() {
        return this.send_name;
    }

    public void setCollect_name(String collect_name) {
        this.collect_name = collect_name;
    }

    public String getCollect_name() {
        return this.collect_name;
    }

    public void setCollect_area(String collect_area) {
        this.collect_area = collect_area;
    }

    public String getCollect_area() {
        return this.collect_area;
    }
}
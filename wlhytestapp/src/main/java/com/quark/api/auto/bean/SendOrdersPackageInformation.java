package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-23 11:59:33
 */
public class SendOrdersPackageInformation {
    String send_orders_package_information_id;//29,
    String waybill_number;//String 888888String ,
    String collect_name;//String 火龙果String ,
    String collect_telephone;//String 18565858586String ,
    String send_orders_package_id;//34,
    String content;//String 快递员 芙蓉哥，已经接单，在赶来的路上，联系电话 18565711657String ,
    String post_time;//String 2017-01-05 18:35:11String

    public String getSend_orders_package_information_id() {
        return send_orders_package_information_id;
    }

    public void setSend_orders_package_information_id(String send_orders_package_information_id) {
        this.send_orders_package_information_id = send_orders_package_information_id;
    }

    public String getWaybill_number() {
        return waybill_number;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public String getCollect_name() {
        return collect_name;
    }

    public void setCollect_name(String collect_name) {
        this.collect_name = collect_name;
    }

    public String getCollect_telephone() {
        return collect_telephone;
    }

    public void setCollect_telephone(String collect_telephone) {
        this.collect_telephone = collect_telephone;
    }

    public String getSend_orders_package_id() {
        return send_orders_package_id;
    }

    public void setSend_orders_package_id(String send_orders_package_id) {
        this.send_orders_package_id = send_orders_package_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }
}
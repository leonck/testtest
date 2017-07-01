package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-01-09 17:48:35
 */
public class SendOrdersPackage {
    //物流公司名称
    public String logistics_name;
    //寄送-地址
    public String send_address;
    //备注
    public String remarker;
    //快递订单ID
    public int send_orders_id;
    //上门结束时间
    public String home_end_time;
    //物流公司ID-同一个订单必须同一个物流公司
    public int logistics_id;
    //收-经度
    public String collect_longitude;
    //用户类型：1-注册用户，2-商家
    public int user_type;
    //寄送-经度
    public String send_longitude;
    //收-地址
    public String collect_address;
    //收用户地址ID
    public int collect_user_address_id;
    //寄用户地址ID
    public int send_user_address_id;
    //寄送-电话
    public String send_telephone;
    //收-维度
    public String collect_latitude;
    //收-电话
    public String collect_telephone;
    //寄送包裹快递ID
    public int send_orders_package_id;
    //寄送-地区
    public String send_area;
    //身份证记录是否国际物流：0-国内，其他-国际（大于0）
    public int idcard_record_id;
    //上门开始时间
    public String home_start_time;
    //日期
    public String post_time;
    //收-地区
    public String collect_area;
    //寄送快递用户ID
    public int user_id;
    //运单号/快递单号/物流单号
    public String waybill_number;
    //寄送-姓名
    public String send_name;
    //收-姓名
    public String collect_name;
    //条形码
    public String bar_code;
    //寄送-维度
    public String send_latitude;
    //申报重量-kg
    public String declared_weight;
    //物品货值-元
    public String goods_value;
    //-1无效订单，0-待提交订单，1-待取件，2-运送中，3-已送达，31-删除，4-已失效
    public int status;
    public String sOPinfo;

    public String getsOPinfo() {
        return sOPinfo;
    }

    public void setsOPinfo(String sOPinfo) {
        this.sOPinfo = sOPinfo;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public String getLogistics_name() {
        return this.logistics_name;
    }

    public void setSend_address(String send_address) {
        this.send_address = send_address;
    }

    public String getSend_address() {
        return this.send_address;
    }

    public void setRemarker(String remarker) {
        this.remarker = remarker;
    }

    public String getRemarker() {
        return this.remarker;
    }

    public void setSend_orders_id(int send_orders_id) {
        this.send_orders_id = send_orders_id;
    }

    public int getSend_orders_id() {
        return this.send_orders_id;
    }

    public void setHome_end_time(String home_end_time) {
        this.home_end_time = home_end_time;
    }

    public String getHome_end_time() {
        return this.home_end_time;
    }

    public void setLogistics_id(int logistics_id) {
        this.logistics_id = logistics_id;
    }

    public int getLogistics_id() {
        return this.logistics_id;
    }

    public void setCollect_longitude(String collect_longitude) {
        this.collect_longitude = collect_longitude;
    }

    public String getCollect_longitude() {
        return this.collect_longitude;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getUser_type() {
        return this.user_type;
    }

    public void setSend_longitude(String send_longitude) {
        this.send_longitude = send_longitude;
    }

    public String getSend_longitude() {
        return this.send_longitude;
    }

    public void setCollect_address(String collect_address) {
        this.collect_address = collect_address;
    }

    public String getCollect_address() {
        return this.collect_address;
    }

    public void setCollect_user_address_id(int collect_user_address_id) {
        this.collect_user_address_id = collect_user_address_id;
    }

    public int getCollect_user_address_id() {
        return this.collect_user_address_id;
    }

    public void setSend_user_address_id(int send_user_address_id) {
        this.send_user_address_id = send_user_address_id;
    }

    public int getSend_user_address_id() {
        return this.send_user_address_id;
    }

    public void setSend_telephone(String send_telephone) {
        this.send_telephone = send_telephone;
    }

    public String getSend_telephone() {
        return this.send_telephone;
    }

    public void setCollect_latitude(String collect_latitude) {
        this.collect_latitude = collect_latitude;
    }

    public String getCollect_latitude() {
        return this.collect_latitude;
    }

    public void setCollect_telephone(String collect_telephone) {
        this.collect_telephone = collect_telephone;
    }

    public String getCollect_telephone() {
        return this.collect_telephone;
    }

    public void setSend_orders_package_id(int send_orders_package_id) {
        this.send_orders_package_id = send_orders_package_id;
    }

    public int getSend_orders_package_id() {
        return this.send_orders_package_id;
    }

    public void setSend_area(String send_area) {
        this.send_area = send_area;
    }

    public String getSend_area() {
        return this.send_area;
    }

    public void setIdcard_record_id(int idcard_record_id) {
        this.idcard_record_id = idcard_record_id;
    }

    public int getIdcard_record_id() {
        return this.idcard_record_id;
    }

    public void setHome_start_time(String home_start_time) {
        this.home_start_time = home_start_time;
    }

    public String getHome_start_time() {
        return this.home_start_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getPost_time() {
        return this.post_time;
    }

    public void setCollect_area(String collect_area) {
        this.collect_area = collect_area;
    }

    public String getCollect_area() {
        return this.collect_area;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public String getWaybill_number() {
        return this.waybill_number;
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

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public String getBar_code() {
        return this.bar_code;
    }

    public void setSend_latitude(String send_latitude) {
        this.send_latitude = send_latitude;
    }

    public String getSend_latitude() {
        return this.send_latitude;
    }

    public void setDeclared_weight(String declared_weight) {
        this.declared_weight = declared_weight;
    }

    public String getDeclared_weight() {
        return this.declared_weight;
    }

    public void setGoods_value(String goods_value) {
        this.goods_value = goods_value;
    }

    public String getGoods_value() {
        return this.goods_value;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
}
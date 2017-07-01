package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:42
 *
 */
public class ListSendOrdersPackage{
   //寄送包裹快递ID
   public int send_orders_package_id;
   //寄送-姓名
   public String send_name;
   //寄送-地址
    public String send_area;
   public String send_address;
   //收-姓名
   public String collect_name;
   //收-地址
    public String collect_area;
   public String collect_address;
   //运单号/快递单号/物流单号
   public String waybill_number;
   //备注
   public String remarker;
   //日期
   public String post_time;
   //物流信息
   public String sOPinfo;
    public String follow_logistics_id; //关注的

    public String getFollow_logistics_id() {
        return follow_logistics_id;
    }

    public void setFollow_logistics_id(String follow_logistics_id) {
        this.follow_logistics_id = follow_logistics_id;
    }

    public String getSend_area() {
        return send_area;
    }

    public void setSend_area(String send_area) {
        this.send_area = send_area;
    }

    public String getCollect_area() {
        return collect_area;
    }

    public void setCollect_area(String collect_area) {
        this.collect_area = collect_area;
    }

    public String getsOPinfo() {
        return sOPinfo;
    }

    public void setsOPinfo(String sOPinfo) {
        this.sOPinfo = sOPinfo;
    }

    public void setSend_orders_package_id(int send_orders_package_id){
     this.send_orders_package_id = send_orders_package_id;
   }
   public int getSend_orders_package_id(){
     return this.send_orders_package_id;
   }
   public void setSend_name(String send_name){
     this.send_name = send_name;
   }
   public String getSend_name(){
     return this.send_name;
   }
   public void setSend_address(String send_address){
     this.send_address = send_address;
   }
   public String getSend_address(){
     return this.send_address;
   }
   public void setCollect_name(String collect_name){
     this.collect_name = collect_name;
   }
   public String getCollect_name(){
     return this.collect_name;
   }
   public void setCollect_address(String collect_address){
     this.collect_address = collect_address;
   }
   public String getCollect_address(){
     return this.collect_address;
   }
   public void setWaybill_number(String waybill_number){
     this.waybill_number = waybill_number;
   }
   public String getWaybill_number(){
     return this.waybill_number;
   }
   public void setRemarker(String remarker){
     this.remarker = remarker;
   }
   public String getRemarker(){
     return this.remarker;
   }
   public void setPost_time(String post_time){
     this.post_time = post_time;
   }
   public String getPost_time(){
     return this.post_time;
   }
   public void setSOPinfo(String sopinfo){
     this.sOPinfo = sopinfo;
   }
   public String getSOPinfo(){
     return this.sOPinfo;
   }
}
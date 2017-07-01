package com.quark.api.auto.bean;
import java.util.ArrayList;
import java.util.List;
import com.quark.api.auto.bean.*;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:42
 *
 */
public class ListDaifaOrdersLogistics{
   //物流代发订单ID
   public int daifa_orders_logistics_id;
   //订单编号
   public String orders_number;
   //收-姓名
   public String collect_name;
   //收-电话
   public String collect_telephone;

   public void setDaifa_orders_logistics_id(int daifa_orders_logistics_id){
     this.daifa_orders_logistics_id = daifa_orders_logistics_id;
   }
   public int getDaifa_orders_logistics_id(){
     return this.daifa_orders_logistics_id;
   }
   public void setOrders_number(String orders_number){
     this.orders_number = orders_number;
   }
   public String getOrders_number(){
     return this.orders_number;
   }
   public void setCollect_name(String collect_name){
     this.collect_name = collect_name;
   }
   public String getCollect_name(){
     return this.collect_name;
   }
   public void setCollect_telephone(String collect_telephone){
     this.collect_telephone = collect_telephone;
   }
   public String getCollect_telephone(){
     return this.collect_telephone;
   }
}
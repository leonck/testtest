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
public class ListSendOrders{
   //快递订单ID
   public int send_orders_id;
   //订单编号
   public String orders_number;
   //包裹数量
   public String package_number;

   public void setSend_orders_id(int send_orders_id){
     this.send_orders_id = send_orders_id;
   }
   public int getSend_orders_id(){
     return this.send_orders_id;
   }
   public void setOrders_number(String orders_number){
     this.orders_number = orders_number;
   }
   public String getOrders_number(){
     return this.orders_number;
   }
   public void setPackage_number(String package_number){
     this.package_number = package_number;
   }
   public String getPackage_number(){
     return this.package_number;
   }
}
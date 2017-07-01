package com.quark.api.auto.bean;
import java.util.ArrayList;
import java.util.List;
import com.quark.api.auto.bean.*;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-23 11:59:33
 *
 */
public class ListDaifaOrdersCompanyGoods{
   //公司发布单价
   public String company_price;
   //购买数量
   public int buy_number;
   //
   public String title;
   //物流发布单价【商家发布不是代发时为0】
   public String logistics_price;
   //代发物品ID
   public int daifa_orders_company_goods_id;

   public void setCompany_price(String company_price){
     this.company_price = company_price;
   }
   public String getCompany_price(){
     return this.company_price;
   }
   public void setBuy_number(int buy_number){
     this.buy_number = buy_number;
   }
   public int getBuy_number(){
     return this.buy_number;
   }
   public void setTitle(String title){
     this.title = title;
   }
   public String getTitle(){
     return this.title;
   }
   public void setLogistics_price(String logistics_price){
     this.logistics_price = logistics_price;
   }
   public String getLogistics_price(){
     return this.logistics_price;
   }
   public void setDaifa_orders_company_goods_id(int daifa_orders_company_goods_id){
     this.daifa_orders_company_goods_id = daifa_orders_company_goods_id;
   }
   public int getDaifa_orders_company_goods_id(){
     return this.daifa_orders_company_goods_id;
   }
}
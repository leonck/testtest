package com.quark.api.auto.bean;
import java.util.ArrayList;
import java.util.List;
import com.quark.api.auto.bean.*;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-26 10:59:54
 *
 */
public class ListhomeBanner{
   //banner
   public int index_banner_id;
   //封面
   public String image_01;
   //PC封面
   public String big_image_01;

   public void setIndex_banner_id(int index_banner_id){
     this.index_banner_id = index_banner_id;
   }
   public int getIndex_banner_id(){
     return this.index_banner_id;
   }
   public void setImage_01(String image_01){
     this.image_01 = image_01;
   }
   public String getImage_01(){
     return this.image_01;
   }
   public void setBig_image_01(String big_image_01){
     this.big_image_01 = big_image_01;
   }
   public String getBig_image_01(){
     return this.big_image_01;
   }
}
package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-29 13:58:28
 *
 */
public class DeleteOrderPackageResponse{
   //
   public String message;
   //1-操作成功
   public int status;
   //200-正常返回，405-重新登陆
   public int code;
    public DeleteOrderPackageResponse() {
    }
    public DeleteOrderPackageResponse(String json) {
      Map<String, DeleteOrderPackageResponse> map = JSON.parseObject(json, new TypeReference<Map<String, DeleteOrderPackageResponse>>() {
      });
      this.message = map.get("DeleteOrderPackageResponse").getMessage();
      this.status = map.get("DeleteOrderPackageResponse").getStatus();
      this.code = map.get("DeleteOrderPackageResponse").getCode();
    }

   public void setMessage(String message){
     this.message = message;
   }
   public String getMessage(){
     return this.message;
   }
   public void setStatus(int status){
     this.status = status;
   }
   public int getStatus(){
     return this.status;
   }
   public void setCode(int code){
     this.code = code;
   }
   public int getCode(){
     return this.code;
   }
}
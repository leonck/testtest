package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-01-19 17:23:42
 *
 */
public class ExchangeRateResponse{
   //汇率
   public String exchange_rate;
   //
   public String message;
   //1-操作成功
   public int status;
   //200-正常返回，405-重新登陆
   public int code;
    public ExchangeRateResponse() {
    }
    public ExchangeRateResponse(String json) {
      Map<String, ExchangeRateResponse> map = JSON.parseObject(json, new TypeReference<Map<String, ExchangeRateResponse>>() {
      });
      this.exchange_rate = map.get("ExchangeRateResponse").getExchange_rate();
      this.message = map.get("ExchangeRateResponse").getMessage();
      this.status = map.get("ExchangeRateResponse").getStatus();
      this.code = map.get("ExchangeRateResponse").getCode();
    }

   public void setExchange_rate(String exchange_rate){
     this.exchange_rate = exchange_rate;
   }
   public String getExchange_rate(){
     return this.exchange_rate;
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
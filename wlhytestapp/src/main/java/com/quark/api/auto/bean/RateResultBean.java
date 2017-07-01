package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class RateResultBean {
    String currencyF;   //CNYString ,
    String currencyF_Name;   //人民币String ,
    String currencyT;   //NZDString ,
    String currencyT_Name;   //新西兰元String ,
    String currencyFD;   //1String ,
    String exchange;   //0.2043String ,
    String result;   //0.2043String ,
    String updateTime;   //2017-01-19 11:33:59String

    public String getCurrencyF() {
        return currencyF;
    }

    public void setCurrencyF(String currencyF) {
        this.currencyF = currencyF;
    }

    public String getCurrencyF_Name() {
        return currencyF_Name;
    }

    public void setCurrencyF_Name(String currencyF_Name) {
        this.currencyF_Name = currencyF_Name;
    }

    public String getCurrencyT() {
        return currencyT;
    }

    public void setCurrencyT(String currencyT) {
        this.currencyT = currencyT;
    }

    public String getCurrencyT_Name() {
        return currencyT_Name;
    }

    public void setCurrencyT_Name(String currencyT_Name) {
        this.currencyT_Name = currencyT_Name;
    }

    public String getCurrencyFD() {
        return currencyFD;
    }

    public void setCurrencyFD(String currencyFD) {
        this.currencyFD = currencyFD;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}

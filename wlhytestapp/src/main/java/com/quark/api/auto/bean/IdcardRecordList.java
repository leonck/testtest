package com.quark.api.auto.bean;

import java.io.Serializable;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-29 11:31:34
 */
public class IdcardRecordList implements Serializable{
    //
    public int idcard_record_id;
    //姓名
    public String name;
    //身份证号码
    public String idcard_number;
    //身份证正面
    public String front_card;
    //身份证背面
    public String back_card;

    public void setIdcard_record_id(int idcard_record_id) {
        this.idcard_record_id = idcard_record_id;
    }

    public int getIdcard_record_id() {
        return this.idcard_record_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setIdcard_number(String idcard_number) {
        this.idcard_number = idcard_number;
    }

    public String getIdcard_number() {
        return this.idcard_number;
    }

    public void setFront_card(String front_card) {
        this.front_card = front_card;
    }

    public String getFront_card() {
        return this.front_card;
    }

    public void setBack_card(String back_card) {
        this.back_card = back_card;
    }

    public String getBack_card() {
        return this.back_card;
    }
}
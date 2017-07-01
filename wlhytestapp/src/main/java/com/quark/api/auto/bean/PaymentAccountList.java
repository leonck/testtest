package com.quark.api.auto.bean;

import java.io.Serializable;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-01 16:44:25
 */
public class PaymentAccountList implements Serializable{
    //
    public int payment_account_id;
    //账号
    public String account_number;
    //名称
    public String name;
    //图片
    public String images_01;
    //是否允许用人民币支付：1-不允许，2-允许
    public int is_pay_rmb;
    //1-银行，2-支付宝，3-微信
    public int type;
    public String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPayment_account_id(int payment_account_id) {
        this.payment_account_id = payment_account_id;
    }

    public int getPayment_account_id() {
        return this.payment_account_id;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_number() {
        return this.account_number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setImages_01(String images_01) {
        this.images_01 = images_01;
    }

    public String getImages_01() {
        return this.images_01;
    }

    public void setIs_pay_rmb(int is_pay_rmb) {
        this.is_pay_rmb = is_pay_rmb;
    }

    public int getIs_pay_rmb() {
        return this.is_pay_rmb;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
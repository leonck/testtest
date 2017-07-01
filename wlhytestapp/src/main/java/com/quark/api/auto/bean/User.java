package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class User {

    String token;

    private String balance_money;//余额

    private int user_id;

    private String image_01;

    private String nickname;

    private String history_gift_money;//累计赠送

    private String telephone;

    private int is_set_trade_pwd;//是否设置密码

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBalance_money() {
        return balance_money;
    }

    public void setBalance_money(String balance_money) {
        this.balance_money = balance_money;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImage_01() {
        return image_01;
    }

    public void setImage_01(String image_01) {
        this.image_01 = image_01;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHistory_gift_money() {
        return history_gift_money;
    }

    public void setHistory_gift_money(String history_gift_money) {
        this.history_gift_money = history_gift_money;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getIs_set_trade_pwd() {
        return is_set_trade_pwd;
    }

    public void setIs_set_trade_pwd(int is_set_trade_pwd) {
        this.is_set_trade_pwd = is_set_trade_pwd;
    }
}
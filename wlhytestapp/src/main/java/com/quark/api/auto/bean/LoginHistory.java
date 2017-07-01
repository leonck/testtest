package com.quark.api.auto.bean;

import java.io.Serializable;

/**
 * Created by pan on 2016/11/1 0001.
 * >#
 * >#
 */
public class LoginHistory implements Serializable {

    String history;//
    String password;//

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

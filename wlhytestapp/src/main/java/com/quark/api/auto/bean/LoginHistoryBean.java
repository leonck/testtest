package com.quark.api.auto.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pan on 2016/11/1 0001.
 * >#
 * >#
 */
public class LoginHistoryBean implements Serializable {

    List<LoginHistory> history;//

    public List<LoginHistory> getHistory() {
        return history;
    }

    public void setHistory(List<LoginHistory> history) {
        this.history = history;
    }
}

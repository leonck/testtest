package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class RateResultResponse {
    String reason;
    List<RateResultBean> result;
    String error_code;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<RateResultBean> getResult() {
        return result;
    }

    public void setResult(List<RateResultBean> result) {
        this.result = result;
    }
}

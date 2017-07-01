package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class RateResponse {
    String reason;//":"查询成功",
    RateResult result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RateResult getResult() {
        return result;
    }

    public void setResult(RateResult result) {
        this.result = result;
    }
}

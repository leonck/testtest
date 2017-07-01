package com.utils;

/**
 * @project name: X-Hubsan
 * @class name：com.csk.hbsdrone.utils
 * @class describe:
 * @anthor shengkun.cheng
 * @time 2017/4/5 16:08
 * @change
 * @chang time:
 * @class describe:
 */
public class TestNetwork {

    /**
     * code : 605
     * message : 已经连接到服务器
     * apiversion : 1.0
     * data : null
     */

    private int code;
    private String message;
    private String apiversion;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApiversion() {
        return apiversion;
    }

    public void setApiversion(String apiversion) {
        this.apiversion = apiversion;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

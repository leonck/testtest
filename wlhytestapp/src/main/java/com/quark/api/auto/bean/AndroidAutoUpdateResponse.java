package com.quark.api.auto.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:40
 */
public class AndroidAutoUpdateResponse {
    //版本号
    public String out_versionCode;
    //更新内容
    public String update_text;
    //APK名称：http://Ip:端口/files/image?name=apk_name
    public String apk_name;
    //大小
    public String apk_size;
    //
    public String message;
    //1-不需更新，2-有新版本,3-必须更新
    public int status;
    //200-正常返回，405-重新登陆
    public int code;

    public AndroidAutoUpdateResponse() {
    }

    public AndroidAutoUpdateResponse(String json) {
        Map<String, AndroidAutoUpdateResponse> map = JSON.parseObject(json, new TypeReference<Map<String, AndroidAutoUpdateResponse>>() {
        });
        this.out_versionCode = map.get("AndroidAutoUpdateResponse").getOut_versionCode();
        this.update_text = map.get("AndroidAutoUpdateResponse").getUpdate_text();
        this.apk_name = map.get("AndroidAutoUpdateResponse").getApk_name();
        this.apk_size = map.get("AndroidAutoUpdateResponse").getApk_size();
        this.message = map.get("AndroidAutoUpdateResponse").getMessage();
        this.status = map.get("AndroidAutoUpdateResponse").getStatus();
        this.code = map.get("AndroidAutoUpdateResponse").getCode();
    }

    public String getOut_versionCode() {
        return out_versionCode;
    }

    public void setOut_versionCode(String out_versionCode) {
        this.out_versionCode = out_versionCode;
    }

    public String getUpdate_text() {
        return update_text;
    }

    public void setUpdate_text(String update_text) {
        this.update_text = update_text;
    }

    public String getApk_name() {
        return apk_name;
    }

    public void setApk_name(String apk_name) {
        this.apk_name = apk_name;
    }

    public String getApk_size() {
        return apk_size;
    }

    public void setApk_size(String apk_size) {
        this.apk_size = apk_size;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
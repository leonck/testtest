package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-22 18:05:24
 */
public class NoticeDetailH5Request {
    public String url = "/app/CommonManage/noticeDetailH5";
    public String method = "get";
    private String notices_id;//

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public void setNotices_id(String notices_id) {
        this.notices_id = notices_id;
    }

    public String getNotices_id() {
        return this.notices_id;
    }

}

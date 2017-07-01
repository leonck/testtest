package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-26 10:59:54
 */
public class IndexBannerDetailRequest {
    public String url = "/app/IndexBannerManage/indexBannerDetail";
    public String method = "get";
    private String index_banner_id;//banner

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

    public void setIndex_banner_id(String index_banner_id) {
        this.index_banner_id = index_banner_id;
    }

    public String getIndex_banner_id() {
        return this.index_banner_id;
    }

}

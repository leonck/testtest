package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-29 11:31:34
 */
public class UpdateImageRequest {
    public String url = "/app/SendOrdersManage/updateImage";
    public String method = "post";
    private String images_01;//Infer

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getImages_01() {
        return images_01;
    }

    public void setImages_01(String images_01) {
        this.images_01 = images_01;
    }
}

package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-23 17:49:39
 */
public class Notices {
    //
    public int notices_id;
    //标题
    public String title;

    public void setNotices_id(int notices_id) {
        this.notices_id = notices_id;
    }

    public int getNotices_id() {
        return this.notices_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
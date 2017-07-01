package com.quark.api.auto.bean;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-12-28 09:43:49
 */
public class ListMyInformation {
    //
    public int my_information_id;
    //运单号/快递单号【运单号：没有的时候显示等待分配运单号】
    public String waybill_number;
    //内容
    public String content;
    //时间
    public String post_time;
    //2-已读，1-未读，0-删除
    public int status;

    public void setMy_information_id(int my_information_id) {
        this.my_information_id = my_information_id;
    }

    public int getMy_information_id() {
        return this.my_information_id;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public String getWaybill_number() {
        return this.waybill_number;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getPost_time() {
        return this.post_time;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
}
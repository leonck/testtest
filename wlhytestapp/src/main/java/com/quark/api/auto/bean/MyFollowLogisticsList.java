package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-03-14 17:15:43
 */
public class MyFollowLogisticsList {
    //page number
    public int pageNumber;
    //result amount of this page
    public int pageSize;
    //total page
    public int totalPage;
    //total row
    public int totalRow;
    //快递信息
    public List<ListSendOrdersPackage> list;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public List<ListSendOrdersPackage> getList() {
        return list;
    }

    public void setList(List<ListSendOrdersPackage> list) {
        this.list = list;
    }
}
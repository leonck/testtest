package com.quark.api.auto.bean;
import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:41
 *
 */
public class MyUserAddressList{
   //page number
   public int pageNumber;
   //result amount of this page
   public int pageSize;
   //total page
   public int totalPage;
   //total row
   public int totalRow;
   //
   public List<ListUserAddress> list;

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

    public List<ListUserAddress> getList() {
        return list;
    }

    public void setList(List<ListUserAddress> list) {
        this.list = list;
    }
}
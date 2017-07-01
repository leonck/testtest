package com.quark.api.auto.bean;

import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-25 18:01:29
 */
public class SearchUserTransportListResult {
    //寄送包裹快递ID
    public List<SearchUserTransportList> SearchUserTransportList;

    public List<com.quark.api.auto.bean.SearchUserTransportList> getSearchUserTransportList() {
        return SearchUserTransportList;
    }

    public void setSearchUserTransportList(List<com.quark.api.auto.bean.SearchUserTransportList> searchUserTransportList) {
        SearchUserTransportList = searchUserTransportList;
    }
}
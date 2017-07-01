package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-24 17:58:41
 *
 */
public class MyUserAddressListResult{
   //page number
   public MyUserAddressList MyUserAddressList;

    public com.quark.api.auto.bean.MyUserAddressList getMyUserAddressList() {
        return MyUserAddressList;
    }

    public void setMyUserAddressList(com.quark.api.auto.bean.MyUserAddressList myUserAddressList) {
        MyUserAddressList = myUserAddressList;
    }
}
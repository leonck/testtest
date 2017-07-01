package com.quark.api.auto.bean;
import java.util.List;

/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2017-01-06 17:31:15
 *
 */
public class MyPayOrdersListResult{
   //包裹数量
   public List<ListPaySendOrdersList> PaySendOrdersList;

    public List<ListPaySendOrdersList> getPaySendOrdersList() {
        return PaySendOrdersList;
    }

    public void setPaySendOrdersList(List<ListPaySendOrdersList> paySendOrdersList) {
        PaySendOrdersList = paySendOrdersList;
    }
}
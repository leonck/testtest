package com.quark.wanlihuanyunuser.ui.personal.daifa;

import com.quark.api.auto.bean.ListDaifaOrdersCompany;
import com.quark.wanlihuanyunuser.BasePresenter;
import com.quark.wanlihuanyunuser.BaseView;

import java.util.ArrayList;

class ShopSendExpressContract {

    interface  View extends BaseView<Presenter>{
        void showResult(ArrayList<ListDaifaOrdersCompany> datas);
        void stopRefresh(int size);
        void stopLoard(int size);
        void showLoarding(boolean wait);
        void notifyListView();
    }

    interface Presenter extends BasePresenter{
        void getData(String kw,boolean refresh);
        void toDetail(int position);
        void deleteData(int position);
        void toSearch();
    }

}

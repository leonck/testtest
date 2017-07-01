package com.quark.wanlihuanyunuser.ui.personal.myConcerned;

import com.quark.api.auto.bean.ListSendOrdersPackage;
import com.quark.wanlihuanyunuser.BasePresenter;
import com.quark.wanlihuanyunuser.BaseView;

import java.util.ArrayList;

class ConcernedContract {

    interface  View extends BaseView<Presenter>{
        void showResult(ArrayList<ListSendOrdersPackage> datas);
        void stopRefresh(int size);
        void stopLoard(int size);
        void showLoarding(boolean wait);
        void notifyListView();
    }

    interface Presenter extends BasePresenter{
        void getData(boolean refresh);
        void toDetail(int position);
        void deleteData(int position);
    }

}

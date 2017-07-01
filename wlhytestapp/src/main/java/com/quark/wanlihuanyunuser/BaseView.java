package com.quark.wanlihuanyunuser;

import android.view.View;

/**
 * Created by Administrator on 2017/2/23 0023.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void initViews(View view);
}

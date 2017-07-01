package com.quark.wanlihuanyunuser.mainview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseFragment;

import butterknife.ButterKnife;

public class FragmentTwo extends BaseFragment {
    View twoLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        twoLayout = inflater.inflate(R.layout.fragment_two, container, false);
        ButterKnife.inject(this, twoLayout);

        return twoLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}





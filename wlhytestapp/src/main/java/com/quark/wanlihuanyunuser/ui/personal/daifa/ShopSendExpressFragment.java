package com.quark.wanlihuanyunuser.ui.personal.daifa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.api.auto.bean.ListDaifaOrdersCompany;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.DaiCourierAdapter;
import com.quark.wanlihuanyunuser.base.BaseFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.maxwin.view.XListView;

public class ShopSendExpressFragment extends BaseFragment implements ShopSendExpressContract.View, XListView.IXListViewListener {

    ShopSendExpressContract.Presenter presenter;
    String kw;
    DaiCourierAdapter adapter;

    @InjectView(R.id.xlv)
    XListView xlstv;
    @InjectView(R.id.rightrig)
    ImageView rightrig;
    @InjectView(R.id.right)
    LinearLayout right;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_shopsendcourier, container, false);
        ButterKnife.inject(this, view);

        ((TextView) view.findViewById(R.id.title)).setText("代发快递");
        right.setVisibility(View.VISIBLE);
        rightrig.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.search));
        right.setOnClickListener(righton);
        initViews(view);
        setHasOptionsMenu(true);

        presenter.start();

        return view;
    }

    @OnClick(R.id.left)
    public void bak(View v) {
        getActivity().finish();
    }

    View.OnClickListener righton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.toSearch();
        }
    };

    @Override
    public void setPresenter(ShopSendExpressContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
    }

    @Override
    public void showResult(ArrayList<ListDaifaOrdersCompany> datas) {
        if (adapter == null) {
            myinitlist(datas);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void myinitlist(ArrayList<ListDaifaOrdersCompany> datas) {
        xlstv.setFooterDividersEnabled(false);
        xlstv.setPullLoadEnable(true);
        xlstv.setPullRefreshEnable(true);
        xlstv.setXListViewListener(this);
        adapter = new DaiCourierAdapter(getActivity(), datas, handler);
        xlstv.setAdapter(adapter);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    int position = msg.arg1;
                    presenter.toDetail(position);
                    break;
                case 201:
                    int currentPosition = msg.arg1;
                    presenter.deleteData(currentPosition);
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0) {
            if (resultCode == 101) {
                kw = data.getStringExtra("kw");
                presenter.getData(kw, true);
            }
        }
    }

    @Override
    public void showLoarding(boolean wait) {
        if (wait) {
            showWait(true);
        } else {
            showWait(false);
        }
    }

    @Override
    public void stopLoard(int size) {
        xlstv.setDataSize(size);
        xlstv.stopLoadMore();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stopRefresh(int size) {
        xlstv.setDataSize(size);
        xlstv.stopRefresh();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        presenter.getData(kw, true);
    }

    @Override
    public void onLoadMore() {
        presenter.getData(kw, false);
    }

    @Override
    public void notifyListView() {
        adapter.notifyDataSetChanged();
    }

}

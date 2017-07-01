package com.quark.wanlihuanyunuser.ui.personal.myConcerned;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.api.auto.bean.ListSendOrdersPackage;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.TakeAdapter;
import com.quark.wanlihuanyunuser.base.BaseFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.maxwin.view.XListView;

import static com.quark.wanlihuanyunuser.R.id.xlv;

public class ConcernedFragment extends BaseFragment implements ConcernedContract.View, XListView.IXListViewListener {

    ConcernedContract.Presenter presenter;
    String kw;
    TakeAdapter adapter;

    @InjectView(xlv)
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

        ((TextView) view.findViewById(R.id.title)).setText("关注的快递");
        right.setVisibility(View.GONE);

        initViews(view);
        setHasOptionsMenu(true);

        presenter.start();

        return view;
    }

    @OnClick(R.id.left)
    public void bak(View v) {
        getActivity().finish();
    }

    @Override
    public void setPresenter(ConcernedContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
    }

    @Override
    public void showResult(ArrayList<ListSendOrdersPackage> datas) {
        if (adapter == null) {
            myinitlist(datas);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void myinitlist(ArrayList<ListSendOrdersPackage> datas) {
        xlstv.setFooterDividersEnabled(false);
        xlstv.setPullLoadEnable(true);
        xlstv.setPullRefreshEnable(true);
        xlstv.setXListViewListener(this);
        adapter = new TakeAdapter(getActivity(), datas, handler, 4);
        xlstv.setAdapter(adapter);
        xlstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position - 1;
                presenter.toDetail(position);

            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
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
                presenter.getData(true);
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
        presenter.getData(true);
    }

    @Override
    public void onLoadMore() {
        presenter.getData(false);
    }

    @Override
    public void notifyListView() {
        adapter.notifyDataSetChanged();
    }

}

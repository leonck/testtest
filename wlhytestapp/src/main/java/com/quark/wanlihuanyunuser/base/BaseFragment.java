package com.quark.wanlihuanyunuser.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.interf.BaseFragmentInterface;
import com.quark.wanlihuanyunuser.ui.dialog.DialogControl;
import com.quark.wanlihuanyunuser.ui.widget.WaitDialog;
import com.quark.wanlihuanyunuser.util.ToastUtil;
import com.quark.wanlihuanyunuser.util.ValidateHelper;

/**
 * 碎片基类
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年9月25日 上午11:18:46
 */
public abstract class BaseFragment extends Fragment implements
        View.OnClickListener, BaseFragmentInterface {
    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
    public static int mState = STATE_NONE;

    protected LayoutInflater mInflater;
    View view;
    public AppContext getApplication() {
        return (AppContext) getActivity().getApplication();
    }
    protected WaitDialog dialog;
    protected void showWait(boolean isShow) {
        if (isShow) {
            if (null == dialog) {
                dialog = new WaitDialog(getActivity());
            }
            dialog.show();
        } else {
            if (null != dialog) {
                dialog.dismiss();
            }
        }
    }

    public void setBackButton() {
        LinearLayout back_lay = (LinearLayout) view.findViewById(R.id.left);
        back_lay.setVisibility(View.VISIBLE);
        back_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    public void showToast(String msg) {
        if (ValidateHelper.isEmptyString(msg)) {
            return;
        }

        ToastUtil.showShortToast(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mInflater = inflater;
        view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected int getLayoutId() {
        return 0;
    }

    protected View inflateView(int resId) {
        return this.mInflater.inflate(resId, null);
    }

    public boolean onBackPressed() {
        return false;
    }

    protected void hideWaitDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof DialogControl) {
            ((DialogControl) activity).hideWaitDialog();
        }
    }

    protected ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    protected ProgressDialog showWaitDialog(int resid) {
        FragmentActivity activity = getActivity();
        if (activity instanceof DialogControl) {
            return ((DialogControl) activity).showWaitDialog(resid);
        }
        return null;
    }

    protected ProgressDialog showWaitDialog(String str) {
        FragmentActivity activity = getActivity();
        if (activity instanceof DialogControl) {
            return ((DialogControl) activity).showWaitDialog(str);
        }
        return null;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

}

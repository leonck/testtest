//package com.quark.kuaishi.base;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.quark.kuaishi.R;
//import com.quark.kuaishi.interf.BaseActivityInterface;
//import com.quark.kuaishi.ui.widget.AutoListView;
//import com.quark.kuaishi.ui.widget.WaitDialog;
//import com.quark.kuaishi.util.ToastUtil;
//import com.quark.kuaishi.util.ValidateHelper;
//
//
//public abstract class BaseAutoListActivity extends Activity implements BaseActivityInterface, AutoListView.OnRefreshListener, AutoListView.OnLoadListener {
//    public static final String TAG = "com.quark";
//    protected TextView title;
//    protected Button right;
//
//    protected WaitDialog dialog;
//    public AutoListView lstv;
//    public String param;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        param = (String)getValue4Intent("param");
//        initView();
//        initData();
//    }
//
//
//    @Override
//    public void initView() {
//
//    }
//
//    public void setRightLayout(View.OnClickListener listener) {
//        LinearLayout right_button = (LinearLayout) findViewById(R.id.right);
//        right_button.setVisibility(View.VISIBLE);
//        if (null != listener) {
//            right_button.setOnClickListener(listener);
//        } else {
//            Log.e(TAG, "set right button error! listener is null");
//        }
//    }
//
//    public void setRightImage(int id, View.OnClickListener listener) {
//        if (id > 0) {
//            ImageView right_img = (ImageView) findViewById(R.id.rightrig);
//            LinearLayout right = (LinearLayout) findViewById(R.id.right);
//            right_img.setImageResource(id);
//            right.setVisibility(View.VISIBLE);
//            right_img.setVisibility(View.VISIBLE);
//            if (null != listener) {
//                right.setOnClickListener(listener);
//            } else {
//                Log.e(TAG, "set right button error! listener is null");
//            }
//        } else {
//            Log.e(TAG, "set right button error! name is null");
//        }
//    }
//
//
//    public void setTopTitle(String titlestr) {
//        TextView title = (TextView) findViewById(R.id.title);
//        title.setText(titlestr);
//    }
//
//    public void setBackButton() {
//        LinearLayout back_lay = (LinearLayout) findViewById(R.id.left);
//        back_lay.setVisibility(View.VISIBLE);
//        back_lay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//    public void startActivityByClass(Class clazz) {
//        startActivityByClass(clazz, null);
//    }
//
//    public void startActivityByClass(Class clazz, Bundle bundle) {
//        Intent intent = new Intent();
//        intent.setClass(this, clazz);
//        if (null != bundle) {
//            intent.putExtras(bundle);
//        }
//        startActivity(intent);
//    }
//
//    public Object getValue4Intent(String key) {
//        if (ValidateHelper.isEmptyString(key)) {
//            return null;
//        }
//        Bundle b = getIntent().getExtras();
//        if (null == b) {
//            return null;
//        }
//        return b.get(key);
//    }
//
//    public void showToast(String msg) {
//        if (ValidateHelper.isEmptyString(msg)) {
//            return;
//        }
//
//        ToastUtil.showShortToast(msg);
//    }
//
//    public void showToast(int resid) {
//        ToastUtil.showShortToast(resid);
//    }
//
//    protected void showWait(boolean isShow) {
//        if (isShow) {
//            if (null == dialog) {
//                dialog = new WaitDialog(this);
//            }
//            dialog.show();
//        } else {
//            if (null != dialog) {
//                dialog.dismiss();
//            }
//        }
//    }
//
//    protected void onPostExecute(Void result) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//
//    }
//
//    public void initListView() {
//        lstv = (AutoListView) findViewById(R.id.xlv);
//        lstv.setOnRefreshListener(this);
//        lstv.setOnLoadListener(this);
//
//        Message msg = handlerbase.obtainMessage();
//        msg.what = type;
//        msg.arg1 = 0;
//        handlerbase.sendMessage(msg);
//    }
//
//    public Handler handlerbase = new Handler() {
//        public void handleMessage(Message msg) {
//            int rs = msg.arg1;
//            switch (msg.what) {
//                case AutoListView.REFRESH:
//                    lstv.onRefreshComplete();
//                    break;
//                case AutoListView.LOAD:
//                    lstv.onLoadComplete();
//                    break;
//            }
//            lstv.setResultSize(rs);
//            notifyList();
//        }
//
//        ;
//    };
//
//    public int pn = 1;
//    public int type = AutoListView.REFRESH;
//
//    @Override
//    public void onRefresh() {
//        pn = 1;
//        type = AutoListView.REFRESH;
//        initData();
//    }
//
//    @Override
//    public void onLoad() {
//        type = AutoListView.LOAD;
//        pn++;
//        initData();
//    }
//
//    public void notifyList() {
//
//    }
//}

package com.quark.wanlihuanyunuser.ui.send;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.LoginDaifaAccountRequest;
import com.quark.api.auto.bean.LoginDaifaAccountResponse;
import com.quark.api.auto.bean.LoginHistory;
import com.quark.api.auto.bean.LoginHistoryBean;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.OptionsAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

/**
 * Created by pan on 2016/11/9 0009.
 * >#
 * >#商家代发
 */
public class ShopSendActivity extends BaseActivity implements Handler.Callback {

    @InjectView(R.id.pwd_et)
    EditText pwdEt;
    @InjectView(R.id.close_eye_ibt)
    ImageButton closeEyeIbt;
    @InjectView(R.id.xia_ibt)
    ImageView xiaIbt;
    @InjectView(R.id.xia_rely)
    RelativeLayout xiaRely;
    @InjectView(R.id.user_et)
    EditText userEt;

    //PopupWindow对象
    private PopupWindow selectPopupWindow = null;
    //自定义Adapter
    private OptionsAdapter optionsAdapter = null;
    //下拉框选项数据源
    private ArrayList<LoginHistory> datas = new ArrayList<LoginHistory>();
    private ListView listView;
    //下拉框依附组件
    private RelativeLayout parent;
    //下拉框依附组件宽度，也将作为下拉框的宽度
    private int pwidth;
    //文本框
    private EditText et;
    //下拉箭头图片组件
    private ImageView image;
    //恢复数据源按钮
    private Button button;
    //展示所有下拉选项的ListView
//    private ListView listView = null;
    //用来处理选中或者删除下拉项消息
    private Handler handler;
    //是否初始化完成标志
    private boolean flag = false;

    LoginHistoryBean loginHistory;
    String user;//用户账号
    String psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopsend);
        ButterKnife.inject(this);

        loginHistory = new AppParam().getLoginHistory();
        if (loginHistory != null && loginHistory.getHistory() != null && loginHistory.getHistory().size() > 0) {
            userEt.setText(loginHistory.getHistory().get(0).getHistory());
            pwdEt.setText(loginHistory.getHistory().get(0).getPassword());
        }
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.cancel_tv, R.id.close_eye_ibt, R.id.login_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_tv:
                finish();
                break;
            case R.id.close_eye_ibt:
                switchPwd();
                break;
            case R.id.login_bt:
                if (Check()) {
                    getData();
                }
                break;
        }
    }

    private boolean Check() {
        if (Utils.isEmpty(userEt.getText().toString())) {
            showToast("请输入商家id");
            return false;
        }
        if (Utils.isEmpty(pwdEt.getText().toString())) {
            showToast("请输入商家密码");
            return false;
        }
        return true;
    }

    //密码显示切换
    boolean showpssword = false;

    private void switchPwd() {
        if (!showpssword) {
            // 设置为明文显示
            showpssword = true;
            pwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            closeEyeIbt.setBackground(ContextCompat.getDrawable(this, R.drawable.open_eye));
        } else {
            // 设置为秘闻显示
            showpssword = false;
            pwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            closeEyeIbt.setBackground(ContextCompat.getDrawable(this, R.drawable.close_eye));
        }
        // 切换后将EditText光标置于末尾
        CharSequence charSequence = pwdEt.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    /**
     * 没有在onCreate方法中调用initWedget()，而是在onWindowFocusChanged方法中调用，
     * 是因为initWedget()中需要获取PopupWindow浮动下拉框依附的组件宽度，在onCreate方法中是无法获取到该宽度的
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        while (!flag) {
            initWedget();
            flag = true;
        }
    }

    /**
     * 初始化界面控件
     */
    private void initWedget() {
        //初始化Handler,用来处理消息
        handler = new Handler(ShopSendActivity.this);
        //初始化界面组件
        parent = (RelativeLayout) findViewById(R.id.parent);
        et = (EditText) findViewById(R.id.user_et);
        image = (ImageView) findViewById(R.id.xia_ibt);
        //获取下拉框依附的组件宽度
        int width = parent.getWidth() - Utils.dip2px(this, 60);
        pwidth = width;
        xiaRely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
//                    xiaIbt.setBackground(ContextCompat.getDrawable(ShopSendActivity.this, R.drawable.up));
                    //显示PopupWindow窗口
                    popupWindwShowing();
                }
            }
        });

        //设置点击下拉箭头图片事件，点击弹出PopupWindow浮动下拉框
//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (flag) {
//                    xiaIbt.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.down));
//                    //显示PopupWindow窗口
//                    popupWindwShowing();
//                }
//            }
//        });

        //初始化PopupWindow
        initPopuWindow();

    }

    /**
     * 初始化PopupWindow
     */
    private void initPopuWindow() {
        initDatas();
        //PopupWindow浮动下拉框布局
        View loginwindow = (View) this.getLayoutInflater().inflate(R.layout.options, null);
        listView = (ListView) loginwindow.findViewById(R.id.list);

        //设置自定义Adapter
        optionsAdapter = new OptionsAdapter(this, handler, datas);
        listView.setAdapter(optionsAdapter);

        selectPopupWindow = new PopupWindow(loginwindow, pwidth, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        selectPopupWindow.setOutsideTouchable(true);
//        selectPopupWindow.showAsDropDown(parent);

        //这一句是为了实现弹出PopupWindow后，当点击屏幕其他部分及Back键时PopupWindow会消失，
        //没有这一句则效果不能出来，但并不会影响背景
        selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    /**
     * 初始化填充Adapter所用List数据
     */
    private void initDatas() {
        if (loginHistory != null && loginHistory.getHistory() != null && loginHistory.getHistory().size() >= 0) {
            datas.addAll(loginHistory.getHistory());
        }
    }

    /**
     * 显示PopupWindow窗口
     */
    public void popupWindwShowing() {
        //将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
        //这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
        //（是否会产生缝隙，及产生缝隙的大小，可能会根据机型、Android系统版本不同而异吧，不太清楚）
        selectPopupWindow.showAsDropDown(parent, Utils.dip2px(this, 30), 0);
    }

    /**
     * PopupWindow消失
     */
    public void dismiss() {
        selectPopupWindow.dismiss();
        xiaIbt.setBackground(ContextCompat.getDrawable(ShopSendActivity.this, R.drawable.down));
    }

    @Override
    public boolean handleMessage(Message message) {
        Bundle data = message.getData();
        switch (message.what) {
            case 1:
                //选中下拉项，下拉框消失
                int selIndex = data.getInt("selIndex");
                userEt.setText(datas.get(selIndex).getHistory());
                pwdEt.setText(datas.get(selIndex).getPassword());
                dismiss();
                break;
            case 2:
                //移除下拉项数据
                int delIndex = data.getInt("delIndex");
                datas.remove(delIndex);
                //刷新下拉列表
                optionsAdapter.notifyDataSetChanged();
                loginHistory.getHistory().remove(delIndex);
                toSave();
                break;
        }
        return false;
    }

    public void getData() {
        LoginDaifaAccountRequest rq = new LoginDaifaAccountRequest();
        rq.setDaifa_type("1");//1-商家代发，2-物流代发
        rq.setLogistics_number(userEt.getText().toString());
        rq.setRandom_password(pwdEt.getText().toString());
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, ShopSendActivity.this, LoginDaifaAccountResponse.class);
            if (kd != null) {
                LoginDaifaAccountResponse info = (LoginDaifaAccountResponse) kd;
                if (info.getStatus() == 1) {

                    new AppParam().setSharedPreferencesy(ShopSendActivity.this, "login_id", userEt.getText().toString());
                    new AppParam().setSharedPreferencesy(ShopSendActivity.this, "login_pwd", pwdEt.getText().toString());
                    new AppParam().setSharedPreferencesy(ShopSendActivity.this, "pay_id", info.getLogistics_company_id() + "");

                    Bundle bundle = new Bundle();
                    bundle.putString("logistics_company_id", info.getLogistics_company_id() + "");
                    startActivityByClass(UndertakesToItemsActivity.class, bundle);

                    //保存账号
                    saveHistory();
                } else {
                    showToast(info.getMessage());
                }
            }
            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            showWait(false);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            showWait(false);
        }
    };

    public void saveHistory() {
        user = userEt.getText().toString();
        psw = pwdEt.getText().toString();
        if (loginHistory == null) {
            loginHistory = new LoginHistoryBean();
            List<LoginHistory> history = new ArrayList<>();
            LoginHistory hst = new LoginHistory();
            hst.setHistory(user);
            hst.setPassword(psw);
            history.add(hst);
            loginHistory.setHistory(history);
        } else {
            for (int i = 0, size = loginHistory.getHistory().size(); i < size; i++) {
                if (user.equals(loginHistory.getHistory().get(i).getHistory())) {
                    loginHistory.getHistory().remove(i);
                    break;
                }
            }
            LoginHistory hst = new LoginHistory();
            hst.setHistory(user);
            hst.setPassword(psw);
            loginHistory.getHistory().add(0, hst);
        }
        if (loginHistory.getHistory() != null && loginHistory.getHistory().size() > 5) {
            loginHistory.getHistory().remove(5);
        }
        toSave();
    }

    public void toSave() {
        new AppParam().setNewSharedPreferencesy(this, "historyjson", JSON.toJSONString(loginHistory));
    }

}

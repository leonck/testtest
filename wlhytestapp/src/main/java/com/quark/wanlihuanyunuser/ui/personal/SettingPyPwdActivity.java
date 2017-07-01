package com.quark.wanlihuanyunuser.ui.personal;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pan on 2016/11/4 0004.
 * >#
 * >#开启电子卡支付
 */
public class SettingPyPwdActivity extends BaseActivity {


    int type;
    String onePwd;
    String twoPwd;

    String code;
    String password;
    @InjectView(R.id.one)
    TextView one;
    @InjectView(R.id.two)
    TextView two;
    @InjectView(R.id.three)
    TextView three;
    @InjectView(R.id.four)
    TextView four;
    @InjectView(R.id.five)
    TextView five;
    @InjectView(R.id.six)
    TextView six;
    @InjectView(R.id.password)
    EditText editView;
    final TextView[] dian = new TextView[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingpypwd);
        ButterKnife.inject(this);
//        initPwdView();


        dian[0] = one;
        dian[1] = two;
        dian[2] = three;
        dian[3] = four;
        dian[4] = five;
        dian[5] = six;

        editView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//					System.out.println("onTextChanged "+" s="+s.toString()+"  start="+start+"  before = "+before+"  count"+count);
                setvisiable(start, s.toString());
                if (start == 5) {
                    password = editView.getText().toString();
//                    submint(ed);
//                    dialog.dismiss();
                    showToast("密码是：" + password);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                System.out.println("=======beforeTextChanged===== " + " s=" + s.toString() + "  start=" + start + "  after = " + after + "  count" + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

    }

    int lastPostion = -1;
    String st = "";

    public void setvisiable(int start, String s) {
        if ((lastPostion == start || lastPostion > start) && st.length() > s.length()) {//删除
            dian[start].setVisibility(View.INVISIBLE);
        } else {                                      //增加
            dian[start].setVisibility(View.VISIBLE);
            for (int i = start + 1; i < 6; i++) {
                dian[i].setVisibility(View.INVISIBLE);
            }
        }
        if ((start == 0 && lastPostion == 1) || (start == 0 && lastPostion == 0)) {   //fuck wen ti
            lastPostion = -1;
        } else {
            lastPostion = start;
        }
        st = s;
    }

//    private void initPwdView() {
//        final PasswordView pwdView = new PasswordView(this);
//        setContentView(pwdView);
//
//        LinearLayout left = (LinearLayout) pwdView.findViewById(R.id.left);
//
//        left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        TextView title = (TextView) pwdView.findViewById(R.id.title);
//        final TextView setPwd = (TextView) pwdView.findViewById(R.id.seting_pwd);
//        title.setText("开启电子卡支付");
//
//        pwdView.setOnFinishInput(new OnPasswordInputFinish() {
//            @Override
//            public void inputFinish() {
//
//
////                pwdView.emptyPwd();
//
////                pwdView.setOnFinishInput(new OnPasswordInputFinish() {
////                    @Override
////                    public void inputFinish() {
////
////                    }
////                });
//
//
//            }
//        });

//}

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

}

package com.quark.wanlihuanyunuser.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.base.BaseFragementActivity;
import com.quark.wanlihuanyunuser.ui.personal.CourierDetailsActivity;
import com.quark.wanlihuanyunuser.util.Utils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 定制化显示扫描界面
 */
public class SecondActivity extends BaseFragementActivity {


    @InjectView(R.id.linear1)
    ImageView linear1;


    private CaptureFragment captureFragment;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;

    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;

    String scan_code;//不同界面进入的状态码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.inject(this);

        scan_code = (String) getValue4Intent("scan_code");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

//        initView();
    }

    @OnClick({R.id.btn_cancel_scan, R.id.linear1, R.id.pickpicly})
    public void cl(View b) {
        switch (b.getId()) {
            case R.id.btn_cancel_scan:
                finish();
                break;
            case R.id.linear1:
                if (!isOpen) {
                    CodeUtils.isLightEnable(true);
                    isOpen = true;
                } else {
                    CodeUtils.isLightEnable(false);
                    isOpen = false;
                }
                break;
            case R.id.pickpicly:
//                showToast("335");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
        }
    }

    public static boolean isOpen = false;


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            SecondActivity.this.setResult(RESULT_OK, resultIntent);
//            SecondActivity.this.finish();

            dealData(result);

        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            SecondActivity.this.setResult(RESULT_OK, resultIntent);
//            SecondActivity.this.finish();

        }
    };

    public void dealData(String result) {

        if (!Utils.isEmpty(scan_code)) {
            Intent intent = new Intent();
            intent.putExtra("code", result);
            setResult(70, intent);
            finish();
        } else {
            Intent intent = new Intent(SecondActivity.this, CourierDetailsActivity.class);
            intent.putExtra("waybill_number",result);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }

        /**
         * 选择系统图片并解析
         */
        else if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Toast.makeText(SecondActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                            dealData(result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(SecondActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_CAMERA_PERM) {
            Toast.makeText(this, "从设置页面返回...", Toast.LENGTH_SHORT).show();
        }
    }
}

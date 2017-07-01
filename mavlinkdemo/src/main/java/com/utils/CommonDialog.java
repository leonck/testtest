package com.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by shengkun.cheng on 2016/3/31.
 * 显示正在加载的进度框
 *
 */
public class CommonDialog {

    private ProgressDialog progressDialog;

    /*
  * 提示加载
  */
    public void showProgressDialog(Context context, String message, boolean cancelable) {

        if (progressDialog == null){
            progressDialog = ProgressDialog.show(context, "",message, true, cancelable);
            if (progressDialog.isShowing()){
                progressDialog.setMessage(message);
            }else {
                progressDialog.show();
            }
        }else {
            try{
                progressDialog.setMessage(message);
                progressDialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /*
     * 隐藏提示加载
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }

    }

}

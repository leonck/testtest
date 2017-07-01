package com.quark.wanlihuanyunuser.shearUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.util.FileUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShearUi {
    public static Dialog showShear(final Context context, final android.os.Handler handler) {
        final Dialog dlg = new Dialog(context, R.style.ActionSheet);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.actionsheet_shear, null);
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);
        TextView wechat = (TextView) layout.findViewById(R.id.wechat);
        TextView wechatmoment = (TextView) layout.findViewById(R.id.wechatmoment);
        TextView logistics = (TextView) layout.findViewById(R.id.logistics);
        TextView mCancel = (TextView) layout.findViewById(R.id.cancel);

        wechat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
                dlg.dismiss();
            }
        });

        wechatmoment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
                dlg.dismiss();
            }
        });
        logistics.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 3;
                handler.sendMessage(msg);
                dlg.dismiss();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(false);
        dlg.setContentView(layout);
        dlg.show();

        return dlg;
    }

    /**
     * 分享到微信
     */
    public static void wechat(Context context, PlatformActionListener l) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitleUrl("https://www.baidu.com/"); // 标题的超链接
        sp.setTitle("测试标题");
        sp.setText("测试内容");
        sp.setImagePath(FileUtils.sendImgFriend(context));//
//        sp.setImageUrl(shareParams.getImageUrl());
        sp.setUrl("https://www.baidu.com/");
        sp.setComment("我对此分享内容的评论");
        sp.setSite("万里环运");
        sp.setSiteUrl("https://www.baidu.com/");
        Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
        weixin.setPlatformActionListener(l); // 设置分享事件回调 //
        // 执行图文分享
        weixin.share(sp);
    }

    public static void wechatMoments(Context context, PlatformActionListener l) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitleUrl("https://www.baidu.com/"); // 标题的超链接
        sp.setTitle("测试标题");
        sp.setText("测试内容");
        sp.setUrl("https://www.baidu.com/");
        sp.setComment("我对此分享内容的评论");
        sp.setSite("万里环运");
        sp.setSiteUrl("https://www.baidu.com/");
        Platform weixin = ShareSDK.getPlatform(WechatMoments.NAME);
        weixin.setPlatformActionListener(l); // 设置分享事件回调 //
        // 执行图文分享
        weixin.share(sp);
    }

}

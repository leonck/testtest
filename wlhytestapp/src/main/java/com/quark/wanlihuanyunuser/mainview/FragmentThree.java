package com.quark.wanlihuanyunuser.mainview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.supertextviewlibrary.SuperTextView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loveplusplus.update.UpdateChecker;
import com.quark.api.auto.bean.UpdateAvatarRequest;
import com.quark.api.auto.bean.UpdateAvatarResponse;
import com.quark.api.auto.bean.UserBaseInfoRequest;
import com.quark.api.auto.bean.UserBaseInfoResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseFragment;
import com.quark.wanlihuanyunuser.ui.chooseImage.FileItem;
import com.quark.wanlihuanyunuser.ui.chooseImage.ImageUtils;
import com.quark.wanlihuanyunuser.ui.personal.AboutUsActivity;
import com.quark.wanlihuanyunuser.ui.personal.EditNameActivity;
import com.quark.wanlihuanyunuser.ui.personal.MyAdsActivity;
import com.quark.wanlihuanyunuser.ui.personal.MyCardActivity;
import com.quark.wanlihuanyunuser.ui.personal.MyCourierActivity;
import com.quark.wanlihuanyunuser.ui.personal.MyMsgActivity;
import com.quark.wanlihuanyunuser.ui.personal.MyorderActivity;
import com.quark.wanlihuanyunuser.ui.personal.daifa.ShopSendExpressActivity;
import com.quark.wanlihuanyunuser.ui.personal.myConcerned.ConcernedActivity;
import com.quark.wanlihuanyunuser.ui.user.EditPwdActivity;
import com.quark.wanlihuanyunuser.ui.user.LoginActivity;
import com.quark.wanlihuanyunuser.util.FileUtils;
import com.quark.wanlihuanyunuser.util.StringUtils;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;

import org.kymjs.kjframe.http.HttpCallBack;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

@SuppressLint("ResourceAsColor")
public class FragmentThree extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    View threeViewt;
    @InjectView(R.id.profile_image)
    CircleImageView profileImage;
    @InjectView(R.id.name_tv)
    TextView nameTv;
    @InjectView(R.id.one_stv)
    SuperTextView oneStv;
    @InjectView(R.id.two_stv)
    SuperTextView twoStv;
    @InjectView(R.id.three_stv)
    SuperTextView threeStv;
    @InjectView(R.id.four_stv)
    SuperTextView fourStv;
    @InjectView(R.id.five_stv)
    SuperTextView fiveStv;
    @InjectView(R.id.six_stv)
    SuperTextView sixStv;
    @InjectView(R.id.seven_stv)
    SuperTextView sevenStv;
    @InjectView(R.id.eight_stv)
    SuperTextView eightStv;
    @InjectView(R.id.nine_stv)
    SuperTextView nineStv;
    @InjectView(R.id.out_bt)
    Button outBt;

    @InjectView(R.id.concern_stv)
    SuperTextView concernStv;

    String versionCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        threeViewt = inflater.inflate(R.layout.fragment_three, container, false);
        ButterKnife.inject(this, threeViewt);

        initIcon();
        infoRequest();
        return threeViewt;
    }

    public void initIcon() {
        oneStv.setLeftIcon(ContextCompat.getDrawable(getActivity(), R.drawable.me_11));
        twoStv.setLeftIcon(ContextCompat.getDrawable(getActivity(), R.drawable.me_2));
        threeStv.setLeftIcon(ContextCompat.getDrawable(getActivity(), R.drawable.me_3));
        fourStv.setLeftIcon(ContextCompat.getDrawable(getActivity(), R.drawable.me_4));
        fiveStv.setLeftIcon(ContextCompat.getDrawable(getActivity(), R.drawable.me_5));
        sixStv.setLeftIcon(ContextCompat.getDrawable(getActivity(), R.drawable.me_6));
        sevenStv.setLeftIcon(ContextCompat.getDrawable(getActivity(), R.drawable.me_7));
        eightStv.setLeftIcon(ContextCompat.getDrawable(getActivity(), R.drawable.me_8));
        nineStv.setLeftIcon(ContextCompat.getDrawable(getActivity(), R.drawable.me_9));
        concernStv.setLeftIcon(ContextCompat.getDrawable(getActivity(), R.drawable.me_1));

        oneStv.setRightIcon(ContextCompat.getDrawable(getActivity(), R.drawable.right_2));
        twoStv.setRightIcon(ContextCompat.getDrawable(getActivity(), R.drawable.right_2));
        threeStv.setRightIcon(ContextCompat.getDrawable(getActivity(), R.drawable.right_2));
        fourStv.setRightIcon(ContextCompat.getDrawable(getActivity(), R.drawable.right_2));
        fiveStv.setRightIcon(ContextCompat.getDrawable(getActivity(), R.drawable.right_2));
        sixStv.setRightIcon(ContextCompat.getDrawable(getActivity(), R.drawable.right_2));
        sevenStv.setRightIcon(ContextCompat.getDrawable(getActivity(), R.drawable.right_2));
        eightStv.setRightIcon(ContextCompat.getDrawable(getActivity(), R.drawable.right_2));
        nineStv.setRightIcon(ContextCompat.getDrawable(getActivity(), R.drawable.right_2));
        concernStv.setRightIcon(ContextCompat.getDrawable(getActivity(), R.drawable.right_2));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void infoRequest() {
        UserBaseInfoRequest rq = new UserBaseInfoRequest();
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    UserBaseInfoResponse info;
    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), UserBaseInfoResponse.class);
            if (kd != null) {
                info = (UserBaseInfoResponse) kd;
                if (info.getStatus() == 1) {
                    ApiHttpClient.loadImage(info.getUserBaseInfoResult().getUserInfo().getImage_01(), profileImage, R.drawable.avatar_s);
                    String name = info.getUserBaseInfoResult().getUserInfo().getNickname();
                    if (Utils.isEmpty(name)) {
                        nameTv.setText(info.getUserBaseInfoResult().getUserInfo().getTelephone());
                    } else {
                        nameTv.setText(info.getUserBaseInfoResult().getUserInfo().getNickname());
                    }
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
            showWait(false);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            showWait(false);
        }
    };

    @OnClick({R.id.name_tv, R.id.profile_image, R.id.one_stv, R.id.two_stv, R.id.three_stv,
            R.id.four_stv, R.id.five_stv, R.id.six_stv, R.id.seven_stv, R.id.eight_stv, R.id.nine_stv,
            R.id.out_bt,R.id.concern_stv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.name_tv:
                Intent in1 = new Intent(getActivity(), EditNameActivity.class);
                startActivityForResult(in1, 101);
                break;
            case R.id.profile_image:
                ImageUtils.showSheetPic(getActivity(), handlerphoto);
                break;
            case R.id.one_stv:
                Intent int1 = new Intent(getActivity(), MyCourierActivity.class);
                startActivity(int1);
                break;
            case R.id.two_stv:
//                Intent int2 = new Intent(getActivity(), ShopSendCourierActivity.class);
                Intent int2 = new Intent(getActivity(), ShopSendExpressActivity.class);
                startActivity(int2);
                break;
            case R.id.three_stv:
                Intent int3 = new Intent(getActivity(), MyorderActivity.class);
                startActivity(int3);
                break;
            case R.id.four_stv:
                Intent int4 = new Intent(getActivity(), MyAdsActivity.class);
                startActivity(int4);
                break;
            case R.id.five_stv:
                Intent int5 = new Intent(getActivity(), MyMsgActivity.class);
                startActivity(int5);
                break;
            case R.id.six_stv:
                Intent int6 = new Intent(getActivity(), MyCardActivity.class);
                startActivity(int6);
                break;
            case R.id.seven_stv:
                Intent int8 = new Intent(getActivity(), EditPwdActivity.class);
                int8.putExtra("type", "2");
                startActivity(int8);
                break;
            case R.id.eight_stv:
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.nine_stv:
//                updateRequest();
                startUpdatebyPermissions();
                break;
            case R.id.out_bt:
                exitApp();
            case R.id.concern_stv:
                Intent inCon = new Intent(getActivity(), ConcernedActivity.class);
                startActivity(inCon);
                break;
        }
    }

    private void exitApp() {
        final AlertDialog dlg = new AlertDialog.Builder(getActivity()).create();
        dlg.setTitle("退出提示");
        dlg.setMessage("是否确定退出登录？");
        dlg.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                new AppParam().setSharedPreferencesy(getActivity(), "token", "");
                if (MainActivity.instance != null) {
                    MainActivity.instance.finish();
                }
                getActivity().finish();
            }
        });
        dlg.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dlg.cancel();
            }
        });
        dlg.show();
    }

    /*=========================拍照===========================*/
    public static final int ACTION_TYPE_ALBUM = 0;
    public static final int ACTION_TYPE_PHOTO = 1;
    private boolean isChangeFace = false;

    private final static int CROP = 200;

    private final static String FILE_SAVEPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/daka/";
    private Uri origUri;
    private File protraitFile;
    private Bitmap protraitBitmap;
    private String protraitPath;

    /**
     * 上传身份证照片
     */
    private void uploadNewPhoto() {
        // 获取头像缩略图
        if (!StringUtils.isEmpty(protraitPath) && protraitFile.exists()) {
            protraitBitmap = ImageUtils.loadImgThumbnail(protraitPath, AppParam.picx, AppParam.picy);
        } else {
            AppContext.showToast("图像不存在，上传失败");
        }
        if (protraitBitmap != null) {
            try {
                List<FileItem> ls = new LinkedList<FileItem>();
                FileItem f = new FileItem("image_01", protraitFile);
                ls.add(f);
                UpdateAvatarRequest rq = new UpdateAvatarRequest();
                showWait(true);
                QuarkApi.HttpuploadFile(rq, ls, httpCallBack);
            } catch (Exception e) {
                AppContext.showToast("图像不存在，上传失败");
            }
        }
    }

    HttpCallBack httpCallBack = new HttpCallBack() {
        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            showWait(false);
            Log.e("error", "==" + t);
            try {
                UpdateAvatarResponse info = new UpdateAvatarResponse(t);
                showToast(info.getMessage());
                profileImage.setImageBitmap(protraitBitmap);
            } catch (Exception e) {
                Log.e("error", "數據解析出錯");
            }
            showWait(false);
        }

        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("更換失敗");

            showWait(false);
        }
    };

    /**
     * 选择图片裁剪
     */
    private void startImagePick() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "選擇圖片"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "選擇圖片"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        }
    }

    private void startTakePhoto() {
        Intent intent;
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/dakatemp/";
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (StringUtils.isEmpty(savePath)) {
            AppContext.showToastShort("无法保存照片，请检查SD卡是否挂载");
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = "temp_" + timeStamp + ".jpg";// 照片命名
        File out = new File(savePath, fileName);
        Uri uri = Uri.fromFile(out);
        origUri = uri;

        String theLarge = savePath + fileName;

        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    // 裁剪头像的绝对路径
    private Uri getUploadTempFile(Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            AppContext.showToast("無法保存上傳的頭像，請檢查SD卡是否掛載");
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

        // 如果是标准Uri
        if (StringUtils.isEmpty(thePath)) {
            thePath = ImageUtils.getAbsoluteImagePath(getActivity(), uri);
        }
        String ext = FileUtils.getFileFormat(thePath);
        ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
        // 照片命名
        String cropFileName = "daka_" + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);

        return Uri.fromFile(protraitFile);
    }

    /**
     * 拍照后裁剪
     *
     * @param data 原始图片
     */
    private void startActionCrop(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("output", this.getUploadTempFile(data));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", AppParam.picx);// 输出图片大小
        intent.putExtra("outputY", AppParam.picy);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent imageReturnIntent) {
        if (requestCode != 0) {
            if (resultCode == 101) {
                String name = imageReturnIntent.getStringExtra("name");
                nameTv.setText(name);
            }
        }
        //圖片
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
                startActionCrop(origUri);// 拍照后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
                startActionCrop(imageReturnIntent.getData());// 选图后裁剪
                break;
            case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
                uploadNewPhoto();
                break;
        }
    }

    private static final int CAMERA_PERM = 1;

    @AfterPermissionGranted(CAMERA_PERM)
    private void startTakePhotoByPermissions() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            try {
                startTakePhoto();
            } catch (Exception e) {
                Toast.makeText(getActivity(), R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
            }
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this,
                    getResources().getString(R.string.str_request_camera_message),
                    CAMERA_PERM, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        try {
            if (requestCode == 10002) {
                checkupdate();
            } else {
                startTakePhoto();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(getActivity(), R.string.permissions_camera_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private Handler handlerphoto = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    startTakePhotoByPermissions();
                    break;
                case 2:
                    startImagePick();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };

    /*=========================拍照end===========================*/
    //=============更新===================
    private void startUpdatebyPermissions() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            try {
                checkupdate();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "缺少运行权限,请正确授权", Toast.LENGTH_LONG).show();
            }
        } else {
            EasyPermissions.requestPermissions(this, "申请运行所需的权限，如果拒绝将影响您的正常使用", 10002, perms);
        }
    }


    public void checkupdate() {
        showWait(true);
        String checkUrl = ApiHttpClient.HOSTURL + "/app/Setting/androidAutoUpdate?method=get&type=1&versionCode=" + Utils.getVersionCode(getActivity());
        TLog.error(checkUrl);
        UpdateChecker.checkForDialog(getActivity(), checkUrl, ApiHttpClient.HOSTURL, handlerupdate, false);
    }

    private Handler handlerupdate = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    showWait(false);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };

}






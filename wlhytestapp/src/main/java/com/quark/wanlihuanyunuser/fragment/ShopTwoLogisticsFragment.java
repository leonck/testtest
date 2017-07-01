package com.quark.wanlihuanyunuser.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.AddDaifaOrdersRequest;
import com.quark.api.auto.bean.AddDaifaOrdersResponse;
import com.quark.api.auto.bean.IdcardRecordList;
import com.quark.api.auto.bean.IdcardRecordListRequest;
import com.quark.api.auto.bean.IdcardRecordListResponse;
import com.quark.api.auto.bean.ListProduct;
import com.quark.api.auto.bean.ListUserAddress;
import com.quark.api.auto.bean.UpdateImageRequest;
import com.quark.api.auto.bean.UpdateImageResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.ItemsInfoAdapter;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseFragment;
import com.quark.wanlihuanyunuser.ui.chooseImage.FileItem;
import com.quark.wanlihuanyunuser.ui.chooseImage.ImageUtils;
import com.quark.wanlihuanyunuser.ui.personal.MyAdsActivity;
import com.quark.wanlihuanyunuser.ui.send.HistoryRecordActivity;
import com.quark.wanlihuanyunuser.ui.send.SelectPayShopActivity;
import com.quark.wanlihuanyunuser.ui.send.TermsActivity;
import com.quark.wanlihuanyunuser.ui.widget.ListViewForScrollView;
import com.quark.wanlihuanyunuser.util.FileUtils;
import com.quark.wanlihuanyunuser.util.StringUtils;
import com.quark.wanlihuanyunuser.util.TLog;
import com.quark.wanlihuanyunuser.util.Utils;

import org.kymjs.kjframe.http.HttpCallBack;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by pan on 2016/11/7 0007.
 * >#
 * >商家 国际物流到中国物流
 */
public class ShopTwoLogisticsFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

    @InjectView(R.id.lv)
    ListViewForScrollView lv;
    @InjectView(R.id.selected_tv)
    TextView selectedTv;
    @InjectView(R.id.id_zheng)
    ImageView idZheng;
    @InjectView(R.id.id_fan)
    ImageView idFan;
    @InjectView(R.id.id_number)
    EditText idNumber;
    @InjectView(R.id.send_one_tv)
    TextView sendOneTv;
    @InjectView(R.id.send_two_tv)
    TextView sendTwoTv;
    @InjectView(R.id.send_three_tv)
    TextView sendThreeTv;
    @InjectView(R.id.recevie_one_tv)
    TextView recevieOneTv;
    @InjectView(R.id.recevie_two_tv)
    TextView recevieTwoTv;
    @InjectView(R.id.recevie_three_tv)
    TextView recevieThreeTv;
    @InjectView(R.id.number)
    TextView number;

    private String orders_number;
    private String type;//1-新加波物流，2-国际中国物流
    private String daifa_orders_company_id;//代发订单ID
    private String money;//费用
    private String idcard_record_id;//身份证记录是否国际物流：0-国内，其他-国际（大于0）
    private String idcard_number;//身份证号码
    private String front_card;//身份证正面
    private String back_card;//身份证背面
    private String send_user_address_id;//寄送地址
    private String collect_user_address_id;//收地址
    private String logistics_company_id;//商家公司ID
    private int id_type;
    ArrayList<ListProduct> newGoodsList;
    View oneView;
    ArrayList<ListProduct> datas;
    ItemsInfoAdapter adapter;
    private boolean is_select;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        oneView = inflater.inflate(R.layout.fragment_logistics_shop_two, container, false);
        ButterKnife.inject(this, oneView);

        daifa_orders_company_id = getArguments().getString("daifa_orders_company_id");
        logistics_company_id = getArguments().getString("logistics_company_id");
        newGoodsList = (ArrayList<ListProduct>) getArguments().getSerializable("list");
        money = getArguments().getString("money");

        datas = new ArrayList<>();
        adapter = new ItemsInfoAdapter(getActivity(), newGoodsList);
        lv.setAdapter(adapter);
        number.setText(money + "元");
        getIDHistoryData();

        return oneView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.send_ly, R.id.collect_ly, R.id.id_zheng, R.id.id_fan, R.id.id_list, R.id.selected_tv, R.id.terms_tv, R.id.sumbit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_ly:
                Intent intent = new Intent(getActivity(), MyAdsActivity.class);
                intent.putExtra("select", "3");
                startActivityForResult(intent, 401);
                break;
            case R.id.collect_ly:
                Intent intent1 = new Intent(getActivity(), MyAdsActivity.class);
                intent1.putExtra("select", "4");
                startActivityForResult(intent1, 402);
                break;
            case R.id.id_zheng:
                id_type = 1;
                ImageUtils.showSheetPic(getActivity(), handlerphoto);
                break;
            case R.id.id_fan:
                id_type = 2;
                ImageUtils.showSheetPic(getActivity(), handlerphoto);
                break;
            case R.id.selected_tv:
                if (is_select) {
                    is_select = false;
                    Drawable drawable = this.getResources().getDrawable(R.drawable.agree_o);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    selectedTv.setCompoundDrawables(drawable, null, null, null);
                } else {
                    is_select = true;
                    Drawable drawable = this.getResources().getDrawable(R.drawable.agree);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    selectedTv.setCompoundDrawables(drawable, null, null, null);
                }
                break;
            case R.id.terms_tv:
                Intent in = new Intent(getActivity(), TermsActivity.class);
                startActivity(in);
                break;
            case R.id.sumbit_tv:
                if (check()) {
                    getData();
                }
                break;
            case R.id.id_list:      //身份证历史记录
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("idhistory", (Serializable) idhistory);
                Intent intent5 = new Intent(getActivity(), HistoryRecordActivity.class);
                intent5.putExtras(bundle1);
                startActivityForResult(intent5, 88);
                break;
        }
    }

    public boolean check() {
        if (!is_select) {
            showToast("同意快递条款才能提交");
            return false;
        } else if (Utils.isEmpty(collect_user_address_id)) {
            showToast("请选择收件地址");
            return false;
        } else if (Utils.isEmpty(send_user_address_id)) {
            showToast("请选择寄件地址");
            return false;
        } else if (Utils.isEmpty(idNumber.getText().toString())) {
            showToast("请输入身份证号码");
            return false;
        } else if (Utils.isEmpty(front_card)) {
            showToast("请上传身份证正面照片");
            return false;
        } else if (Utils.isEmpty(back_card)) {
            showToast("请上传身份证背面照片");
            return false;
        }

        return true;
    }

    public void getData() {
        AddDaifaOrdersRequest rq = new AddDaifaOrdersRequest();
        rq.setType("2");
        rq.setDaifa_orders_company_id(daifa_orders_company_id);
        rq.setMoney(money);
        rq.setIdcard_record_id("1");
        rq.setIdcard_number(idNumber.getText().toString());
        rq.setFront_card(front_card);
        rq.setBack_card(back_card);
        rq.setSend_user_address_id(send_user_address_id);
        rq.setCollect_user_address_id(collect_user_address_id);
        rq.setLogistics_company_id(logistics_company_id);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), AddDaifaOrdersResponse.class);
            if (kd != null) {
                AddDaifaOrdersResponse info = (AddDaifaOrdersResponse) kd;
                if (info.getStatus() == 1) {
                    orders_number = info.getOrders_number();
                    Intent in1 = new Intent(getActivity(), SelectPayShopActivity.class);
                    in1.putExtra("send_orders_id", daifa_orders_company_id);
                    in1.putExtra("orders_number", info.getOrders_number());
                    in1.putExtra("money", money);
                    in1.putExtra("from", "daifa");
                    startActivity(in1);
                    showToast(info.getMessage());
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
    };

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
//        showWaitDialog("正在上传头像...");

        // 获取头像缩略图
        if (!StringUtils.isEmpty(protraitPath) && protraitFile.exists()) {
            protraitBitmap = ImageUtils.loadImgThumbnail(protraitPath, AppParam.picx, AppParam.picy);
        } else {
            AppContext.showToast("图像不存在，上传失败");
        }
        if (protraitBitmap != null) {
            try {
                List<FileItem> ls = new LinkedList<FileItem>();
                FileItem f = new FileItem("images_01", protraitFile);
                ls.add(f);
                UpdateImageRequest rq = new UpdateImageRequest();
                showWait(true);
                QuarkApi.HttpuploadFileNewList(rq, ls, httpCallBack);
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
                UpdateImageResponse info = new UpdateImageResponse(t);
                showToast(info.getMessage());
                if (id_type == 1) {
                    idZheng.setImageBitmap(protraitBitmap);
                    front_card = info.getFileName();
                } else if (id_type == 2) {
                    idFan.setImageBitmap(protraitBitmap);
                    back_card = info.getFileName();
                }
            } catch (Exception e) {
                Log.e("error", "數據解析出錯");
            }
            showWait(false);
        }

        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("更换失败");
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
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
        } else {
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
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
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
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
//        intent.putExtra("aspectX", 1);// 裁剪框比例
//        intent.putExtra("aspectY", 1);
        intent.putExtra("aspectX", 85);// 裁剪框比例
        intent.putExtra("aspectY", 54);
        intent.putExtra("outputX", AppParam.picx);// 输出图片大小
        intent.putExtra("outputY", AppParam.picy);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent imageReturnIntent) {
        if (requestCode != 0) {
//            if (resultCode == 101) {
//                send_user_address_id = imageReturnIntent.getStringExtra("id");
//                TLog.error("寄送地址id" + send_user_address_id);
//            } else if (resultCode == 102) {
//                collect_user_address_id = imageReturnIntent.getStringExtra("id");
//                TLog.error("收地址id" + collect_user_address_id);
//
//            } else if (resultCode == 99) {
//                logistics_id = imageReturnIntent.getStringExtra("wuliu_id");
//                logistics_name = imageReturnIntent.getStringExtra("wuliu_name");
//            } else if (resultCode == 98) {
//                String goodsName = imageReturnIntent.getStringExtra("goods_name");
//                GoodsList goodsList = new GoodsList(goodsName);
//                datas.add(goodsList);
//                adapter = new GoodsAdapter(ChinaParcelInfoActivity.this, datas, handler);
//                goodsLv.setAdapter(adapter);
//
//            } else
//
            if (resultCode == 88) {
//                idcard_number = imageReturnIntent.getStringExtra("id_number");
//                front_card = imageReturnIntent.getStringExtra("front_card");
//                back_card = imageReturnIntent.getStringExtra("back_card");
//                idNumber.setText(idcard_number);
//                ApiHttpClient.loadImage(front_card, idZheng, R.drawable.camera);
//                ApiHttpClient.loadImage(back_card, idFan, R.drawable.camera);

                idcard_number = imageReturnIntent.getStringExtra("id_number");
                front_card = imageReturnIntent.getStringExtra("front_card");
                back_card = imageReturnIntent.getStringExtra("back_card");
                idhistory = (List<IdcardRecordList>) imageReturnIntent.getSerializableExtra("idhistory");

                idNumber.setText(idcard_number);
                ApiHttpClient.loadImage(front_card, idZheng, R.drawable.camera);
                ApiHttpClient.loadImage(back_card, idFan, R.drawable.camera);
            }
//            if (resultCode == 101) {
//                send_user_address_id = imageReturnIntent.getStringExtra("id");
//                if (!send_user_address_id.equals(collect_user_address_id)) {
//                    ListUserAddress adsItem = (ListUserAddress) imageReturnIntent.getSerializableExtra("ads_item");
//                    sendOneTv.setText(adsItem.getProvince() + " " + adsItem.getName() + " " + adsItem.getTelephone());
//                    sendTwoTv.setText(adsItem.getAddress());
//                    sendThreeTv.setText(adsItem.getProvince() + adsItem.getCity() + adsItem.getArea());
//                    sendCity = adsItem.getCity();
//                    sendZone = adsItem.getArea();
//                    checkIsInHistory(adsItem.getName());
//                } else {
//                    showToast("寄件地址不能与收货地址相同");
//                    send_user_address_id = "";
//                }
//            } else if (resultCode == 102) {
//                collect_user_address_id = imageReturnIntent.getStringExtra("id");
//                if (!collect_user_address_id.equals(send_user_address_id)) {
//                    ListUserAddress adsItem = (ListUserAddress) imageReturnIntent.getSerializableExtra("ads_item");
//                    recevieOneTv.setText(adsItem.getProvince() + " " + adsItem.getName() + " " + adsItem.getTelephone());
//                    recevieTwoTv.setText(adsItem.getAddress());
//                    recevieThreeTv.setText(adsItem.getProvince() + adsItem.getCity() + adsItem.getArea());
//                    TLog.error("收地址id" + collect_user_address_id);
//                } else {
//                    showToast("寄件地址不能与收货地址相同");
//                    collect_user_address_id = "";
//                }
//            }

            if (resultCode == 401) {
                send_user_address_id = imageReturnIntent.getStringExtra("id");
                if (!send_user_address_id.equals(collect_user_address_id)) {
                    ListUserAddress adsItem = (ListUserAddress) imageReturnIntent.getSerializableExtra("ads_item");
                    sendOneTv.setText(adsItem.getProvince() + " " + adsItem.getName() + " " + adsItem.getTelephone());
                    sendTwoTv.setText(adsItem.getAddress());
                    sendThreeTv.setText(adsItem.getProvince() + adsItem.getCity() + adsItem.getArea());
                    TLog.error("寄送地址id" + send_user_address_id);
                    checkIsInHistory(adsItem.getName());
                } else {
                    showToast("寄件地址不能与收货地址相同");
                    send_user_address_id = "";
                }
            }
            if (resultCode == 402) {
                collect_user_address_id = imageReturnIntent.getStringExtra("id");
                if (!collect_user_address_id.equals(send_user_address_id)) {
                    ListUserAddress adsItem = (ListUserAddress) imageReturnIntent.getSerializableExtra("ads_item");
                    recevieOneTv.setText(adsItem.getProvince() + " " + adsItem.getName() + " " + adsItem.getTelephone());
                    recevieTwoTv.setText(adsItem.getAddress());
                    recevieThreeTv.setText(adsItem.getProvince() + adsItem.getCity() + adsItem.getArea());
                    TLog.error("收地址id" + collect_user_address_id);
                } else {
                    showToast("寄件地址不能与收货地址相同");
                    collect_user_address_id = "";
                }
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
            startTakePhoto();
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
    List<IdcardRecordList> idhistory;

    /**
     * 获取身份证历史记录
     *
     * @author pan
     * @time 2016/11/29 0029 下午 2:12
     */
    public void getIDHistoryData() {
        IdcardRecordListRequest rq = new IdcardRecordListRequest();
        rq.setUser_type(AppParam.user_type);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, getActivity(), IdcardRecordListResponse.class);
            if (kd != null) {
                IdcardRecordListResponse info = (IdcardRecordListResponse) kd;
                if (info.getStatus() == 1) {
                    idhistory = info.getIdcardRecordList();
                    if (idhistory == null) {
                        idhistory = new ArrayList<>();
                    }
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

    public void checkIsInHistory(String name) {
        for (int i = 0; i < idhistory.size(); i++) {
            if (name.equals(idhistory.get(i).getName())) {
                front_card = idhistory.get(i).getFront_card();
                back_card = idhistory.get(i).getBack_card();

                idNumber.setText(idhistory.get(i).getIdcard_number());
                ApiHttpClient.loadImage(front_card, idZheng, R.drawable.camera);
                ApiHttpClient.loadImage(back_card, idFan, R.drawable.camera);
                break;
            }
        }
    }
}

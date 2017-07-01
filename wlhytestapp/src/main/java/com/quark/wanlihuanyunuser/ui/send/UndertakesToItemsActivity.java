package com.quark.wanlihuanyunuser.ui.send;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.quark.api.auto.bean.FirstDaifaOrdersPremissRequest;
import com.quark.api.auto.bean.FirstDaifaOrdersPremissResponse;
import com.quark.api.auto.bean.ListProduct;
import com.quark.api.auto.bean.ProductListRequest;
import com.quark.api.auto.bean.ProductListResponse;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.adapter.UdsToItemsAdapter;
import com.quark.wanlihuanyunuser.api.ApiResponse;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.base.BaseActivity;
import com.quark.wanlihuanyunuser.ui.widget.SoftKeyboardSizeWatchLayout;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import me.maxwin.view.XListView;

/**
 * Created by pan on 2016/11/9 0009.
 * >#
 * >#代发物品
 */
public class UndertakesToItemsActivity extends BaseActivity implements XListView.IXListViewListener, SoftKeyboardSizeWatchLayout.OnResizeListener {

    ArrayList<ListProduct> datas;
    UdsToItemsAdapter adapter;
    @InjectView(R.id.xlv)
    XListView xlstv;
    @InjectView(R.id.result_tv)
    TextView resultTv;
    @InjectView(R.id.search_et)
    EditText searchEt;

    String login_id;
    String kw;
    int current;
    double totalMoney = 0;
    private String product_ids_nums;//购买多商品拼接{商品ID‘A’商品数量#商品ID‘A’商品数量}如：1A3#2A12{商品id=1购买了3个，商品id=2购买了12个}
    private String money;  //费用
    private String send_type;//1-正常买卖，2-物流代发
    private String logistics_id;//正常购买时id=0，物流代发Id{只需一个值}
    int pn = 1;
    int type = 1;
    ArrayList<ListProduct> newList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undertakestoitems);
        ButterKnife.inject(this);
        login_id = (String) getValue4Intent("logistics_company_id");
        searchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    type = 1;
                    datas.clear();
                    kw = searchEt.getText().toString();
                    getData();
                    return true;
                }
                return false;
            }
        });
        myinitlist();
        getData();
        SoftKeyboardSizeWatchLayout activityRootView = (SoftKeyboardSizeWatchLayout) findViewById(R.id.root_layout);
        activityRootView.addOnResizeListener(this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.left, R.id.sumbit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                finish();
                break;
            case R.id.sumbit_tv:
                dealData();
                break;
        }
    }

    /**
     * 选好物品下单处理
     *
     * @author pan
     * @time 2016/12/20 0020 下午 3:15
     */
    public void dealData() {
        List<String> numberLs = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).isCheck()) {
                numberLs.add(datas.get(i).getSend_type() + "");
                logistics_id = datas.get(i).getLogistics_id();//物流代发Id{只需一个值}
            }
        }
        if (numberLs != null && numberLs.size() > 0) {
            int b = 0;
            for (int j = 0; j < numberLs.size() - 1; j++) {
                if (!numberLs.get(j).equals(numberLs.get(j + 1))) {//选中的物品中 物流代发的物品跟仓库物品不能一起下单
                    b++;
                }
            }
            if (b == 0) {
                send_type = numberLs.get(0);
                if (send_type.equals("1")) {//正常购买时id=0，物流代发Id{只需一个值}
                    logistics_id = "0";
                }
                calculationMoney();
                tiJiaoRequest();
            } else {
                showToast("物流代发和仓库货物不可一起下单");
            }
        } else {
            showToast("请选择物品");
        }
    }

    public void myinitlist() {
        datas = new ArrayList();
        xlstv.setFooterDividersEnabled(false);
        // 设置xlistview可以加载、刷新
        xlstv.setPullLoadEnable(true);
        xlstv.setPullRefreshEnable(true);
        xlstv.setXListViewListener(this);
//        xlstv.setOnItemClickListener(listListener);
        adapter = new UdsToItemsAdapter(this, datas, handler);
        xlstv.setAdapter(adapter);
        Message msg = handler.obtainMessage();
        msg.what = 1;
        msg.arg1 = datas.size();
        handler.sendMessage(msg);
    }

//    AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            position = position - 1;
//            if (position < datas.size()) {
//            }
//        }
//    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    xlstv.setDataSize(msg.arg1);
                    xlstv.stopRefresh();
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    xlstv.setDataSize(msg.arg1);
                    xlstv.stopLoadMore();
                    adapter.notifyDataSetChanged();
                    break;
                case 115:
                    current = msg.arg1;
                    //判断不同商家代发和物流代发不能同时选择
                    if (checkCheck()) {
                        if (datas.get(current).getStock() > 0) {
                            if (datas.get(current).isCheck()) {
                                datas.get(current).setCheck(false);
                                adapter.notifyDataSetChanged();
                            } else {
                                datas.get(current).setCheck(true);
                                adapter.notifyDataSetChanged();
                            }
                            calculationMoney();
                        } else {
                            showToast("库存不足");
                        }
                    }
                    break;
                case 116:
                    int posi = msg.arg1;
                    int gn = msg.arg2;
                    if (gn > datas.get(posi).getStock()) {
                        datas.get(posi).setGoods_number(datas.get(posi).getStock());
                        showToast("已超出库存量");
                        updateItem(posi);
                    } else {
                        datas.get(posi).setGoods_number(gn);
                    }
                    calculationMoney();
                    break;
                default:
                    break;
            }
        }
    };

    //商家代发和物流代发不能同时选择
    public boolean checkCheck() {
        int type = datas.get(current).getSend_type();
        if (newList.size() > 0) {
            if (type == newList.get(0).getSend_type()) {
                return true;
            } else {
                showToast("物流代发和仓库货物不可一起下单");
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRefresh() {
        pn = 1;
        type = 1;
        getData();
    }

    @Override
    public void onLoadMore() {
        type = 2;
        pn++;
        getData();
    }

    /**
     * 物品列表
     *
     * @author pan
     * @time 2016/11/30 0030 上午 9:31
     */
    public void getData() {
        ProductListRequest rq = new ProductListRequest();
        rq.setPn(pn);
        rq.setPage_size(10);
        rq.setUser_type("2");
        rq.setLogin_id(login_id);
        rq.setKw(kw);
        showWait(true);
        QuarkApi.HttpRequestNewList(rq, mHandlerget);
    }

    private final AsyncHttpResponseHandler mHandlerget = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, UndertakesToItemsActivity.this, ProductListResponse.class);
            if (kd != null) {
                ProductListResponse info = (ProductListResponse) kd;
                if (info.getStatus() == 1) {
                    dealData(info);
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

    public void dealData(ProductListResponse info) {
        if (type == 1) {
            datas.clear();
        }
        List<ListProduct> tin = info.getProductListResult().getProductList().getList();
        Message msg = handler.obtainMessage();
        msg.what = type;
        if (tin != null && tin.size() > 0) {
            for (int i = 0, size = tin.size(); i < size; i++) { //默认数量为1
                if (tin.get(i).getStock() > 0) {
                    tin.get(i).setGoods_number(1);
                } else {
                    tin.get(i).setGoods_number(0);
                }
            }
            datas.addAll(tin);
            msg.arg1 = tin.size();
        } else {
            msg.arg1 = 0;
        }
        handler.sendMessage(msg);
        if (type == 1) {//刷新清空
            calculationMoney();
        }
    }

    /**
     * 提交
     *
     * @author pan
     * @time 2016/11/30 0030 上午 9:31
     */
    public void tiJiaoRequest() {
        FirstDaifaOrdersPremissRequest rq = new FirstDaifaOrdersPremissRequest();
        rq.setLogistics_id(logistics_id);
        rq.setMoney(money);
        rq.setProduct_ids_nums(product_ids_nums);
        rq.setSend_type(send_type);
        showWait(true);
        QuarkApi.HttpRequest(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            Object kd = ApiResponse.get(arg2, UndertakesToItemsActivity.this, FirstDaifaOrdersPremissResponse.class);
            if (kd != null) {
                FirstDaifaOrdersPremissResponse info = (FirstDaifaOrdersPremissResponse) kd;
                if (info.getStatus() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("daifa_orders_company_id", info.getDaifa_orders_company_id());
                    bundle.putString("logistics_company_id", login_id);
                    bundle.putSerializable("list", newList);
                    bundle.putString("money", money);
                    startActivityByClass(FillToLogInfoActivity.class, bundle);
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

    /**
     * 计算物品数量和价格  拼接物品
     * 购买多商品拼接{商品ID‘A’商品数量#商品ID‘A’商品数量}如：1A3#2A12{商品id=1购买了3个，商品id=2购买了12个}
     *
     * @author pan
     * @time 2016/11/30 0030 上午 11:07
     */
    public void calculationMoney() {
        StringBuilder sb = new StringBuilder();
        totalMoney = 0;
        int a = 0;
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).isCheck()) {
                a += datas.get(i).getGoods_number();
                Double money = Double.valueOf(datas.get(i).getCompany_price());
                int number = datas.get(i).getGoods_number();
                totalMoney += money * number;
                sb.append("#" + datas.get(i).getProduct_id() + "A" + adapter.getList().get(i).getGoods_number());
                if (!newList.contains(datas.get(i))) {
                    newList.add(datas.get(i));
                }
            } else {
                newList.remove(datas.get(i));
            }
        }
        if (sb.length() > 0) {
            product_ids_nums = sb.substring(1, sb.length());
        }
        money = Utils.DoubleFormat(totalMoney);
        resultTv.setText("共" + a + "件物品,合计" + money + "元");
    }

    @Override
    public void OnSoftPop(int height) {
//        Toast.makeText(this, "监听11到软键盘弹起...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSoftClose() {
//        Toast.makeText(this, "监听到22软件盘关闭...", Toast.LENGTH_SHORT).show();
//        calculationMoney();
    }

    /**
     * 刷新指定item
     *
     * @param index item在listview中的位置
     */
    private void updateItem(int index) {
        if (datas == null) {
            return;
        }
        int visiblePosition = xlstv.getFirstVisiblePosition();
        View view = xlstv.getChildAt(index - visiblePosition + 1);
        TextView txt = (TextView) view.findViewById(R.id.number_et);
        txt.setText(datas.get(index).getStock() + "");
    }
}

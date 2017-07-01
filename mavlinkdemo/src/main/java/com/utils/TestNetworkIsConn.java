package com.utils;

import com.hubsansdk.application.HubsanApplication;
import com.hubsansdk.drone.HubsanDrone;
import com.hubsansdk.drone.HubsanDroneVariable;
import com.hubsansdk.utils.LogX;
import java.util.Locale;

/**
 * @project name: X-Hubsan
 * @class name：com.csk.hbsdrone.utils
 * @class describe:
 * @anthor shengkun.cheng
 * @time 2017/4/5 16:11
 * @change
 * @chang time:
 * @class describe:
 */
public class TestNetworkIsConn extends HubsanDroneVariable {
//
//    private HubsanDroneApplication app;
//    private FinalHttp http;
//    private TestNetwork userBean;
//
//    public TestNetworkIsConn(HubsanDrone myDrone) {
//        super(myDrone);
//        app = (HubsanDroneApplication) HubsanApplication.getApplication();
//        http = new FinalHttp();
//    }
//
//    /**
//     * 请求测试网络
//     * @return
//     */
//    public TestNetwork requestByPost() {
//        String url = app.ucPreserter.getTestServiceUrl();
//        AjaxParams params = new AjaxParams();
//        params.put("example", "isconn");
//        params.put("secret", app.ucPreserter.getMD5Secret());
//        params.put("apiversion", app.ucPreserter.getAppApi());
//        params.put("format", "json");
//        params.put("lang", Locale.getDefault().toString());
//        http.post(url, params, new AjaxCallBack<String>() {
//
//            @Override
//            public void onSuccess(String t) {
//                super.onSuccess(t);
//                Gson gson = new Gson();
//                userBean = gson.fromJson(t, TestNetwork.class);
//                LogX.e("onSuccess:" + t);
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                LogX.e("错误码：" + errorNo + "错误信息：" + strMsg);
//                userBean = null;
//            }
//        });
//        return userBean;
//    }

}

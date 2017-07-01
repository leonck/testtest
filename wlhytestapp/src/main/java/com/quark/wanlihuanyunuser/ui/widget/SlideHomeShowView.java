package com.quark.wanlihuanyunuser.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.quark.api.auto.bean.HomeBannerRequest;
import com.quark.api.auto.bean.HomeBannerResponse;
import com.quark.api.auto.bean.ListhomeBanner;
import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.AppParam;
import com.quark.wanlihuanyunuser.R;
import com.quark.wanlihuanyunuser.api.ApiHttpClient;
import com.quark.wanlihuanyunuser.api.remote.QuarkApi;
import com.quark.wanlihuanyunuser.ui.home.AdDetailActivity;
import com.quark.wanlihuanyunuser.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

/**
 * ViewPager实现的轮播图广告自定义视图，如京东首页的广告轮播图效果；
 * 既支持自动轮播页面也支持手势滑动切换页面
 */

public class SlideHomeShowView extends FrameLayout {


    // 使用universal-image-loader插件读取网络图片，需要工程导入universal-image-loader-1.8.6-with-sources.jar
    private ImageLoader imageLoader = ImageLoader.getInstance();

    //轮播图图片数量
//    private final static int IMAGE_COUNT = 5;
    //自动轮播的时间间隔
//    private final static int TIME_INTERVAL = 5;
    //自动轮播启用开关
    private final static boolean isAutoPlay = true;

    //自定义轮播图的资源
    private String[] imageUrls;
    //放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
    //放圆点的View的list
    private List<View> dotViewsList;

    private ViewPager viewPager;
    //当前轮播页
    private int currentItem = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;
    private Context context;

    //Handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }
    };

    public SlideHomeShowView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public SlideHomeShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public SlideHomeShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
        initImageLoader(context);
        initData();
        if (isAutoPlay) {
            startPlay();
        }

    }

    /**
     * 开始轮播图切换
     */
    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
    }

    /**
     * 停止轮播图切换
     */
    private void stopPlay() {
        scheduledExecutorService.shutdown();
    }

    /**
     * 初始化相关Data
     */
    private void initData() {
//        imageViewsList = new ArrayList<ImageView>();
//        dotViewsList = new ArrayList<View>();

        //获取缓存数据
        String bannarjson = new AppParam().getBannarjson(context);
        if (!Utils.isEmpty(bannarjson)) {
            dealDatas(bannarjson);
        }
        //更新数据
        getData();
    }

    List<ListhomeBanner> banners;

    public void getData() {
        HomeBannerRequest rq = new HomeBannerRequest();

        QuarkApi.HttpRequestNewList(rq, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            String ds = null;
            try {
                ds = new String(arg2, "UTF-8");
                new AppParam().setSharedPreferencesy(context, "homebannarjson", ds);
                dealDatas(ds);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", "解析出错");
            }
            Log.e("error", ds);
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
            AppContext.showToast("网络出错" + arg0);
            Log.e("error", "出错");
        }

        @Override
        public void onFinish() {
            super.onFinish();

        }
    };

    HomeBannerResponse info;

    public void dealDatas(String str) {
        info = new HomeBannerResponse(str);
        banners = info.getHomeBannerResult().getHomeBanner();
        imageUrls = new String[banners.size()];
        for (int i = 0; i < banners.size(); i++) {
            imageUrls[i] = ApiHttpClient.loadImage + banners.get(i).getImage_01();
        }
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();

        initUI(context);
    }

    RelativeLayout per;

    /**
     * 初始化Views等UI
     */
    private void initUI(Context context) {
        if (imageUrls == null || imageUrls.length == 0)
            return;

        per = (RelativeLayout) findViewById(R.id.per);
        LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();

        // 热点个数与图片特殊相等
        for (int i = 0; i < imageUrls.length; i++) {
            ImageView view = new ImageView(context);
            view.setTag(imageUrls[i]);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.logo);//占位图
            }
            view.setScaleType(ScaleType.CENTER_CROP);
            imageViewsList.add(view);

            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager) container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            final ImageView imageView = imageViewsList.get(position);

            //修改为 如果加载失败显示默认图片
//			imageLoader.displayImage(imageView.getTag() + "", imageView);

//            ApiHttpClient.loadImage2(imageView.getTag() + "",imageView);

            imageLoader.displayImage(imageView.getTag() + "", imageView, new ImageLoadingListener() {

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    imageView.setBackgroundResource(R.drawable.logo);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view,
                                            FailReason failReason) {
                    // TODO Auto-generated method stub
//					Toast.makeText(context, "加载失败", 0).show();
                    imageView.setBackgroundResource(R.drawable.logo);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    // TODO Auto-generated method stub

                }
            });

            ((ViewPager) container).addView(imageViewsList.get(position));

            imageViewsList.get(position).setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //广告详情
//							Toast.makeText(context, "==" + position+info.getHomeBannerResult().getHomeBanner().get(position).getIndex_banner_id(),Toast.LENGTH_SHORT).show();
//                            String index_banner_id = banners.get(position).getIndex_banner_id() + "";
//                            Bundle bundle = new Bundle();
//                            String url = ApiHttpClient.HOSTURL + "/app/IndexBannerManage/indexBannerDetail?index_banner_id=" + index_banner_id;
//                            bundle.putString("url", url+"");
//                            bundle.putString("title", "詳情");
                            Intent intent = new Intent(context, AdDetailActivity.class);
                            intent.putExtra("index_banner_id", info.getHomeBannerResult().getHomeBanner().get(position).getIndex_banner_id() + "");
//                            intent.putExtra("url",url);
                            context.startActivity(intent);
                        }
                    });

            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(final int pos) {
            // TODO Auto-generated method stub

            currentItem = pos;
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == pos) {
                    ((View) dotViewsList.get(pos)).setBackgroundResource(R.drawable.kongxin_yuan);
                } else {
                    ((View) dotViewsList.get(i)).setBackgroundResource(R.drawable.bai_yuan);
                }
            }
        }
    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    /**
     * 销毁ImageView资源，回收内存
     *
     */
//    private void destoryBitmaps() {
//
//        for (int i = 0; i < IMAGE_COUNT; i++) {
//            ImageView imageView = imageViewsList.get(i);
//            Drawable drawable = imageView.getDrawable();
//            if (drawable != null) {
//                //解除drawable对view的引用
//                drawable.setCallback(null);
//            }
//        }
//    }


    /**
     * 异步任务,获取数据
     *
     */
//	class GetListTask extends AsyncTask<String, Integer, Boolean> {
//
//		@Override
//		protected Boolean doInBackground(String... params) {
//			try {
//				// 这里一般调用服务端接口获取一组轮播图片，下面是从百度找的几个图片
//				imageUrls = new String[]{
//						"http://image.zcool.com.cn/56/403967876491.jpg",
//						"http://image.zcool.com.cn/59/533967870670.jpg",
//						"http://image.zcool.com.cn/47/19/349992.jpg",
//						"http://image.zcool.com.cn/59/11/m_1303967844788.jpg"
//				};
//				return true;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return false;
//			}
//		}
//
//		@Override
//		protected void onPostExecute(Boolean result) {
//			super.onPostExecute(result);
//			if (result) {
//		        initUI(context);
//			}
//		}
//	}

    /**
     * ImageLoader 图片组件初始化
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove
                // app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


//    // 使用universal-image-loader插件读取网络图片，需要工程导入universal-image-loader-1.8.6-with-sources.jar
////    private ImageLoader imageLoader = ImageLoader.getInstance();
//
//    //轮播图图片数量
////    private final static int IMAGE_COUNT = 5;
//    //自动轮播的时间间隔
////    private final static int TIME_INTERVAL = 5;
//    //自动轮播启用开关
//    private final static boolean isAutoPlay = true;
//
//    //自定义轮播图的资源
////    private String[] imageUrls;
//    //放轮播图片的ImageView 的list
//    private List<ImageView> imageViewsList;
//    //放圆点的View的list
//    private List<View> dotViewsList;
//
//    private ViewPager viewPager;
//    //当前轮播页
//    private int currentItem  = 0;
//    //定时任务
//    private ScheduledExecutorService scheduledExecutorService;
//    private Context context;
//
//    int[] pics = {R.drawable.p001,R.drawable.p002,R.drawable.p003,R.drawable.p004};
//
//    //Handler
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            viewPager.setCurrentItem(currentItem);
//        }
//    };
//
//    public SlideHomeShowView(Context context) {
//        this(context,null);
//        // TODO Auto-generated constructor stub
//    }
//
//    public SlideHomeShowView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//        // TODO Auto-generated constructor stub
//    }
//
//    public SlideHomeShowView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        this.context = context;
//
////        initImageLoader(context);
//        initData();
//        if(isAutoPlay){
//            startPlay();
//        }
////		registerBoradcastReceiverIndex();
//
//    }
//
//    // 注册广播
////    public static ReceiveBroadCastIndex receiveBroadCastIndex;
////	public void registerBoradcastReceiverIndex() {
////		receiveBroadCastIndex = new ReceiveBroadCastIndex();
////		IntentFilter filter = new IntentFilter();
////		filter.addAction("picslideIndex");
////		context.registerReceiver(receiveBroadCastIndex, filter);
////	}
////
////	public class ReceiveBroadCastIndex extends BroadcastReceiver {
////		@Override
////		public void onReceive(Context context, Intent data) {
////			initImageLoader(context);
////			initData();
////	        if(isAutoPlay){
////	            startPlay();
////	        }
////		}
////	}
//
//    /**
//     * 开始轮播图切换
//     */
//    private void startPlay(){
//        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
//    }
//    /**
//     * 停止轮播图切换
//     */
//    private void stopPlay(){
//        scheduledExecutorService.shutdown();
//    }
//
//    /**
//     * 初始化相关Data
//     */
//    private void initData(){
//        imageViewsList = new ArrayList<ImageView>();
//        dotViewsList = new ArrayList<View>();
//        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
//        //获取缓存数据
////        String bannarjson = new AppParam().getBannarjson(context);
////        if (!Utils.isEmpty(bannarjson)){
////            dealDatas(bannarjson);
////        }
//        //更新数据
////        getData();
//        dealDatas();
//    }
//
////    List<ListIndexBanner> banners;
////    public void getData() {
////        IndexBannerRequest rq = new IndexBannerRequest();
////        QuarkApi.HttpRequest(rq, mHandler);
////    }
////
////    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {
////        @Override
////        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
////            String str = null;
////            try {
////                str = new String(arg2, "UTF-8");
////                new AppParam().setSharedPreferencesy(context,"bannarjson",str);
////                dealDatas(str);
////            } catch (Exception e) {
////                e.printStackTrace();
////                Log.e("error", "解析出错");
////            }
////            Log.e("error", str);
////        }
////
////        @Override
////        public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
////            AppContext.showToast("网络出错" + arg0);
////            Log.e("error", "出错");
////        }
////
////        @Override
////        public void onFinish() {
////            super.onFinish();
////
////        }
////    };
//
//    public void dealDatas(){
////        IndexBannerResponse info = new IndexBannerResponse(str);
////        banners = info.getIndexBanners();
////        imageUrls = new String[banners.size()];
////        for (int i = 0; i < banners.size(); i++) {
////            imageUrls[i] = ApiHttpClient.loadImage + banners.get(i).getCover();
////        }
//        imageViewsList = new ArrayList<ImageView>();
//        dotViewsList = new ArrayList<View>();
//        initUI(context);
//    }
//
//
//    RelativeLayout per;
//
//    /**
//     * 初始化Views等UI
//     */
//    private void initUI(Context context){
////        if(imageUrls == null || imageUrls.length == 0)
////            return;
//
//
//        per = (RelativeLayout)findViewById(R.id.per);
//        LinearLayout dotLayout = (LinearLayout)findViewById(R.id.dotLayout);
//        dotLayout.removeAllViews();
//
//        // 热点个数与图片特殊相等
//        for (int i = 0; i < pics.length; i++) {
//            ImageView view =  new ImageView(context);
//
//            view.setTag(pics[i]);
////            if(i==0){
//                view.setImageDrawable(ContextCompat.getDrawable(context, pics[i]));
////            }
//
//            view.setScaleType(ScaleType.CENTER_CROP);
//            imageViewsList.add(view);
//
//            ImageView dotView =  new ImageView(context);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//            params.leftMargin = 4;
//            params.rightMargin = 4;
//            dotLayout.addView(dotView, params);
//            dotViewsList.add(dotView);
//        }
//
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        viewPager.setFocusable(true);
//
//        viewPager.setAdapter(new MyPagerAdapter());
//        viewPager.setOnPageChangeListener(new MyPageChangeListener());
//    }
//
//    /**
//     * 填充ViewPager的页面适配器
//     *
//     */
//    private class MyPagerAdapter  extends PagerAdapter {
//
//        @Override
//        public void destroyItem(View container, int position, Object object) {
//            // TODO Auto-generated method stub
//            //((ViewPag.er)container).removeView((View)object);
//            ((ViewPager)container).removeView(imageViewsList.get(position));
//        }
//
//        @Override
//        public Object instantiateItem(View container, final int position) {
//            final ImageView imageView = imageViewsList.get(position);
//
//            //修改为 如果加载失败显示默认图片
////			imageLoader.displayImage(imageView.getTag() + "", imageView);
//
////            ApiHttpClient.loadImage2(imageView.getTag() + "",imageView);
//
////        	imageLoader.displayImage(imageView.getTag() + "", imageView, new ImageLoadingListener() {
////
////                @Override
////                public void onLoadingStarted(String imageUri, View view) {
////
////                }
////
////                @Override
////                public void onLoadingFailed(String imageUri, View view,
////                                            FailReason failReason) {
////                    // TODO Auto-generated method stub
//////					Toast.makeText(context, "加载失败", 0).show();
////                    imageView.setBackgroundResource(R.drawable.text_banner);
////                }
////
////                @Override
////                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
////                    // TODO Auto-generated method stub
////
////                }
////
////                @Override
////                public void onLoadingCancelled(String imageUri, View view) {
////                    // TODO Auto-generated method stub
////
////                }
////			} );
//
//            ((ViewPager)container).addView(imageViewsList.get(position));
//
////            imageViewsList.get(position).setOnClickListener(
////                    new OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
//////							Toast.makeText(context, "==" + position, 0).show();
////                            String newid = banners.get(position).getIndex_banner_id()+"";
////                            Bundle bundle = new Bundle();
////                            bundle.putString("index_banner_id", newid+"");
////                            Intent intent = new Intent();
//////                            intent.setClass(context, WebInfoActivity.class);
////                            intent.putExtras(bundle);
////                            context.startActivity(intent);
////                        }
////                    });
//
//            return imageViewsList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            // TODO Auto-generated method stub
//            return imageViewsList.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View arg0, Object arg1) {
//            // TODO Auto-generated method stub
//            return arg0 == arg1;
//        }
//        @Override
//        public void restoreState(Parcelable arg0, ClassLoader arg1) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public Parcelable saveState() {
//            // TODO Auto-generated method stub
//            return null;
//        }
//
//        @Override
//        public void startUpdate(View arg0) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void finishUpdate(View arg0) {
//            // TODO Auto-generated method stub
//
//        }
//    }
//
//    /**
//     * ViewPager的监听器
//     * 当ViewPager中页面的状态发生改变时调用
//     *
//     */
//    private class MyPageChangeListener implements OnPageChangeListener {
//
//        boolean isAutoPlay = false;
//
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//            // TODO Auto-generated method stub
//            switch (arg0) {
//                case 1:// 手势滑动，空闲中
//                    isAutoPlay = false;
//                    break;
//                case 2:// 界面切换中
//                    isAutoPlay = true;
//                    break;
//                case 0:// 滑动结束，即切换完毕或者加载完毕
//                    // 当前为最后一张，此时从右向左滑，则切换到第一张
//                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
//                        viewPager.setCurrentItem(0);
//                    }
//                    // 当前为第一张，此时从左向右滑，则切换到最后一张
//                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
//                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
//                    }
//                    break;
//            }
//        }
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void onPageSelected(final int pos) {
//            // TODO Auto-generated method stub
//
//            currentItem = pos;
//            for(int i=0;i < dotViewsList.size();i++){
//                if(i == pos){
//                    ((View)dotViewsList.get(pos)).setBackgroundResource(R.drawable.dot_focus);
//                }else {
//                    ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_blur);
//                }
//            }
//        }
//    }
//
//    /**
//     *执行轮播图切换任务
//     *
//     */
//    private class SlideShowTask implements Runnable{
//
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            synchronized (viewPager) {
//                currentItem = (currentItem+1)%imageViewsList.size();
//                handler.obtainMessage().sendToTarget();
//            }
//        }
//    }
//
//    /**
//     * 销毁ImageView资源，回收内存
//     *
//     */
////    private void destoryBitmaps() {
////
////        for (int i = 0; i < IMAGE_COUNT; i++) {
////            ImageView imageView = imageViewsList.get(i);
////            Drawable drawable = imageView.getDrawable();
////            if (drawable != null) {
////                //解除drawable对view的引用
////                drawable.setCallback(null);
////            }
////        }
////    }
//
//
//    /**
//     * 异步任务,获取数据
//     *
//     */
////	class GetListTask extends AsyncTask<String, Integer, Boolean> {
////
////		@Override
////		protected Boolean doInBackground(String... params) {
////			try {
////				// 这里一般调用服务端接口获取一组轮播图片，下面是从百度找的几个图片
////				imageUrls = new String[]{
////						"http://image.zcool.com.cn/56/403967876491.jpg",
////						"http://image.zcool.com.cn/59/533967870670.jpg",
////						"http://image.zcool.com.cn/47/19/349992.jpg",
////						"http://image.zcool.com.cn/59/11/m_1303967844788.jpg"
////				};
////				return true;
////			} catch (Exception e) {
////				e.printStackTrace();
////				return false;
////			}
////		}
////
////		@Override
////		protected void onPostExecute(Boolean result) {
////			super.onPostExecute(result);
////			if (result) {
////		        initUI(context);
////			}
////		}
////	}
//
//    /**
//     * ImageLoader 图片组件初始化
//     *
//     * @param context
//     */
////	public static void initImageLoader(Context context) {
////		// This configuration tuning is custom. You can tune every option, you
////		// may tune some of them,
////		// or you can create default configuration by
////		// ImageLoaderConfiguration.createDefault(this);
////		// method.
////		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove
////																																																																								// app
////				.build();
////		// Initialize ImageLoader with configuration.
////		ImageLoader.getInstance().init(config);
////	}
}
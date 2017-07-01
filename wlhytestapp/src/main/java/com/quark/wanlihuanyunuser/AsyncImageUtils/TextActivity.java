//package com.quark.kuaishi.AsyncImageUtils;
//
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.widget.ImageView;
//
//import com.quark.kuaishi.R;
//import com.quark.kuaishi.base.BaseActivity;
//
//
//public class TextActivity extends BaseActivity {
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.imgtext);
//		ViewUtils.inject(this);
//
//		 final ImageView iv = (ImageView)findViewById(R.id.iv);
//	        //网络图片地址
//	        String imgUrl = "http://www.mobileyx.com/upload/2015-03-01/3ad99e3c-c01a-4bd6-8b21-ce3b2c3e0838.png";
//
//	        //for test
//	        AsyncImageLoader loader = new AsyncImageLoader(getApplicationContext());
//
//	        //将图片缓存至外部文件中
//	        loader.setCache2File(true); //false
//	        //设置外部缓存文件夹
//	        loader.setCachedDir(this.getCacheDir().getAbsolutePath());
//
//	        //下载图片，第二个参数是否缓存至内存中
//	        loader.downloadImage(imgUrl, true/*false*/, new AsyncImageLoader.ImageCallback() {
//	            @Override
//	            public void onImageLoaded(Bitmap bitmap, String imageUrl) {
//	                if(bitmap != null){
//	                    iv.setImageBitmap(bitmap);
//	                }else{
//	                    //下载失败，设置默认图片
//	                }
//	            }
//	        });
//	}
//  }
//

////网络图片地址
//String imgUrl = list.get(position).getMlogourl();
//		String ds = Environment.getExternalStorageDirectory().getPath();
////        Log.e("dd", "缓存路径2："+ds);
//		//for test
//		AsyncImageLoader loader = new AsyncImageLoader(mContext);
//
//		//将图片缓存至外部文件中
//		loader.setCache2File(true); //false
//		//设置外部缓存文件夹
//		loader.setCachedDir(mContext.getCacheDir().getAbsolutePath());
////        loader.setCachedDir(ds);
//		Log.e("dd", "缓存路径："+mContext.getCacheDir().getAbsolutePath());
//		//下载图片，第二个参数是否缓存至内存中
//		loader.downloadImage(imgUrl, true, new AsyncImageLoader.ImageCallback() {
//@Override
//public void onImageLoaded(Bitmap bitmap, String imageUrl) {
//		if(bitmap != null){
//		logo.setImageBitmap(bitmap);
//		}else{
//		//下载失败，设置默认图片
//
//		}
//		}
//		});

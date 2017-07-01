package com.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

import java.io.File;

/**
 * @author xiaojun.lan
 * @description 从SDcard获取图片，并以Bitmap形式返回
 * @date 2015.9.13
 * 
 * */
public class GetImageFromSDcard {

	public Bitmap getSDcardBitmap(String dirPath, String fileName) {
		Bitmap mBitmap = null;
		String filePath = dirPath + fileName;
		try {
			File file = new File(filePath);
			SystemClock.sleep(500);
			if (file.exists()) {
				mBitmap = BitmapFactory.decodeFile(filePath);
			} 
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return mBitmap;
	}

	@SuppressWarnings("deprecation")
	public Drawable getSDcardDrawable(String dirPath, String fileName) {
		Bitmap bitmap = null;
		String filePath = dirPath + fileName;
		try {
			File file = new File(filePath);
			SystemClock.sleep(500);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(filePath);
			} 
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return new BitmapDrawable(bitmap);
	}

}

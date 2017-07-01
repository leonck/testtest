package com.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class MyFile {


    /**
     * 从Assets中读取图片
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {

        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 创建文件路径
     */
    public static void creatFilePath(String path) {
        java.io.File dir = new java.io.File(path);
        if (!dir.exists()) {
//            Utils.doLog("//不存在");
            dir.mkdirs();
        } else {
//            Utils.doLog("////目录存在");
        }
    }

    public static String getFileNum(String filePath) {
        File f = new File(filePath);
        File[] fl = null;
        int iNum = 0;
        boolean isFw = false;
        String fileName="";
        //判断是不是目录
        if (f.isDirectory()) {
            fl = f.listFiles();
            for (int i = 0; i < fl.length; i++) {
                File f2 = fl[i];
                if (f2.isFile()) {
                    isFw = getVideoFileName(f2.getPath());
                    iNum = iNum + 1;
                }
                fileName = f2.getPath();
            }
            if (iNum > 0 && isFw == true) {
                return fileName;
            }
        } else {
            return "";
        }
        return "";
    }

    /**
     * 判断文件是否包含fw
     * Description
     *
     * @param fileAbsolutePath
     * @return
     * @see [class/class#field/class#method]
     */
    public static boolean getVideoFileName(String fileAbsolutePath) {

        File file = new File(fileAbsolutePath);
        // 判断是否为文件夹
        String filename = file.getName();
        // 判断是否为fw结尾
        if (filename.trim().toLowerCase().endsWith(".fw")) {
            return true;
        }
        return false;
    }
}

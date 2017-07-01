package com.utils;

import android.os.Environment;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 上传工具类
 */
public class UploadUtil {
    public void uploadFile() {
        String end = "/r/n";
        String Hyphens = "--";
        String boundary = "*****";
        try {
            URL url = new URL(Utils.WRITE_SERVIVE_DATA);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
      /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
      /* 设定传送的method=POST */
            con.setRequestMethod("POST");
      /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
      /* 设定DataOutputStream */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(Hyphens + boundary + end);
//            ds.writeBytes("Content-Disposition: form-data; " + "name=/"file1/ ";filename=/"" + newName + " / "" + end);
            ds.writeBytes(end);
      /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(Environment.getExternalStorageDirectory().getPath()+"/DCIM/root_uimage");
      /* 设定每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
      /* 从文件读取数据到缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
        /* 将数据写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(Hyphens + boundary + Hyphens + end);
            fStream.close();
            ds.flush();
      /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            System.out.println("上传成功");
            ds.close();
        } catch (Exception e) {
            System.out.println("上传失败" + e.getMessage());
        }
    }
}
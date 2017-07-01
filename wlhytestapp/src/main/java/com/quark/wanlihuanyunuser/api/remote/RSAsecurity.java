package com.quark.wanlihuanyunuser.api.remote;

import android.util.Log;

import com.quark.wanlihuanyunuser.AppContext;

import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

import javax.crypto.Cipher;

import Decoder.BASE64Encoder;

public class RSAsecurity {
//     BASE64Decoder decoder = new BASE64Decoder();
     BASE64Encoder encoder = new BASE64Encoder();
     String DES = "DES";
     String RSA = "RSA";
     String encode = "UTF-8";// 保持平台兼容统一使用utf-8

    // 公钥文件路径
     String publicFile =  "public_key.der";


    // 客户端加密
     public String DESAndRSAEncrypt(String pwd)  {
         String encryptKey="";
         try {
             encryptKey = RSAEncrypt(pwd.getBytes(encode));
         } catch (Exception e) {
             e.printStackTrace();
         }

        return encryptKey;
    }

    // 公钥加密
    public  String RSAEncrypt(byte[] plainText)  {
        // 读取公钥
        CertificateFactory certificatefactory = null;
        try {
            certificatefactory = CertificateFactory.getInstance("X.509");
//            FileInputStream bais = null;
//            bais = new FileInputStream(publicFile);
            InputStream bais = AppContext.instance.getResources().getAssets().open(publicFile);
            Certificate cert = certificatefactory.generateCertificate(bais);
            bais.close();
            PublicKey puk = cert.getPublicKey();
            Log.e("err","公钥base64：" + encoder.encode(puk.getEncoded()));

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //B用A的公钥把信息加密后发给A
            cipher.init(Cipher.ENCRYPT_MODE, puk);
            cipher.update(plainText);
            byte[] result1 = cipher.doFinal();
            System.out.println("加密结果：" + Arrays.toString(result1));
            Log.e("error", encoder.encode(result1));

//            Cipher cipher = Cipher.getInstance(type);
//            cipher.init(Cipher.ENCRYPT_MODE, key);

//            return cipher.doFinal(data);

            String dss = encoder.encode(result1);
            return dss;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行加密操作
     *
     * @param data
     *            待操作数据
     * @param key
     *            Key
     * @param type
     *            算法 RSA or DES
     * @return
     * @throws Exception
     */
//    public  byte[] doEncrypt(byte[] data, Key key, String type)
//            throws Exception {
//        Cipher cipher = Cipher.getInstance(type);
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//
//        return cipher.doFinal(data);
//    }



}

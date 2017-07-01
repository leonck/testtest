package com.quark.wanlihuanyunuser.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.quark.wanlihuanyunuser.AppContext;
import com.quark.wanlihuanyunuser.ui.user.LoginActivity;

import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.Iterator;

public class ApiResponse {

    public static Object get(byte[] arg2, Context context, Class cl) {
        String code = "";
        String message = "";
        Object obj = null;
        try {
            String ds = new String(arg2, "UTF-8");
            //发布注释掉
            Log.e("error",ds);
            JSONObject js = new JSONObject(ds);
            Iterator it = js.keys();
            while (it.hasNext()) {
                String key = it.next().toString();
                JSONObject dsg = js.getJSONObject(key);
                code = dsg.getString("code");
                message = dsg.getString("message");
                break;
            }
            if (code.equals("405")) {
                AppContext.isException = true;
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("from","exception");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(context, LoginActivity.class);
                context.startActivity(intent);
                return null;
            } else {
                Constructor c1 = cl.getDeclaredConstructor(new Class[]{String.class});
                c1.setAccessible(true);
                obj = c1.newInstance(new Object[]{ds});
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", cl.getName()+"JSON解析出错");
        }

        return obj;
    }

}

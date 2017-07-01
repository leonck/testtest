package com.example.androidvideoplayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

public class EventHandler {

    private ArrayList<Handler> mHandlerList;
    private static EventHandler mInstance;

    private EventHandler() {
        mHandlerList = new ArrayList<Handler>();
    }

    public static EventHandler getInstance() {
        if (mInstance == null) {
            mInstance = new EventHandler();
        }
        return mInstance;
    }

    public void addHandler(Handler handler) {
        if (!mHandlerList.contains(handler))
            mHandlerList.add(handler);
    }

    public void removeHandler(Handler handler) {
        mHandlerList.remove(handler);
    }

    /** This method is called by a native thread **/
    public void callback(int type, Bundle b) {
        for (int i = 0; i < mHandlerList.size(); i++) {
            Message msg = Message.obtain();
            msg.what = type;
            msg.setData(b);
            mHandlerList.get(i).sendMessage(msg);
        }
    }
}

package com.hubsansdk.application;

import android.app.Application;
import android.content.Context;

/**
 * HubsanApplication
 * @author shengkun.cheng
 *
 */
public class HubsanApplication extends Application {

	
	public static HubsanApplication baseApp;

    public static HubsanApplication getInstance() {
        return baseApp;
    }

    public static Context getApplication() {
        return baseApp.getApplicationContext();

    }

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		 baseApp = this;
	}

    @Override
    public void onTerminate() {
        System.exit(0);
        super.onTerminate();
    }

    
}

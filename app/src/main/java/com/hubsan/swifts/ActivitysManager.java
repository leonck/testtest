package com.hubsan.swifts;

import android.content.Context;
import android.content.Intent;

import com.hubsan.swifts.TestShow.TestShowActivity;

/**
 *
 * Created by Leon.Li on 2017/6/21.
 */

public class ActivitysManager {
    public static void startShowActivity(Context context){
        context.startActivity(new Intent(context, TestShowActivity.class));
    }
}

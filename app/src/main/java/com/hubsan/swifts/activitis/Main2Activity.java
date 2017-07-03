package com.hubsan.swifts.activitis;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.hubsan.swifts.R;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void into(View v){
        startActivity(new Intent().setClass(this,MainActivity.class));
    }
}

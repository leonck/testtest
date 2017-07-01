//package com.hubsan.joystick.activitys;
//
//import android.app.FragmentManager;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.Window;
//import android.view.WindowManager;
//
//import com.hubsan.joystick.R;
//
//public class JoystickActivity extends AppCompatActivity implements RCFragment.OnRockerClickListener {
//    FragmentManager fragmentManager;
//    RCFragment rcFragment;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_joystick);
//        fragmentManager = getFragmentManager();
//        if (rcFragment == null) {
//            rcFragment = new RCFragment();
//            fragmentManager.beginTransaction().add(R.id.containerRC, rcFragment).commit();
//        }
//    }
//
//    @Override
//    public void notifyRocker() {
//
//    }
//}

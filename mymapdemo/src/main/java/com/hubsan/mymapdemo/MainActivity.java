package com.hubsan.mymapdemo;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import com.example.androidvideoplayer.VideoPlayer;

public class MainActivity extends AppCompatActivity {

    TextureView textureView;
    SurfaceTexture surfaceTexture;
    Surface surface;
    VideoPlayer videoplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textureView = (TextureView)findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(listener);
    }

    /**
     * TCP 连接图像传递
     */
    public final String TCP_IMAGE_TRANSFER = "tcp://192.168.31.111:8855/";

    TextureView.SurfaceTextureListener listener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            surfaceTexture = textureView.getSurfaceTexture();
            surface = new Surface(surfaceTexture);
            surface.release();
            videoplayer = new VideoPlayer();

            videoplayer.setSurface(surface);
            videoplayer.surfaceChanged();

            videoHandle.postDelayed(videoRunnable,1000);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };
    Handler videoHandle = new Handler();
    Runnable videoRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e("error","begin");
            videoplayer.start(TCP_IMAGE_TRANSFER);
//            videoHandle.removeCallbacks(videoRunnable);
        }
    };

}

package com.hubsan.swifts.fragment;

import android.app.Fragment;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.hubsan.swifts.R;

/**
 * @describe describe
 * @anthor leon.li
 * @time 2017/7/3 11:05
 * @chang 11:05
 */

public class VideoFragment extends Fragment {
    View view;
    TextureView cameraTextureOne;
    VideoListener listener;

    public interface VideoListener {
        void playCameraVoid(Surface surface);
    }

    public void setListener(VideoListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        view = inflater.inflate(R.layout.fragment_video, viewGroup, false);
        cameraTextureOne = view.findViewById(R.id.cameraTextureOne);//图传SurfaceView
        cameraTextureOne.setSurfaceTextureListener(mTextureListenerOne);
        return view;
    }

    public TextureView.SurfaceTextureListener mTextureListenerOne = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            listener.playCameraVoid(new Surface(surface));
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

}


package com.example.androidvideoplayer;

import android.view.Surface;

public class VideoPlayer {
    /** Native methods, implemented in jni folder */

    // version
    public native String buildtime();

    // audio video renderer
    public native void setAout(Aout inst);

    public native void detachAout();

    public native void setSurface(Surface surface);

    public native void surfaceChanged();

    public native void detachSurface();

    // event callback
    public native void setEventHandler(EventHandler eventHandler);

    public native void detachEventHandler();

    // settings
    public native boolean isAccelerationEnabled();
    public native void enableAcceleration(boolean enable);

    public native boolean isNoFrameSkipEnabled();
    public native void enableNoFrameSkip(boolean enable);

    public native boolean isFrameHalfEnabled();
    public native void enableFrameHalf(boolean enable);

    public native boolean isNoDisplayEnabled();
    //隐藏当前的视频图传,true
    public native void enableNoDisplay(boolean enable);

    public native boolean isAudioDisabled();
    public native void disableAudio(boolean disable);

    public native boolean isFrameThreadEnabled();
    public native void enableFrameThread(boolean enable);

    public native boolean isMultiEnabled();
    public native void enableMulti(boolean enable);

    public native boolean isLiveEnabled();
    public native void enableLive(boolean enable);

    // status
    public native int getBitrate();

    public native int getFramerate();

    public native int getFramerateSkipped();

    // play control
    public native boolean start(String filename);

    public native void stop();

    public native void pause();

    public native void resume();

    public native int getPlayState();// player state: 0 = STOPPED, 1 = PLAYING, 2 = PAUSED

    public native int getDuration();// file total time in seconds

    public native int getCurrentTime();// current playing time

    public native boolean setCurrentTime(int timeInSecs);// seek to timeInSecs

    // video analyze
    public native void drawRect(float x1, float y1, float x2, float y2);// to drag a rect in surfaceview
    public native void startTrack(float x1, float y1, float x2, float y2);
    public native int getFrameWidth();// the width of image to analyze
    public native int getFrameHeight();// the height of image to analyze
    public native void setCurrentPitch(float pitch);
    public native void setCurrentHeight(float height);
    public native void stopTrack();

    public boolean videoplayer_started = false;
    /**
     * 下载时候标签 正在下载：1  已下载完：2
     */
    public boolean SHARE_DOWNLAOD_TAG = false;

    /** Load jni .so on initialization */
    static {
        System.loadLibrary("androidvideoplayerjni");
    }
}

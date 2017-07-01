
package com.hubsan.swifts.activitis.bean;

/**
 * 
 * Description 
 *  摇杆数据
 * @author ShengKun.Cheng
 * @date  2015年11月25日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
public class Rocker {
    
    //==============================================
    //     h306s
    //==============================================
    /**油门*/
    public int spd;
    
    /**左右转向*/
    public int lr;
    
    /**左右转向微调*/
    public int lrSpine;
    
    /**前后*/
    public int mSb;
    
    /**侧飞*/
    public int mSide;
    
    /**前后微调*/
    public int fbSpine;
    
    /**侧飞微调*/
    public int sideSpine;
    
    /**前后发送的最终参数*/
    public int fb;
    
    /**侧飞发送的最终参数*/
    public int side;
    
    //==============================================================
    // h306s 功能按钮
    // mHaveOrNoHead = "0", mCamera = "0", mVideo = "0", mCalibration = "0", mStop = "0", mLanding = "0", mStart = "0", mRolling = "0";
    //==============================================================
    /**有头无头*/
    public int mHaveOrNoHead;
    /** 照相*/
    public int mCamera;
    /**录像*/
    public int mVideo;
    /**校验*/
    public int mCalibration;
    /**急停*/
    public int mStop;
    /**一键降落*/
    public int mLanding;
    /**一键起飞*/
    public int mStart;
    /**翻滚*/
    public int mRolling;
    

}

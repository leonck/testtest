
package com.hubsan.joystick.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

import com.hubsan.joystick.R;


import java.io.InputStream;

/**
 * Description
 * 虚拟摇杆
 *
 * @author ShengKun.Cheng
 * @date 2015年11月10日
 * @see [class/class#method]
 * @since [product/model]
 */
public class JoystickView extends View {

    public static final int INVALID_POINTER_ID = -1;

    String TAG = "JoystickView";

    private Paint bgPaint;

    private Paint handlePaint;

    private int innerPadding;

    private int bgRadius;

    private int handleRadius;

    private int movementRadius;

    private int handleInnerBoundaries;

    private JoystickMovedListener moveListener;

    private JoystickClickedListener clickListener;

    //# of pixels movement required between reporting to the listener
    private float moveResolution;

    private boolean yAxisInverted;

    private boolean autoReturnToCenter;

    //Max range of movement in user coordinate system
    public final static int CONSTRAIN_BOX = 0;

    public final static int CONSTRAIN_CIRCLE = 1;

    private int movementConstraint;

    private float movementRange;

    public final static int COORDINATE_CARTESIAN = 0; //Regular cartesian coordinates

    public final static int COORDINATE_DIFFERENTIAL = 1; //Uses polar rotation of 45 degrees to calc differential drive paramaters

    private int userCoordinateSystem;

    //Records touch pressure for click handling
    private float touchPressure;

    private boolean clicked;

    private float clickThreshold;

    //Last touch point in view coordinates
    private int pointerId = INVALID_POINTER_ID;

    private float touchX, touchY;

    //Last reported position in view coordinates (allows different reporting sensitivities)
    private float reportX, reportY;

    //Handle center in view coordinates
    private float handleX, handleY;

    //Center of the view in view coordinates
    private int cX, cY;

    //Size of the view in view coordinates
    private int dimX, dimY;

    //Cartesian coordinates of last touch point - joystick center is (0,0)
    private float cartX, cartY;

    //Polar coordinates of the touch point from joystick center
    private double radial;

    private double angle;

    //User coordinates of last touch point
    private float userX, userY;

    //Offset co-ordinates (used when touch events are received from parent's coordinate origin)
    private int offsetX;

    private int offsetY;

    private boolean handleVisible = false;

    private boolean isSencor;

    /**
     * 背景
     */
    public Bitmap mBmpRockerBg;
    /**
     * 小圆
     */
    public Bitmap mBmpRockerBtn;


    // =========================================
    // Constructors
    // =========================================
    public JoystickView(Context context) {
        super(context);
        initJoystickView();
    }

    public JoystickView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initJoystickView();
    }

    public JoystickView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        initJoystickView();
    }

    // =========================================
    // Initialization
    // =========================================

    private void initJoystickView() {

        setFocusable(true);
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(Color.TRANSPARENT);
        bgPaint.setStrokeWidth(1);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        handlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        handlePaint.setColor(Color.TRANSPARENT);
        handlePaint.setStrokeWidth(1);
        handlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //压缩，用于节省BITMAP内存空间--解决BUG的关键步骤
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;    //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰
//        mBmpRockerBg = BitmapFactory.decodeResource(getResources(), R.drawable.rocker_bkg, opts);
//        mBmpRockerBtn = BitmapFactory.decodeResource(getResources(), R.drawable.rocker_small);
        mBmpRockerBg = readBitMap(getContext(), R.drawable.rocker_bkg);
        mBmpRockerBtn = readBitMap(getContext(), R.drawable.rocker_small);

//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rocker_ball);
//        bitmap = Bitmap.createScaledBitmap(bitmap,400 , 400, false);
        innerPadding = 20;
        setMovementRange(10);
        setMoveResolution(1.0f);
        setClickThreshold(0.4f);
        setYAxisInverted(true);
        setUserCoordinateSystem(COORDINATE_CARTESIAN);
        setAutoReturnToCenter(true);
    }

    public void setAutoReturnToCenter(boolean autoReturnToCenter) {

        this.autoReturnToCenter = autoReturnToCenter;
    }

    public void setUserCoordinateSystem(int userCoordinateSystem) {

        if (userCoordinateSystem < COORDINATE_CARTESIAN || movementConstraint > COORDINATE_DIFFERENTIAL)
            Log.e(TAG, "invalid value for userCoordinateSystem");
        else
            this.userCoordinateSystem = userCoordinateSystem;
    }

    public void setMovementConstraint(int movementConstraint) {

        if (movementConstraint < CONSTRAIN_BOX || movementConstraint > CONSTRAIN_CIRCLE)
            Log.e(TAG, "invalid value for movementConstraint");
        else
            this.movementConstraint = movementConstraint;
    }

    public void setYAxisInverted(boolean yAxisInverted) {

        this.yAxisInverted = yAxisInverted;
    }

    /**
     * Set the pressure sensitivity for registering a click
     *
     * @param clickThreshold threshold 0...1.0f inclusive. 0 will cause clicks to never be reported, 1.0 is a very hard click
     */
    public void setClickThreshold(float clickThreshold) {

        if (clickThreshold < 0 || clickThreshold > 1.0f)
            Log.e(TAG, "clickThreshold must range from 0...1.0f inclusive");
        else
            this.clickThreshold = clickThreshold;
    }

    public void setMovementRange(float movementRange) {

        this.movementRange = movementRange;
    }


    public void setMoveResolution(float moveResolution) {

        this.moveResolution = moveResolution;
    }

    // =========================================
    // Public Methods
    // =========================================

    public void setOnJostickMovedListener(JoystickMovedListener listener) {

        this.moveListener = listener;
    }

    public void setOnJostickClickedListener(JoystickClickedListener listener) {

        this.clickListener = listener;
    }

    // =========================================
    // Drawing Functionality
    // =========================================

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // Here we make sure that we have a perfect circle
        int measuredWidth = measure(widthMeasureSpec);
        int measuredHeight = measure(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);

        int d = Math.min(getMeasuredWidth(), getMeasuredHeight());
        dimX = d;
        dimY = d;
        cX = d / 3;
        cY = d / 3;
        bgRadius = dimX / 3 - innerPadding;
        handleRadius = (int) (d * 0.1);
        handleInnerBoundaries = handleRadius;
        movementRadius = Math.min(cX, cY) - handleInnerBoundaries;
    }

    private int measure(int measureSpec) {

        int result = 0;
        // Decode the measurement specifications.
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.UNSPECIFIED) {
            // Return a default size of 200 if no bounds are specified.
            result = 200;
        } else {
            // As you want to fill the available space
            // always return the full available bounds.
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        if (handleVisible) {
            handleX = touchX + cX;
            handleY = touchY + cY;
            canvas.drawBitmap(mBmpRockerBg, null,
                    new Rect((int) (cX - bgRadius),
                            (int) (cY - bgRadius),
                            (int) (cX + bgRadius),
                            (int) (cY + bgRadius)),
                    null);
            canvas.drawBitmap(mBmpRockerBtn, null,
                    new Rect((int) (handleX - bgRadius),
                            (int) (handleY - bgRadius),
                            (int) (handleX + bgRadius),
                            (int) (handleY + bgRadius)),
                    null);

        }
        canvas.restore();
    }

    // Constrain touch within a box
    private void constrainBox() {
        touchX = Math.max(Math.min(touchX, movementRadius), -movementRadius);
        touchY = Math.max(Math.min(touchY, movementRadius), -movementRadius);
    }

    // Constrain touch within a circle
    private void constrainCircle() {

        float diffX = touchX;
        float diffY = touchY;
        double radial = Math.sqrt((diffX * diffX) + (diffY * diffY));
        if (radial > movementRadius) {
            touchX = (float) ((diffX / radial) * movementRadius);
            touchY = (float) ((diffY / radial) * movementRadius);
        }
    }

    public void setPointerId(int id) {

        this.pointerId = id;
    }

    public int getPointerId() {

        return pointerId;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE: {
//                LogX.e("***********ACTION_MOVE*************");
                return processMoveEvent(ev);
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
//                LogX.e("***********ACTION_UP*************");
                if (isPointerValid()) {
                    invalidate();
                    postInvalidate();
                    return processRelease();
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                if (isPointerValid()) {
                    handleVisible = false;
                    final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    final int pointerId = ev.getPointerId(pointerIndex);
                    if (pointerId == this.pointerId) {
                        return processRelease();
                    }
                }
                break;
            }
            case MotionEvent.ACTION_DOWN: {
//                LogX.e("***********ACTION_DOWN*************");
                if (pointerId == INVALID_POINTER_ID) {
                    processFirstTouch(ev);
                    int x = (int) ev.getX();
                    if (x >= offsetX && x < offsetX + dimX) {
                        setPointerId(ev.getPointerId(0));
                        return true;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
//                LogX.e("***********ACTION_POINTER_DOWN*************");
                if (pointerId == INVALID_POINTER_ID) {
                    processFirstTouch(ev);
                    final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    final int pointerId = ev.getPointerId(pointerIndex);
                    try {
                        int x = (int) ev.getX(pointerId);
                        if (x >= offsetX && x < offsetX + dimX) {
                            setPointerId(pointerId);
                            return true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
            }
        }
        return false;
    }

    /**
     * Description
     *
     * @return 手指离开
     * @see [class/class#field/class#method]
     */
    private boolean processRelease() {
        this.pointerId = INVALID_POINTER_ID;
        handleVisible = false;
        if (moveListener != null) {
            userX = true ? 0 : userX;
            userY = true ? 0 : userY;
            moveListener.OnMoved(userX, userY);
        }
        return true;
    }

    private void processFirstTouch(MotionEvent ev) {

        cX = (int) ev.getX();
        cY = (int) ev.getY();
        touchX = 0;
        touchY = 0;
        handleVisible = true;
        //通知当前组件重绘自己
        invalidate();
    }

    private boolean isPointerValid() {

        return pointerId != INVALID_POINTER_ID;
    }

    //移动是产生新数据
    private boolean processMoveEvent(MotionEvent ev) {

        if (isPointerValid()) {
            final int pointerIndex = ev.findPointerIndex(pointerId);
            // Translate touch position to center of view
            float x = ev.getX(pointerIndex);
            float y = ev.getY(pointerIndex);
//            Utils.doLog("processMoveEvent x :"+x +" processMoveEvent y :"+y);
            touchX = x - cX - offsetX;
            touchY = y - cY - offsetY;
            reportOnMoved();
            invalidate();
            touchPressure = ev.getPressure(pointerIndex);
            reportOnPressure();
            return true;
        }
        return false;
    }

    private void reportOnMoved() {

        if (movementConstraint == CONSTRAIN_CIRCLE) {
            constrainCircle();
        } else {
            constrainBox();
        }
        calcUserCoordinates();
        if (moveListener != null) {
            boolean rx = Math.abs(touchX - reportX) >= moveResolution;
            boolean ry = Math.abs(touchY - reportY) >= moveResolution;
            if (rx || ry) {
                this.reportX = touchX;
                this.reportY = touchY;
//                Utils.doLog("reportOnMoved x :"+userX +" reportOnMoved y :"+userY);
                moveListener.OnMoved(userX, userY);
            }
        }
    }

    private void calcUserCoordinates() {

        //First convert to cartesian coordinates
        cartX = (touchX / movementRadius * movementRange);
        cartY = (touchY / movementRadius * movementRange);

        radial = Math.sqrt((cartX * cartX) + (cartY * cartY));
        angle = Math.atan2(cartY, cartX);
        //Invert Y axis if requested
        if (!yAxisInverted) {
            cartY *= -1;
        }
        if (userCoordinateSystem == COORDINATE_CARTESIAN) {
            userX = cartX;
            userY = cartY;
        } else if (userCoordinateSystem == COORDINATE_DIFFERENTIAL) {
            userX = cartY + cartX / 4;
            userY = cartY - cartX / 4;

            if (userX < -movementRange)
                userX = (int) -movementRange;
            if (userX > movementRange)
                userX = (int) movementRange;

            if (userY < -movementRange)
                userY = (int) -movementRange;
            if (userY > movementRange)
                userY = (int) movementRange;
        }

    }

    //Simple pressure click
    private void reportOnPressure() {

        if (clickListener != null) {
            if (clicked && touchPressure < clickThreshold) {
                clickListener.OnReleased();
                this.clicked = false;
                invalidate();
            } else if (!clicked && touchPressure >= clickThreshold) {
                clicked = true;
                clickListener.OnClicked();
                invalidate();
                performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            }
        }
    }

    public void setTouchOffset(int x, int y) {

        offsetX = x;
        offsetY = y;
    }


    public void recycleBitmap() {
        if (!mBmpRockerBg.isRecycled()) {
            mBmpRockerBg.recycle();
            System.gc();
        }
        if (!mBmpRockerBtn.isRecycled()) {
            mBmpRockerBtn.recycle();
            System.gc();
        }
    }

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
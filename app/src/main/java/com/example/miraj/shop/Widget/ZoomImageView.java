package com.example.miraj.shop.Widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class ZoomImageView extends View {
    private Bitmap image;
    private Paint paint = new Paint();

    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    private float scaleFactor;

    public ZoomImageView(Context context) {
        super(context);
        init(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureListener());

        paint.setFilterBitmap(true);
        paint.setDither(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect dst = new Rect(0, 0, getScaledWidth(), getScaledHeight());
        canvas.drawBitmap(image, null, dst, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);

        return true;
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.image = bitmap;
        scaleFactor = 1;
    }

    private int getScaledWidth() {
        return (int)(image.getWidth() * scaleFactor);
    }

    private int getScaledHeight() {
        return (int)(image.getHeight() * scaleFactor);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            scrollBy((int)distanceX, (int)distanceY);
            return true;
        }
    }

    private class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            int newScrollX = (int)((getScrollX() + detector.getFocusX()) * detector.getScaleFactor() - detector.getFocusX());
            int newScrollY = (int)((getScrollY() + detector.getFocusY()) * detector.getScaleFactor() - detector.getFocusY());
            scrollTo(newScrollX, newScrollY);

            invalidate();

            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        }
    }
}

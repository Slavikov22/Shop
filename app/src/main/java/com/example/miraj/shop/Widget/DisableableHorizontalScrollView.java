package com.example.miraj.shop.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class DisableableHorizontalScrollView extends HorizontalScrollView {
    public DisableableHorizontalScrollView(Context context) {
        super(context);
    }

    public DisableableHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisableableHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.isEnabled())
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.isEnabled())
            return super.onTouchEvent(ev);
        else
            return false;
    }
}

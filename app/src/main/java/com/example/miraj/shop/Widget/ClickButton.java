package com.example.miraj.shop.Widget;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ClickButton extends AppCompatButton {
    private static final long MAX_TIME_MILLIS = 1000;

    private long timer;
    private OnMyClickListener listener;

    public ClickButton(Context context) {
        super(context);
    }

    public ClickButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            timer = System.currentTimeMillis();

        if (event.getAction() == MotionEvent.ACTION_UP)
            if (System.currentTimeMillis() - timer < MAX_TIME_MILLIS)
                listener.OnMyClick(this);

        return true;
    }

    public void setOnMyClickListener(OnMyClickListener listener) {
        this.listener = listener;
    }

    public interface OnMyClickListener {
        void OnMyClick(View view);
    }
}

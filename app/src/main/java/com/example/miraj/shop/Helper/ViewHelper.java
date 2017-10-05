package com.example.miraj.shop.Helper;

import android.view.View;
import android.view.ViewGroup;

public class ViewHelper {
    public static void setEnabledAllViews(View v, boolean enabled) {
        v.setEnabled(enabled);
        v.setFocusable(enabled);

        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++)
                setEnabledAllViews(vg.getChildAt(i), enabled);
        }
    }
}

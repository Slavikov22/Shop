package com.example.miraj.shop.Helper;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class BitmapHelper {
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}

package com.example.miraj.shop.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.example.miraj.shop.Activity.CategoriesActivity;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.ProductProvider;
import com.example.miraj.shop.R;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SpecialOfferService extends IntentService {
    NotificationManager nm;
    List<Product> products;
    int lastId;

    public SpecialOfferService() {
        super("SpecialOfferService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        products = ProductProvider.getProducts();

        lastId = 2;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sendNotification(lastId);
            lastId += 1;
        }
    }

    void sendNotification(int startId) {
        int pos = new Random().nextInt(products.size());
        Product product = products.get(pos);

        Intent intent = new Intent(this, CategoriesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("New special offer")
                .setContentText("Discounts for " + product.getName())
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentIntent(pendingIntent)
                .build();

        nm.notify(startId, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

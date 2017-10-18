package com.example.miraj.shop.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.miraj.shop.R;

import java.util.concurrent.TimeUnit;

public class StateService extends IntentService {
    private static final String ACTION = "action";
    private static final String PARAM_STATE = "state";

    public StateService() {
        super("StateService");
    }


    public static void startAction(Context context, String state) {
        Intent intent = new Intent(context, StateService.class);
        intent.setAction(ACTION);
        intent.putExtra(PARAM_STATE, state);
        context.startService(intent);
    }

    public static void stopAction(Context context) {
        Intent intent = new Intent(context, StateService.class);
        context.stopService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (true) {
            final String action = intent.getAction();
            if (ACTION.equals(action)) {
                final String state = intent.getStringExtra(PARAM_STATE);
                handleAction(state);
            }

            try {
                TimeUnit.SECONDS.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAction(String state) {
        Notification notification = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Shop state")
                .setContentText(state)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setOngoing(true)
                .build();

        startForeground(1488, notification);
    }
}

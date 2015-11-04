package com.steptime.notification.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.steptime.notification.NotificationUtil;

public class NotificationService extends Service {

    private NotificationBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new NotificationBinder();
        stopForeground(true);// 这个不确定是否有作用
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class NotificationBinder extends Binder {
        public void start() {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    NotificationUtil.createVibrationFlashNotification(getBaseContext(), "震动通知标题", "内容内容内容内容内容内容", null);
                    stopSelf();// 停掉服务自身
                }
            }, 5000);
        }
    }
}
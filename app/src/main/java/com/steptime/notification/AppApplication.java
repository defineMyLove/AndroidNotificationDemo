package com.steptime.notification;

import android.app.Application;
import android.content.Context;

/**
 * Created by N002 on 15/11/2.
 */
public class AppApplication extends Application {
    private  static AppApplication appApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
    }

    public static AppApplication getApp() {
        return appApplication;
    }
}

package com.steptime.notification.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;

import com.steptime.notification.NotificationUtil;

/**
 * Created by zhangxl on 15/11/4.
 */
public class TaskService extends IntentService {
    public static final String ACTION_PUB_TASK = "com.steptime.ACTION_PUB_TASK";
    private static final String SERVICE_NAME = "TaskService";
    private Handler handler;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TaskService(String name) {
        super(name);
    }

    public TaskService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if (handler == null)
            handler = new Handler();
        if (ACTION_PUB_TASK.equals(action)) {
            publicTask();
        }
    }

    private void publicTask() {
        final int id = 100;
        NotificationUtil.createTextNotification(id, getBaseContext(), "发布任务", "正在发布任务...", null);
        //模拟网路发送数据到服务器5秒
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                NotificationUtil.createTextNotification(id, getBaseContext(), "发布任务", "发布任务成功", null);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NotificationUtil.cancel(id);
                        stopSelf();
                    }
                }, 3000);
            }
        }, 3000);
    }
}

package com.steptime.notification.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import com.steptime.notification.AppApplication;
import com.steptime.notification.NotificationUtil;
import com.steptime.notification.R;
import com.steptime.notification.RemoveControlWidget;
import com.steptime.notification.service.DownloadService;
import com.steptime.notification.service.NotificationService;
import com.steptime.notification.service.TaskService;

public class NotificationActivity extends Activity implements View.OnClickListener {

    /**
     * 通知索引
     */
    private int indexNotification;
    private ReceiveTaskBroadcastReciver mBroadcastReciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        findViewById(R.id.btn_simple).setOnClickListener(this);
        findViewById(R.id.btn_vibration).setOnClickListener(this);
        findViewById(R.id.btn_vibration_flash).setOnClickListener(this);
        findViewById(R.id.btn_progress).setOnClickListener(this);
        findViewById(R.id.btn_ing).setOnClickListener(this);
        findViewById(R.id.btn_extend_layout).setOnClickListener(this);
        findViewById(R.id.btn_more_action).setOnClickListener(this);
        findViewById(R.id.btn_custom_layout).setOnClickListener(this);
        findViewById(R.id.btn_manager).setOnClickListener(this);
        findViewById(R.id.btn_auto_clear).setOnClickListener(this);
        findViewById(R.id.btn_normal_activity).setOnClickListener(this);
        findViewById(R.id.btn_special_activity).setOnClickListener(this);
        findViewById(R.id.btn_service_start).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_state).setOnClickListener(this);
        findViewById(R.id.btn_more).setOnClickListener(this);
        findViewById(R.id.btn_clear_all).setOnClickListener(this);


    }
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RemoveControlWidget.ACTION_NEXT);
        intentFilter.addAction(RemoveControlWidget.ACTION_PLAY);
        intentFilter.addAction(RemoveControlWidget.ACTION_PREVIOUS);
        if (mBroadcastReciver == null) {
            mBroadcastReciver = new ReceiveTaskBroadcastReciver();
        }
        registerReceiver(mBroadcastReciver, intentFilter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReciver);
    }
    
    private class ReceiveTaskBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(RemoveControlWidget.ACTION_NEXT)) {
                Toast.makeText(NotificationActivity.this, "下一曲", Toast.LENGTH_SHORT);
            }else if(action.equals(RemoveControlWidget.ACTION_PLAY)) {
                Toast.makeText(NotificationActivity.this, "播放", Toast.LENGTH_SHORT);
            }else if(action.equals(RemoveControlWidget.ACTION_PREVIOUS)) {
                Toast.makeText(NotificationActivity.this, "上一曲", Toast.LENGTH_SHORT);
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_simple:
                NotificationUtil.createTextNotification(this, "简单通知标题", "内容内容内容内容内容内容", null);
                break;
            case R.id.btn_clear_all:
                NotificationUtil.cancelAll();
                break;
            case R.id.btn_vibration:
                NotificationUtil.createVibrationNotification(this, "震动通知标题", "内容内容内容内容内容内容", null);
                break;
            case R.id.btn_vibration_flash:
                NotificationUtil.createVibrationFlashNotification(this, "震动通知标题", "内容内容内容内容内容内容", null);
                break;
            case R.id.btn_progress:
                NotificationUtil.createProgressNotification(this, "滚动通知", "内容内容内容内容内容内容", null);
                break;
            case R.id.btn_ing:
                NotificationUtil.createIngNotification(this, "正在进行通知", "内容内容内容内容内容内容", null);
                break;
            case R.id.btn_extend_layout:
                //TODO implement
                break;
            case R.id.btn_more_action:
                NotificationUtil.createAction(this);
                break;
            case R.id.btn_custom_layout:
                NotificationUtil.createCustomNotification(this, "正在进行通知", "内容内容内容内容内容内容", null);
                break;
            case R.id.btn_manager:
                indexNotification++;
                NotificationUtil.createOneTypeNotification(100,this,  indexNotification+"条信息","内容内容内容内容内容内容", null);
                break;
            case R.id.btn_auto_clear:
                NotificationUtil.createAutoClearNotification(this, "震动通知标题", "内容内容内容内容内容内容", null);
                break;
            case R.id.btn_normal_activity:
                NotificationUtil.createNormalLevelNotification(this, "正在进行通知", "内容内容内容内容内容内容", null);
                break;
            case R.id.btn_special_activity:
                NotificationUtil.createSpecialNotification(this, "正在进行通知", "内容内容内容内容内容内容", null);
                break;
            case R.id.btn_service_start:
                Toast.makeText(this, "5秒后启动通知", Toast.LENGTH_SHORT).show();
                openNotificationService();
                break;
            case R.id.btn_update:
                Toast.makeText(this, "升级通知",Toast.LENGTH_SHORT).show();
                openDownLoadService();
                break;
            case R.id.btn_state:
                taskService();
                break;
            case R.id.btn_more:
                indexNotification++;
                NotificationUtil.createOneTypeNotification(100, this, indexNotification + "条信息", "内容内容内容内容内容内容", null);
                break;
        }
    }

    /**
     * 场景1:常用于定时提醒等功能。
     * 5秒后创建一个通知
     */
    public  void openNotificationService() {
        ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                NotificationService.NotificationBinder binder = (NotificationService.NotificationBinder) service;
                binder.start();
            }
        };
        Intent intent = new Intent(NotificationActivity.this, NotificationService.class);
        startService(intent);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }


    /**
     * 场景2:升级通知
     */
    public  void openDownLoadService() {
        ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                binder.start();
            }
        };
        Intent intent = new Intent(NotificationActivity.this, DownloadService.class);
        intent.putExtra(DownloadService.BUNDLE_KEY_DOWNLOAD_URL,"http://dd.myapp.com/16891/E043786A101ABBEE0E88AB3C0D7AE5B7.apk");
        intent.putExtra(DownloadService.BUNDLE_KEY_TITLE,"QQ音乐");
        startService(intent);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 场景3：向服务器提交数据，通知用于显示提交数据的状态和结果[成功或失败]
     */
    public void taskService(){
        Intent intent = new Intent(TaskService.ACTION_PUB_TASK);
        intent.setPackage(AppApplication.getApp().getPackageName());
        startService(intent);
    }
}
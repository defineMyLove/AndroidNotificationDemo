package com.steptime.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.steptime.notification.ui.AboutActivity;
import com.steptime.notification.ui.MainActivity;

/**
 * Created by zxl on 15/5/9.
 * 系统通知栏
 * <br>
 * note:
 * A Notification object must contain the following:
 * A small icon, set by setSmallIcon()
 * A title, set by setContentTitle()
 * Detail text, set by setContentText()
 */
public class NotificationUtil {
    //声音(手机默认的提醒声音)
    static Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    static NotificationManager mNotificationManager =
            (NotificationManager) AppApplication.getApp().getSystemService(Context.NOTIFICATION_SERVICE);

    /**
     * 创建基本的通知
     *
     * @param context
     * @param title
     * @param content
     * @param icon
     * @return
     */
    public static Notification createNotification(Context context, String title, String content, Integer icon) {
        icon = icon == null ? R.mipmap.ic_launcher : icon;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(icon)
                        .setContentTitle(title)  //标题
                        .setTicker(content)  //状态条上进行滚动
                        .setAutoCancel(true)
                        .setOngoing(false)  //是否正在进行
                        .setOnlyAlertOnce(false) //是否只提醒一次
                                //.setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND) //震动、灯光
                        .setContentText(content);//内容
        Notification notification = mBuilder.build();
        return notification;
    }

    /**
     * 创建简单的文本通知栏(包含图标，标题和内容)
     *
     * @return 通知栏ID
     */
    public static Notification createTextNotification(Context context, String title, String content, Integer icon) {
        Notification notification = createNotification(context, title, content, icon);
        int id = RandomUtil.getNumber6FromRandom();
        mNotificationManager.notify(id, notification);
        return notification;
    }

    public static Notification createTextNotification(Integer id,Context context, String title, String content, Integer icon) {
        Notification notification = createNotification(context, title, content, icon);
        mNotificationManager.notify(id, notification);
        return notification;
    }

    /**
     * 震动效果通知
     *
     * @param context
     * @param title
     * @param content
     * @param icon
     * @return
     */
    public static Notification createVibrationNotification(Context context, String title, String content, Integer icon) {
        Notification notification = createNotification(context, title, content, icon);
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
        int id = RandomUtil.getNumber6FromRandom();
        mNotificationManager.notify(id, notification);
        return notification;
    }

    /**
     * 震动闪灯效果通知
     *
     * @param context
     * @param title
     * @param content
     * @param icon
     * @return
     */
    public static Notification createVibrationFlashNotification(Context context, String title, String content, Integer icon) {
        Notification notification = createNotification(context, title, content, icon);
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        int id = RandomUtil.getNumber6FromRandom();
        mNotificationManager.notify(id, notification);
        return notification;
    }


    public static Notification createIngNotification(Context context, String title, String content, Integer icon) {
        Notification notification = createNotification(context, title, content, icon);
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        notification.flags = Notification.FLAG_ONGOING_EVENT; // 通知放置在正在运行
        int id = RandomUtil.getNumber6FromRandom();
        mNotificationManager.notify(id, notification);
        return notification;
    }

    public static Notification createAutoClearNotification(Context context, String title, String content, Integer icon) {
        Notification notification = createNotification(context, title, content, icon);
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        notification.flags = Notification.FLAG_AUTO_CANCEL;  // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
        int id = RandomUtil.getNumber6FromRandom();
        mNotificationManager.notify(id, notification);
        return notification;
    }

    public static Notification createCustomNotification(Context context, String title, String content, Integer icon) {
        Notification notification = createNotification(context, title, content, icon);
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        RemoteViews contentView = new RemoteViews(context.getPackageName(),R.layout.notification_mutimedia);
        Intent intent = new Intent(context,AboutActivity.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 100,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        contentView.setOnClickPendingIntent(R.id.songer_pic, pendingIntent);
        notification.contentView = contentView;
        int id = RandomUtil.getNumber6FromRandom();
        mNotificationManager.notify(id, notification);
        return notification;
    }


    public static Notification createOneTypeNotification(int id,Context context, String title, String content, Integer icon) {
        Notification notification = createNotification(context, title, content, icon);
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        notification.flags = Notification.FLAG_ONGOING_EVENT; // 通知放置在正在运行
        mNotificationManager.notify(id, notification);
        return notification;
    }

    /**
     * 滚动条通知
     *
     * @param context
     * @param title
     * @param content
     * @param icon
     * @return
     */
    public static Notification createProgressNotification(final Context context, String title, String content, Integer icon) {
        final Notification mNotification = createNotification(context, title, content, icon);
        // 放置在"正在运行"栏目中
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
        RemoteViews contentView = new RemoteViews(context.getPackageName(),
                R.layout.download_notification_show);
        contentView.setTextViewText(R.id.tv_download_state,title+" 0%");
        // 指定个性化视图
        mNotification.contentView = contentView;
        final int NOTIFY_ID = 100;
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                    case 2:
                        mNotificationManager.cancel(NOTIFY_ID);// 取消通知
                        break;
                    case 1:
                        int rate = msg.arg1;
                        if (rate < 100) {
                            RemoteViews contentview = mNotification.contentView;
                            contentview.setTextViewText(R.id.tv_download_state,rate+ "%" );
                            contentview.setProgressBar(R.id.pb_download, 100, rate,
                                    false);
                        } else {
                            // 下载完毕后变换通知形式
                            mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                            mNotification.contentView = null;
                            mNotification.setLatestEventInfo(context, "滚动结束",
                                    "内容内容内容内容内容", null);
                        }
                        mNotificationManager.notify(NOTIFY_ID, mNotification);
                        break;
                }
            }
        };
        Notification notification = createNotification(context, title, content, icon);
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        int id = RandomUtil.getNumber6FromRandom();
        mNotificationManager.notify(id, notification);

        new Thread(new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if (count <= 100) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = count;
                    mHandler.sendMessage(msg);
                    count++;
                    //use Handler to control the time
                    mHandler.postDelayed(this, 200);  //添加间隔时间，防止速度过快
                }
            }
        }).start();
        return notification;
    }

    /**
     * 创建链接通知栏(包含图标，标题和内容,以及点击事件)
     *
     * @return 通知栏ID
     */
    public static int createLinkNotification(Context context, String title, String content, Integer icon, Class intentClass) {
        int id = RandomUtil.getNumber6FromRandom();
        icon = icon == null ? R.mipmap.ic_launcher : icon;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(icon)
                        .setContentTitle(title)  //标题
                        .setTicker(content)  //状态条上进行滚动
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .setOnlyAlertOnce(true)
                        .setContentText(content);//内容
        mBuilder.setSound(alarmSound);
        Intent intent = new Intent(context, intentClass);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(id, mBuilder.build());
        return id;
    }


    /**
     * 创建链接通知栏(包含图标，标题和内容,以及点击事件)
     *
     * @return 通知栏ID
     */
    public static int createLinkNotification(Context context, String title, String content, Integer icon, Intent intent) {
        int id = RandomUtil.getNumber6FromRandom();
        icon = icon == null ? R.mipmap.ic_launcher : icon;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(icon)
                        .setContentTitle(title)  //标题
                        .setTicker(content)  //状态条上进行滚动
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .setOnlyAlertOnce(true)
                        .setContentText(content);//内容
        mBuilder.setSound(alarmSound);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(id, mBuilder.build());
        return id;
    }

    public static void createAction(Context context) {
        int id = RandomUtil.getNumber6FromRandom();
        NotificationCompat.Builder
                mBuilder = new NotificationCompat.Builder(context);
        Intent resultIntent = new Intent(context, AboutActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(AboutActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent)
                // Show controls on lock screen even when user hides sensitive content.
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher)
                        // Add media control buttons that invoke intents in your media service
                .addAction(R.mipmap.ic_launcher, "Previous", resultPendingIntent) // #0
                .addAction(R.mipmap.ic_launcher, "Pause", resultPendingIntent)  // #1
                .addAction(R.mipmap.ic_launcher, "Next", resultPendingIntent)     // #2
                .setContentTitle("Wonderful music")
                .setContentText("My Awesome Band")
                .setAutoCancel(true)
                .setOngoing(false)
                .setOnlyAlertOnce(true)
                .setTicker("My Awesome Band")  //状态条上进行滚动
                .build();
        mNotificationManager.notify(id, mBuilder.build());
    }

    /**
     * 删除指定ID的通知栏
     *
     * @param id
     */
    public static void cancel(int id) {
        mNotificationManager.cancel(id);
    }


    /**
     * 删除所有的通知栏
     */
    public static void cancelAll() {
        mNotificationManager.cancelAll();
    }

    /**
     * 如果您在 Gmail 中撰写消息时点击了一封电子邮件的通知，则会立即转到该电子邮件。 触摸“返回”会依次转到收件箱和主屏幕，而不是转到您在撰写的邮件。
     * @param context
     * @param title
     * @param content
     * @param icon
     */
    public static void createNormalLevelNotification(Context context, String title, String content, Integer icon) {
        Intent mainIntent = new Intent(context, MainActivity.class);
        Intent resultIntent = new Intent(context, AboutActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        //stackBuilder.addParentStack(AboutActivity.class);
        stackBuilder.addNextIntent(mainIntent);  //第一级
        stackBuilder.addNextIntent(resultIntent);//第二级
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        Notification notification = createNotification(context, title, content, icon);
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        notification.contentIntent = resultPendingIntent;
        int id = RandomUtil.getNumber6FromRandom();
        mNotificationManager.notify(id, notification);

    }

    /**
     * 仅当从通知中启动时，用户才会看到此 Activity。
     * [由于启动的 Activity 不是应用 Activity 流程的一部分，因此无需创建返回栈。]
     * @param context
     * @param title
     * @param content
     * @param icon
     */
    public static void createSpecialNotification(Context context, String title, String content, Integer icon) {
        Intent intent = new Intent(context, SpecialNotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //设置为在新的空任务中启动
        int id = RandomUtil.getNumber6FromRandom();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = createNotification(context, title, content, icon);
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        // Sets the Activity to start in a new, empty task

        notification.contentIntent = pendingIntent;
        mNotificationManager.notify(id, notification);

    }


}

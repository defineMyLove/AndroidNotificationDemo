package com.steptime.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class RemoveControlWidget extends RemoteViews {
    private final Context mContext;

    public static final String ACTION_PLAY = "com.steptime.app.ACTION_PLAY";

    public static final String ACTION_PREVIOUS = "com.steptime.app.ACTION_PREVIOUS";

    public static final String ACTION_NEXT = "com.steptime.app.ACTION_NEXT";

    public RemoveControlWidget(Context context, String packageName, int layoutId) {
        super(packageName, layoutId);
        mContext = context;
        Intent intent = new Intent(ACTION_PLAY);
        PendingIntent pendingIntent = PendingIntent.getService(mContext.getApplicationContext(), 100,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        setOnClickPendingIntent(R.id.paly_pause_music, pendingIntent);
        Intent preintent = new Intent(ACTION_PREVIOUS);
        PendingIntent prependingIntent = PendingIntent.getService(mContext.getApplicationContext(), 101,
                preintent, PendingIntent.FLAG_UPDATE_CURRENT);
        setOnClickPendingIntent(R.id.previous_control, prependingIntent);
        Intent nextintent = new Intent(ACTION_NEXT);
        PendingIntent nextpendingIntent = PendingIntent.getService(mContext.getApplicationContext(), 102,
                nextintent, PendingIntent.FLAG_UPDATE_CURRENT);
        setOnClickPendingIntent(R.id.next_control, nextpendingIntent);
    }
}